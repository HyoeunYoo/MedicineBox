<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String id ="";
try {
	id = (String)session.getAttribute("id");
	
	if (id != null) {
		
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Medicine Box</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.0/css/bootstrap.min.css" integrity="sha384-SI27wrMjH3ZZ89r4o+fGIJtnzkAnFs3E4qz9DIYioCQ5l9Rd/7UAa8DHcaL8jkWt" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
<link rel="icon" href="favicon.ico" type="image/x-icon">

<style>
.main {
	width: 100%;
}

.page {
	margin-right: 100px;
}

.form {
	margin-top: 20px;
}
</style>

</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script>

    $(document).ready(function () {

        $('#setting').addClass('menu_active');

    });

</script>

<div class="main">
	<jsp:include page="inc_header.jsp" flush="false" />
	<div class="row page">
		<div class="col-3">
			<jsp:include page="inc_menu.jsp" flush="false" />
		</div>

		<div class="col-9">
			<nav class="navbar">
				<h3>비밀번호 변경</h3>
			</nav>
			
			<form class="form" method="post" action="admin_control.jsp?action=setting">
				<input type="hidden" value="<%=id %>" id="id" name="id">
				<div class="form-group">
					<label>기존 비밀번호</label>
					<input type="password" class="form-control" id="oldPasswd" name="oldPasswd" style="width: 300px;">
				</div>
				<div class="form-group">
					<label>새 비밀번호</label>
					<input type="password" class="form-control" id="newPasswd" name="newPasswd" style="width: 300px;">
				</div>
				<div class="form-group">
					<label>새 비밀번호 확인</label>
					<input type="password" class="form-control" id="newPasswd2" name="newPasswd2" style="width: 300px;">
				</div>
				<div class="form-group editbtn">
					<input type="submit" class="btn btn-outline-success" value="변경하기">
				</div>
			</form>

		</div>
	</div>
</div>

</body>
</html>

<% 
	} else {
		response.sendRedirect("index.jsp");
	}
}catch(Exception e){
	e.printStackTrace();
}
%>