<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@page import="medicine.*"%>

	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Medicine Box</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.0/css/bootstrap.min.css" integrity="sha384-SI27wrMjH3ZZ89r4o+fGIJtnzkAnFs3E4qz9DIYioCQ5l9Rd/7UAa8DHcaL8jkWt" crossorigin="anonymous">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
<link rel="icon" href="favicon.ico" type="image/x-icon">
<style>
.top {
	/* border: 1px solid black; */
	background-color: #4BBF6A;
}

.logo {
	width: 20%;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.title {
	color: white;
	text-align: center;
}

.inp_text {
    width: 400px;
    margin-left: 20%;
   
}

.inp_text input {
    display: block;
    width: 100%;
    height: 100%;
    font-size: 17px;
}

.btn_login {
    width: 80px;
    height: 80px;
    position:relative;
    left:570px;
    bottom:100px;
    border-radius: 10px;
    font-size: 16px;
    color: #fff;
    background-color: #4BBF6A;
}

.logintable {
	width: 400px;
	padding: 0;
	display: block;
	margin-left: auto;
	margin-right: auto;
	margin-top: 20px;
}
.inputword {
	width: 280px;
}
.loginbtn {
	height: 95px;
	margin-top: -73px;
	margin-left: 10px;
	vertical-align: middle;
}
</style>

</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<div class="top">
	<img alt="logo" src="img/logo.png" class="logo">
	<h3 class="title">Medicine Box 관리자 페이지</h3>
	<br>
</div>

<!-- <form method="post" action="admin_control.jsp?action=login">
	<div class="inp_text">
		<input type="text" class="form-control" id="loginId" name="loginId" placeholder="ID" >
	</div>
	<div class="inp_text">
		<input type="password" class="form-control" id="loginPw" name="loginPw" placeholder="PASSWORD">
	</div>
	<input type="submit" class="btn_login" value="LOGIN">
</form> -->

<form method="post" action="admin_control.jsp?action=login">
<table class="logintable">
	<tr>
		<td>
			<div class="form-group">
				<label for="userid" class="sr-only">아이디</label>
				<input type="text" name="id" id="id" class="form-control inputword" placeholder="아이디" required autofocus style="outline:none">
			</div>
		</td>
		<td rowspan="2"></td>
	</tr>
	<tr>
		<td>
			<div class="form-group">
				<label for="passwd" class="sr-only">비밀번호</label>
				<input type="password" name="passwd" id="passwd" class="form-control inputword" placeholder="비밀번호" required>
			</div>
		</td>
		<td>
			<input type="submit" class="btn btn-success btn-block btn-lg loginbtn" value="LOGIN">
		</td>
	</tr>
</table>
</form>
 
</body>
</html>