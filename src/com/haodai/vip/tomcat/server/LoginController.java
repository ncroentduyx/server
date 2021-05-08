package com.haodai.vip.tomcat.server;

/**
 * Created by huangtao on 17/5/27.
 */
public class LoginController implements Servlet {

    @Override
    public void get(Request request, Response response) throws Exception {
        response.write("这个是GET方法<br/>");

        int i = 5/0;
    }

    @Override
    public void post(Request request, Response response) throws Exception {

    }
}
