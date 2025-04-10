<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
</head>
<body>
<!--  헤더 포함: 가장 위에 위치 -->
<jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
<script type="text/javascript">
	$(document).ready(function () {
		$("#background").hide();
		$("#p").hide();
		$("#p1").hide();
	});
	
	
</script>

<h1>비밀번호 찾기</h1>
<form method="POST" id="findPwForm">
    <table>
        <tr>
            <td>*이름</td>
            <td><input type="text" name="name" placeholder="홍길동"/></td>
        </tr>
        <tr>
            <td>*아이디</td>
            <td><input type="text" name="userId" placeholder="abc123"/></td>
        </tr>
        <tr>
            <td>*전화번호</td>
            <td><input type="tel" name="phonenumber" placeholder="010-0000-0000"/></td>
        </tr>
        <tr>
            <td>*생년월일</td>
            <td><input type="text" id="birthdate" name="birthdate" placeholder="MM/DD/YYYY" required></td>
        </tr>
        
        <tr>
		    <td>*이메일</td>
		    <td>
		        <input type="email" id="email" placeholder="you@example.com">
		        <button type="button" onclick="sendEmailVerification($('#email').val())">인증코드 전송</button>
		    </td>
		</tr>
    </table>
    <div class="button-group">
        <button type="button" id="findPW">비밀번호찾기</button>
        <button type="button">취소</button>
    </div>
</form>

<script>
$("#findPW").click(function () {
    const name = $("input[name='name']").val();
    const userId = $("input[name='userId']").val();
    const phone = $("input[name='phonenumber']").val();
    const birthdate = $("#birthdate").val();

    $.ajax({
        url: "/find/FindPw.do",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            name: name,
            userId: userId,
            phone: phone,
            birthdate: birthdate
        }),
        success: function (response) {
            if (response.success) {
                alert("비밀번호는: " + response.password); // 실제 서비스에서는 임시 비밀번호 발급 또는 이메일 발송 권장
            } else {
                alert("일치하는 정보가 없습니다.");
            }
        },
        error: function () {
            alert("서버 오류 발생");
        }
    });
    
    function sendEmailVerification(email) {
        $.ajax({
            url: "/EmailAuthController",
            type: "POST",
            data: { email: email },
            success: function (response) {
                if (response.success) {
                    alert("이메일로 인증 코드가 전송되었습니다.");
                } else {
                    alert("이메일 전송 실패");
                }
            },
            error: function () {
                alert("서버 오류 발생");
            }
        });
    }
});
</script>]
</body>
</html>