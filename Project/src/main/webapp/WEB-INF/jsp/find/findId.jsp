<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
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
<h1>아이디 찾기</h1>
<form method="POST" id="findIdForm">
	<table>
		<tr>
			<td>*이름</td>
			<td><input type="text" name="name" placeholder="문주성"/></td>
		</tr>
		<tr>
			<td>*전화번호</td>
			<td><input type="tel" name="phonenumber" placeholder="010-1234-5678"></td>
		</tr>
		
		<tr>
			<td>*생년월일</td>
			<td><input type="text" id="birthdate" name="birthdate" placeholder="MM/DD/YYYY" required></td>
		</tr>
		<div class="button-group">
            <button type="button" id="findID">아이디찾기</button>
            <button type="button">취소</button>
        </div>			
	</table>
</form>
<script type="text/javascript">
$("#findID").click(function () {
    const name = $("input[name='name']").val();
    const phone = $("input[name='phonenumber']").val();
    const birthdate = $("#birthdate").val();

    $.ajax({
        url: "/find/FindID.do",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            name: name,
            phone: phone,
            email: "", // 이메일 입력란이 없다면 빈 문자열
            birthdate: birthdate
        }),
        success: function (response) {
            if (response.success) {
                alert("당신의 아이디는: " + response.userId);
            } else {
                alert("일치하는 정보가 없습니다.");
            }
        },
        error: function () {
            alert("서버 오류 발생");
        }
    });
});
</script>
</body>
</html>