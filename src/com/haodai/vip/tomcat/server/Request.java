package com.haodai.vip.tomcat.server;

import com.haodai.vip.tomcat.utils.IOUtils;
import com.haodai.vip.tomcat.utils.LogUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangtao on 17/5/27.
 */
public class Request {

    private String method;
    private String path;
    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> paramMap = new HashMap<>();


    private String fileSplitLine;

    public Request(InputStream input) {
        parse(input);
    }

    public void parse(InputStream inputStream) {
        String requestString = IOUtils.inputStream2String(inputStream);
        if (requestString == null){
            return;
        }
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
                    String name = line[0].trim();
                    String value = line[1].trim();

                    if (Config.RequestInfo.CONTENT_TYPE.equalsIgnoreCase(name)){
                        if (value.contains(";")){
                            String[] contentTypeArray = value.split(";");
                            fileSplitLine = contentTypeArray[1];
                            value = contentTypeArray[0];
                            fileSplitLine = fileSplitLine.substring(fileSplitLine.indexOf(Config.RequestInfo.BOUNDARY)+Config.RequestInfo.BOUNDARY.length()+1);
                        }
                    }
                    headerMap.put(name, value);
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

    public List<String> getHeaderNames(){
        List<String> nameList = new ArrayList<>();
        for (Map.Entry<String, String> entry : headerMap.entrySet()){
            nameList.add(entry.getKey());
        }
        return nameList;
    }

    public List<String> getParamsNames(){
        List<String> nameList = new ArrayList<>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()){
            nameList.add(entry.getKey());
        }
        return nameList;
    }

    public String getContentType(){
        String contentType = headerMap.get(Config.RequestInfo.CONTENT_TYPE);
        if (contentType == null){
            return Config.RequestInfo.CONTENT_TYPE_NO_FILE;
        }
        return contentType;
    }

    public String getFileSplitLine() {
        return fileSplitLine;
    }
}
