<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>예약신청목록</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reservation/list.css">
</head>
<body>
	<div class="container">
	<h2>예약신청 목록</h2>
	<table border="1">
		<thead>
			<tr>
				<th class="num">아이디</th>
				<th>날짜</th>
				<th>주소</th>
				<th class="num">품종</th>
				<th class="num">펫이름</th>
				<th>전화번호</th>
				<th class="num">펫시터</th>
				<th class="num2">일급</th>
				<th class="num3">관리</th>
			</tr>
		</thead>
		<tbody >
		<!-- 게시글 목록을 반복 출력 -->
			<c:forEach var="reservation"  items="${reservationList}">
				<tr>
				<!-- 각각의 게시글 정보를 출력 -->
					<td class="num" >${reservation.createId}</td> <!-- 게시글의 순번 (DB에서 ROW_NUMBER()로 생성된 값) -->
					<td>${fn:substring(reservation.startDate, 0, 10)}</br>
					 &nbsp;&nbsp;&nbsp;~ ${fn:substring(reservation.endDate, 0, 10)}</td> <!-- 게시글 제목 -->
					<td>${reservation.address}</td> <!-- 게시글 작성자 ID -->
					<td class="num">${reservation.variety}</td> <!-- 조회수 -->
					<td class="num">${reservation.petName}</td> <!-- 작성 날짜 -->
					<td>${reservation.phoneNumber}</td>
					<td class="num">${reservation.sitter}</td>
					<td class="num2">${reservation.price}원</td>
					<!-- 상세보기 링크 (게시글 ID를 URL 파라미터로 전달) -->
					<td class="num3"><a class="bt1" href="view.do?id=${reservation.boardId}">보기</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagenation">
	<!-- 페이지 네비게이션 (페이징 기능) -->
	<ul>
	<!-- 이전 페이지로 이동 버튼 -->
		<c:if test=" ${currentPage  > 1}">
		<!-- 현재 페이지가 1보다 클 때만 표시 -->
			<a class="paginationbt" href="list.do?page=${currentPage-1}&size=${size}">&laquo;</a>
			<!-- '&laquo;'는 왼쪽 화살표 기호로 이전 페이지 표시 -->
		</c:if>
		<!-- 페이지 번호 목록 출력 -->
		<c:forEach begin="1" end="${totalPage}" var="i">
		<!-- 현재 페이지와 동일하면 굵은 글씨로 표시 -->
			<a class="paginationbt"  href="list.do?page=${i}&size=${size}"   			
				<c:if test="${i == currentPage }"> style="font-weight : bold;"</c:if>
				>${i}</a>				
		</c:forEach>
		<!-- 다음 페이지로 이동 버튼 -->
		<c:if test="${currentPage < totalpages }">
		<!-- 현재 페이지가 마지막 페이지보다 작을 때만 표시 -->
			<a class="paginationbt"  href="list.do?page=${currentPage+1}&size=${size}">&raquo;</a>
			<!-- '&raquo;'는 오른쪽 화살표 기호로 다음 페이지 표시 -->
		</c:if>
	</ul>
	</div>
	<a href="/user/main.do">메인 페이지로 이동</a>
</body>
</html>