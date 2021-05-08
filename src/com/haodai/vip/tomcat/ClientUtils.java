package com.haodai.vip.tomcat;


import com.haodai.vip.tomcat.client.Client;

public class ClientUtils {

    public static void main(String[] args) {
        Client.getInstance().connect();
    }

}
