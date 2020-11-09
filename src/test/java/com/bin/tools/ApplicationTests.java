package com.bin.tools;

import com.bin.tools.socket.SocketClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

//@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void socketTest() throws IOException, InterruptedException {
        SocketClient socketClient = new SocketClient("127.0.0.1", 4433);
        for (int i = 0; i < 100; i++) {
            new Thread(socketClient).start();
        }
        Thread.sleep(10000000);
    }
}
