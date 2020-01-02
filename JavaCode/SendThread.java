package qingyouSystem;
 import java.net.Socket;
 public class SendThread implements Runnable {
	private Socket socket;
 	public SendThread(Socket socket) {
		this.socket = socket;
	}
 	public void run() {
		try {
			LoginInterface.ops = socket.getOutputStream();
			while (true) {
				if (LoginInterface.sendMsg2 != "") {
					byte[] b = LoginInterface.sendMsg2.getBytes();
					LoginInterface.ops.write(b);
					LoginInterface.ops.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
