package com.haodai.vip.tomcat.server;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/

public class Response {

    private OutputStream output;
    private List<Byte> responseBody = new ArrayList<>();

    public Response(OutputStream output) {
        this.output = output;
    }

    public void write(byte[] content){
        for (int i = 0; i < content.length; i++) {
            responseBody.add(content[i]);
        }
    }

    public void write(String content){
        try {
            byte[] bytes = content.getBytes(Config.Encoding.ENCODE);
            for (int i = 0; i < bytes.length; i++) {
                responseBody.add(bytes[i]);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public byte[] getResponseBody(){
        byte[] chars = new byte[responseBody.size()];
        for (int i = 0; i < responseBody.size(); i++) {
            chars[i] = responseBody.get(i);
        }
        return chars;
    }

    public void out() throws Exception{
        String content = new String(getResponseBody(), Config.Encoding.ENCODE);
        byte[] contentBytes = content.getBytes(Config.Encoding.ENCODE);
        String errorMessage = "HTTP/1.1 200 ok\r\n" +
                "Content-Type: text/html;charset="+Config.Encoding.ENCODE+"\r\n" +
                "Content-Length: "+contentBytes.length+"\r\n" +
                "\r\n" +content;
        output.write(errorMessage.getBytes(Config.Encoding.ENCODE));
    }
}