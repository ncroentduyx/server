package com.haodai.vip.tomcat.utils;


import com.haodai.vip.tomcat.server.Config;

import java.io.*;

/**
 * Created by huangtao on 17/5/24.
 */
public class IOUtils {
    public static String inputStream2String(InputStream inputStream){
        String requestString = null;
        try {
            byte[] buffer = new byte[1024*100];
            int len = inputStream.read(buffer);
            requestString = new String(buffer, 0, len, Config.Encoding.ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestString;
    }

}
