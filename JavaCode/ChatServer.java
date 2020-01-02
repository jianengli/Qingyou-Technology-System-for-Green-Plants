package qingyouSystem;
 import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 public class ChatServer extends Thread{
 	private int port;// �˿�
    private boolean running = false;// �������Ƿ������еı��
    static ServerSocket sc;// ����������
     /*
     * ��������������ʱ�����봫��˿ں�
     * 
     * @param port:���������ڶ˿ں�
     */
    public ChatServer(int port) {
        this.port = port;
    }
     // �߳�������������
    public void run() {
        setupServer();
    }
     // ��ָ���˿�������������
    public void setupServer() {
        try {
            ServerSocket sc = new ServerSocket(this.port);
            running = true;
            System.out.println("�����������ɹ���" + port);
            while (running) {
                Socket client = sc.accept();// �ȴ��������
                System.out.println("������һ���ͻ�������" + client.getRemoteSocketAddress());
                // ����һ�������̣߳�ȥ��������������
                ServerThread ct = new ServerThread(client);
                ct.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     /*
     * ��ѯ�������Ƿ�������
     * 
     * @return: trueΪ������
     */
    public boolean isRunning() {
        return this.running;
    }
     // �رշ�����
    public void stopchatServer() {
        this.running = false;
        try {
            sc.close();
        } catch (Exception e) {}
    }
}
