<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.0/css/bootstrap.min.css"
	integrity="sha384-SI27wrMjH3ZZ89r4o+fGIJtnzkAnFs3E4qz9DIYioCQ5l9Rd/7UAa8DHcaL8jkWt"
	crossorigin="anonymous">
<style>
.top {
	/* border: 1px solid black; */
	background-color: #4BBF6A;
}

.logo {
	width: 25%;
	display: block;
	margin-left: auto;
	margin-right: auto;
	margin-left: auto;
}

.title {
	color: white;
	text-align: center;
}

#fliedset{
width:180; height:180
}
.box_login {
    position: relative;
    border: 1px #ff0000;
    border-radius: 3px;
    background-color: #fff;
    width: 70%;
    margin:auto;
}
.inp_text {
    position: relative;
    width: 538px;
    margin-left: 20%;
    padding: 19px;
   
}

.inp_text input {
    display: block;
    width: 100%;
    height: 100%;
    font-size: 16px;
    color: blake;
    border: none;
    outline: 0;
    -webkit-appearance: none;
    background-color: transparent;
}
.btn_login {
    width: 70px%;
    height: 70px;
    position:relative;
    left:670px;
    bottom:100px;
    border-radius: 10px;
    font-size: 16px;
    color: #fff;
    background-color: #4BBF6A;
}

</style>

</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>

	<div class="top">
		<img alt="logo" src="img/logo.png" class="logo">
		<h3 class="title">Medicine Box 관리자 페이지</h3>
	</div>
	
	<form method="post" id="autoForm" action="" target="">
		<input type=hidden name="redirectUrl" value="">
		<fieldset>
		 <div class="box_login">
		  <div class="inp_text">
		   <input type="text" id="loginId" name="loginId" placeholder="ID" >
		  </div>
		  <div class="inp_text">
		   <input type="password" id="loginPw" name="password" placeholder="PASSWORD">
		  </div>
		   <button type="submit" class="btn_login">LOGIN</button>
		 </div>
		</fieldset>
	</form>
	
 
</body>
</html>