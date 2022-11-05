package com.example.dongdongapp.util;

import com.example.dongdongapp.config.dongdongappConfiguration;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class DongFTPClient {
    private  String ftpHost= dongdongappConfiguration.ftpHost;
    private  int ftpPort=dongdongappConfiguration.ftpPort;
    private  String ftpUser=dongdongappConfiguration.ftpUser;
    private  String ftpPassword=dongdongappConfiguration.ftpPassword;

    private FTPClient ftpClient;

    private FTPFile[] ftpFiles;

    /**
     * @param ftpHost FTP服务器端口ip
     * @param ftpPort FTP服务器端口号
     */
    public DongFTPClient(String ftpHost, int ftpPort) {
        this.ftpHost = ftpHost;
        this.ftpPort = ftpPort;
    }

    /**
     * @param ftpHost FTP服务器端口ip
     * @param ftpPort FTP服务器端口号
     * @param ftpUser FTP用户名
     * @param ftpPassword FTP密码
     */
    public DongFTPClient(String ftpHost, int ftpPort, String ftpUser, String ftpPassword, String ftpBasePath) {
        this.ftpHost = ftpHost;
        this.ftpPort = ftpPort;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
    }

    public DongFTPClient() {
    }

    public boolean connectFTPServer(){
        boolean isSuccess=false;
        ftpClient=new FTPClient();
        try {
            ftpClient.connect(ftpHost,ftpPort);
            isSuccess=ftpClient.login(ftpUser,ftpPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 从远端FTP服务器下载文件
     * @param localFilePath 本地文件存储路径
     * @param remoteFilePath 远端文件存储路径
     * @param fileName 文件名（远端和本地使用同一文件名）
     * @return 是否下载成功
     */
    public boolean downloadFile(String localFilePath,String remoteFilePath,String fileName){
        boolean isSuccess=false;
        File localFile=new File(localFilePath+"/"+fileName);
        try{
            if (!Objects.requireNonNull(localFile.getParentFile()).exists()){//文件夹目录不存在创建目录
                localFile.getParentFile().mkdirs();
                localFile.createNewFile();
            }
            OutputStream outputStream=new FileOutputStream(localFile);
            isSuccess=ftpClient.retrieveFile(new String((remoteFilePath+fileName).getBytes(),"ISO-8859-1"),outputStream);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 从ftp服务器下载所有本地没有的文件
     * @param localFilePath 本地文件存储路径
     * @param remoteFilePath 远端文件存储路径
     * @return 是否下载成功
     */
    public boolean downloadAllUnExistFile(String localFilePath,String remoteFilePath){
        boolean isSuccess=true;
        try{
            FTPFile[] ftpFiles=ftpClient.listFiles(remoteFilePath);
            for(FTPFile ftpFile:ftpFiles){
                String fileName=ftpFile.getName();
                File file=new File(localFilePath+"/"+fileName);
                if(!file.exists()){
                    isSuccess=downloadFile(localFilePath,remoteFilePath,fileName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 从ftp服务器下载所有文件
     * @param localFilePath 本地文件存储路径
     * @param remoteFilePath 远端文件存储路径
     * @return 是否下载成功
     */
    public boolean downloadAllFile(String localFilePath,String remoteFilePath){
        boolean isSuccess=true;
        try{
            FTPFile[] ftpFiles=ftpClient.listFiles(remoteFilePath);
            for(FTPFile ftpFile:ftpFiles){
                String fileName=ftpFile.getName();
                isSuccess=downloadFile(localFilePath,remoteFilePath,fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }
}
