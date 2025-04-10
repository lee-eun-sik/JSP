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
<link rel="stylesheet" href="/js/jquery-ui-1.14.1.custom/jquery-ui-1.14.1.custom/jquery-ui.min.css">
<script src="/js/jquery-ui-1.14.1.custom/jquery-ui-1.14.1.custom/jquery-ui.min.js"></script>
<style>
body {
        background-image: url('<%= request.getContextPath() %>/images/pet.jpg'); 
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
    }
</style>
<script type="text/javascript">
	$(document).ready(function () {
		$("#background").hide(); // 헤더 숨기기
		$("#p").hide(); // 단락 숨기기
		$("#p1").hide(); //단락 숨기기
		$("#join").hide(); // 회원가입 버튼 숨기기
		$("#login").hide(); // 로그인 버튼 숨기기
		$("#checkDuplicate").click(function () {
			let userId = $("#userId").val().trim();
			
			if (userId === "") {
				alert("아이디를 입력하세요.");
				$("#userId").focus();
				return;
			}
			
			$.ajax({
				type: "POST",
				url: "/user/checkDuplicate.do",
				data: { userId: userId },
				success: function(response) {
					console.log(response)
					if (response.success) {
						alert("이미 사용중인 아이디입니다.");
					} else {
						alert("사용 가능한 아이디입니다.");
					}
				},
				error: function ()  {
					alert("서버 오류가 발생했습니다.");
				}
			});
		});
		$("#joinForm").submit(function (event) {
			event.preventDefault();//기본 제출 방지
			let userId = $("#userId").val().trim();
			let username = $("#username").val().trim();
			let password = $("#password").val().trim();
			let password_confirm = $("#password_confirm").val().trim();
			let gender = $("#input[name='gender']:checked").val()
			let email = $("#email").val().trim();
			let phone = $("#phonenumber").val()
			let birthdate = $("#birthdate").val().trim();
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
			
			if (!validationUtil.isEmpty(password_confirm,20)) {
				alert("비밀번호를 다시 입력해주세요.")
				$("#password_confirm").focus();
				return;
			}
			
			if (!validationUtil.maxLength(password_confirm,20)) {
				alert("비밀번호는 최대 20자까찌 입력할 수 있습니다.")
				return;
			}
			if (!validationUtil.isEmpty(email)) {
				alert("이메일를 입력해주세요.")
				$("#email").focus();
				return;
			}
			
			if (!validationUtil.maxLength(email, 100)){
				alert("이메일 최대 100자까지 입력할 수 있습니다.")
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
		});
		});
	
</script>
</head>
<body>
<!-- ✅ 헤더 포함: 가장 위에 위치 -->
<jsp:include page="header.jsp" />
				<h1>상표</h1>	
				<h2>회원가입</h2>
				<form id="joinForm" action="/user/register.do" method="POST">
					<label for="userId">*아이디</label>
					<input type="text" id="userId" name="userId"maxlength="20" placeholder="Placeholder" required/> 
					<button type="button" id="checkDuplicate">중복체크</button>
					<br/>
					<label for="password">*비밀번호</label>
					<input type="password" id="password" name="password" maxlength="20" placeholder="Placeholder" required/>
					<br/>
					<label for="password_confirm">*비밀번호확인</label>
					<input type="password" id="password_confirm" name="password_confirm" maxlength="20" placeholder="Placeholder" required/>
					<br/>
					<label for="username">*이름</label>
					<input type="text" id="username" name="username" maxlength="15"  placeholder="Placeholder" required/>
					<br/>
					<label for="gender">*성별</label>
					<input type="radio" name="gender" value="M"/>남성
					<input type="radio" name="gender" value="F"/>여성 <br>
					<label for="phone">*전화번호</label>
					<input type="tel" name="phonenumber" pattern="[0-9]{2,3}-[0-9]{4}-[0-9]{4}" placeholder="Placeholder" required>
					<br> 
					<label for="email">*이메일</label>
					<input type="email" id="email" name="email" maxlength="100" placeholder="Placeholder" required/>
					<br/>
					<label for="birthdate">*생년월일</label>
					<input type="text" id="birthdate" name="birthdate" placeholder="MM/DD/YYYY" required/><br>
					<div>
						<button type="submit" id="registerBtn">가입하기</button>
						<button type="button" id="registerCencel">가입취소</button>
					</div>
				</form>
<script type="text/javascript">
$(document).ready(function() {
    $("#birthdate").datepicker({
        dateFormat: "mm/dd/yy", // MM/DD/YYYY 형식 설정
        changeMonth: true,
        changeYear: true,
        yearRange: "1900:2025"
    });
    console.log(typeof $.datepicker);
});
</script>				
</body>
<script src="/js/common.js"></script>
</html>