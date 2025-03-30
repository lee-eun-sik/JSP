<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		$("#joinForm").submit(function (event) {
			event.preventDefault();//기본 제출 방지
			let userId = $("#userId").val().trim();
			let username = $("#username").val().trim();
			let password = $("#password").val().trim();
			let email = $("#email").val().trim();
			
			if (!validationUtil.isEmpty(userId)) {
				alert("아이디를 입력해주세요.")
				$("#userId").focus();
				return;
			}
			
			if (!validationUtil.maxLength(userId, 20)) {
				alert("아이디는 최대 20자까지 입력할 수 있습니다.");
			}
			
			if (!validationUtil.maxLength(username, 15)) {
				alert("이름은 최대 15자까지 입력할 수 있습니다.")
			}
			
			if (!validationUtil.isEmpty(password)){
				alert("비밀번호를 입력해주세요.")
				$("#password").focus();
				return;
			}
			
			if (!validationUtil.maxLength(password, 20)) {
				alert("비밀번호는 최대 20자까지 입력할 수 있습니다.")
			}
			
			if (!validationUtil.isEmpty(email)) {
				alert("아이디를 입력해주세요.")
				$("#email").focus();
				return;
			}
			
			if (!validationUtil.maxLength(email, 100)){
				alert("이메일 최대 20자까지 입력할 수 있습니다.")
			}
			
			if (!validationUtil.isEmpty(email)) {
				alert("올바른 이메일을 입력해주세요.")
				$("#userId").focus();
				return;
			}
			
			// 모든 검증 통과 후 AJAX 요청
					ajaxRequest (
												"/user/register.do",
												$("#joinForm").serialize(),
												function(response) {
													if(response.success) {
														alert("회원가입 성공하셨습니다."
																+"로그인 페이지로 이동합니다.");
														window.location.href =
																	"/user/login.do";
													} else {
														alert("회원가입 실패하셨습니다.");
													}
												});
		})
		});
	
</script>
</head>
<body>
				<h2>회원가입</h2>
				<form id="joinForm">
					<lable for="userId">아이디:</lable>
					<input type="text" id="userId" name="userId"maxlength="20" placeholder="아이디 입력" required/>
					<br/>
					<label for="username">이름:</label>
					<input type="text" id="username" name="username" maxlength="15"  placeholder="이름 입력" required/>
					<br/>
					<label for="password">비밀번호:</label>
					<input type="password" id="password" name="password" maxlength="20" placeholder="비밀번호 입력" required/>
					<br/>
					<label for="email">이메일:</label>
					<input type="email" id="email" name="email" maxlength="100" placeholder="이메일 입력" required/>
					<br/>
					<button type="submit" id="registerBtn">가입하기</button>
				</form>
				<a href="/user/login.do">로그인 페이지로 이동</a>
</body>
</html>