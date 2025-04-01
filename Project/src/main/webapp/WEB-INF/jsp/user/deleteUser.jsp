<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
			event.preventDefault();
			
			
			if (!confirm("정말 탈퇴하시겠습니까?")) return;
			
			$.ajax({
				type: "POST",
				url : "/user/delete.do",
				data: { userId: $("#userId").val() },
				dataType : "json",
				success: function (response) {
					if (response.success) {
						alert("회원 탈퇴가 완료되었습니다");
						window.location.href = "/user/login.do";
					} else {
						alert("회원 탈퇴에 실패했습니다.");
					}
				}
			});
	});	
	});
</script>
</head>
<body>
		<h2>회원 탈되</h2>
		<form id="deleteForm">
			<input type="hidden" id="userId" name="userId" value="${sessionScope.user.userId}" />
			<button type="submit">탈퇴하기</button>
		</form>
</body>
</html>