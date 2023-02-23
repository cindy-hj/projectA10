package projectA10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTest {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			String sql="INSERT INTO usertable VALUES (?,?,?)"; // 테이블에 행이 3개 이므로
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, 3);
			pstmt.setString(2, "김똘똘");
			pstmt.setString(3, "aaa@test.com");
			int count = pstmt.executeUpdate();
			if(count == 0) {
				System.out.println("데이터 입력 실패");
			} else {
				System.out.println("데이터 입력 성공");
			}
		} catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch(SQLException e) {
			System.out.println("오류:"+e.getMessage());
		} finally {
			try {
				if(conn != null && !conn.isClosed()) { 
					conn.close();
				}
				if(pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
