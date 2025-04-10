<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

		
<script type="text/javascript">
$(document).ready(function () {
    $("#birthdate").datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        yearRange: "1900:2025"
    });
    $("#findIdBtn").click(function () {
        const name = $("#name").val().trim();
        const phone = $("#phone").val().trim();
        const email = $("#email").val().trim();
        const birthdate = $("#birthdate").val().trim();

        if (!name || !phone || !email || !birthdate) {
            alert("모든 항목을 입력해주세요.");
            return;
        }
		alert(name)
        $.ajax({
            url: "/find/findId.do",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ name, phone, email, birthdate }),
            success: function (res) {
            	console.log(res);
                if (res.success) {
                    alert("회원님의 아이디는: " + res.userId);
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
<h1>아이디 찾기</h1>
<form id="FindIdForm" method="POST">
    <table id="idFindtable" border="1">
        <tr>
            <td>*이름</td>
            <td><input type="text" id="name" placeholder="문주성"></td>
        </tr>
        <tr>
            <td>*전화번호</td>
            <td><input type="tel" id="phone" placeholder="010-1234-5678"></td>
        </tr>
        <tr>
            <td>*이메일</td>
            <td><input type="email" id="email" placeholder="abc@naver.com"></td>
        </tr>
        <tr>
            <td>*생년월일</td>
            <td><input type="text" id="birthdate" placeholder="YYYY-MM-DD"></td>
        </tr>
    </table>
    <div class="button-group">
        <button type="button" id="findIdBtn">아이디찾기</button>
        <button type="reset">취소</button>
    </div>
</form>
</body>
</html>