package qingyouSystem;
 import java.sql.*;
import java.util.ArrayList;
 public class ConnectSql {
 	final String Drive = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 建立与驱动包的联系
 	final String URL = "jdbc:sqlserver://localhost:1433;databaseName=qingyouSystem"; // 数据库地址
 	final String USER = "sa"; // 用户名
 	final String PASSWORD = "123"; // 密码
 	String table=null;
 	Connection conn = null;// DriverManager.getConnection(URL ,USER,PASSWORD);;
							// // 定义数据库连接对象
 	Statement stmt = null; // 定义SQL命令集对象
 	ResultSet rs = null; // 定义结果集对象
 	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*ConnectSql outerClass = new ConnectSql();
		outerClass.getConnection();
		outerClass.getRegInfoByQuery("select * from register_info");*/
	}
 	private Connection getConnection() {
		try {
			// 加载驱动
			Class.forName(Drive);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接数据库失败");
		}
		return null;
	}
 	public void registe(String phoneNumber, String pw, String sex,String register_name) {
		try {
 			String sql2 = "insert into register_info values ('" + phoneNumber + "', '" + pw + "','" + 200
					+ "', '"+ register_name +"', '" +sex+"', '" +0+
					"')";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
			System.out.println("注册成功,快去登陆吧！");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("注册失败,该用户已经注册！");
		}
 	}
	public void onlineMarkBackToZero() {
		try {
 			String sql2 = "update register_info set isChatting=0 ";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新失败，上线状态登记出错！");
		}
	}
	public void onlineMarkBackToZeroForAClient(String oneQuittng) {
		try {
 			String sql2 = "update register_info set isChatting=0 where register_name='"+oneQuittng+"'";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
			//ServerThread.sendMsg2Me("有一位用户下线了！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onlineMark(String register_name) {
		try {
 			String sql2 = "update register_info set isChatting=1 where register_name='"+register_name+"'";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
			//System.out.println("注册成功,快去登陆吧！");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新失败，上线状态登记出错！");
		}
	}
	public String countOnlineUsers() {
		try {
			String sql2 = "select count(*)from register_info where isChatting='1'";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())return resultSet.getString(1);
 		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("注册失败,该用户已经注册！");
		}
		return "";
 	}
 	public String getOnlineUsersName(ArrayList<String> listUsers) {
		try {
			String sql2 = "select * from register_info where isChatting='1'";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
			ResultSet resultSet = preparedStatement.executeQuery();
			//stmt = this.getConnection().createStatement();
			//stmt.executeUpdate(sql2);
			while(resultSet.next()){ 
				listUsers.add(resultSet.getString(4));
			}
 		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("注册失败,该用户已经注册！");
		}
		return "";
 	}
 	public void storeBabyName(String strbabyName, String strSex, String strRegisterName) {
		try {
 			String sql = "insert into role_of_baby values ('" + strbabyName + "', '" 
			+ strSex + "','" + strRegisterName +"')";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql);
			System.out.println("领养成功,开始游戏吧！");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("领养失败。");
		}
 	}
	public void storeBabyInfo(String babyName, String age, String temperature,String light,String humidity,String
			sex) {
		try {
 			String sql2 = "insert into baby_info values ('" + babyName + "', '" + sex + "','" + age
					+ "', '"+ temperature +"', '" +humidity+"', '" +light+
					"')";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
			System.out.println("更新植物状态成功！");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新失败！");
		}
 	}
	public boolean hasRegiste(String phoneNumber, String pw) {
		try {
			String sql2 = "select count(*)from register_info where phone_number='" + phoneNumber +"' and "
					+ "pw='" + pw + "'";//select count(*)from register_info where phone_number='13576074107' and pw='123'
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
			ResultSet resultSet = preparedStatement.executeQuery();
			//stmt = this.getConnection().createStatement();
			//stmt.executeUpdate(sql2);
			if(resultSet.next())
				if(resultSet.getInt(1)==1)return true;
 		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("注册失败,该用户已经注册！");
			return false;
		}
		return false;
 	}
 	public ResultSet getPersonalInfo(String phoneNumber) {
		try {
			String sql2 = "select * from register_info where phone_number='" + phoneNumber +"'";//select count(*)from register_info where phone_number='13576074107' and pw='123'
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())return resultSet;
			else 
				return null;
 		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("注册失败,该用户已经注册！");
			return null;
		}
		
	}
 	public ResultSet getBabyName(String register_name) {
		try {
			String sql2 = "select * from role_of_baby where register_name='" + register_name +"'";//select count(*)from register_info where phone_number='13576074107' and pw='123'
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())return resultSet;
//			else //对话框提示
//				LoginInterface.error.showAndWait();
 		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
 	public void getDataByUpdate(String sql) {
		try {
			stmt = this.getConnection().createStatement();
			int line = stmt.executeUpdate(sql);
			System.out.println("更新了" + line + "条记录\n");
			// 查询数据库
			String sqlQuery = "select *from student";
			rs = stmt.executeQuery(sqlQuery);
			while (rs.next())
				System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getInt(3) + "   "
						+ rs.getString(4) + "   " + rs.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库更新出错");
		}
	}
 	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
 			if (stmt != null) {
				stmt.close();
			}
 			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("关闭连接出错");
			System.exit(0);
		}
	}
 	public String getTable(){
		String str=this.table;
		return str;
	}
}
