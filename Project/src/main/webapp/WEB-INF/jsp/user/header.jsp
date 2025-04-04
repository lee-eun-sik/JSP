<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더 메인 페이지</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/logout.js"></script>
<style>

	
	nav {
        display: flex; /* nav 내부 요소들을 가로로 정렬 */
        align-items: center; /* 요소들을 세로 중앙 정렬 */
        gap: 100px; /* 이미지와 메뉴 사이 여백 */
    }
    
    .menu {
        display: flex; /* 메뉴 항목을 가로 정렬 */
        gap: 15px; /* 각 메뉴 항목 간 여백 */
        position: relative;
    }
    .menu a {
	    display: inline-block; /* 가로 길이를 적용하기 위해 inline-block 사용 */
	    width: 100px; /* 가로 길이 조정 */
	    text-align: center; /* 텍스트 중앙 정렬 */
        text-decoration: none; /* 링크 밑줄 제거 */
        color: black; /* 글자색 */
        background-color: orange; /* 배경색 */
        padding: 10px 15px; /* 내부 여백 */
        border-radius: 40px; /* 모서리를 둥글게 */
    }
    
    /* 드롭다운 스타일 */
    .dropdown {
    	position: relative;
    }
    
    .dropdown-content {
        display: none;
        position: absolute;
        top: 100%;
        left: 0;
        overflow: hidden;
        width: 130px;
    }

    .dropdown-content a {
        display: block;
        padding: 10px;
        color: black;
        text-decoration: none;
        background: orange;
        text-align: center;
    }

    
    img {
        width: 110px;
        height: 110px;
        z-index: -5;
    }
    .signInfo button{
    	background-color: #5A7262;
    	color: white;
    	padding: 10px 15px;
    	border: 2px solid white; /* 테두리 흰색으로 변경 */
    	border-radius: 5px; /* 모서리를 둥글게 */
    }
    
    .backgroundimg img {
	    width: 1270px;
	    height: 700px;
	    position: relative; /* 내부 요소의 위치 조정을 위해 relative 설정 */
	    display: flex;
	    justify-content: center; /* 이미지를 중앙 정렬 */
	}

	.text-overlay {
		font-family: 'Nanum Gothic', sans-serif; /* 나눔고딕 적용 */
	    position: absolute; /* 절대 위치 지정 */
	    bottom: 18%; /* PET MATE 아래 위치 조정 */
	    left: 21%; /* 중앙 정렬 */
	    transform: translateX(-50%); /* 정확한 가운데 정렬 */
	    text-align: center; /* 텍스트 중앙 정렬 */
	    font-size: 24px;
	    font-weight: bold;
	    color: white; /* 가독성을 위해 검은색 사용 */
	    width: 80%; /* 텍스트 너비 조정 */
	}
	.text-overlay #p {
		font-size: 32px;
		text-shadow: 3px 3px 7px rgba(0, 0, 0, 0.7);
		
	}
	.text-overlay #p1 {
		font-size: 22px;
	    margin-top: 10px;
	    text-shadow: 2px 2px 7px rgba(0, 0, 0, 0.7);
	    
	}
</style>
</head>
<body>

<nav>

<img src="/images/logo.png">
	<div class="menu">
		<a href="noticeBoard.jsp">공지사항</a>
		<a href="petsitter.jsp">펫시터</a>
		<a href="reservationList.jsp">예약목록</a>
		<a href="reviewList.jsp">리뷰목록</a>
		<!-- 드롭다운 메뉴 -->
		<div class="dropdown">
			<a href="#" id="communityBtn">커뮤니티 ▼</a>
			<div class="dropdown-content">
				<a href="freeeBoard.jsp">자유게시판</a>
				<a href="petPhotoShare.jsp">펫사진공유게시판</a>
			</div>
		</div>
	</div>

	<c:choose>
    <c:when test="${empty sessionScope.user}">
        <!-- 로그인하지 않은 상태 -->
        <div class="signInfo">
            <button type="button" name="login" id="login">로그인</button>
            <button type="button" name="join" id="join">회원가입</button>
        </div>
    </c:when>
    <c:otherwise>
        <!-- 로그인한 상태 -->
        <div class="signInfo">
            <p id="userGreeting">
                안녕하세요, 
                <c:choose>
                    <c:when test="${sessionScope.user.role eq 'user'}">
                        <a href="/user/manager.do">${sessionScope.user.userId}님!</a>
                    </c:when>
                    <c:otherwise>
                        <a href="/user/main.do">${sessionScope.user.userId}님!</a>
                    </c:otherwise>
                </c:choose>
            </p>
            <button type="button" id="logout">로그아웃</button>
        </div>
    </c:otherwise>
</c:choose>
</nav>
<div class="backgroundimg">
	<c:if test="${pageName ne 'manager'}">
    <img id="background" src="/images/pet.png">
    <div class="text-overlay">
        <p id="p">전문 펫시터가 돌봐드려요</p>
        <p id= "p1">나와 내 반려동물을 위한 돌봄 서비스</p>
    </div>
    </c:if>
</div>

<script>
$(document).ready(function() {
	//커뮤니티 버튼 클릭 시 드롭다운 메뉴 표시
	$("#communityBtn").click(function(event) {
		event.preventDefault(); // 링크 이동 방지
		$(".dropdown-content").toggle(); // 드롭다운 표시/숨김
	});
	
	// 메뉴 외부 클릭 시 드롭다운 닫기
	$(document).click(function(event) {
		if (!$(event.target).closest('.dropdown').length) {
			$(".dropdown-content").hide();
		}
	});
	
	// 로그인 버튼 클릭 시 login.jsp로 이동
	$("#login").click(function() {
        window.location.href = "/user/login.do";
    });
	
	// 회원가입 버튼 클릭 시 join.jsp로 이동
	$("#join").click(function() {
		window.location.href = "/user/join.do";
	});
	
	$("#logout").click(function () {
        console.log("로그아웃 버튼 클릭됨");

        $.ajax({
            url: "/user/logout.do",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.success) {
                    alert("로그아웃 되었습니다.");
                    window.location.href = "/user/login.do";
                } else {
                    alert("로그아웃에 실패했습니다.");
                }
            },
            error: function () {
                alert("서버 오류로 로그아웃에 실패했습니다.");
            }
        });
    });
	
	// 로그인 여부 확인 후 버튼 숨기기 및 사용자 인사말 표시
	var userId = "${sessionScope.user.userId}"; //서버에서 받아온 유저 ID
	
	if (userId) {
		$("#login, #join").hide(); // 로그인 & 회원가입 버튼 숨기기
		$("#userGreeting").show(); // 사용자 환영 메시지 표시
	}
	
	
});
</script>
</body>
</html>