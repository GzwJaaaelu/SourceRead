package com.google.jaaaelu.gzw.myapplication.okHttp.demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/12/14.
 */

public class SocketDemo {

    public void TCPSendMessage(String msg) {
        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            socket = new Socket("192.168.50.120", 8888);
            outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes());
            //  shutdownOutput() 表明写完了
            socket.shutdownOutput();
            inputStream = socket.getInputStream();
            byte[] b = new byte[1024];
            int len = -1;
            final StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(b)) != -1) {
                sb.append(new String(b, 0, len, Charset.forName("GBK")));
            }
            //  TODO 主线程更新 UI
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //  注意，输出流不需要关闭，因为它只是在 Socket 中得到的输出流对象，我们并没有创建它
                //  所以我们只需要关闭 Socket 就可以了
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ReceiveMessage() {
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(8888);
            while (true) {
                //  负责接收客户端发送的请求
                socket = server.accept();
                System.out.println(socket.getInetAddress().getHostName());
                System.out.println(socket.getInetAddress().getHostAddress());
                InputStream inputStream = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = bis.read(b)) != -1) {
                    System.out.println(new String(b, 0, len, "UTF-8"));
                }
                socket.shutdownInput();
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("ok,我已经收到!".getBytes());
                bis.close();
                socket.close();
                socket = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
