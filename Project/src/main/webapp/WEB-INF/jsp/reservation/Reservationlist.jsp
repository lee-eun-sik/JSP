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
<script type="text/javascript">
	$(document).ready(function() {
			$("#background").hide(); // 헤더 숨기기
			$("#p").hide(); // 단락 숨기기
			$("#p1").hide(); //단락 숨기기
	});
</script>	
<jsp:include page="../user/header.jsp" />
<h2>신청된 예약목록</h2>
<div>
	<button type="button" onclick="location.href='/user/main.do'">마이페이지</button>
	<button type="button" onclick="location.href='/reservation/Reservationlist.do'">예약관리</button>
	<button type="button" onclick="location.href='/member/Memberlist.do'">회원관리</button>
</div>

<table border="1">
		<thead> 
				<tr>
							<th>아이디</th>
							<th>날짜</th>
							<th>주소</th>
							<th>품종</th>
							<th>펫이름</th>
							<th>전화번호</th>
							<th>펫시터</th>
							<th>일급</th>
							<th>관리</th>
				</tr>
		</thead>
		<tbody>
				<c:forEach var="reservation" items="${reservationList}">
			    <tr>
			        <td>${reservation.reservationId}</td>
			        <td>${reservation.startDate}~${reservation.endDate}</td>
			        <td>${reservation.address}</td>
			        <td>${reservation.variety}</td>
			        <td>${reservation.petName}</td>
			        <td>${reservation.phoneNumber}</td>
			        <td>${reservation.sitterName}</td>
			        <td>${reservation.price}</td>
			        <td><button type="button" onclick="acceptReservation('${reservation.reservationId}')">수락</button>
			            <button type="button" onclick="rejectReservation('${reservation.reservationId}')">거절</button>
			        </td>    
			    </tr>
			</c:forEach>			
			<c:if test="${empty reservationList}">
			  <tr>
			    <td colspan="9" style="text-align: center;">검색 결과가 없습니다.</td>
			  </tr>
			</c:if>			
		</tbody>
</table>
</body>
</html>