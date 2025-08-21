package jsp12_paging_250821.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jsp12_paging_250821.dto.BoardDto;

public class BoardDao {
	private String driverName = "com.mysql.cj.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/jspdb";
	private String username = "root";
	private String password = System.getenv("DB_PASSWORD");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 1페이지당 보여지는 글의 개수 PAGE_SIZE
	public static final int RECORD_PER_PAGE = 20;
	// 게시판 하단에 표시될 현재 글의 개수로 만들어진 전체 페이지 수 [1][2][3][4][5][6][7][8][9][10]
	public static final int PAGE_GROUP_SIZE = 10;
	
	
	
	
	// 페이징 해서 게시글 가져오는 메서드. 매개변수로 page를 받아야 함.
	public List<BoardDto> boardList(int page) {
		int offset = (page - 1) * RECORD_PER_PAGE;
		String sql = "SELECT * FROM boardmvc ORDER BY bnum DESC LIMIT ? OFFSET ?";
		List<BoardDto> boardDtos = new ArrayList<>();

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, RECORD_PER_PAGE);
			pstmt.setInt(2, offset);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				
				BoardDto boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
				boardDtos.add(boardDto);
			} 

			} catch (Exception e) {
				System.out.println("DB 에러 발생, 페이징 실패");
				e.printStackTrace();
			} finally { 
				try {
					if (rs != null) {
						rs.close();
				}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return boardDtos;
	}
	
	
	
	
	
	// 게시판 모든 글 개수 반환하는 메서드 (count(*)로 하거나 count++로 하거나)
	public int countBoard() {
		String sql = "SELECT * FROM boardmvc";
		int count = 0;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				count++;
			} 

			} catch (Exception e) {
				System.out.println("DB 에러 발생, 모든 글 개수 반환 실패");
				e.printStackTrace();
			} finally { 
				try {
					if (rs != null) {
						rs.close();
				}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return count;
	}
	
	
	
	
	// 게시판 모든 글 개수 반환하는 메서드 (count(*)로 하거나 count++로 하거나)
		public int countBoard2() {
			String sql = "SELECT count(*) AS totalrecords FROM boardmvc";
			int totalrecords = 0;
			
			try {
				Class.forName(driverName);
				conn = DriverManager.getConnection(url, username, password);
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
				    totalrecords = rs.getInt("totalrecords");
				}

				} catch (Exception e) {
					System.out.println("DB 에러 발생, 모든 글 개수 반환 실패");
					e.printStackTrace();
				} finally { 
					try {
						if (rs != null) {
							rs.close();
					}
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {
							conn.close();
					}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			return totalrecords;
		}

	
}
