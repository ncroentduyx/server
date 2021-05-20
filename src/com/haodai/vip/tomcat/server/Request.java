package com.haodai.vip.tomcat.server;

import com.haodai.vip.tomcat.utils.IOUtils;

import javax.lang.model.element.Name;
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
        String[] requestArray = requestString.split(IOUtils.LINE_SEPARATOR);

        for (int i = 0; i < requestArray.length; i++) {
            if (i == 0){
                String line = requestArray[i];
                String[] lineArray = line.split(" ");
                method = lineArray[0];
                path = lineArray[1];
                parseParam();
            } else {
                String[] line = requestArray[i].split(":");
                headerMap.put(line[0].trim(), line[1].trim());
            }
        }

    }

    private void parseParam() {
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
