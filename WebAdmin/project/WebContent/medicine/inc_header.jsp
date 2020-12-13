<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String id ="";
id = (String)session.getAttribute("id");
%>

<style>
.logo {
	color: white;
	cursor: pointer;
	margin-left: 70px;
}

.logo:link, .logo:visited {
	color: white;
	cursor: pointer;
}

.logo:link:active, .logo:visited:active {
	color: white;
}
.home {
	background-color: #4BBF6A;
	margin-bottom: 20px;
}
.home > a > img {
	cursor: pointer;
}
.header {
	margin-right: 100px;
}
.header_li {
	text-decoration: none;
	color: white;
}
.header_active {
    color: white;
    background-color: #4BBF6A;
    text-decoration: none;
    transition: all 0.3s ease-in-out;
}

.header_li:hover:not(.active) {
    color: white;
    background-color: #4BBF6A;
    text-decoration: none;
    transition: all 0.3s ease-in-out;
}

</style>

<nav class="navbar home">
	<a class="navbar-brand logo" href="chart.jsp">
		<img src="img/logo.png" width="40px" alt="logo">Medicine Box
	</a>
	
	<ul class="nav justify-content-end header">
		<li class="nav-item"><a class="nav-link header_li" href=""><%=id %>님</a></li>
		<li class="nav-item"><a class="nav-link header_li" href="logout.jsp">로그아웃</a></li>
	</ul>
</nav>