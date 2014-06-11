package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectTest {
	public static void main(String[] srg) {
		String fileName = "database.properties";
		String userName = "supmid";// = "sa"; //默认用户名
		String userPwd = "unityprima";// = "root"; //密码
		Properties p = new Properties();
		try {
			String path = ConnectTest.class.getResource("/").getPath()+"/"+fileName;
//			/System.out.print(path);
			InputStream in = new FileInputStream(new File(path));
			// FileInputStream(fileName),不过这种方式找不到配置文件。有人说是在classes下，我调过了，不行。
			p.load(in);
			in.close();
			if (p.containsKey("username")) {
				userName = p.getProperty("username");
			}
			if (p.containsKey("password")) {
				userPwd = p.getProperty("password");
			}
		} catch (IOException ex) {
			Logger.getLogger(ConnectTest.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
		String dbURL = "jdbc:sqlserver://192.168.1.158:1433;databaseName=SUPMID;"; // 连接服务器和数据库sample
		@SuppressWarnings("unused")
		Connection dbConn;

		try {
			Class.forName(driverName);
			System.out.println(userName);
			System.out.println(userPwd);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("Connection Successful! "); // 如果连接成功
															// 控制台输出Connection
															// Successful!
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
