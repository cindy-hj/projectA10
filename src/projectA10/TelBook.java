package projectA10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TelBook {

	public static void main(String[] args) {
		String menu = "1.전화번호 입력\n2.이름으로 검색\n3.전화번호 삭제\n4.전화번호 수정\n5.종료\n6.전화번호로 검색\n7.메모내용 검색\n8.메모 수정\n";
		while(true) {
			System.out.println(menu);
			Scanner in = new Scanner(System.in);
			String selectMenu = in.nextLine();
			if(selectMenu.equals("5")) {
				System.out.println("프로그램 종료");
				break;
			}
			
			if(selectMenu.equals("1")) {
				telInsert();
			}
			
			if(selectMenu.equals("2")) {
				telSearch();
			}
			
			if(selectMenu.equals("6")) {
				tel_TelSearch();
			}
			
			if(selectMenu.equals("7")) {
				tel_MemoSearch();
			}
			
			if(selectMenu.equals("4")) {
				tel_modify();
			}
			
			if(selectMenu.equals("8")) {
				memo_modify();
			}
			
			if(selectMenu.equals("3")) {
				delete();
			}
		}	
	}
	/*
	 * 전화번호 입력
	 */
	static void telInsert() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Scanner in = new Scanner(System.in);
			
			System.out.println("이름:");
			String name = in.nextLine();
			System.out.println("전화번호:");
			String tel = in.nextLine();
			System.out.println("전화번호2:");
			String tel2 = in.nextLine();
			System.out.println("전화번호3:");
			String tel3 = in.nextLine();
			System.out.println("메모사항:");
			String memo = in.nextLine();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			
			// idx는 자동으로 들어가므로 제외
			String sql="INSERT INTO teltable (name,tel,tel2,tel3,memo) VALUES (?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, tel);
			pstmt.setString(3, tel2);
			pstmt.setString(4, tel3);
			pstmt.setString(5, memo);
			
			int count = pstmt.executeUpdate();
			
			if(count == 0) {
				System.out.println("입력 실패");
			} else {
				System.out.println("입력 성공");
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	/*
	 * 이름으로 검색하기
	 */
	static void telSearch() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			System.out.println("검색할 이름을 입력하세요.");
			Scanner in = new Scanner(System.in);
			String name = in.nextLine();
					
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
		
			String sql = "SELECT * FROM teltable where name like ?";
			pstmt = conn.prepareStatement(sql);
			// 포함되는 내용 다 검색하게 하면 보안상 문제 생김->스프링부트에서는 검색못하도록 막는 기능이 있음
			pstmt.setString(1, "%"+name+"%");
			
			rs = pstmt.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String name1 = rs.getString("name");
				String tel = rs.getString("tel");
				String tel2 = rs.getString("tel2");
				String tel3 = rs.getString("tel3");
				String memo = rs.getString("memo");
				
				System.out.println(idx+" "+name1+" "+tel+" "+tel2+" "+tel3+" "+" "+memo);
				count++;
			}
			if(count==0) {
				System.out.println("값이 없습니다.");
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	/*
	 * 전화번호로 검색
	 */
	static void tel_TelSearch() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			System.out.println("검색할 전화번호를 입력하세요.");
			Scanner in = new Scanner(System.in);
			String searchtel = in.nextLine(); // 검색할 전화번호
					
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			// where tel or tel2 or tel2 like? 로 하니 없는 번호 입력해도 출력됨
			String sql = "SELECT * FROM teltable where tel like? or tel2 like? or tel3 like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+searchtel+"%");
			pstmt.setString(2, "%"+searchtel+"%");
			pstmt.setString(3, "%"+searchtel+"%");
			
			rs = pstmt.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String tel2 = rs.getString("tel2");
				String tel3 = rs.getString("tel3");
				String memo = rs.getString("memo");
				
				System.out.println(idx+" "+name+" "+tel+" "+tel2+" "+tel3+" "+" "+memo);
				count++;
			}
			if(count==0) {
				System.out.println("값이 없습니다.");
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	/*
	 * 메모로 검색
	 */
	static void tel_MemoSearch() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			System.out.println("검색할 메모의 내용을 입력하세요.");
			Scanner in = new Scanner(System.in);
			String searchMemo = in.nextLine(); // 검색할 메모
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			String sql = "SELECT * from teltable where memo like ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+searchMemo+"%");
			
			rs = pstmt.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String tel2 = rs.getString("tel2");
				String tel3 = rs.getString("tel3");
				String memo = rs.getString("memo");
				
				System.out.println(idx+" "+name+" "+tel+" "+tel2+" "+tel3+" "+memo);
				count++;
			}
			if(count==0) {
				System.out.println("값이 없습니다.");
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * 전화번호 수정
	 */
	static void tel_modify() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println("전화번호를 수정할 인덱스를 입력하세요.");
			Scanner in = new Scanner(System.in);
			String midx = in.nextLine();
			System.out.println("tel:");
			String mtel = in.nextLine();
			System.out.println("tel2:");
			String mtel2 = in.nextLine();
			System.out.println("tel3:");
			String mtel3 = in.nextLine();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			String sql="UPDATE teltable set tel=?, tel2=?, tel3=? where idx=?"; 
			pstmt = conn.prepareStatement(sql);
						
			pstmt.setString(1, mtel);
			pstmt.setString(2, mtel2);
			pstmt.setString(3, mtel3);
			pstmt.setString(4, midx);
			int count = pstmt.executeUpdate(); // 전달할 쿼리가 만들어져서 반영
			if(count == 0) {
				System.out.println("데이터 수정 실패");
			} else {
				System.out.println("데이터 수정 성공");
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * 메모 수정
	 */
	static void memo_modify() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println("메모를 수정할 인덱스를 입력하세요.");
			Scanner in = new Scanner(System.in);
			String midx = in.nextLine();
			System.out.println("수정할 메모 내용을 입력하세요.");
			String mMemo = in.nextLine();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			String sql="UPDATE teltable set memo=? where idx=?"; 
			pstmt = conn.prepareStatement(sql);
						
			pstmt.setString(1, mMemo);
			pstmt.setString(2, midx);
			int count = pstmt.executeUpdate(); 
			if(count == 0) {
				System.out.println("데이터 수정 실패");
			} else {
				System.out.println("데이터 수정 성공");
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * 데이터 삭제
	 */
	static void delete() {
		// delete도 변경이 발생하는것이므로 update
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println("삭제할 인덱스를 입력하세요.");
			Scanner in = new Scanner(System.in);
			// idx는 int이므로 원칙적으로는 이렇게 써야함
			int didx = Integer.parseInt(in.nextLine());
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/condb";
			conn = DriverManager.getConnection(url,"user","0000");
			
			String sql="DELETE FROM teltable where idx=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, didx);
			int count = 0;
			count = pstmt.executeUpdate(); 
			if(count == 0) {
				System.out.println("데이터 삭제 실패");
			} else {
				System.out.println("데이터 삭제 성공");
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
