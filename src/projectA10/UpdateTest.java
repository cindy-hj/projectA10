package projectA10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTest {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			String sql="UPDATE usertable set userName=?, email=? where id=?"; // 내용 수정
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "김길동");
			pstmt.setString(2, "a1test@test.com");
			pstmt.setInt(3, 3);
			int count = pstmt.executeUpdate();
			if(count == 0) {
				System.out.println("데이터 수정 실패");
			} else {
				System.out.println("데이터 수정 성공");
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
