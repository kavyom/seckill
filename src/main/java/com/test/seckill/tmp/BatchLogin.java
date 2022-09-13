package com.test.seckill.tmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.seckill.base.BaseResult;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by pzh on 2022/9/12.
 */
public class BatchLogin {

    private static String[] phones = {"15861821790", "15861821791", "15861821792", "15861821793", "15861821794", "15861821795", "15861821796", "15861821797", "15861821798", "15861821799"};

    public static void doBatchLogin() throws Exception {
        //登录，生成token
        String urlString = "http://localhost:8090/seckill/login/doLogin";
        File file = new File("E:\\tmp\\config.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < phones.length; i++) {
            int userId = i + 1;
            String phone = phones[i];
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + phone + "&password=123";
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            BaseResult respBean = mapper.readValue(response, BaseResult.class);
            String userTicket = ((String) respBean.getData());
            System.out.println("create userTicket : " + userId);
            String row = userId + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + row);
        }
        raf.close();
        System.out.println("over");
    }

    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/seckill?useUnicode = true & characterEncoding = UTF - 8 & serverTimezone = Asia / Shanghai ";
        String username = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        doBatchLogin();
    }
}
