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
                //收到服务端传来的消息
                String msg = new String(b);
                //将消息显示在消息面板上
                String strOld=LoginInterface.area1.getText();
                LoginInterface.area1.setText(strOld+ System.getProperty("line.separator")+
                		msg); 
                //将服务端的消息接收到并显示在面板后，清空输入流，下次收到消息后就不会显示上次已经收到的消息
                bos.reset();
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
