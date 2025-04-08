<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<style>
<style>
body {
        background-image: url('<%= request.getContextPath() %>/images/pet.jpg'); 
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
    }
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/user/header.jsp" />
<script type="text/javascript">
$(document).ready(function () {
	 $("#background").hide(); // 헤더 배경 사진 숨기기
     $("#p").hide(); // 단락 숨기기
	 $("#p1").hide(); //단락 숨기기
	 $("#join").hide(); // 회원가입 버튼 숨기기
	 $("#login").hide(); // 로그인 버튼 숨기기
});
</script>

<form method="POST"  id="FindPWForm" action="/user/findPw.do">
						<h1>비밀번호 찾기</h1>
						<table id="pwFindTable">
							<tr>
		                        <td>*아이디</td>
		                        <td><input type="text" name="id" placeholder="아이디"></td>
		                    </tr>
		                    <tr>
		                        <td>*이름</td>
		                        <td><input type="text" name="name" placeholder="문주성"></td>
		                    </tr>
		                    <tr>
		                        <td>*전화번호</td>
		                        <td><input type="tel" name="phone" placeholder="전화번호"></td>
		                    </tr>
		                    <tr>
		                        <td>*이메일</td>
		                        <td><input type="email" name="email" placeholder="이메일"></td>
		                    </tr>
		                    <tr>
		                        <td>*생년월일</td>
		                        <td><input type="text" id="birthdate" name="birthdate" placeholder="MM/DD/YYYY" required></td>
		                    </tr>
		                </table>
						<div class="button-group">
		                    <button type="button">아이디찾기</button>
		                    <button type="button">취소</button>
		                </div>
			</form>
</body>
</html>