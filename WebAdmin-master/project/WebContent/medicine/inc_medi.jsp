<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, medicine.*"%>

<jsp:useBean id="medi" scope="application" class="medicine.DBConnect"/>

<style>
/* 의약품 정보 관리 */
.medicineimg{
	width: 50px;
}
.medi_add {
	color: #4BBF6A;
	font-size: 30px;
	cursor: pointer;
}
</style>

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

int totalRows = medi.getTotalRows();

int len = 5;
int totalPages = totalRows % len == 0 ? totalRows / len : (totalRows / len) + 1;
if (totalPages == 0) {
    totalPages = 1;
}
if (cPage > totalPages) {
    cPage = 1;
}
int start = (cPage - 1) * 5;
int end = cPage * 5;

ArrayList<Medi> datas = (ArrayList<Medi>)medi.getMediList(start, end);
%>
<div class="tab-pane" id="medi" role="tabpanel" aria-labelledby="medi-tab">
	<div class="tab-pane" id="profile" role="tabpanel" aria-labelledby="profile-tab">
		<nav class="navbar">
			<a class="navbar-brand">의약품 목록
				<button class="btn medi_add" data-target="#mediaddModal" data-toggle="modal"><i class="fas fa-plus-circle"></i></button>
			</a>
			
			<form class="form-inline">
				<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
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
        	  
				for(Medi me : (ArrayList<Medi>) datas) {
				%>
				<tr>
					<td scope="row" id="num"><%=me.getMedi_num()%></td>
					<td><img src="img/<%=me.getMedi_photo()%>" id="photo" class="medicineimg"></td>			
					<td id="name"><%=me.getMedi_name() %></td>
					<td id="effect" style="display: none;"><%=me.getMedi_effect() %></td>
					<td id="use" style="display: none;"><%=me.getMedi_use() %></td>
					<td id="store"><%=me.getMedi_store() %>회</td>
					<td id="search"><%=me.getMedi_search() %>회</td>
					<td><input type="submit" class="btn btn-outline-success" value="수정" onclick="editFunction(<%=me.getMedi_num()%>,'<%=me.getMedi_name() %>','img/<%=me.getMedi_photo()%>','<%=me.getMedi_effect() %>','<%=me.getMedi_use() %>')" data-target="#updateModal" data-toggle="modal"></td>
				</tr>
				<%
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
					<a class="page-link" href="home.jsp?page=<%=startPage - 1%>" tabindex="-1" aria-disabled="true">
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
					<a class="page-link" href="home.jsp?page=<%=i %>">
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
				<a class="page-link" href="home.jsp?page=<%=endPage + 1%>">
					>
				</a>
			</li>
			<%
			}
			%>
		</ul>
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
						<input type="text" class="form-control col-sm-8" id="medi_name" name="medi_name">
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">의약품 사진</label>
						<input type="hidden" class="upload-name" disabled="disabled">
						<input type="file" class="form-control col-sm-7 upload-hidden" id="medi_photo" name="medi_photo">
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">효능·효과</label>
						<textarea rows="5" cols="71" class="form-control col-sm-8" id="medi_effect" name="medi_effect"></textarea>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">용법·용량</label>
						<textarea rows="3" cols="71" class="form-control col-sm-8" id="medi_use" name="medi_use"></textarea>
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
<form method="post" action="admin_control?action=mediUpdate.jsp">
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
						<input type="text" class="form-control col-sm-8" id="medi_e_name" name="medi_e_name" value="">
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">의약품 사진</label>
						<label class="col-sm-1"><img class="medicineimg" id="medi_e_photo"></label>
						<input type="hidden" class="upload-name" disabled="disabled">
						<input type="file" class="form-control col-sm-7 upload-hidden" id="medi_photo" name="medi_photo">
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">효능·효과</label>
						<textarea rows="5" cols="71" class="form-control col-sm-8" id="medi_e_effect" name="medi_e_effect"></textarea>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 col-form-label">용법·용량</label>
						<textarea rows="3" cols="71" class="form-control col-sm-8" id="medi_e_use" name="medi_e_use"></textarea>
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

<script>
//사진첨부
$(document).ready(function(){
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

</script>