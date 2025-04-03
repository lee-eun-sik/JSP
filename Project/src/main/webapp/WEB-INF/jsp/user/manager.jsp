<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<style>
body {
        background-image: url('<%= request.getContextPath() %>/images/pet.jpg'); /* 배경 이미지 */
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        margin: 0;
        padding: 0;
    }
    
    
    .signInfo {
    	position: absolute;
    	top: 20px;
    	right: 30px;
    	background-color: rgba(255, 255, 255, 0.8);
    	padding: 10px 15px;
    	border-radius: 10px;
    }
    
    .signInfo p {
    		magin: 0;
    		font-size: 16px;
    		font-weight: bold;
    }
</style>    
</head>
<body>
 
<jsp:include page="header.jsp" />

<!--  로그인된 경우 오른쪽 상단에 사용자 환영 메시지 표시  -->
<c:if test="${not empty sessionScope.user}">
	<div class="signInfo">
			<p id="userGreating">안녕하세요, <a href="main.do"><c:out value="${sessionScope.user.userId}"/>님!</a></p>
	</div>
</c:if>			
	<div class="manager-container">
		<h1>관리자 페이지</h1>
		<div class="container">
			<button type="button">마이페이지</button>
			<button type="button">예약관리</button>
			<button type="button">회원관리</button>
		</div>
	
			<form method="POST" id="managerForm">
				<p>당신은 관리자입니다.</p>
				
				*비밀번호 <input type="password" id="password" name="password"/> <br>
				
				<button type="button">비밀번호 변경</button>
			</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#background").hide(); // 헤더 숨기기
			$("#p").hide(); // 단락 숨기기
			$("#p1").hide(); //단락 숨기기
			
			
			
		});
	</script>	
	
	
</body>
</html>