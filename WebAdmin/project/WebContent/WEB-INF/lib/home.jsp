<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.0/css/bootstrap.min.css" integrity="sha384-SI27wrMjH3ZZ89r4o+fGIJtnzkAnFs3E4qz9DIYioCQ5l9Rd/7UAa8DHcaL8jkWt" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">

<style>
.logo {
	width: 250px;
	height: auto;
}
.menu {
	background-color: #4BBF6A;
	height: 100%;
	width: 250px;
    position: fixed;
    z-index: 0;
    left: 0;
}
.content {
	margin-left: 20%;
	margin-right: 5%;
    max-width: 90%;
    min-width: 200px;
}
.menuitem {
	color: white;
}
</style>

</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<!-- Nav tabs -->
<div>
	<img alt="logo" src="img/medicinebox.png" class="logo">
</div>
<ul class="nav nav-tabs flex-column menu" id="myTab" role="tablist" style="padding:0px;">
	<li class="nav-item" style="margin-top:10px;">
		<a class="nav-link active menuitem" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">
			관리자 페이지
		</a>
	</li>
	<li class="nav-item">
		<a class="nav-link menuitem" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">
			사용자 정보 관리
		</a>
	</li>
	<li class="nav-item">
		<a class="nav-link menuitem" id="messages-tab" data-toggle="tab" href="#messages" role="tab" aria-controls="messages" aria-selected="false">
			의약품 정보 관리
		</a>
	</li>
	<li class="nav-item">
		<a class="nav-link menuitem" id="settings-tab" data-toggle="tab" href="#settings" role="tab" aria-controls="settings" aria-selected="false">
			사용자 검색어 조회
		</a>
	</li>
</ul>

<!-- Tab panes -->
<div class="tab-content content">
	<div class="tab-pane active" id="home" role="tabpanel" aria-labelledby="home-tab">
	
	</div>
	
	<!-- 사용자 정보 관리 -->
	<jsp:include page="inc_user.jsp" flush="false"/>
	
	<!-- 의약품 정보 관리 -->
	<jsp:include page="inc_medi.jsp" flush="false"/>
	
	<!-- 사용자 검색어 조회 -->
	<jsp:include page="inc_search.jsp" flush="false"/>
</div>

</body>
</html>