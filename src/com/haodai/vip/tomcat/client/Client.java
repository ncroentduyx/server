package com.haodai.vip.tomcat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by huangtao on 17/5/24.
 */
public final class Client {

    private static Client client = new Client();

    public static Client  getInstance(){
        return client;
    }

    public void connect() {
        Socket socket = null;
        try {
            //创建一个流套接字并将其连接到指定主机上的指定端口号
            socket = new Socket("localhost", 8093);

            //向服务器端发送数据
            PrintStream out = new PrintStream(socket.getOutputStream());
            System.out.print("请输入: \t");
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.println(str);

            //读取服务器端数据
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String ret = input.readLine();
            System.out.println("服务器端返回过来的是: " + ret);
//            if ("OK".equals(ret)) {
//                System.out.println("客户端将关闭连接");
//                Thread.sleep(500);
//            }

            out.close();
//            input.close();
        } catch (Exception e) {
            System.out.println("客户端异常:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }
    }

}