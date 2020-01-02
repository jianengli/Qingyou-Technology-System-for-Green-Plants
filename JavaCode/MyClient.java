package qingyouSystem;
 import java.net.Socket;
 public class MyClient {
    public void initClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            // 启动收消息的线程
            RecieveThread rt = new RecieveThread(socket);
            new Thread(rt).start();
            // 启动发消息的线程
            SendThread st = new SendThread(socket);
            new Thread(st).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
