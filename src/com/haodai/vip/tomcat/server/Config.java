package com.haodai.vip.tomcat.server;

import java.io.File;

/**
 * Created by huangtao on 17/5/27.
 */
public class Config {

    public class RequestInfo{
        public static final String CONTENT_TYPE = "Content-Type";

        public static final String CONTENT_TYPE_NO_FILE = "application/x-www-form-urlencoded";
        public static final String BOUNDARY = "boundary";



    }

    public class Server{
        public static final String CONFIG_PATH = "server.properties";

        public static final String WEB_ROOT = "WebRoot";
    }

    public class Encoding {
        public static final String ENCODE = "UTF-8";
    }

    public class IO{
        public static final int BUFFER_SIZE = 1024;
    }

    public class Controller{
        public static final String SUFFIX = ".action";
    }

    public class Method{
        public static final String GET = "get";
        public static final String POST = "post";
    }

}
