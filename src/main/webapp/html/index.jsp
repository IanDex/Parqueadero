<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String path =  request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>Parqueadero</title>
	<meta name="description" content="#">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap core CSS -->
	<link href="dist/css/lib/bootstrap.min.css" type="text/css" rel="stylesheet">
	<!-- Swipe core CSS -->
	<link href="dist/css/swipe.min.css" type="text/css" rel="stylesheet">
	<link rel="stylesheet" href="dist/css/lib/animate.min.css">
	<link href="dist/css/dark.css" id="dark" type="text/css" rel="stylesheet">
</head>

<body>
	<main>
		<div class="layout">
			<!-- Start of Navigation -->
			<div class="navigation">
				<div class="container">
					<div class="inside">
						<div class="nav nav-tab menu">

							<a href="#addPlaca" data-toggle="tab" class="active"><i class="material-icons active">add_box</i></a>
							<a href="#discussions" data-toggle="tab"><i class="material-icons">exit_to_app</i></a>
							<button class="btn mode"><i class="material-icons">brightness_2</i></button>
						</div>
					</div>
				</div>
			</div>
			<!-- End of Navigation -->
			<!-- Start of Sidebar -->
			<div class="sidebar" id="sidebar">
				<div class="container">
					<div class="col-md-12">
						<div class="tab-content">
							<!-- Start of Contacts -->
							<div class="tab-pane fade active show" id="addPlaca">
								<div class="search">
									<form class="form-inline position-relative">
										<input type="text" class="form-control" id="placa" placeholder="Ingresar Nueva Placa">
									</form>
									<button class="btn create" id="sendForm"><i class="material-icons">send</i></button>
								</div>
								<div class="notifications">
									<label>La Hora es:</label>
									<h1 class="clock"></h1><br>
									<label>La Fecha es:</label>
									<h1 id="date"></h1>
								</div>
							</div>
							<!-- End of Contacts -->
							<!-- Start of Discussions -->
							<div id="discussions" class="tab-pane fade">
								<div class="search">
									<form class="form-inline position-relative">
										<input type="search" class="form-control" id="conversations" placeholder="Buscar Placa">
									</form>
									<button class="btn create"><i class="material-icons">search</i></button>
								</div>
								<div class="discussions">
									<h1>Placas en el Sistema</h1>
									<div class="listPlacas"></div>
								</div>
							</div>
							<!-- End of Discussions -->
						</div>
					</div>
				</div>
			</div>
			<!-- End of Sidebar -->
			<div class="main">
					<div class="tab-content" id="nav-tabContent">
							<div class="chat" id="chat1">
								<div class="content" id="content">
									<div class="container">
										<h1>Tabla de Registros</h1>			
										<div class="tbl"></div>
	
									</div>
								</div>
							
							</div>
						<!-- End of Babble -->
						<!-- Start of Babble -->
						
						<!-- End of Babble -->
					</div>
			</div>
		</div> <!-- Layout -->
		<div class="app"></div>
	</main>
	
	<!--  Bootstrap/Swipe core JavaScript
		================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="<%=path %>/dist/js/vendor/popper.min.js"></script>
	<script src="<%=path %>/dist/js/swipe.min.js"></script>
	<script src="<%=path %>/dist/js/bootstrap.min.js"></script>
	<script src="<%=path %>/dist/js/app.js"></script>
</body>

</html>