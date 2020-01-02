package qingyouSystem;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
public class ChatTools {
    // 保存处理线程的队列对象
	static UserInfo user=new UserInfo();
     static List<ServerThread> stList = new ArrayList<ServerThread>();
     private ChatTools() {
    }
     /*
     * 取得保存处理线程对象的队列
     */
    public static List<ServerThread> getAllThread() {
        return stList;
    }
     /*
     * 将一个客户对应的处理线程对象加入到队列中
     * 
     * @param ct:处理线程对象
     */
    public static void addClient(ServerThread ct) throws SQLException, IOException {
        // 通知大家一下，有人上限了
    	stList.add(ct);//
        castMsg("有一位用户上线啦！目前人数" + stList.size());
        LoginInterface.table.refresh();
    }
     // 给队列中某一个用户发消息
    public static void sendMsg2One(int index, String msg) throws IOException {
        stList.get(index).sendMsg2Me(msg);
    }
     // 根据表中选中索引，取得处理线程对象对应的用户对象
   /* public static UserInfo getUser(int index) {
        return stList.get(index).getOwerUser();
    }*/
     /*
     * 移除队列中指定位置的处理线程对象，界面踢人时调用
     * 
     * @param index:要移除的位置
     */
    public static void removeClient(int index) throws IOException {
        stList.remove(index).closeMe();
    }
     /*
     * 从队列中移除一个用户对象对应的处理线程
     * 
     * @param user:要一处的用户对象
     */
    public static void removeAllClient(String user) throws IOException, SQLException {
        for (int i = 0; i < stList.size(); i++) {
            ServerThread ct = stList.get(i);
            stList.remove(i);
            ct.closeMe();
            ct = null;
            castMsg("我下线啦");
        }
    }
     /*
     * 服务器关闭时，调用这个方法，移除，停止队列中所有处理线程对象
     */
    public static void removeAllClient() throws IOException {
    	System.out.print("  "+stList.size());
        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            st.sendMsg2Me("系统消息：服务器即将关闭");
            st.closeMe();
            stList.remove(i);
            System.out.println("关闭了一个..." + i);
            st = null;
        }
    }
     /*
     * 将一条消息发送给队列中的其他客户机处理对象
     * 
     * @param sender:发送者用户对象
     * 
     * @param msg:要发送的消息内容
     */
    public static void castMsg(String msg) throws SQLException, IOException {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msg =  msg+"("+df.format(System.currentTimeMillis())+")";//sender.getName() + 
        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            // ServerThread类中定义有一个将消息发送出去的方法
                st.sendMsg2Me(msg);// 发送给每一个客户机
        }
    }
}
