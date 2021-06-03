package com.haodai.vip.tomcat.server;

import com.haodai.vip.tomcat.utils.IOUtils;
import com.haodai.vip.tomcat.utils.LogUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangtao on 17/5/27.
 */
public class Request {

    private String method;
    private String path;
    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> paramMap = new HashMap<>();

    public Request(InputStream input) {
        parse(input);
    }

    public void parse(InputStream inputStream) {
        String requestString = IOUtils.inputStream2String(inputStream);
        String[] requestArray = requestString.split("\\r\\n");

        for (int i = 0; i < requestArray.length; i++) {
            String lineString = requestArray[i];
            if (i == 0){
                String line = lineString;
                String[] lineArray = line.split(" ");
                method = lineArray[0];
                path = lineArray[1];
                if (Config.Method.GET.equalsIgnoreCase(method)){
                    parseGetParam();
                }
            } else {
                if (lineString.contains(":")){
                    String[] line = lineString.split(":");
                    headerMap.put(line[0].trim(), line[1].trim());
                } else {
                    if (!"".equals(lineString)){
                        parsePostParam(lineString);
                    }
                }
            }
        }
    }

    private void parseGetParam() {
        if (path.contains("?")){
            String[] pathSplit = path.split("\\?");
            path = pathSplit[0];
            String endString = pathSplit[1];
            String[] params = endString.split("&");
            for (int i = 0; i < params.length; i++) {
                String paramString = params[i];
                String[] param = paramString.split("=");
                paramMap.put(param[0], param[1]);
            }
        }
    }

    private void parsePostParam(String posParamString) {
        String[] posParamsString = posParamString.split("&");
        for (int i = 0; i < posParamsString.length; i++) {
            String pos = posParamsString[i];
            String[] posParam = pos.split("=");
            paramMap.put(posParam[0], posParam[1]);
        }
    }

    public String getHeader(String name){
        return headerMap.get(name);
    }

    public String getParam(String name){
        return paramMap.get(name);
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }
}
