package cn.webbus.spring;

import cn.webbus.util.FileUtil;
import com.alibaba.fastjson.JSON;
import org.aopalliance.aop.Advice;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultSpringPluginFactory implements ApplicationContextAware {


    private static final String configPath = "E:\\config\\plugins.config";
    private Map<String, PluginConfig> configs = new HashMap<String, PluginConfig>();
    private Map<String, Advice> adviceCache = new HashMap<String, Advice>();
    private ApplicationContext applicationContext;

    public void activePlugin(String pluginId) {
        if (!configs.containsKey(pluginId)) {
            throw new RuntimeException(String.format("指定插件不存在 id = %s", pluginId));
        }

        PluginConfig config = configs.get(pluginId);

        config.setActive(true);

        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(name);
            if (bean == this) {
                continue;
            }
            if (!(bean instanceof Advised)) {
                continue;
            }
            if (findAdvice((Advised) bean, config.getClassName()) != null) {
                continue;
            }
            Advice advice = null;
            try {
                advice = buildAdvice(config);
                ((Advised) bean).addAdvice(advice);
            } catch (Exception e) {
                throw new RuntimeException("安装失败！", e);
            }
        }
    }

    private Advice findAdvice(Advised bean, String className) {
        for (Advisor advisor : bean.getAdvisors()) {
            if (advisor.getAdvice().getClass().getName().equals(className)) {
                return advisor.getAdvice();
            }
        }
        return null;
    }


    public Advice buildAdvice(PluginConfig config) throws Exception {
        if (adviceCache.containsKey(config.getClassName())) {
            return adviceCache.get(config.getClassName());
        }
        //获取本地待加载的jar插件包路径
        URL targetUrl = new URL(config.getJarRemoteUrl());
        //获取当前正在运行的项目，加载了那些jar包（比如spring-）core.jar
        URLClassLoader loader = (URLClassLoader) getClass().getClassLoader();
        boolean isloader = false;
        for (URL url : loader.getURLs()) {
            if (url.equals(targetUrl)) { //判断当前待加载的jar包，是否已经被加载到loader
                isloader = true;
                break;
            }
        }
        if (!isloader) {//如果插件jar没有被加载到loader，则调用add.invoke将jar加载进来
            Method add = URLClassLoader.class.getDeclaredMethod("addURL",new Class[]{URL.class});
            add.setAccessible(true);
            add.invoke(loader, targetUrl);
        }
        //将插件jar包里的cn.webbus.CountTimesPlugin类，创建class对象
        Class<?> adviceClass = loader.loadClass(config.getClassName());//advice
        adviceCache.put(adviceClass.getName(), (Advice) adviceClass.newInstance());//adviceClass对象通过反射创建advice
        return adviceCache.get(adviceClass.getName());

    }

    public void disablePlugin(String pluginId) {
        if (!configs.containsKey(pluginId)) {
            throw new RuntimeException(String.format("指定插件不存在 id = %s", pluginId));
        }
        PluginConfig config = configs.get(pluginId);
        config.setActive(false);
        for (String name : applicationContext.getBeanDefinitionNames()){
            Object bean = applicationContext.getBean(name);
            if(bean instanceof Advised){
                Advice advice = findAdvice((Advised)bean,config.getClassName());
                if(advice != null){
                    ((Advised) bean).removeAdvice(advice);
                }
            }
        }
    }

    public Collection<PluginConfig> flushConfigs() {
        File configFile = new File(configPath);
        String configJson = FileUtil.readAsString(configFile);
        Plugins pluginConfigs = JSON.parseObject(configJson, Plugins.class);
        for (PluginConfig pluginConfig : pluginConfigs.getConfigs()) {
            if (configs.get(pluginConfig.getId()) == null) {
                configs.put(pluginConfig.getId(), pluginConfig);
            }
        }
        return configs.values();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void doBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.toString());
    }
}
