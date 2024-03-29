package com.haodai.vip.tomcat.server.controller;

import com.haodai.vip.tomcat.server.Request;
import com.haodai.vip.tomcat.server.Response;
import com.haodai.vip.tomcat.server.Servlet;

/**
 * Created by huangtao on 17/5/27.
 */
public class LoginController implements Servlet {

    @Override
    public void get(Request request, Response response) throws Exception {
        String name = request.getParam("name");
        String pwd = request.getParam("pwd");
        String agent = request.getHeader("User-Agent");
        response.write("这是GET请求name="+name+"    pwd="+pwd+"   agent="+agent);
    }

    @Override
    public void post(Request request, Response response) throws Exception {
        String name = request.getParam("name");
        String pwd = request.getParam("pwd");
        String agent = request.getHeader("User-Agent");
        response.write("这是POST请求name="+name+"    pwd="+pwd+"   agent="+agent);
    }
}
