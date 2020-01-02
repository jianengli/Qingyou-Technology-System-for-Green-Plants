package qingyouSystem;
 import java.sql.*;
import java.util.ArrayList;
 public class ConnectSql {
 	final String Drive = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����������������ϵ
 	final String URL = "jdbc:sqlserver://localhost:1433;databaseName=qingyouSystem"; // ���ݿ��ַ
 	final String USER = "sa"; // �û���
 	final String PASSWORD = "123"; // ����
 	String table=null;
 	Connection conn = null;// DriverManager.getConnection(URL ,USER,PASSWORD);;
							// // �������ݿ����Ӷ���
 	Statement stmt = null; // ����SQL�������
 	ResultSet rs = null; // ������������
 	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*ConnectSql outerClass = new ConnectSql();
		outerClass.getConnection();
		outerClass.getRegInfoByQuery("select * from register_info");*/
	}
 	private Connection getConnection() {
		try {
			// ��������
			Class.forName(Drive);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�������ݿ�ʧ��");
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
			System.out.println("ע��ɹ�,��ȥ��½�ɣ�");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ע��ʧ��,���û��Ѿ�ע�ᣡ");
		}
 	}
	public void onlineMarkBackToZero() {
		try {
 			String sql2 = "update register_info set isChatting=0 ";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ�ܣ�����״̬�Ǽǳ���");
		}
	}
	public void onlineMarkBackToZeroForAClient(String oneQuittng) {
		try {
 			String sql2 = "update register_info set isChatting=0 where register_name='"+oneQuittng+"'";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
			//ServerThread.sendMsg2Me("��һλ�û������ˣ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onlineMark(String register_name) {
		try {
 			String sql2 = "update register_info set isChatting=1 where register_name='"+register_name+"'";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql2);
			//System.out.println("ע��ɹ�,��ȥ��½�ɣ�");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ�ܣ�����״̬�Ǽǳ���");
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
			//System.out.println("ע��ʧ��,���û��Ѿ�ע�ᣡ");
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
			//System.out.println("ע��ʧ��,���û��Ѿ�ע�ᣡ");
		}
		return "";
 	}
 	public void storeBabyName(String strbabyName, String strSex, String strRegisterName) {
		try {
 			String sql = "insert into role_of_baby values ('" + strbabyName + "', '" 
			+ strSex + "','" + strRegisterName +"')";
			stmt = this.getConnection().createStatement();
			stmt.executeUpdate(sql);
			System.out.println("�����ɹ�,��ʼ��Ϸ�ɣ�");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ�ܡ�");
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
			System.out.println("����ֲ��״̬�ɹ���");
 		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����ʧ�ܣ�");
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
			//System.out.println("ע��ʧ��,���û��Ѿ�ע�ᣡ");
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
			//System.out.println("ע��ʧ��,���û��Ѿ�ע�ᣡ");
			return null;
		}
		
	}
 	public ResultSet getBabyName(String register_name) {
		try {
			String sql2 = "select * from role_of_baby where register_name='" + register_name +"'";//select count(*)from register_info where phone_number='13576074107' and pw='123'
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())return resultSet;
//			else //�Ի�����ʾ
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
			System.out.println("������" + line + "����¼\n");
			// ��ѯ���ݿ�
			String sqlQuery = "select *from student";
			rs = stmt.executeQuery(sqlQuery);
			while (rs.next())
				System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getInt(3) + "   "
						+ rs.getString(4) + "   " + rs.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���ݿ���³���");
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
			System.out.println("�ر����ӳ���");
			System.exit(0);
		}
	}
 	public String getTable(){
		String str=this.table;
		return str;
	}
}
