<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>    
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		$("#updateForm").submit(function (event) {
			event.preventDefault(); // 기본 이벤트 중지
			// 비밀번호가 비어있으면 전송 데이터에서 제외
			let formData = $("#updateForm").serializeArray();
			formData = formData.filter(field = > !(field.name === "password" && field.value.trim() === ""));
			$.ajax({
				type: "POST",
				url : "/user/update.do",
				data: $.param(formData), // 필터링된 데이터전송
				dataType: "json",
				success: function (fresponse) { // 변수명 수정
					if (response.success) {
						alert("회원정보가 수정되었습니다.");
						window.location.href = "/user/main.do"; // 메인 페이지로 이동
					} else {
						alert("회원정보 수정에 실패했습니다.");
					}
				},
				error: function () {
					alert("서버 오류가 발생했습니다. 다시 시도해주세요.");
				}
			});
		});
	});
</script>
</head>
<body>
		<h2>회원정보 수정</h2>
		<form id="updateForm" method="POST">
				<label for="userId">아이디</label>
				<input type="text" id="userId" name="userId" value="${sessionScope.user.userId}" readonly/>
				<br/>
				<label for="username">이름</label>
				<input type="text" id="username" name="username" value="${sessionScope.user.username}" required/>
				<br/>
				<label for="email">이메일</label>
				<input type="email" id="email" name="email" value="${sessionScope.user.email}" required/>
				<br/>
				<label for="password">새 비밀번호 (선택)</label>
				<input type="password" id="password" name="password" placeholder="변경하지 않으려면 비워두세요"/>
				<br/>
				<button type="submit">수정하기</button>
		</form>
</body>
</html>