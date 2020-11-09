package com.bin.tools.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Socket 处理器
 *
 * @author bin.wang
 * @version 1.0 2020/9/24
 */
@Service
public class SocketHandler {
    public static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    @Async
    public void handle(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        try {
            byte[] bytes = new byte[1024];
            int length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((length = inputStream.read(bytes)) != -1) {
                //将bytes中存放的字节写到byteArrayOutputStream中
                byteArrayOutputStream.write(bytes, 0, length);
                //如果不判断break,会一直读取,导致客户端一直等待状态
                if (length < bytes.length) {
                    break;
                }
            }
            byte[] data = byteArrayOutputStream.toByteArray();
            String msg = new String(data);
            logger.info(msg);
            logger.info("线程名称：" + Thread.currentThread().getName());
            logger.info(socket.getInetAddress().getHostName());
            outputStream.write("服务端返回数据".getBytes());
            outputStream.flush();
        } finally {
            inputStream.close();
            outputStream.close();
            socket.close();
        }
    }

}
