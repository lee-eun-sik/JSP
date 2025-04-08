<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
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
<jsp:include page="/WEB-INF/jsp/user/header.jsp"></jsp:include>

<script type="text/javascript">
$(document).ready(function () {
	 $("#background").hide(); // 헤더 배경 사진 숨기기
     $("#p").hide(); // 단락 숨기기
	 $("#p1").hide(); //단락 숨기기
	 $("#join").hide(); // 회원가입 버튼 숨기기
	 $("#login").hide(); // 로그인 버튼 숨기기
});
</script>


			<form method="GET"  id="FindIdForm" action="#">
						<h1>아이디 찾기</h1>
						<table id="idFindtable">
		                    <tr>
		                        <td>*이름</td>
		                        <td><input type="text" id="name"name="name" placeholder="문주성"></td>
		                    </tr>
		                    <tr>
		                        <td>*전화번호</td>
		                        <td><input type="tel" id="phone"  name="phone" placeholder="전화번호"></td>
		                    </tr>
		                    <tr>
		                        <td>*이메일</td>
		                        <td><input type="email" id="email"  name="email" placeholder="이메일"></td>
		                    </tr>
		                    <tr>
		                        <td>*생년월일</td>
		                        <td><input type="text" id="birthdate" name="birthdate" placeholder="MM/DD/YYYY" required></td>
		                    </tr>
		                </table>
						<div class="button-group">
		                    <button type="button" id="findid">아이디찾기</button>
		                    <button type="button">취소</button>
		                </div>
			</form>
<script type="text/javascript">			
$(document).ready(function () {
    $("#birthdate").datepicker({
        dateFormat: "mm/dd/yy",
        changeMonth: true,
        changeYear: true,
        yearRange: "1900:2025"
    });

    $("#background, #p, #p1, #join, #login").hide();

    $("#findid").click(function (event) {
        event.preventDefault();

        let name = $("#name").val();
        let phone = $("#phone").val();
        let email = $("#email").val();
        let birthdate = $("#birthdate").val();

        if (!name || !phone || !email || !birthdate) {
            alert("모든 항목을 입력해주세요.");
            return;
        }

        $.ajax({
            url: "/find/findId.do",
            method: "GET",
            data: {
                name: name,
                phone: phone,
                email: email,
                birthdate: birthdate
            },
            dataType: "json",
            success: function (response) {
                if (response.success) {
                    alert("회원님의 아이디는 '" + response.userId + "' 입니다.");
                } else {
                    alert("일치하는 회원 정보가 없습니다.");
                }
            },
            error: function () {
                alert("서버 오류가 발생했습니다.");
            }
        });
    });
});
	
</script>
</body>
</html>