package qingyouSystem;
 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
 /*
 * ÿ���пͻ����ͷ���������ʱ����Ҫ����һ�����ܶ������������ݵĴ���
 * �ӷ������ĽǶȿ����������ǿͻ���
 */
public class ServerThread extends Thread{
	private Socket client;//�߳��еĴ������
	private OutputStream ous;//���������
	//�û���Ϣ����
 	public ServerThread(Socket client) {
		this.client=client;
	}
 	public String getOwerUser() throws SQLException {
		return ChatTools.user.getName();// ChatTools.stList.size()
	}
 	public void run() {
		try {
			processSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 	//����ʾ���д�ӡ��Ϣ������"�û���"��"����"�ȵ�
	public void sendMsg2Me(String msg) throws IOException {
		msg+="\r\n";
		ous.write(msg.getBytes());
		ous.flush();
	}
 	private void processSocket() throws IOException, SQLException {
		// TODO Auto-generated method stub
		InputStream ins=client.getInputStream();
		ous=client.getOutputStream();
		BufferedReader brd=new BufferedReader(new InputStreamReader(ins));
 		sendMsg2Me("��ã���ӭһ������:");
		ChatTools.addClient(this);//��֤�ɹ���������û��������������
 		String input=brd.readLine();
		while(!input.substring(input.length()-3,input.length()).equals("bye")) {
			System.out.println("��������������:"+input);
			ChatTools.castMsg(input);
			input=brd.readLine();
		}
		ChatTools.castMsg(input);
		this.closeMe();
	}
 	//�رյ�ǰ�ͻ���������������ӡ�
	public void closeMe() throws IOException {
		client.close();
	}
}
