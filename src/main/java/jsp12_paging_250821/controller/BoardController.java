package jsp12_paging_250821.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsp12_paging_250821.dao.BoardDao;
import jsp12_paging_250821.dto.BoardDto;

import java.io.IOException;
import java.util.List;

// boardlist 요청만 구현
@WebServlet("/boardlist") 
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDao boardDao = new BoardDao();

    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 처음 게시판 들어가면 무조건 1 페이지 내용이 출력 되어야 함 따라서 1로 초기값 해줘야 함
		int page = 1;
		
		// 총 글의 개수
		int totalrecords = boardDao.countBoard();
		// 총 글의 개수로 표현 될 전체 페이지의 수(예를 들면 총 37개 글이면 4페이지가 전달 되어야 함)
		// 총 글 개수 나누기 상수 71/10 -> 7.1 -> 올림 8
		int totalPages = (int) Math.ceil((double) totalrecords / BoardDao.RECORD_PER_PAGE);
		
		// 클라이언트가 누른 하단 페이지 넘버 가져오기
		if (request.getParameter("page") == null) {
			page = 1;
		} else {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		// 게시글 가져오기
		List<BoardDto> boardDtos = boardDao.boardList(page);
		request.setAttribute("boardDtos", boardDtos); // 클라이언트가 선택한 페이지에 해당하는 글들 (현재 10개씩)
		
		// 페이징 **************************************************
		request.setAttribute("currentPage", page); // 클라이언트가 현재 선택한 페이지 번호
		request.setAttribute("totalPages", totalPages); // 상수개씩(현재 10개씩) 계산된 전체 페이지 수
		
		int startPage = (((page - 1) / BoardDao.PAGE_GROUP_SIZE) * BoardDao.PAGE_GROUP_SIZE) + 1;
		int endPage = startPage + BoardDao.PAGE_GROUP_SIZE - 1;
		// 계산한 endPage 값 (startPage + 9) 이 실제 마지막 페이지 값보다 작으면 마지막 페이지 값으로 endPage 값을 대체
		if (endPage > totalPages) {
			endPage = totalPages;
		}
		request.setAttribute("startPage", startPage); // 하단 페이지 넘버 중 시작 페이지?
		request.setAttribute("endPage", endPage); // 하단 페이지 넘버 중 마지막 페이지?
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("boardList.jsp");
		dispatcher.forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
