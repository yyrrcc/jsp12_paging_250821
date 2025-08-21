<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>게시판 리스트 (페이징)</title>
	<link rel="stylesheet" href="boardListStyle.css">
	
</head>
<body>
	<main>
        <h2 class="title">게시글 목록 (페이징 연습)</h2>
        <div class="wrapper">    	
			<table class="table">
                <thead>
                    <tr class="header">
                        <th>번호</th>
                        <th>제목</th>
                        <th>아이디</th>
                        <th>조회수</th>
                        <th>글등록일</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- c:forEach로 돌려주기 -->
					<c:forEach var="boardDto" items="${boardDtos }">
						<tr>
		                    <td>${boardDto.bnum }</td>
							<td>${boardDto.btitle }</td>
		                    <td>${boardDto.memberid }</td>
		                    <td>${boardDto.bhit }</td>
		                    <td>${fn:substring(boardDto.bdate,0,10) }</td>         
		                </tr>
          	      </c:forEach>
                </tbody>
            </table>
            
        	<hr>
        	
        	<!-- 1부터 페이지수만큼 돌리기. foreach문 안에 ifelse문 -->
        	<c:forEach begin="1" end="${totalPages }" var="i">
        		<c:choose>
        			<c:when test="${i == currentPage }">
        				<a href="boardlist?page=${i }" class="buttoncurrent">${i }페이지</a>
        			</c:when>
        			<c:otherwise>
        				<a href="boardlist?page=${i }" class="button">${i }페이지</a>
        			</c:otherwise>
        		</c:choose>
        	</c:forEach>
        </div>
    </main>
</body>
</html>