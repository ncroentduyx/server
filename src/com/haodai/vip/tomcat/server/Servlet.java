package com.haodai.vip.tomcat.server;

/**
 * Created by huangtao on 17/5/27.
 */
public interface Servlet {
    public void get(Request request, Response response) throws Exception;
    public void post(Request request, Response response) throws Exception;
}
