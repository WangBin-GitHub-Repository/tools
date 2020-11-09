package com.bin.tools.socket;

import com.bin.tools.socket.config.SocketProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 服务端
 * Socket又称"套接字"，应用程序通常通过"套接字"向网络发出请求或者应答网络请求。
 * Socket和ServerSocket类库位于java.net包中。
 * ServerSocket用于服务端，Socket是建立网络连接时使用的。
 * 在连接成功时，应用程序两端都会产生一个Socket实例，操作这个实例，完成所需的会话。
 * 对于一个网络连接来说，套接字时平等的，不因为在服务器端或在客户端而产生不用级别。
 * 不管是Socket还是ServerSocket它们的工作都是通过SocketImpl类及其子类完成的。
 * <p>
 * 套接字之间的连接过程可以分为四个步骤：服务器监听、客户端请求服务器、服务器确认、客户端确认。
 * (1)服务器监听：服务器端套接字并不定位具体的客户端套接字，而是处于等待连接的状态。
 * (2)客户端请求：由客户端的套接字提出连接请求，要连接的目标是服务器端的套接字。为此，客户端的套接字必须首先描述它要连接的服务器套接字，指出服务器端套接字的地址和端口，然后向服务器端套接字提出连接请求。
 * (3)服务器端连接确认：当服务器端套接字监听到客户端套接字的连接请求，响应客户端套接字的请求，建立一个新的线程，把服务器端套接字的描述发送给客户端。
 * (4)客户端连接确认：客户端确认此描述，连接就会建立，双方开始通信，而服务器端套接字继续处于监听状态，继续接收其他客户端套接字的连接请求。
 *
 * @author bin.wang
 * @version 1.0 2020/9/24
 */
@Component
public class SocketServer {
    public static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    @Autowired
    private SocketProperties socketProperties;
    @Autowired
    private SocketHandler socketHandler;

    ServerSocket serverSocket = null;

    public void start() {
        try {
            serverSocket = new ServerSocket(socketProperties.getPort());
            logger.info("Socket服务已启动，端口： {}", serverSocket.getLocalPort());

            while (true) {
                //阻塞，等待客户端请求
                Socket socket = serverSocket.accept();
                /**
                 * 使用线程池处理socket客户端请求，如果使用主线程进行处理，当操作执行结束，线程退出，socket将自动关闭
                 **/
                socketHandler.handle(socket);
            }
        } catch (Exception e) {
            logger.info("Socket服务启动失败： {}", e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serverSocket = null;
        }
    }
}
