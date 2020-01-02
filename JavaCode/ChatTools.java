package qingyouSystem;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
public class ChatTools {
    // ���洦���̵߳Ķ��ж���
	static UserInfo user=new UserInfo();
     static List<ServerThread> stList = new ArrayList<ServerThread>();
     private ChatTools() {
    }
     /*
     * ȡ�ñ��洦���̶߳���Ķ���
     */
    public static List<ServerThread> getAllThread() {
        return stList;
    }
     /*
     * ��һ���ͻ���Ӧ�Ĵ����̶߳�����뵽������
     * 
     * @param ct:�����̶߳���
     */
    public static void addClient(ServerThread ct) throws SQLException, IOException {
        // ֪ͨ���һ�£�����������
    	stList.add(ct);//
        castMsg("��һλ�û���������Ŀǰ����" + stList.size());
        LoginInterface.table.refresh();
    }
     // ��������ĳһ���û�����Ϣ
    public static void sendMsg2One(int index, String msg) throws IOException {
        stList.get(index).sendMsg2Me(msg);
    }
     // ���ݱ���ѡ��������ȡ�ô����̶߳����Ӧ���û�����
   /* public static UserInfo getUser(int index) {
        return stList.get(index).getOwerUser();
    }*/
     /*
     * �Ƴ�������ָ��λ�õĴ����̶߳��󣬽�������ʱ����
     * 
     * @param index:Ҫ�Ƴ���λ��
     */
    public static void removeClient(int index) throws IOException {
        stList.remove(index).closeMe();
    }
     /*
     * �Ӷ������Ƴ�һ���û������Ӧ�Ĵ����߳�
     * 
     * @param user:Ҫһ�����û�����
     */
    public static void removeAllClient(String user) throws IOException, SQLException {
        for (int i = 0; i < stList.size(); i++) {
            ServerThread ct = stList.get(i);
            stList.remove(i);
            ct.closeMe();
            ct = null;
            castMsg("��������");
        }
    }
     /*
     * �������ر�ʱ����������������Ƴ���ֹͣ���������д����̶߳���
     */
    public static void removeAllClient() throws IOException {
    	System.out.print("  "+stList.size());
        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            st.sendMsg2Me("ϵͳ��Ϣ�������������ر�");
            st.closeMe();
            stList.remove(i);
            System.out.println("�ر���һ��..." + i);
            st = null;
        }
    }
     /*
     * ��һ����Ϣ���͸������е������ͻ����������
     * 
     * @param sender:�������û�����
     * 
     * @param msg:Ҫ���͵���Ϣ����
     */
    public static void castMsg(String msg) throws SQLException, IOException {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msg =  msg+"("+df.format(System.currentTimeMillis())+")";//sender.getName() + 
        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            // ServerThread���ж�����һ������Ϣ���ͳ�ȥ�ķ���
                st.sendMsg2Me(msg);// ���͸�ÿһ���ͻ���
        }
    }
}
