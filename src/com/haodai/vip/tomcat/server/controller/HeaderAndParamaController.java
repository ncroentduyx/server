package com.haodai.vip.tomcat.server.controller;

import com.haodai.vip.tomcat.server.Request;
import com.haodai.vip.tomcat.server.Response;
import com.haodai.vip.tomcat.server.Servlet;

import java.util.List;

/**
 * Created by huangtao on 17/5/31.
 */
public class HeaderAndParamaController implements Servlet {

    @Override
    public void get(Request request, Response response) throws Exception {
        response.write("get请求<br/><br/><br/>");
        printRequestHeader(request, response);
        response.write("<br/><br/>");
        printRequestParamter(request, response);
    }



    @Override
    public void post(Request request, Response response) throws Exception {
        response.write("<font color='#F00'>post</font>请求<br/><br/><br/>");
        printRequestHeader(request, response);
        printRequestParamter(request, response);
    }

    private void printRequestHeader(Request request, Response response) {
        List<String> headerNameList = request.getHeaderNames();
        response.write("Header信息：<br/>");
        for (int i = 0; i < headerNameList.size(); i++) {
            String name = headerNameList.get(i);
            String header = request.getHeader(name);
            response.write("&nbsp;&nbsp;&nbsp;&nbsp;"+name+": "+header+"<br/>");
        }
    }

    private void printRequestParamter(Request request, Response response) {
        List<String> paramNameList = request.getParamsNames();
        response.write("params：<br/>");
        for (int i = 0; i < paramNameList.size(); i++) {
            String name = paramNameList.get(i);
            String param = request.getParam(name);
            response.write("&nbsp;&nbsp;&nbsp;&nbsp;"+name+": "+param+"<br/>");
        }
    }

}
