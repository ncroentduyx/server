package com.haodai.vip.tomcat.server;

import com.haodai.vip.tomcat.utils.LogUtils;

/**
 * Created by huangtao on 17/5/27.
 */
public class LoginController implements Servlet {

    @Override
    public void get(Request request, Response response) throws Exception {
        String name = request.getParam("name");
        String pwd = request.getParam("pwd");
        String agent = request.getHeader("User-Agent");
        LogUtils.info(name+"   "+pwd+"   "+agent);
    }

    @Override
    public void post(Request request, Response response) throws Exception {

    }
}
