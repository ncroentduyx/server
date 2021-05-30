package com.haodai.vip.tomcat;

import com.haodai.vip.tomcat.server.HttpServer;
import com.haodai.vip.tomcat.server.controller.LoginController;
import com.haodai.vip.tomcat.utils.LogUtils;

public class TomcatUtils {

    public static void main(String[] args) {
        HttpServer server = HttpServer.getInstance();
        server.addController("login.action", new LoginController());
        server.start();
    }

}
