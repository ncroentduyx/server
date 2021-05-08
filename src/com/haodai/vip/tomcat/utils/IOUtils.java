package com.haodai.vip.tomcat.utils;


import java.io.*;

/**
 * Created by huangtao on 17/5/24.
 */
public class IOUtils {
    public static String LINE_SEPARATOR=  System.getProperty("line.separator");

    public static String inputStream2String(InputStream inputStream){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String readContent;
            while ((readContent = bufferedReader.readLine()) != null) {
                sb.append(readContent+"\n");
                if (readContent.length() == 0){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
