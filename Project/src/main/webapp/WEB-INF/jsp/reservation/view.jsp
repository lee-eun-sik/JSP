<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko"> 
<head>
<meta charset="UTF-8">
<title>예약신청 상세</title>
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reservation/view.css">
</head>
<body>
	<div class="contentbox">
	<h2>예약내용 상세보기</h2>
	<form id="reservationForm">
			<label for="createId">작성자:</label> ${reservation.createId} <br /> 
			<label for="createDt">예약신청일:</label> ${reservation.createDt} <br /> 
			<label for="petName">펫네임:</label> ${reservation.petName} <br />
			<label for="variety">품종:</label> ${reservation.variety} <br />
			<label for="phoneNumber">휴대폰번호:</label> ${reservation.phoneNumber} <br />
			<label for="reservationDate">신청기간:</label> ${reservation.reservationDate}일 <br />
			<label for="price">가격:</label> ${reservation.price}원 <br /> 
			<label for="address">주소:</label> ${reservation.address},${reservation.addressDetail} <br /> 
			<label for="reply">문의사항:</label>${reservation.reply} <br />
	</form>
	</div>
	<c:if test="${not empty reservation.postFiles}">
		<ul>
			<c:forEach var="file" items="${reservation.postFiles}">
				<li>${file.fileName} <a
					href="/file/down.do?fileId=${file.fileId}">다운로드</a>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<%-- <c:if test="${not empty sessionScope.user.userId}">
	 <h4>댓글 작성</h4><br/>
	 <textarea id="commentContent" rows="4"  placeholder="댓글을 입력하세요...."></textarea>
	<button type="button" id="commentCreateBtn" onclick="addComment()">댓글 작성</button>
	</c:if> --%>
	
<%-- 	<c:if test="${not empty board.comments}">
	 <h4>댓글 목록</h4><br/>
	 <c:set var="comments" value="${ board.comments }" scope="request" />
	 <jsp:include page="commentItem.jsp">
	 <jsp:param value="0" name="commentId"/>
	 </jsp:include>
	 </c:if> --%>
	 	
	<a href="/user/main.do">메인 페이지로 이동</a>
	<script>
	//댓글 작성
	/* function addComment(parentId) {
		var content = parentId && parentId != 0 ?
				$('#replayContent_'+ parentId).val().trim()
				:  $('#commentContent').val().trim();
		if (!content) {
			alert("댓글 내용을 입력해 주세요.");
			return;	
		}		
		if (content.length > 500) {
			alert("댓글은 500자 이하로 작성해 주세요.");
			return;	
			}		
		var boardId = '${board.boardId}';
		var createId = '${sessionScope.user.userId}';
		
		//AJAX 요청을 통해 댓글 작성 요청
		ajaxRequest(
				'/board/comment/create.do',
		{
			content : content,
			boardId : boardId,
			createId : createId,
			parentCommentId : parentId || 0
		},
		function (response) {
			if (response.success){
				alert(response.message);
				location.reload();
			}else{
				alert(response.message);
				}
			}
		);
		
	} */
	
	
	//댓글 수정창 표시/숨기기: toggleEditComment 함수
	/* function toggleEditComment(commentId) {
		let editForm = $('#editForm_'+commentId);
		let content = $('#commentContent_'+commentId).text().trim();

			if (editForm.is(":visible")){
				editForm.hide();
			}else {
					editForm.show();
					$('#editContent_'+commentId).val(content);
					}		
				} */
	
	
	 //댓글수정
	/* function editComment(commentId) {
		let content = $('#editContent_'+commentId).val().trim();
			
			if (!content) {
				alert("댓글 내용을 입력해 주세요.");
				return;	
			}		
			if (content.length > 500) {
				alert("댓글은 500자 이하로 작성해 주세요.");
				return;	
			}		
			
			let boardId = '${board.boardId}';
			let updateId = '${sessionScope.user.userId}'; */
		//AJAX댓글수정요청				
		/* ajaxRequest(
			'/board/comment/update.do',
			{
				commentId : commentId,
				content : content,
				boardId : boardId,
				updateId : updateId
			},
			function (response) {
				
				if (response.success){
					alert(response.message);
					location.reload();
				}else{
					alert(response.message);
				}
			}
		);
	} */
	
	
	//댓글 삭제
	/* function deleteComment(commentId) {
			
		if (confirm('이 댓글을 삭제 하시겠습니까?')){
			
			let updateId = '${sessionScope.user.userId}'; */
		//AJAX로 삭제 요청 전송
		/* ajaxRequest(
			'/board/comment/delete.do',
			{
					commentId : commentId,
					updateId : updateId
			},
				
			function (response) {
				if (response.success){
					
					alert(response.message);
					location.reload();
					
				}else{
					alert(response.message);
					}
				}
			);
	
		}
	} */
	
	
	//댓글 답글
	/* function toggleReplayComment(commentId){

		let replayForm = $('#replayForm_'+commentId);
		
		if (replayForm.is(":visible")){
			replayForm.hide();
			}else {
				replayForm.show();
				}			
		
	}
	 */
	
	
	</script>
 
</body>
</html>