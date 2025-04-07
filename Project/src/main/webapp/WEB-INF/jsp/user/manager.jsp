<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/logout.js"></script>
<style>
	
    
    /* 로그인 정보 표시를 오른쪽 상단에 배치 */
    .signInfo {
    	position: absolute;
    	top: 20px;
    	right: 30px;
    	background-color: rgba(255, 255, 255, 0.8);
    	padding: 10px 15px;
    	border-radius: 10px;
    }
    
    .signInfo p {
    		margin: 0;
    		font-size: 16px;
    		font-weight: bold;
    }
</style>    
</head>
<body>
 
<jsp:include page="header.jsp" />

	
	<div class="manager-container">
		<h1>관리자 페이지</h1>
		<div class="container">
			<button type="button" onclick="location.href='/user/main.do?form=show'">마이페이지</button>
			<button type="button">예약관리</button>
			<button type="button" onclick="location.href='/member/Memberlist.do'">회원관리</button>
		</div>
	
			<form method="POST" id="managerForm">
				<p>당신은 관리자입니다.</p>
				
				*현재 비밀번호 <input type="password" id="currentPassword" name="currentPassword"/> <br>
			    *새 비밀번호 <input type="password" id="newPassword" name="newPassword"/> <br>
			    *새 비밀번호 확인 <input type="password" id="confirmPassword" name="confirmPassword"/> <br>
				
				<button type="button" id="changePasswordBtn">비밀번호 변경</button>
			</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#background").hide(); // 헤더 숨기기
			$("#p").hide(); // 단락 숨기기
			$("#p1").hide(); //단락 숨기기
			
			$("#changePasswordBtn").click(function() {
		        let currentPassword = $("#currentPassword").val();
		        let newPassword = $("#newPassword").val();
		        let confirmPassword = $("#confirmPassword").val();

		        if (newPassword !== confirmPassword) {
		            alert("새 비밀번호가 일치하지 않습니다.");
		            return;
		        }

		        $.ajax({
		            type: "POST",
		            url: "/user/changePassword.do",
		            contentType: "application/json",
		            data: JSON.stringify({
		                currentPassword: currentPassword,
		                newPassword: newPassword
		            }),
		            success: function(response) {
		                if (response.success) {
		                    alert("비밀번호가 성공적으로 변경되었습니다.");
		                    location.reload();
		                } else {
		                    alert(response.message);
		                }
		            },
		            error: function() {
		                alert("서버 오류가 발생했습니다.");
		            }
		        });
		    });
			
		});
	</script>	
	
	
</body>
</html>