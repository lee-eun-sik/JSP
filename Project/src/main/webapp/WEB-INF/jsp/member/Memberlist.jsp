<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록 관리</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<style>
	body {
        background-image: url('<%= request.getContextPath() %>/images/pet.jpg'); 
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        margin: 0 auto;
        padding: 0 auto;
    }
    
    .pagination a {
	    display: inline-block;
	    padding: 8px 12px;
	    margin: 2px;
	    text-decoration: none;
	    border: 1px solid #ccc;
	    border-radius: 4px;
	    color: #333;
	    font-weight: normal;
	}
	
	.pagination a.active {
	    background-color: #333;
	    color: #fff;
	    font-weight: bold;
	    pointer-events: none;
	}
	
	.pagination span {
	    display: inline-block;
	    padding: 8px 12px;
	    color: #999;
	}
</style>    
</head>
<body>
<script type="text/javascript">
$(document).ready(function() {
			$("#background").hide(); // 헤더 숨기기
			$("#p").hide(); // 단락 숨기기
			$("#p1").hide(); //단락 숨기기
			
			
			
		});
	</script>	
<jsp:include page="../user/header.jsp" />
<h2>가입된 회원목록</h2>
<div>
	<button type="button" onclick="location.href='/user/main.do'">마이페이지</button>
	<button type="button">예약관리</button>
	<button type="button" onclick="location.href='/member/Memberlist.do'">회원관리</button>
</div>
<table border="1">
		<thead> 
				<tr>
							<th>이름</th>
							<th>성별</th>
							<th>아이디</th>
							<th>비밀번호</th>
							<th>전화번호</th>
							<th>이메일</th>
							<th>가입일자</th>
							<th>관리</th>
				</tr>
		</thead>
		<tbody>
				<c:forEach var="user" items="${userList}">
			    <tr>
			        <td>${user.username}</td>
			        <td>${user.gender}</td>
			        <td>${user.userId}</td>
			        <td>****</td>
			        <td>${user.phonenumber}</td>
			        <td>${user.email}</td>
			        <td>
					  <c:if test="${not empty user.createDt}">
					    <fmt:formatDate value="${user.createDt}" pattern="yyyy-MM-dd"/>
					  </c:if>
					</td>
			        <td>
			            <button type="button" onclick="deleteUser('${user.userId}')">탈퇴</button>    
			        </td>
			    </tr>
			</c:forEach>							
		</tbody>
		

</table>
<!-- 페이징 영역 -->
<div class="pagination" style="text-align: center; margin-top: 30px;">
    <c:if test="${page > 1}">
        <a href="?page=${page - 1}">← Previous</a>
    </c:if>

    <!-- 시작 페이지 계산 -->
    <c:set var="startPage" value="${page - 2 < 1 ? 1 : page - 2}" />
    <c:set var="endPage" value="${page + 2 > totalPages ? totalPages : page + 2}" />

    <!-- 첫 페이지 출력 -->
    <c:if test="${startPage > 1}">
        <a href="?page=1">1</a>
        <span>...</span>
    </c:if>

    <!-- 가운데 페이지들 -->
    <c:forEach begin="${startPage}" end="${endPage}" var="p">
        <a href="?page=${p}" class="${p == page ? 'active' : ''}">${p}</a>
    </c:forEach>

    <!-- 끝 페이지 생략 ... -->
    <c:if test="${endPage < totalPages}">
        <span>...</span>
        <a href="?page=${totalPages}">${totalPages}</a>
    </c:if>

    <!-- 다음 페이지 -->
    <c:if test="${page < totalPages}">
        <a href="?page=${page + 1}">Next →</a>
    </c:if>
</div>
<script type="text/javascript">
	function deleteUser(userId) {
	    if (confirm(userId + " 회원을 정말 탈퇴하시겠습니까?")) {
	        $.ajax({
	            url: "/member/delete.do",
	            method: "POST",
	            data: { userId: userId },
	            success: function(response) {
	                alert("회원이 성공적으로 탈퇴되었습니다.");
	                location.reload(); // 페이지 새로고침
	            },
	            error: function() {
	                alert("회원 탈퇴 중 오류가 발생했습니다.");
	            }
	        });
	    }
	}
</script>
</body>
</html>