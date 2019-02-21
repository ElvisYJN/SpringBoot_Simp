package com.xinhuanet.ftp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FtpText {

    @Autowired
    private FtpConf ftpConf;

    @Test
    public void test1() {

        //使用前部署机器与ftp机器打通
        FtpClientInfo ftpClientInfo = new FtpClientInfo();
        ftpClientInfo.setFtpIp(ftpConf.getIp());
        ftpClientInfo.setFtpPort(ftpConf.getPort());
        ftpClientInfo.setFtpUserName(ftpConf.getUsername());
        ftpClientInfo.setFtpPassword(ftpConf.getPassword());
        ftpClientInfo.setMaxConnects(ftpConf.getMaxconnect());

        try {
            FtpClientProxy fcp = new FtpClientProxy(ftpClientInfo);
            File file = new File("文件的路径");
            String filename = "文件名称.xml";
            FileInputStream fis = new FileInputStream(file);
            //            对方的ftp机器的文件夹路径
            fcp.storeFile(ftpConf.getFtptargetdir(), filename, fis);
        } catch (IOException e) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
