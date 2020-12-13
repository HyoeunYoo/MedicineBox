<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, medicine.*"%>

<%
String id ="";
try {
	id = (String)session.getAttribute("id");
	
	if (id != null) {
		
%>

<jsp:useBean id="medi" scope="application" class="medicine.DBConnect"/>
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

.medicineimg{
	width: 50px;
}
.medi_add {
	color: #4BBF6A;
	font-size: 30px;
	cursor: pointer;
}

</style>

</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<%
String tempPage = request.getParameter("page");
int cPage = 0;

// cPage(현재 페이지 정하기)
if (tempPage == null || tempPage.length() == 0) {
    cPage = 1;
}
try {
    cPage = Integer.parseInt(tempPage);
} catch (NumberFormatException e) {
    cPage = 1;
}

int totalRows = medi.getMediTotalRows();

int len = 5;
int totalPages = totalRows % len == 0 ? totalRows / len : (totalRows / len) + 1;
if (totalPages == 0) {
    totalPages = 1;
}
if (cPage > totalPages) {
    cPage = 1;
}
int start = (cPage - 1) * 5;
int end = len;

ArrayList<Medi> datas = (ArrayList<Medi>)medi.getMediList(start, end);
%>

<div class="main">
	<jsp:include page="inc_header.jsp" flush="false" />
	<div class="row page">
		<div class="col-3">
			<jsp:include page="inc_menu.jsp" flush="false" />
		</div>

		<div class="col-9">
		
			<nav class="navbar">
				<h3>의약품 목록
					<button class="btn medi_add" data-target="#mediaddModal" data-toggle="modal"><i class="fas fa-plus-circle"></i></button>
				</h3>
			
				<form class="form-inline" action="mediSearch.jsp">
					<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" id="mediS" name="mediS">
					<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
				</form>
			</nav>
			<br>
			
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">사진</th>
						<th scope="col">의약품명</th>
						<th scope="col">보관</th>
						<th scope="col">검색</th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
				<%
				if (datas.size() != 0) {
					int i = 5 * cPage - 4;
					
					for(Medi me : (ArrayList<Medi>) datas) {
					%>
					<tr>
						<td scope="row"><%= i%></td>
						<td><img src="img/<%=me.getMedi_photo()%>" id="photo" class="medicineimg"></td>			
						<td id="name"><%=me.getMedi_name() %></td>
						<td id="effect" style="display: none;"><%=me.getMedi_effect() %></td>
						<td id="use" style="display: none;"><%=me.getMedi_use() %></td>
						<td id="store"><%=me.getMedi_store() %>회</td>
						<td id="search"><%=me.getMedi_search() %>회</td>
						<td>
							<input type="submit" class="btn btn-outline-success" value="수정" onclick="editFunction(<%=me.getMedi_num()%>,'<%=me.getMedi_name() %>','img/<%=me.getMedi_photo()%>','<%=me.getMedi_effect() %>','<%=me.getMedi_use() %>')" data-target="#updateModal" data-toggle="modal">
							<input type="submit" class="btn btn-outline-danger" value="삭제" onclick="deleteFunction(<%=me.getMedi_num()%>)" data-target="#deleteModal" data-toggle="modal">
						</td>
					</tr>
					<%
					i+=1;
					}
	          } else {
	        	  %>
	        	  <tr>
					<td scope="row">데이터가 존재하지 않습니다.</td>
				</tr>
				<%
	          }
					%>
				</tbody>
			</table>
	
	<%
	//페이지 처음과 끝을 지정하는 부분
	int currentBlock = cPage % totalPages == 0 ? cPage / totalPages : (cPage / totalPages) + 1;
	int startPage = (currentBlock - 1) * totalPages + 1;
	int endPage = startPage + totalPages - 1;
	//마지막 페이지 묶음에서 총 페이지수를 넘어가면 끝 페이지를 마지막 페이지 숫자로 지정
	if (endPage > totalPages) {
	 endPage = totalPages;
	}
	
	
	%>
			<!-- 페이징 -->
			<ul class="pagination justify-content-center">
				<%
				if (startPage == 1) {
				%>
					<li class="page-item disabled">
						<a class="page-link" href="#" tabindex="-1" aria-disabled="true">
							<
						</a>
					</li>
				<%
				} else {
				%>
					<li class="page-item">
						<a class="page-link" href="medi.jsp?page=<%=startPage - 1%>" tabindex="-1" aria-disabled="true">
							<
						</a>
					</li>
				<%
				}
				%>
				<%
				for (int i = startPage; i <= endPage; i++) {
				%>
					<li class="page-item">
						<a class="page-link" href="medi.jsp?page=<%=i %>">
							<%=i%>
						</a>
					</li>
				<%
				}
				%>
				<%
				// 마지막 페이지 숫자와 startPage에서 pageLength 더해준 값이 일치할 때(즉 마지막 페이지 블럭일 때)
				if (totalPages == endPage) {
				%>
					<li class="page-item disabled">
						<a class="page-link" href="#">
							>
						</a>
					</li>
				<%
				} else {
				%>
				<li class="page-item">
					<a class="page-link" href="medi.jsp?page=<%=endPage + 1%>">
						>
					</a>
				</li>
				<%
				}
				%>
			</ul>

		</div>
	</div>
</div>

<!-- 의약품 정보 추가 모달 -->
<form method="post" action="admin_control.jsp?action=mediAdd" enctype="multipart/form-data">
	<div class="modal fade" role="dialog" id="mediaddModal" tabindex="-1">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">의약품 정보 추가</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body">
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">의약품명</label>
						<input type="hidden" id="none_num" name="none_num" value="0">
						<input type="text" class="form-control col-sm-8" id="medi_name" name="medi_name" required>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">의약품 사진</label>
						<input type="hidden" class="upload-name" disabled="disabled">
						<input type="file" class="form-control col-sm-8 upload-hidden" id="medi_photo" name="medi_photo" required>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">효능·효과</label>
						<textarea rows="5" cols="71" class="form-control col-sm-8" id="medi_effect" name="medi_effect" required></textarea>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">용법·용량</label>
						<textarea rows="3" cols="71" class="form-control col-sm-8" id="medi_use" name="medi_use" required></textarea>
					</div>
				</div>
				
				<div class="modal-footer">
					<input type="submit" class="btn btn-primary" value="확인">
					<input type="button" class="btn btn-secondary" data-dismiss="modal" value="취소">
				</div>
			</div>
		</div>
	</div>
</form>

<!-- 의약품 정보 수정 모달 -->
<form method="post" action="admin_control.jsp?action=mediUpdate" enctype="multipart/form-data">
	<div class="modal fade" role="dialog" id="updateModal" tabindex="-1">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">의약품 정보 수정</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">일련번호</label>
						<input type="text" class="form-control col-sm-8" id="medi_e_num" name="medi_e_num" readonly>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">의약품명</label>
						<input type="text" class="form-control col-sm-8" id="medi_e_name" name="medi_e_name" required>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">의약품 사진</label>
						<label class="col-sm-1"><img class="medicineimg" id="medi_e_photo"></label>
						<input type="hidden" class="upload-name" disabled="disabled">
						<input type="file" class="form-control col-sm-7 upload-hidden" id="medi_edit_photo" name="medi_edit_photo">
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">효능·효과</label>
						<textarea rows="5" cols="71" class="form-control col-sm-8" id="medi_e_effect" name="medi_e_effect" required></textarea>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">용법·용량</label>
						<textarea rows="3" cols="71" class="form-control col-sm-8" id="medi_e_use" name="medi_e_use" required></textarea>
					</div>
				</div>
				
				<div class="modal-footer">
					<input type="submit" class="btn btn-primary" value="확인">
					<input type="button" class="btn btn-secondary" data-dismiss="modal" value="취소">
				</div>
			</div>
		</div>
	</div>
</form>

<!-- 의약품 정보 삭제 모달 -->
<form method="post" action="admin_control.jsp?action=mediDelete">
	<div class="modal fade" role="dialog" id="deleteModal" tabindex="-1">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">의약품 정보 삭제</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<input type="hidden" id="medi_d_num" name="medi_d_num">
					삭제하시겠습니까?
				</div>
				<div class="modal-footer">
					<input type="submit" class="btn btn-primary" value="확인">
					<input type="button" class="btn btn-secondary" data-dismiss="modal" value="취소">
				</div>
			</div>
		</div>
	</div>
</form>

<script>
//사진첨부
$(document).ready(function(){
	// 메뉴 활성화
	$('#medi').addClass('menu_active');
	
	var fileTarget = $('.upload-hidden');

	fileTarget.on('change', function(){ // 값이 변경되면
		if(window.FileReader){ // modern browser 
		var filename = $(this)[0].files[0].name; 
		}
		else { // old IE 
		var filename = $(this).val().split('/').pop().split('\\').pop(); //파일명만 추출
	}

		// 추출한 파일명 삽입
		$(this).siblings('.upload-name').val(filename);
	});
});

//수정 모달창으로 값 넘겨주기
function editFunction(num, name, photo, effect, use) {	
	var num = num;
	var name = name;
	var photo = photo;
	var effect = effect;
	var use = use;
	
	//var result = effect.replace(/(\n|\r\n)/g, '<br>');
	/* var name = document.getElementById("name").childNodes[0].nodeValue;
	var effect = document.getElementById("effect").childNodes[0].nodeValue;
	var use = document.getElementById("use").childNodes[0].nodeValue;
	var photo = document.getElementById("photo").getAttribute('src'); */
	
	console.log(num, name, photo, effect, use);
	
	$("#medi_e_num").val(num);
	$("#medi_e_name").val(name);
	$("#medi_e_photo").attr("src", photo);
	$("#medi_e_effect").val(effect);
	$("#medi_e_use").val(use);
	
	
}

//삭제 모달창으로 값 넘겨주기
function deleteFunction(num) {	
	var num = num;
	
	//var result = effect.replace(/(\n|\r\n)/g, '<br>');
	/* var name = document.getElementById("name").childNodes[0].nodeValue;
	var effect = document.getElementById("effect").childNodes[0].nodeValue;
	var use = document.getElementById("use").childNodes[0].nodeValue;
	var photo = document.getElementById("photo").getAttribute('src'); */
	
	console.log(num);
	
	$("#medi_d_num").val(num);
	
	
}

</script>

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