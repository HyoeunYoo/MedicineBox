<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, medicine.*"%>

<%@ page import="javax.naming.*" %>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="com.oreilly.servlet.MultipartRequest,com.oreilly.servlet.multipart.DefaultFileRenamePolicy,java.util.*,java.io.*" %>

<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="medi" scope="application" class="medicine.DBConnect"/>
<jsp:useBean id="admin" class="medicine.Admin" />
<jsp:setProperty property="*" name="admin"/>

<%
String action = request.getParameter("action");

// 로그인
if(action.equals("login")) {
	String id = request.getParameter("id");
	String pw = request.getParameter("passwd");
	
	int check = medi.loginCheck(id, pw);
	if(check == 1){
		session.setAttribute("id",id);
		response.sendRedirect("chart.jsp");
	} else if (check == 0) {%>
		<script> 
		alert("비밀번호가 맞지 않습니다.");
		history.go(-1);
		</script>
	<%	
	} else {
	%>
		<script> 
		alert("아이디가 맞지 않습니다.");
		history.go(-1);
		</script>
	<%
	}
}


//비번변경
else if(action.equals("setting")) {
	String id = request.getParameter("id");
	String oldPw = request.getParameter("oldPasswd");
	String newPw = request.getParameter("newPasswd");
	String newPw2 = request.getParameter("newPasswd2");
	
	int check = medi.passwdCheck(id, oldPw);
	
	if(newPw.equals(newPw2)) {
		if(check == 1){
			boolean edit = medi.passwdEdit(id, newPw);
			if (edit == true) {%>
				<script> 
				alert("비밀번호가 변경되었습니다. 재로그인 하시기 바랍니다.");
				location.href="index.jsp";
				</script>
				<% session.invalidate();
			}
			
		} else {%>
			<script> 
			alert("비밀번호가 맞지 않습니다.");
			history.go(-1);
			</script>
		<%	
		}	
	} else {
		%>
		<script> 
		alert("새 비밀번호가 다릅니다.");
		history.go(-1);
		</script>
		<%
	}
}

//사용자 수정
else if (action.equals("userupdate")) {
	String id = request.getParameter("user_id");
	String name = request.getParameter("user_name");
	String pwd = request.getParameter("user_pwd");
	String phone = request.getParameter("user_phone");
	String device = request.getParameter("user_device");
	String alarm = request.getParameter("user_alarm");

	if(alarm.equals("ON")) {
		alarm = "1";
	} else {
		alarm = "0";
	}

	/* System.out.println(id);
	System.out.println(name);
	System.out.println(pwd);
	System.out.println(phone);
	System.out.println(device);
	System.out.println(alarm); */
	
	boolean userupdate = medi.updateUser(id, name, pwd, phone, device, alarm);
	if (userupdate == true) {%>
		<script> 
		alert("수정되었습니다.");
		location.href="user.jsp";
		</script>
	<%
	} else {
	%>
	<script> 
		alert("수정실패");
		history.go(-1);
		</script>
	<%}	
}

// 의약품 추가
else if (action.equals("mediAdd")) {
	//String dir= "C:/Dev/WebAdmin/project/WebContent/medicine/img";
	String realFolder = "";
	String filename1 = ""; 
	String savefile = "img"; 
	String path = session.getServletContext().getRealPath("medicine/img");

	int max = 20*1024*1024;
	MultipartRequest m = null;

	String file = null;

	String ofile = null;


	try {
		
		m = new MultipartRequest(request,path,max,"utf-8",new DefaultFileRenamePolicy());

		file = m.getFilesystemName("medi_photo");

	 	ofile = m.getOriginalFileName("medi_photo");
			
			
	} catch(Exception e) {
		out.println(e);
	}


	String medi_name = m.getParameter("medi_name");
	String medi_effect = m.getParameter("medi_effect");
	String medi_use = m.getParameter("medi_use");
	
	String none_num = m.getParameter("none_num");
	System.out.println(none_num);
	
	if (none_num.equals("0")) {
		boolean insertMedi = medi.insertMedi(medi_name, ofile, medi_effect, medi_use);
		
		if (insertMedi == true) {%>
			<script> 
			alert("의약품 정보가 추가 되었습니다.");
			location.href="medi.jsp";
			</script>
			<%
		} else {
			%>
			<script> 
			alert("추가 실패");
			history.go(-1);
			</script>
			<%
		}
	} else {
		boolean deleteNone = medi.deleteNone(none_num);
		
		if (deleteNone == true) {
			boolean insertMedi = medi.insertMedi(medi_name, ofile, medi_effect, medi_use);
			
			if (insertMedi == true) {%>
			<script> 
			alert("의약품 정보가 추가 되었습니다.");
			location.href="medi.jsp";
			</script>
			<%
			} else {
				%>
				<script> 
				alert("추가 실패");
				history.go(-1);
				</script>
				<%
			}
		} else {
			%>
			<script> 
			alert("검색어 삭제 실패");
			history.go(-1);
			</script>
			<%
		}
		
	}

}

//의약품 삭제
else if (action.equals("mediDelete")) {
	String num = request.getParameter("medi_d_num");

	boolean deleteMedi = medi.deleteMedi(num);
	
	if (deleteMedi == true) {%>
		<script> 
		alert("의약품 정보가 삭제 되었습니다.");
		location.href="medi.jsp";
		</script>
		<%
	} else {
		%>
		<script> 
		alert("삭제 실패");
		history.go(-1);
		</script>
		<%
	}

}

//의약품 수정
else if (action.equals("mediUpdate")) {
	String realFolder = "";
	String filename1 = ""; 
	String savefile = "img"; 
	String path = session.getServletContext().getRealPath("medicine/img");

	int max = 20*1024*1024;
	MultipartRequest m = null;

	String file = null;

	String ofile = null;


	try {
		
		m = new MultipartRequest(request,path,max,"utf-8",new DefaultFileRenamePolicy());

		file = m.getFilesystemName("medi_edit_photo");

	 	ofile = m.getOriginalFileName("medi_edit_photo");
			
			
	} catch(Exception e) {
		out.println(e);
	}

	String num = m.getParameter("medi_e_num");
	String name = m.getParameter("medi_e_name");
	String effect = m.getParameter("medi_e_effect");
	String use = m.getParameter("medi_e_use");
	
	System.out.println(num);
	System.out.println(name);
	System.out.println(ofile);
	System.out.println(effect);
	System.out.println(use);
	
	boolean mediUpdate = medi.updateMedi(num, name, ofile, effect, use);
	
	if (mediUpdate == true) {%>
		<script> 
		alert("수정되었습니다.");
		location.href="medi.jsp";
		</script>
	<%
	} else {
	%>
	<script> 
		alert("수정실패");
		history.go(-1);
		</script>
	<%}	
}

%>