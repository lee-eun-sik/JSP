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
.pagination {
    text-align: center;
    margin: 40px 0;
    font-family: 'Arial', sans-serif;
}

.pagination a, .pagination span {
    display: inline-block;
    padding: 6px 12px;
    margin: 0 3px;
    font-size: 14px;
    text-decoration: none;
    color: #333;
    border-radius: 25%;
    min-width: 32px;
    height: 32px;
    line-height: 32px;
    text-align: center;
    transition: background-color 0.3s, color 0.3s;
}

.pagination a:hover:not(.active):not(.disabled) {
    background-color: #ddd;
}

.pagination a.active {
    background-color: #000;
    color: #fff;
    font-weight: bold;
    pointer-events: none;
}

.pagination a.disabled, .pagination span.disabled {
    color: #ccc;
    cursor: default;
    pointer-events: none;
}

.pagination span {
    color: #999;
}

.search-container {
  position: fixed;
  bottom: -20px; /* 화면 하단에서 20px 위 */
  left: 50%; /* 가로 중앙 정렬을 위한 기준점 */
  transform: translateX(-50%); /* 가로 중앙 정렬 */
  display: flex;
  align-items: center;
  gap: 5px;
  z-index: 999; /* 다른 요소 위에 보이도록 */
  background-color: white; /* 선택사항: 배경 흰색 */
  padding: 10px;
  border-radius: 10px;
  box-shadow: none; /* 약간의 그림자 효과 */
  border:none;
}

.search-box {
  position: relative;
}

.search-box input {
  width: 200px;
  padding: 8px 30px 8px 10px;
  border: 1px solid #ccc;
  border-radius: 20px;
  background-color: #f5f0ff;
}

.search-box .search-btn {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 14px;
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
			<c:if test="${empty userList}">
			  <tr>
			    <td colspan="8" style="text-align: center;">검색 결과가 없습니다.</td>
			  </tr>
			</c:if>				
		</tbody>
		

</table>
<div class="pagination">

    <!-- Previous 버튼 -->
    <c:choose>
        <c:when test="${page > 1}">
            <a href="?page=${page - 1}">← Previous</a>
        </c:when>
        <c:otherwise>
            <a class="disabled">← Previous</a>
        </c:otherwise>
    </c:choose>

    <!-- 페이지 번호 -->
    <c:set var="startPage" value="${page - 2 < 1 ? 1 : page - 2}" />
    <c:set var="endPage" value="${page + 2 > totalPages ? totalPages : page + 2}" />

    <c:if test="${startPage > 1}">
        <a href="?page=1">1</a>
        <span class="disabled">...</span>
    </c:if>

    <c:forEach begin="${startPage}" end="${endPage}" var="p">
        <a href="?page=${p}" class="${p == page ? 'active' : ''}">${p}</a>
    </c:forEach>

    <c:if test="${endPage < totalPages}">
        <span class="disabled">...</span>
        <a href="?page=${totalPages}">${totalPages}</a>
    </c:if>

    <!-- Next 버튼 -->
    <c:choose>
        <c:when test="${page < totalPages}">
            <a href="?page=${page + 1}">Next →</a>
        </c:when>
        <c:otherwise>
            <a class="disabled">Next →</a>
        </c:otherwise>
    </c:choose>

</div>
<div class="search-container">
  <select id="searchType">
    <option value="user_id">아이디</option>
    <option value="user_name">이름</option>
  </select>

  <div class="search-box">
    <input type="text" id="searchKeyword" placeholder="검색어 입력">
    <button onclick="searchMember()" class="search-btn">🔍</button>
  </div>
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
	
	function searchMember() {
	    const type = $("#searchType").val();
	    const keyword = $("#searchKeyword").val().trim();

	    if (!keyword) {
	        alert("검색어를 입력해주세요.");
	        return;
	    }

	    location.href = "/member/Memberlist.do?searchType=" + type + "&searchKeyword=" + encodeURIComponent(keyword);
	}
</script>
</body>
</html>