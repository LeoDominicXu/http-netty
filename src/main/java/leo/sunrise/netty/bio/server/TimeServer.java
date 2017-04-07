package leo.sunrise.netty.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dominic on 17-4-7.
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        AtomicInteger connectCount = new AtomicInteger(0);
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //采用默认值
            }
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is Start in port :" + port);
            Socket socket = null;
            while (true) {
                connectCount.incrementAndGet();
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket, connectCount.get())).start();
            }

        } finally {
            if (serverSocket != null) {
                System.out.println("The time server is close");
                serverSocket.close();
            }
        }
    }
}
