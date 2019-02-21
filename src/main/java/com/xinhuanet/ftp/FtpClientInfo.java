package com.xinhuanet.ftp;

import java.util.concurrent.TimeUnit;

public class FtpClientInfo {

    private String ftpIp; // ftp的IP地址

    private int ftpPort; // ftp的端口

    private String ftpUserName; // ftp的用户名

    private String ftpPassword; // ftp的密码

    private int maxConnects; // 最大连接数

    private long timeout; // 超时时间 ,默认60

    private TimeUnit timeUnit;// 超时时间单位,默认为秒

    public FtpClientInfo() {
        timeout = 60;
        timeUnit = TimeUnit.SECONDS;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public int getMaxConnects() {
        return maxConnects;
    }

    public void setMaxConnects(int maxConnects) {
        this.maxConnects = maxConnects;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}


