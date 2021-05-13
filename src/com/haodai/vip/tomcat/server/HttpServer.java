package com.haodai.vip.tomcat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public final class HttpServer {

    private static HttpServer server = new HttpServer();

    public Map<String, Servlet> servletMap = new HashMap<>();

    private final int serverPort;
    private final int maxConnectionQueue;

    private HttpServer(){
        String packName = HttpServer.class.getPackage().getName();
        InputStream in = HttpServer.class.getClassLoader().getResourceAsStream(packName.replaceAll("\\.", "/")+"/"+ Config.Server.CONFIG_PATH);
        Properties prop = new Properties();
        try {
            prop.load(in);
            serverPort = Integer.parseInt(prop.getProperty("SERVER_PORT"));
            maxConnectionQueue = Integer.parseInt(prop.getProperty("MAX_CONNECTION_QUEUE"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取服务器配置文件出错");
        }
    }

    public static HttpServer getInstance(){
        return server;
    }

    public void start(){
            ServerSocket serverSocket = createSocket();
            while (true){
                try {
                    Socket socket = serverSocket.accept();

                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();

                    boolean isHandlerRequest = false;

                    Request request = new Request(input);
                    Response response = new Response(output);

                    String requestPath = request.getPath();
                    if (requestPath.endsWith(Config.Controller.SUFFIX)){
                        for (Map.Entry<String, Servlet> entry : servletMap.entrySet()){
                            if (requestPath.equals("/"+entry.getKey())){
                                Servlet controller = entry.getValue();
                                try {
                                    if (Config.Method.GET.equalsIgnoreCase(request.getMethod())){
                                        controller.get(request, response);
                                    } else {
                                        controller.post(request, response);
                                    }
                                    response.out();
                                } catch (Exception e){
                                    String errorMessage = "HTTP/1.1 500 error\r\n" +
                                            "Content-Type: text/html\r\n" +
                                            "Content-Type: text/html;charset="+Config.Encoding.ENCODE+"\r\n" +
                                            "\r\n<h1>server error<br/>"+e.getMessage()+"</h1>";
                                    output.write(errorMessage.getBytes());
                                }

                                isHandlerRequest = true;
                            }
                        }
                    } else {
                        FileInputStream fis = null;
                        try {
                            File file = new File(System.getProperty("user.dir")+File.separator+Config.Server.WEB_ROOT, requestPath);
                            byte[] buffer = new byte[1024];
                            if (file.exists()) {
                                fis = new FileInputStream(file);
                                int len = fis.read(buffer, 0, Config.IO.BUFFER_SIZE);
                                while (len!=-1) {
                                    output.write(buffer, 0, len);
                                    len = fis.read(buffer, 0, Config.IO.BUFFER_SIZE);
                                }
                                output.write("静态请求已经处理".getBytes());
                                isHandlerRequest = true;
                            }
                        } finally {
                            if (fis != null){
                                fis.close();
                            }
                        }
                    }


                    if (!isHandlerRequest){
                        String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                                "Content-Type: text/html\r\n" +
                                "Content-Type: text/html;charset="+Config.Encoding.ENCODE+"\r\n" +
                                "\r\n<h1>File Not Found</h1>";
                        output.write(errorMessage.getBytes());
                    }

                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    private ServerSocket createSocket(){
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(serverPort);//, maxConnectionQueue, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建服务器失败");
        }
        return serverSocket;
    }

    public void addController(String path, Servlet servlet){
        servletMap.put(path, servlet);
    }

}
