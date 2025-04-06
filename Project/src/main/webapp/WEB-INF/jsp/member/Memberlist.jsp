<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록 관리</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<style>
body {
        background-image: url('<%= request.getContextPath() %>/images/pet.jpg'); /* 배경 이미지 */
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        margin: 0 auto;
        padding: 0 auto;
    }
</style>    
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
<h2>가입된 회원목록</h2>
<div>
	<button type="button" onclick="location.href='/user/main.do'">마이페이지</button>
	<button type="button">예약관리</button>
	<button type="button" onclick="location.href='/member/Memberlist.do'">회원관리</button>
</div>
<table border="1">
		<thead> 
				<tr>
							<th>이름</th>
							<th>성별</th>
							<th>아이디</th>
							<th>비밀번호</th>
							<th>전화번호</th>
							<th>이메일</th>
							<th>가입일자</th>
							<th>관리</th>
				</tr>
		</thead>
		<tbody>
				<c:forEach var="user" items="${userList}">
			    <tr>
			        <td>${user.username}</td>
			        <td>${user.gender}</td>
			        <td>${user.userId}</td>
			        <td>${user.decryptedPassword}</td>
			        <td>${user.phonenumber}</td>
			        <td>${user.email}</td>
			        <td>
					  <c:if test="${not empty user.createDt}">
					    <fmt:formatDate value="${user.createDt}" pattern="yyyy-MM-dd"/>
					  </c:if>
					</td>
			        <td>
			            <button type="button">탈퇴</button>    
			        </td>
			    </tr>
			</c:forEach>							
		</tbody>
		
<!-- 페이지네이션 예시 -->
<div class="pagination">
    <c:if test="${page > 1}">
        <a href="?page=${page - 1}">Previous</a>
    </c:if>
    <c:forEach begin="1" end="${totalPages}" var="p">
        <a href="?page=${p}" class="${p == page ? 'active' : ''}">${p}</a>
    </c:forEach>
    <c:if test="${page < totalPages}">
        <a href="?page=${page + 1}">Next</a>
    </c:if>
</div>				
</table>
</body>
</html>