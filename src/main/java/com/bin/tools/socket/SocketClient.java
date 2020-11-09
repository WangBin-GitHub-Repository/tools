package com.bin.tools.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author bin.wang
 * @version 1.0 2020/9/25
 */
public class SocketClient implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private String ip;
    private int port;

    public SocketClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(this.ip, this.port));
            logger.info("Socket客户端已启动，端口： {}", this.port);

            outputStream = socket.getOutputStream();
            outputStream.write("客户端发送请求".getBytes());
            outputStream.flush();

            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                byteArray.write(bytes, 0, length);
                byteArray.flush();

                if (length < bytes.length) {
                    break;
                }
            }

            byte[] data = byteArray.toByteArray();
            String res = new String(data);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
