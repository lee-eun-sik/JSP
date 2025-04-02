<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 메인 페이지 </title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<style>
	#mypage-container {
        text-align: center;
    }

    #logoutForm {
        display: inline-block;
        text-align: left;
        margin-top: 10px;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 10px;
        background-color: #f9f9f9;
        width: 70%;
        max-width: 400px;
    }

    #logoutTable {
        width: 100%;
    }

    #logoutTable td {
        padding: 8px;
    }

    #logoutTable input {
        width: 100%;
        padding: 8px;
        box-sizing: border-box; /* 패딩 포함 크기 유지 */
    }
	#logoutForm button {
        display: block;
        margin: 15px auto;
        padding: 10px 15px;
        background-color: skyblue;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        width: 30%;
    }
    
    .button-group {
        display: flex;
        justify-content: center; /* 버튼을 중앙 정렬 */
        gap: 10px; /* 버튼 사이 간격 */
        margin-top: 15px;
    }

    .button-group button {
        flex: 1; /* 버튼 크기 균등 분배 */
        padding: 10px;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    
</style>
</head>
<body>
				<c:choose>
							<c:when test="${empty sessionScope.user}">
											<c:redirect url="/user/login.do" />
							</c:when>
							<c:otherwise>
									<div id="mypage-container">
									    <h1 id="mypage">마이페이지</h1>
									
									    <form method="post" id="logoutForm">
									        <p><strong>${sessionScope.user.userId}</strong>님 안녕하세요.</p>
									        
									        <table id="logoutTable">
									            <tr>
									                <td>비밀번호</td>
									                <td><input type="password" name="password" placeholder="비밀번호"></td>
									            </tr>
									            <tr>
									                <td>전화번호</td>
									                <td><input type="tel" name="phone" placeholder="전화번호"></td>
									            </tr>
									            <tr>
									                <td>이메일</td>
									                <td><input type="email" name="email" placeholder="이메일"></td>
									            </tr>
									            <tr>
									                <td>생년월일</td>
									                <td><input type="text" id="birthdate" name="birthdate" placeholder="MM/DD/YYYY" required></td>
									            </tr>
									        </table>
									
									        <input type="hidden" name="id" value="${sessionScope.user.userId}">
									        <div class="button-group">
										        <button type="button">회원탈퇴</button>
										        <button type="button">회원정보수정</button>
										        <button type="submit">로그아웃</button>
									        </div>
									        <br>
									        <a href="/user/userInfo.do">유저정보 페이지로 이동</a>
									    </form>
									</div>	
										<script type="text/javascript">
											$(document).ready(function() {
												//로그아웃 폼에 섬밋이벤트시 작동
												$("#logoutForm").submit(function(event) {
													event.preventDefault(); // 기본 폼 제출 방지
													
													$.ajax({
														url: '/user/logout.do', // 로그인 요청 URL
														type: 'POST',
														data: $(this).serialize(), // 폼 데이터 직렬화
														dataType: 'json',
														success: function(response) {
															// 응답 처리
															if (response.success) {
																alert("로그아웃에 성공하셨습니다.");
																window.location.href = '/user/login.do'; // 로그인 성공 후 메인 페이지로 이동
															} else {
																alert("로그아웃에 실패하셨습니다.");
															}
														},
														error: function() {
															alert("통신 실패");
														}
													});
												});
												
												$(".button-group button:contains('회원정보수정')").click(function () {
													let userData = {
															id: $("input[name='id']").val(),
															phone: $("input[name='phone']").val(),
															email: $("input[name='email']").val(),
															birthdate: $("input[name='birthdate']").val()
													};
													
													$.ajax({
														url: "/user/update.do",
														type: "POST",
														data: userData,
														dataType: "json",
														success: function(response) {
															alert(response.message);
															if (response.success) {
																location.reload();
															}
														},
														error: function() {
															alert("통신 오류가 발생했습니다.");
														}
													});
												});
											});
										</script>
										<script type="text/javascript"> 
										$(document).ready(function() {
											$(".button-group button:contains('회원탈퇴')").click(function () {
												if (!confirm("정말로 탈퇴하시겠습니까?")) {
													return;
												}
												
												$.ajax({
													url: "/user/delete.do",
													type: "POST",
													data: { userId: $("input[name='id']").val() },
													dataType: "json",
													success: function(response) {
														alert(response.message);
														if (response.success) {
															window.location.href = "/user/login.do"; // 탈퇴 후 로그인 페이지로 이동
														}
													},
													error: function() {
														alert("통신 오류가 발생했습니다.");
													}
												});
											});
										});
										</script>
										<script type="text/javascript">
										$(document).ready(function() {
										    $("#birthdate").datepicker({
										        dateFormat: "mm/dd/yy", // MM/DD/YYYY 형식 설정
										        changeMonth: true,
										        changeYear: true,
										        yearRange: "1900:2025"
										    });
										});
										</script>	
							</c:otherwise>
				</c:choose>			
</body>
</html>