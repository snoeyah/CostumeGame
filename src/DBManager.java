import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.*;

public class DBManager {
	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/usercostume";
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	private RankData data;
	private ArrayList<RankData> dataList;
	
	// db에서 데이타를 받아오는 함수 
	public void getAll() {
		
		dataList = new ArrayList<RankData>();
		
		connectDB(); // db 연결
		try {
			pstmt = (PreparedStatement) conn.prepareStatement("select * from new_table"); // 쿼리 준비
			rs = pstmt.executeQuery(); // 쿼리 실행
			
			while (rs.next()) {
				data = new RankData();
		
				data.setName(rs.getString("username")); 
				data.setScore(rs.getInt("score"));
				
				dataList.add(data);
				System.out.println(data.getName()+" "+data.getScore());
			}
			closeDB(); // db 연결 끊음
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setMember(String name, int scr) {
		connectDB(); // db 연결
		try {
			pstmt = (PreparedStatement) conn.prepareStatement("insert into new_table (username, score) values(?,?)"); // username, score칼럼에 ?, ?넣음
			pstmt.setString(1, name); // 첫 번째 물음표 자리에 name 넣음
			pstmt.setInt(2, scr); // 두번쨰 물음표 자리에 scr 넣음
			
			pstmt.executeUpdate(); // 쿼리 실행
			closeDB(); // db 연결 끊음
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void connectDB() {
		try {
			Class.forName(jdbcDriver);
			conn = (Connection) DriverManager.getConnection(jdbcUrl, "root", "0115");
		} catch (Exception e) {
		}
	}

	private void closeDB() {
		try {
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// 사용자 정보를 저장한 ArrayList 반환하는 함수
	public ArrayList<RankData> getList() { return dataList; }
}
