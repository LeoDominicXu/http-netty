package leo.sunrise.netty.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by dominic on 17-4-7.
 */
public class TimeServerHandler implements Runnable{
    private Socket socket;
    private int index;

    public TimeServerHandler(Socket socket, int index) {
        this.socket = socket;
        this.index = index;
    }

    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            printWriter = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true) {
                body = bufferedReader.readLine();
                if (body == null)
                    break;
                System.out.println("The time server receive order :" + body + ", your id is : " + index);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? "your id is "+ index + ", the time is:" + new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                printWriter.println(currentTime);
            }

        } catch (Exception e) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
