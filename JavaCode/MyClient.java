package qingyouSystem;
 import java.net.Socket;
 public class MyClient {
    public void initClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            // ��������Ϣ���߳�
            RecieveThread rt = new RecieveThread(socket);
            new Thread(rt).start();
            // ��������Ϣ���߳�
            SendThread st = new SendThread(socket);
            new Thread(st).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
