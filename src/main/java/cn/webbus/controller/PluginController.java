package cn.webbus.controller;

import cn.webbus.spring.DefaultSpringPluginFactory;
import cn.webbus.spring.PluginConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Controller
@RequestMapping(value = "/plugin")
public class PluginController {
    @Autowired
    private DefaultSpringPluginFactory pluginFactory;

    //获取插件配置列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getHavePlugins(HttpServletRequest requset, HttpServletResponse response) {
        Collection<PluginConfig> lst =  pluginFactory.flushConfigs();
        requset.setAttribute("havePlugins", lst);
        return "plugins";
    }

    //启用指定插件
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public void activePlugin(HttpServletRequest requset, HttpServletResponse response) {
        pluginFactory.activePlugin(requset.getParameter("id"));
        try {
            response.getWriter().append("active succeed!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //禁用指定插件
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public void disablePlugin(HttpServletRequest requset, HttpServletResponse response) {
        pluginFactory.disablePlugin(requset.getParameter("id"));
        try {
            response.getWriter().append("disable succeed!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
