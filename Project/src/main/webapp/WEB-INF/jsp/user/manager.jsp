<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
</head>
<body>
 <%-- 현재 페이지가 관리자 페이지임을 알리기 위해 requestScope에 설정 --%>
    <c:set var="pageName" value="manager" scope="request" />
	<c:import url="header.jsp"></c:import>
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
</body>
</html>