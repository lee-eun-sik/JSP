<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
body {
        background-image: url('<%= request.getContextPath() %>/images/pet.jpg'); /* 배경 이미지 */
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
    }
</style>
</head>
<body>
<!-- ✅ 헤더 포함: 가장 위에 위치 -->
<jsp:include page="header.jsp" />
			
			<form method="get"  id="loginForm">
						<h2>상표</h2>
						<h4>로그인</h4>
						<input type="text" name="id" placeholder="아이디"><br/>
						<input type="password" name="pass" placeholder="비밀번호">
						<div>
							<a href="FindID.jsp">아이디찾기</a>
							<a href="FIndPW.jsp">비밀번호찾기</a>
						</div>
						<button type="submit">로그인</button>
			</form>
			
			<script type="text/javascript">
			
				$(document).ready(function() {
					$("#background").hide(); // 헤더 배경 사진 숨기기
					$("#p").hide(); // 단락 숨기기
					$("#p1").hide(); //단락 숨기기
					$("#join").hide(); // 회원가입 버튼 숨기기
					$("#login").hide(); // 로그인 버튼 숨기기
						//로그인 폼에 섬밋이벤트시 작동
						$("#loginForm").submit(function(event) {
							event.preventDefault(); // 기본 폼 제출 방지
							
							$.ajax({
								url: '/user/loginCheck.do', // 로그인 요청 URL
								type: 'POST',
								data: $(this).serialize(), // 폼 데이터 직렬화
								dataType: 'json',
								success: function(response) {
									console.log(response);
									// 응답 처리
									if (response.success) {
										alert("로그인 성공하셨습니다.");
										window.location.href=response.redirectUrl; // JSON 응답에서 URL을 가져와 이동
									} else {
										alert("로그인에 실패하셨습니다.")
									}
								},
								error: function() {
									alert("통신 실패");
								}
							});
						});
				});
			</script>
</body>
</html>