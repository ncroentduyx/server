package com.haodai.vip.tomcat;

import com.haodai.vip.tomcat.server.HttpServer;
import com.haodai.vip.tomcat.server.controller.HeaderAndParamaController;
import com.haodai.vip.tomcat.server.controller.LoginController;

public class TomcatUtils {

    public static void main(String[] args) {
        HttpServer server = HttpServer.getInstance();
        server.addController("login.action", new LoginController());
        server.addController("requestInfo.action", new HeaderAndParamaController());
        server.start();
    }

}
