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

<!-- favicon -->
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
<link rel="icon" href="favicon.ico" type="image/x-icon">

<!-- chart -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.css" integrity="sha256-IvM9nJf/b5l2RoebiFno92E5ONttVyaEEsdemDC6iQA=" crossorigin="anonymous" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.css" integrity="sha256-aa0xaJgmK/X74WM224KMQeNQC2xYKwlAt08oZqjeF0E=" crossorigin="anonymous" />
<style>
.main {
	width: 100%;
}

.page {
	margin-right: 100px;
}


.chart {
	width: 50%;
	display: flex;
}
.child {
	flex: 1;
}
#storechart {
	margin-right: 50px;
}
</style>

</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<!-- chart -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.bundle.js" integrity="sha256-8zyeSXm+yTvzUN1VgAOinFgaVFEFTyYzWShOy9w7WoQ=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.bundle.min.js" integrity="sha256-TQq84xX6vkwR0Qs1qH5ADkP+MvH0W+9E7TdHJsoIQiM=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js" integrity="sha256-nZaxPHA2uAaquixjSDX19TmIlbRNCOrf5HO1oHl5p70=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js" integrity="sha256-R4pqcOYV8lt7snxMQO/HSbVCFRPMdrhAFMH+vr9giYI=" crossorigin="anonymous"></script>


<%
ArrayList<Medi> storedatas = (ArrayList<Medi>)medi.getMediStore();
ArrayList<Medi> searchdatas = (ArrayList<Medi>)medi.getMediSearch();
%>
<div class="main">
	<jsp:include page="inc_header.jsp" flush="false" />
	<div class="row page">
		<div class="col-3">
			<jsp:include page="inc_menu.jsp" flush="false" />
		</div>

		<div class="col-9">
			<nav class="navbar">
				<h3>데이터 시각화</h3>
			</nav>
			<br>
			
			<%
			if (storedatas.size() != 0) {
			%>
			<select id="store_name" name="store_name" style="display:none;">
			<%
				for(Medi me : (ArrayList<Medi>) storedatas) {
					%>
					<option value="<%=me.getMedi_name()%>"><%=me.getMedi_name()%></option>
					<%
					}
				%>
			</select>
			<select id="store_num" name="store_num" style="display:none;">
			<%
				for(Medi me : (ArrayList<Medi>) storedatas) {
					%>
					<option value="<%=me.getMedi_store()%>"><%=me.getMedi_store()%></option>
					<%
					}
				%>
			</select>
			<%
				
			}%>
			
			<%
			if (searchdatas.size() != 0) {
			%>
			<select id="search_name" name="search_name" style="display:none;">
			<%
				for(Medi me : (ArrayList<Medi>) searchdatas) {
					%>
					<option value="<%=me.getMedi_name()%>"><%=me.getMedi_name()%></option>
					<%
					}
				%>
			</select>
			<select id="search_num" name="search_num" style="display:none;">
			<%
				for(Medi me : (ArrayList<Medi>) searchdatas) {
					%>
					<option value="<%=me.getMedi_search()%>"><%=me.getMedi_search()%></option>
					<%
					}
				%>
			</select>
			<%
				
			}%>
			
			<div class="chart">
				<canvas id="storechart" class="child"></canvas>
				<canvas id="searchchart" class="child"></canvas>
			</div>

		</div>
	</div>
</div>

</body>

<script>
$(document).ready(function () {
	$('#chart').addClass('menu_active');
	
});
</script>

<script>
var stname = document.getElementById('store_name').options;
var stnameArray = [];
var stnum = document.getElementById('store_num').options;
var stnumArray = [];
var sename = document.getElementById('search_name').options;
var senameArray = [];
var senum = document.getElementById('search_num').options;
var senumArray = [];

for(var i=0; i<stname.length; i++){
	stnameArray[i] = stname[i].value;
	stnumArray[i] = stnum[i].value;
	
	senameArray[i] = sename[i].value;
	senumArray[i] = senum[i].value;
	
	//alert(stnameArray[i] + stnumArray[i] + senameArray[i] + senumArray[i]);
}

var ctx = document.getElementById('storechart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: [stnameArray[0], stnameArray[1], stnameArray[2], stnameArray[3], stnameArray[4]],
        datasets: [{
            label: '# of Votes',
            data: [stnumArray[0], stnumArray[1], stnumArray[2], stnumArray[3], stnumArray[4]],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
    	//maintainAspectRatio: false,
    	legend: {
					position: 'right',
				},
    	title: {
			display: true,
			text: '많이 보관하는 의약품',
			fontSize: 20
		},
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});


var ctx = document.getElementById('searchchart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: [senameArray[0], senameArray[1], senameArray[2], senameArray[3], senameArray[4]],
        datasets: [{
            label: '# of Votes',
            data: [senumArray[0], senumArray[1], senumArray[2], senumArray[3], senumArray[4]],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
    	//maintainAspectRatio: false,
    	legend: {
					position: 'right',
				},
    	title: {
			display: true,
			text: '많이 검색하는 의약품',
			fontSize: 20 
		},
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});
</script>
</html>

<% 
	} else {
		response.sendRedirect("index.jsp");
	}
}catch(Exception e){
	e.printStackTrace();
}
%>