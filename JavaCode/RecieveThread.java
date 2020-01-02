package qingyouSystem;
 import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
 public class RecieveThread implements Runnable{
	private Socket socket;
    public RecieveThread(Socket socket) {
        this.socket = socket;
    }
     @Override
    public void run() {
        try {
            InputStream ips = socket.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while (true) {
                int t = ips.read();
                while (t != '\n') {
                    bos.write(t);
                    t = ips.read();
                }
                byte[] b = bos.toByteArray();
                //�յ�����˴�������Ϣ
                String msg = new String(b);
                //����Ϣ��ʾ����Ϣ�����
                String strOld=LoginInterface.area1.getText();
                LoginInterface.area1.setText(strOld+ System.getProperty("line.separator")+
                		msg); 
                //������˵���Ϣ���յ�����ʾ������������������´��յ���Ϣ��Ͳ�����ʾ�ϴ��Ѿ��յ�����Ϣ
                bos.reset();
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
