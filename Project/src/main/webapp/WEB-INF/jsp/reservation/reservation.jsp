<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약신청 작성</title>
<script src="/js/common.js?ver=1.1"></script>
<script src="/js/jquery-3.7.1.min.js?ver=1"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reservation/create.css">
</head>
<body>
<h2>예약신청 작성</h2>
	<form method="post" id="reservationCreateForm" enctype="multipart/form-data">
		<label for="startDate">*날짜</label>
	    <input type="date" name="startDate" id="startDate" placeholder="시작날 선택" required>
	    ~
	    <input type="date" name="endDate" id="endDate" placeholder="종료날 선택" required> <br>
	    <input type="hidden" name="reservationDate" id="reservationDate"><br/>
	    <input type="hidden" id="createId" name="createId" value="${sessionScope.loginId}" />
        <label for="postcode">*주소</label>
		<input type="text" id="sample6_postcode" placeholder="우편번호" readonly />
		<input type="button" onclick="sample6_execDaumPostcode()" value="우편주소찾기" />
		<br><br>
		<input type="text" id="sample6_address" name="address" placeholder="주소" readonly />
		<input type="text" id="addressDetail" name="addressDetail" placeholder="상세주소" />
        <br/>
        <br/>
        <label for="variety">*품종</label> 
        <select name="variety" id="variety" required>
		    <option value="강아지">강아지</option>
		    <option value="고양이">고양이</option>
		    <option value="조류">조류</option>
		    <option value="파충류">파충류</option>
		</select><br/>
        <br/>
        <label for="petName">*펫이름</label> 
		<input type="text" name="petName" id="petName" maxlength="100" placeholder="펫이름 입력" required><br/>
        <br/>
        <label for="phoneNumber">*전화번호</label> 
		<input type="text" name="phoneNumber" id="phoneNumber" maxlength="100" placeholder="전화번호 입력" required><br/>
        <br/>
        <label for="sitter">*펫시터</label> 
		<select name="sitter" id="sitter" required>
		    <option value="">펫시터 선택</option>
		    <c:forEach var="sitter" items="${sitterList}">
		        <option value="${sitter.sitterName}">${sitter.sitterName} - ${sitter.content}</option>
		    </c:forEach>
		</select>
		<br/>
		        
       	<label for="price">*일급</label> 
       	<input type="text" name="price" id="price">
        <br/>
		<label for="reply">*요청사항</label> 
		<textarea rows="5" cols="40"  id="reply" name="reply" placeholder="요청사항"></textarea>
		<br/>	
		
		<div class="button-group">
			<!-- 예약하기 버튼 -->
			<button type="button" id="btnSubmitReservation">예약하기</button>
			
			<!-- 예약취소 버튼 -->
			<button type="button" id="btnCancelReservation">예약취소</button>
		</div>
	</form>

<script>
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수

            // 사용자가 선택한 주소 타입에 따라 설정
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress; // 도로명 주소
            } else {
                addr = data.jibunAddress; // 지번 주소
            }

            // 값 넣기
            document.getElementById('sample6_postcode').value = data.zonecode; // 우편번호
            document.getElementById("sample6_address").value = addr; // 주소
            document.getElementById("addressDetail").focus(); // 상세주소 입력칸으로 커서 이동
        }
    }).open();
}
</script>

<script type="text/javascript">
const today = new Date();
const yyyy = today.getFullYear();
const mm = String(today.getMonth() + 1).padStart(2, '0');
const dd = String(today.getDate()).padStart(2, '0');
document.getElementById('reservationDate').value = `${yyyy}-${mm}-${dd}`;
	$(document).ready(function() {
		// 날짜가 변경될 때마다 자동으로 가격을 계산하는 이벤트 핸들러
        $("#startDate, #endDate").change(function() {
            let startDate = $("#startDate").val().trim();
            let endDate = $("#endDate").val().trim();

            if (startDate && endDate) {
                // 날짜 차이 계산
                let start = new Date(startDate);
                let end = new Date(endDate);
                let timeDiff = end - start;

                if (timeDiff < 0) {
                    alert("종료일이 시작일보다 이전일 수 없습니다.");
                    return;
                }

                let dayDiff = timeDiff / (1000 * 3600 * 24)+1; // 밀리초 -> 일
                $("#reservationDate").val(dayDiff);

                // 가격 계산
                let calculatedPrice = dayDiff * 10000; // 예시: 하루당 10,000원
               

                // 계산된 가격을 hidden input에 설정
                $("#price").val(calculatedPrice); // price 필드에 계산된 가격 설정
            }
        });

     	// 예약하기 버튼 클릭 이벤트
        $("#btnSubmitReservation").click(function() {
            $("#reservationCreateForm").submit(); // submit 이벤트가 위에서 정의됨
        });

        // 예약취소 버튼 클릭 이벤트
        $("#btnCancelReservation").click(function() {
            if (confirm("예약을 취소하시겠습니까?")) {
                alert("예약이 취소되었습니다.");
                window.location.href = "/user/header.do";
            }
        });
        
	    $("#reservationCreateForm").submit(function(event) {
	        event.preventDefault(); 
			
	        let startDate = $("#startDate").val().trim();
	        let endDate = $("#endDate").val().trim();
	        let reservationDate = $("#reservationDate").val().trim();
	        let address = $("#sample6_address").val().trim();
	        let variety = $("#variety").val().trim();
	        let petName = $("#petName").val().trim();
	        let phoneNumber = $("#phoneNumber").val();
	        console.log("입력된 전화번호:", phoneNumber);
	        let sitter = $("#sitter").val().trim();
	        let price = parseInt($("#price").val().trim());
	        let reply = $("#reply").val().trim();
	        let addressDetail = $("#addressDetail").val().trim();

	        if (!startDate || !endDate) {
	            alert("시작일과 종료일을 모두 선택해주세요.");
	            return;
	        }


			if (isNaN(price)) {
				alert("품종을 선택해주세요");
				$("#variety").focus();
				return;
			}


			


			 let formData = new FormData();
			    formData.append("startDate", startDate);
			    formData.append("endDate", endDate);
			    formData.append("reservationDate", reservationDate);
			    formData.append("address", address);
			    formData.append("variety", variety);
			    formData.append("petName", petName);
			    formData.append("phoneNumber", phoneNumber);
			    formData.append("sitter", sitter);
			    formData.append("price", price);
			    formData.append("reply", reply);
			    formData.append("createId", $("#createId").val());
			    formData.append("addressDetail", addressDetail);
			   
			    $.ajax({
			        url: "/reservation/reservation.do",
			        type: "POST",
			        data: formData,
			        processData: false,
			        contentType: false,
			        dataType: "json",
			        success: function (response) {
			            console.log("성공:", response);
			        },
			        error: function (xhr, status, error) {
			            console.error("에러:", error);
			        }
			    });
			
	    });
	});
</script>

</body>
</html>