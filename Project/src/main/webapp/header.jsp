<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더 메인 페이지</title>
<style>
	nav {
		display: flex; /* nav 내부 요소들을 가로로 정렬*/
		al
	}
</style>
</head>
<body>


<nav>
<c:url value="pet.png" var="data"></c:url>
<img src="${data}" width="70" height="70">
	<div>
		<a href="noticeBoard.jsp">공지사항</a>
		<a href="persitter.jsp">펫시터</a>
		<a href="reservationList.jsp">예약목록</a>
		<a href="reviewList.jsp">리뷰목록</a>
		<a href="community.jsp">커뮤니티</a>
	</div>
</nav>

</body>
</html>