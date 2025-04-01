<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더 메인 페이지</title>

<style>

	
	nav {
        display: flex; /* nav 내부 요소들을 가로로 정렬 */
        align-items: center; /* 요소들을 세로 중앙 정렬 */
        gap: 100px; /* 이미지와 메뉴 사이 여백 */
    }
    
    .menu {
        display: flex; /* 메뉴 항목을 가로 정렬 */
        gap: 15px; /* 각 메뉴 항목 간 여백 */
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
    
    
    img {
        width: 110px;
        height: 110px;
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

<img src="logo.png">
	<div class="menu">
		<a href="noticeBoard.jsp">공지사항</a>
		<a href="petsitter.jsp">펫시터</a>
		<a href="reservationList.jsp">예약목록</a>
		<a href="reviewList.jsp">리뷰목록</a>
		<a href="community.jsp">커뮤니티</a>
	</div>
	<div class="signInfo">
		<button type="button" name="login" id="login">로그인</button>
		<button type="button" name="join" id="join">회원가입</button>
	</div>
</nav>
<div class="backgroundimg">
    <img src="pet.png">
    <div class="text-overlay">
        <p id="p">전문 펫시터가 돌봐드려요</p>
        <p id= "p1">나와 내 반려동물을 위한 돌봄 서비스</p>
    </div>
</div>
</body>
</html>