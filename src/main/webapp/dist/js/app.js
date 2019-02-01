$(document).ready(function(){
	placas();
	$("#sendForm").click(function(){
		var placa = $("#placa").val();
		if(placa!=""){
			$.ajax({
				url: "/test",
				type: "POST",
				data: {placa: placa, opc : "send"},
				beforeSend: function() {
			      console.log("Enviando");
			    },
			    success: function(data){
					if(data=="true"){
						not("Placa Ingresada con Exito");
						$("#placa").val('');
						placas();
					}else if(data=="rep"){
						not("La placa <strong>" + placa +"</strong> ya ingreso");
						$("#placa").val('');
					}else{
						not("Problemas para conectarnos con la BD");
					}
				},error: function(e){
					alert("Error " + e);
				}
			});
		}else{
			console.log("Vacio");
			not("Ingrese la placa para continuar");
		}
	});
	
	function not(msg){
		$(".app").append('<span class="notifications snackbar sb animated fadeInUp">' + msg + '<a class="a" href="#">Ok</a></span>')
		setTimeout(function() {
			$(".sb").removeClass('fadeInUp');
			$(".sb").addClass('fadeOutDown');
			$(".app").remove(".sb");
		}, 8000);
		 $(".a").click(function(){
			 $(".sb").removeClass('fadeInUp');
			$(".sb").addClass('fadeOutDown');
			$(".app").remove(".sb");
		 });
	}
	
	function placas(){
		$("#chats").remove();
		$(".listPlacas").append(`
				<div class="list-group" id="chats" role="tablist"></div>
		`);
		$.ajax({
			url: "/test",
			type: "POST",
			data : {opc : "placas"},
			beforeSend: function() {
		      console.log("Enviando");
		    },
		    success: function(data){
		    	var json = JSON.parse(data);
		    	var html = "";
				$.each(json, function(key, value) {
					html += `    <a href="#list-chat" class="sale filterDiscussions all unread single active" id="list-chat-list" data-toggle="list"
								        role="tab">
								        <div class="data">
								        	<div class="row">
								        		<div class="col-12">
									        		<label style="text-align:left">Placa</label>
									        		<br>
									        		<h5>${value.placa}</h5><br>
									        	</div>
									        	<input type="hidden" value="${value.id}" id="id">
									        	<div class="col-12">
									             <label style="text-align:left">Fecha y Hora de entrada</label><br>
									            <h5>${value.fecha}</h5><br>
									            </div>
									        	</div>
									          <div class="data">  
									            <button type="button" id="" class=" btn button" style="padding: 5px 20px;width: 44%;text-align: center;background: #2196F3;color: #fff;border-radius: 6px;">Sale</button>
								        	</div></div>
								        </div>
								    </a>`
				});
				
				$("#chats").append(html);
				$(".sale").click(function(){
					var id = ($("#id").val());
					$.ajax({
						url: "/test",
						type: "POST",
						data: {id: id, opc : "sale"},
						beforeSend: function() {
					      console.log("Enviando");
					    },
					    success: function(data){
					    	placas();
					    	tabla();
							if(data=="true"){
								not("Placa Ingresada con Exito");
								$("#placa").val('');
							}else if(data=="rep"){
								not("La placa <strong>" + placa +"</strong> ya ingreso");
								$("#placa").val('');
							}else{
								not("Problemas para conectarnos con la BD");
							}
						},error: function(e){
						alert("Error " + e);
						}
					});
				});
			},error: function(e){
				alert("Error " + e);
			}
		});
	}
	tabla();
	function tabla(){
		$(".table").remove();
		$(".tbl").append(`
		<table class="table table-hover table-bordered results">
		  <thead>
		    <tr>
		      <th>#</th>
		      <th class="">Placa</th>
		      <th class="">Entrada</th>
		      <th class="">Salida</th>
		      <th class="">Valor</th>
		    </tr>
		  </thead>
		  <tbody class="tbody">
			
		  </tbody>
		</table>
		`);
		$.ajax({
			url: "/test",
			type: "POST",
			data: {opc : "tabla"},
			beforeSend: function() {
		      console.log("Enviando");
		    },
		    success: function(data){
		    	var json = JSON.parse(data);
		    	var html = "";
		    	$.each(json, function(key, value) {
		    		html += `<tr>
						        <th scope="row">${value.id}</th>
						        <td>${value.placa}</td>
						        <td>${value.fecha}</td>
						        <td>${value.fechasalio}</td>
						        <td>${value.valor}</td>
						      </tr>`;
		    	});
		    	$(".tbody").append(html);
				
			},error: function(e){
				alert("Error " + e);
			}
		});
	}
	
	
});