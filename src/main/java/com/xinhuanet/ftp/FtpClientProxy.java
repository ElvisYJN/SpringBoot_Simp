package com.xinhuanet.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lifei on 2017/6/28.
 */
public class FtpClientProxy {
    private Logger logger = LoggerFactory.getLogger(FtpClientProxy.class);

        private FTPClient ftpClient;

        public FtpClientProxy(FtpClientInfo info) throws InterruptedException{
            ftpClient = new FTPClient();

            try {
                ftpClient.connect(info.getFtpIp(), info.getFtpPort());// 连接
                if (ftpClient.login(info.getFtpUserName(), info.getFtpPassword()) == true)
                    logger.info("login success!");
                else logger.info("login fail");// 登陆
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置为二进制
                ftpClient.enterLocalPassiveMode();
                int reply = ftpClient.getReplyCode();
                    logger.info("reply:"+reply);
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * <br>Description:释放ftpClient
         * <br>Author:张智研(zhangzhiyan@neusoft.com)
         * <br>Date:2013-7-4
         * @return
         */
        public void release() {
            if (ftpClient == null)
                return ;
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * <br>Description:下载文件
         * <br>Author:张智研(zhangzhiyan@neusoft.com)
         * <br>Date:2013-7-4
         * @param remote
         * @return
         * @throws IOException
         */
        public InputStream retrieveFileStream(String remote) throws IOException {
            return ftpClient.retrieveFileStream(remote);
        }

        /**
         * <br>Description:上传文件
         * <br>Author:张智研(zhangzhiyan@neusoft.com)
         * <br>Date:2013-7-4
         * @param remoteDir
         * @param local
         * @return
         * @throws IOException
         */
        public boolean storeFile(String remoteDir, String fileName, InputStream local) throws IOException {
            ftpClient.changeWorkingDirectory(remoteDir);
            return ftpClient.storeFile(fileName, local);
        }

        /**
         * <br>Description:获取本地端口
         * <br>Author:张智研(zhangzhiyan@neusoft.com)
         * <br>Date:2013-7-4
         * @return
         */
        public int getLocalPort() {
            return ftpClient.getLocalPort();
        }
    }

