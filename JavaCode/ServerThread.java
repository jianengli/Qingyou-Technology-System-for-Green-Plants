package qingyouSystem;
 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
 /*
 * 每当有客户机和服务器连接时，都要定义一个接受对象来进行数据的传输
 * 从服务器的角度看，这个类就是客户端
 */
public class ServerThread extends Thread{
	private Socket client;//线程中的处理对象
	private OutputStream ous;//输出流对象
	//用户信息对象
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
 	//在显示屏中打印信息，例如"用户名"、"密码"等等
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
 		sendMsg2Me("你好，欢迎一起聊天:");
		ChatTools.addClient(this);//认证成功，把这个用户加入服务器队列
 		String input=brd.readLine();
		while(!input.substring(input.length()-3,input.length()).equals("bye")) {
			System.out.println("服务器读到的是:"+input);
			ChatTools.castMsg(input);
			input=brd.readLine();
		}
		ChatTools.castMsg(input);
		this.closeMe();
	}
 	//关闭当前客户机与服务器的连接。
	public void closeMe() throws IOException {
		client.close();
	}
}
