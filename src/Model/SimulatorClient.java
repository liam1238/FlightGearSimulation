package Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SimulatorClient {

    public static volatile boolean stop = false;
    private static PrintWriter out;
    private static Socket socket;

    public void Connect(String ip, int port){
        try {
            socket = new Socket(ip, port);
            out=new PrintWriter(socket.getOutputStream());
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("Connected to the Server");
    }

    public static void Send(String[] data){
        for (String send: data) {
            out.println(send);
            out.flush();
            System.out.println(send);
        }
    }

    public void stop()
    {
        if(out!=null ){
            out.close();
            try {
                socket.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}