package com.xinhuanet.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "")
public class FtpConf {

    @Value("${ftp.ip}")
    private String ip;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.maxconnect}")
    private Integer maxconnect;

    @Value("${ftp.ftptarget}")
    private String ftptargetdir;

    public String getFtptargetdir() {
        return ftptargetdir;
    }

    public void setFtptargetdir(String ftptargetdir) {
        this.ftptargetdir = ftptargetdir;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxconnect() {
        return maxconnect;
    }

    public void setMaxconnect(Integer maxconnect) {
        this.maxconnect = maxconnect;
    }

}
