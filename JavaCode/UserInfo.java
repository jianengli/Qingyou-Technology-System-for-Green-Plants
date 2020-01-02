package qingyouSystem;
 import java.sql.SQLException;
 public class UserInfo {
	public String getName() throws SQLException{
		if(LoginInterface.outerClass.getPersonalInfo(LoginInterface.userTextField.getText()).getString(4)==null)
			return "";
		else
    		return  LoginInterface.outerClass.getPersonalInfo(LoginInterface.userTextField.getText()).getString(4);
    }
    public void  addName(String str){
    	LoginInterface.listUsers.add(str);
    }
 	public void setName(String name) {
    }
}
