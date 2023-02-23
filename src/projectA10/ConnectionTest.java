package projectA10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

	public static void main(String[] args) {
		Connection con = null;
		
		try {
			// connector 열어보면 이 구조로 되어있음, driver 읽어와서 사용할 수 있게 해줌
			Class.forName("com.mysql.cj.jdbc.Driver");
			// db와 연결
			String url = "jdbc:mysql://localhost:3306/condb";
			con = DriverManager.getConnection(url,"user","0000");
			System.out.println("연결 성공");
		} catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch(SQLException e) {
			System.out.println("에러"+e.getMessage());
		} finally {
			try {
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
