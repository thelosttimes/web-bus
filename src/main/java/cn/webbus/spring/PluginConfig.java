package cn.webbus.spring;

import java.io.Serializable;

public class PluginConfig implements Serializable{

    private String id;
    private String name;
    private String className;
    private String jarRemoteUrl;
    private String jarFile;
    private Boolean active;
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJarRemoteUrl() {
        return jarRemoteUrl;
    }

    public void setJarRemoteUrl(String jarRemoteUrl) {
        this.jarRemoteUrl = jarRemoteUrl;
    }

    public String getJarFile() {
        return jarFile;
    }

    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "PluginConfig{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", jarRemoteUrl='" + jarRemoteUrl + '\'' +
                ", jarFile='" + jarFile + '\'' +
                ", active=" + active +
                ", version='" + version + '\'' +
                '}';
    }
}
