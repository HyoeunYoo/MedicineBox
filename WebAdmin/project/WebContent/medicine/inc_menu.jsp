<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
.menu_ui{
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: auto;
    background-color: white;
    border-right: 1px solid lightgray;
    height: 100%;
}

.menu_li {
    display: block;
    color: #000;
    padding: 8px 16px;
    text-align: center;
    text-decoration: none;
}

.menu_title{
	text-align: center;
	font-weight: bold;
	font-size: 20px;
	margin-bottom: 10px;
	
}

.menu_active {
    background-color: #4BBF6A;
    color: white;
    text-align: center;
    text-decoration: none;
    transition: all 0.3s ease-in-out;
}

.menu_li:hover:not(.active) {
    background-color: #4BBF6A;
    color: white;
    text-decoration: none;
    transition: all 0.3s ease-in-out;
}

.high {
    height: auto;
}
</style>
</head>
<body>

<ul class="menu_ui sticky-top">
	<li class="menu_title">관리자 페이지</li>

    <li><a href="chart.jsp" class="menu_li" id="chart">데이터시각화</a></li>
    <li><a href="user.jsp" class="menu_li" id="user">사용자 정보 관리</a></li>
    <li><a href="medi.jsp" class="menu_li" id="medi">의약품 정보 관리</a></li>
    <li><a href="search.jsp" class="menu_li" id="search">사용자 검색어 조회</a></li>
    <li><a href="setting.jsp" class="menu_li" id="setting">비밀번호 변경</a></li>
</ul>

