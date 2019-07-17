<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div>				
				
				<div id = "map" style = "z-index: 1;width:100%; height:400px; border-radius: 25px"></div>
				<script>
				
				 
				var startlat = 40.75637123;
				var startlon = -73.98545321;
				var myMarker;
				var map; var options;
				var labelBusqueda;
				var lat;
				var lon;
				var xmlhttp;var url;
				var busquedaCoordenadas;
			
		
			function findUbicacionSeller(object){
				if(object=="seller"){

						if("${seller.lat}"!="0.0" && "${seller.lon}"!="0.0" ){
							lat="${seller.lat}";
							lon="${seller.lon}";					
							busquedaCoordenadas=1;
						}
						
						 xmlhttp = new XMLHttpRequest();
						
						 url = "https://nominatim.openstreetmap.org/search?format=json&limit=3&q="+
											"${seller.direccion},${seller.code},${seller.city},${seller.country}";
					
						 if(!busquedaCoordenadas){
							 //Busqueda direccion
							 labelBusqueda="<p><strong><spring:message code="mapa.label_busqueda_seller_lat_lon" /><strong></p>"+
								 "${seller.direccion}<p>${seller.code},${seller.city}</p>${seller.country}";
							  var no_encuentra_nada = new Promise(
									    // La función resolvedora es llamada con la
									    // habilidad de resolver o rechazar la promesa
									    function(resolve, reject) {
									      
									    	
									    	 xmlhttp.onreadystatechange = function()
											 {
											   if (this.readyState == 4 && this.status == 200)
											   {
											    var myArr = JSON.parse(this.responseText);
											
											    if(myArr && myArr.length>0){
											    	chooseAddr(myArr[0].lat,myArr[0].lon,18);				    	
											    }else{
											    	labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_seller_ciudad" /><b> ${seller.city}</b>'+'<p><i class="material-icons">person_pin</i>${seller.username}</p>';
											    	//labelBusqueda="<spring:message code="mapa.label_busqueda_seller_ciudad" /> <strong>${seller.username}</strong><br>${seller.city}"
											    	url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + "${seller.city}, ${seller.country}";
											    	resolve(url);								    
											    }
											    
											   }else{
												   labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_seller_ciudad" /><b> ${seller.city}</b>'+'<p><i class="material-icons">person_pin</i>${seller.username}</p>';
												  // labelBusqueda="<spring:message code="mapa.label_busqueda_seller_ciudad" /> <strong>${seller.username}</strong><br>${seller.city}"
												   url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + "${seller.city}, ${seller.country}";
											    	resolve(url);	
												   
											   }
											   
											 };
											
											 var url_encode=encodeURI(url);
											 xmlhttp.open("GET", url_encode, true);
											 xmlhttp.setRequestHeader("Content-Type", "text/json;charset=UTF-8");
											 xmlhttp.send();
									    }
									  );
							  
							  no_encuentra_nada.then(
									    // Registrar el valor de la promesa cumplida
									    function(val) {
									      
									      addr_search(val,15);
									    })
									  .catch(
									    // Registrar la raz�n del rechazo
									    function(reason) {
									      
									    });
						}else{
							//Busqueda con  lat and lon
							labelBusqueda='<p><i class="material-icons">location_on</i><b><spring:message code="mapa.label_busqueda_seller_lat_lon" /></b><br /><p><i class="material-icons">person_pin</i> ${seller.username}';
							
							chooseAddr(lat, lon, 15)
						}
				}
			}
			function findUbicacionUser(object){
				if(object=="user"){
						
						if("${user.lat}"!="0.0" && "${user.lon}"!="0.0" ){
							lat="${user.lat}";
							lon="${user.lon}";
							busquedaCoordenadas=1;
						}

						 xmlhttp = new XMLHttpRequest();

						 url = "https://nominatim.openstreetmap.org/search?format=json&limit=3&q="+
											"${user.direccion},${user.code},${user.city},${user.country}";

						 if(!busquedaCoordenadas){
							 //Busqueda direccion
							 labelBusqueda="<p><strong><spring:message code="mapa.label_busqueda_user_lat_lon" /><strong></p>"+
								 "${user.direccion}<p>${user.code},${user.city}</p>${user.country}";
							  var no_encuentra_nada = new Promise(
									    // La función resolvedora es llamada con la
									    // habilidad de resolver o rechazar la promesa
									    function(resolve, reject) {


									    	 xmlhttp.onreadystatechange = function()
											 {
											   if (this.readyState == 4 && this.status == 200)
											   {
											    var myArr = JSON.parse(this.responseText);
											 
											    if(myArr && myArr.length>0){
											    	chooseAddr(myArr[0].lat,myArr[0].lon,18);
											    }else{
											    	labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_user_ciudad" /><br><b> ${user.city}</b>';
											    	url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + "${user.city}, ${user.country}";
											    	resolve(url);
											    }

											   }else{
													labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_user_ciudad" /><br><b ${user.city}</b>';
												   url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + "${user.city}, ${user.country}";
											    	resolve(url);

											   }

											 };

											 var url_encode=encodeURI(url);
											 xmlhttp.open("GET", url_encode, true);
											 xmlhttp.setRequestHeader("Content-Type", "text/json;charset=UTF-8");
											 xmlhttp.send();
									    }
									  );

							  no_encuentra_nada.then(
									    // Registrar el valor de la promesa cumplida
									    function(val) {									     
									      addr_search(val,15);
									    })
									  .catch(
									    // Registrar la raz�n del rechazo
									    function(reason) {
									    
									    });
						}else{
							//Busqueda con  lat and lon
							labelBusqueda='<p><i class="material-icons">location_on</i><b><spring:message code="mapa.label_busqueda_user_lat_lon" /></b><br /> ';
							
							chooseAddr(lat, lon, 15)
						}
				}
			}
			function findUbicacionUserIndex(object){
				if(object=="user_index"){
						
						if("${user_index.lat}"!="0.0" && "${user_index.lon}"!="0.0" ){
							lat="${user_index.lat}";
							lon="${user_index.lon}";
						
							busquedaCoordenadas=1;
						}

						 xmlhttp = new XMLHttpRequest();

						 url = "https://nominatim.openstreetmap.org/search?format=json&limit=3&q="+
											"${user_index.direccion},${user_index.code},${user_index.city},${user_index.country}";

						 if(!busquedaCoordenadas){
							 //Busqueda direccion
							 labelBusqueda="<p><strong><spring:message code="mapa.label_busqueda_user_lat_lon" /><strong></p>"+
								 "${user_index.direccion}<p>${user_index.code},${user_index.city}</p>${user_index.country}";
							  var no_encuentra_nada = new Promise(
									    // La función resolvedora es llamada con la
									    // habilidad de resolver o rechazar la promesa
									    function(resolve, reject) {


									    	 xmlhttp.onreadystatechange = function()
											 {
											   if (this.readyState == 4 && this.status == 200)
											   {
											    var myArr = JSON.parse(this.responseText);
											    
											    if(myArr && myArr.length>0){
											    	chooseAddr(myArr[0].lat,myArr[0].lon,18);
											    }else{
											    	
											    	labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_user_ciudad" /><br><b> ${user_index.city}</b>';
											    	url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + "${user_index.city}, ${user_index.country}";
											    	resolve(url);
											    }

											   }else{
												   labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_user_ciudad" /><br><b> ${user_index.city}</b>';
												   url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + "${user_index.city}, ${user_index.country}";
											    	resolve(url);

											   }

											 };

											 var url_encode=encodeURI(url);
											 xmlhttp.open("GET", url_encode, true);
											 xmlhttp.setRequestHeader("Content-Type", "text/json;charset=UTF-8");
											 xmlhttp.send();
									    }
									  );

							  no_encuentra_nada.then(
									    // Registrar el valor de la promesa cumplida
									    function(val) {									 
									      addr_search(val,15);
									    })
									  .catch(
									    // Registrar la raz�n del rechazo
									    function(reason) {
									     
									    });
						}else{
							//Busqueda con  lat and lon			
							labelBusqueda='<p><i class="material-icons">location_on</i><b><spring:message code="mapa.label_busqueda_user_lat_lon" /></b><br /> ';
							chooseAddr(lat, lon, 15)
						}
				}
			}
	 
				function chooseAddr(lat1, lng1, zoom)
				{
					if(myMarker){
						
						 myMarker.closePopup();
						 map.setView([lat1, lng1],zoom);
						 myMarker.setLatLng([lat1, lng1]);
						 lat = lat1;
						 lon = lng1;
						 myMarker.bindPopup(labelBusqueda,zoom).openPopup();
						 
					}else{
						// Primera Addr, Inicializar
						 options = {
						            center: [lat1, lng1],
						            zoom: zoom
						         };
						map = L.map('map',options);

					

						L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {attribution: 'BANCALET'}).addTo(map);
						myMarker = L.marker([lat1, lng1], {title: "Coordinates", alt: "Coordinates", draggable: false}).addTo(map).on('dragend', function() {
							 var lat = myMarker.getLatLng().lat.toFixed(8);
							 var lon = myMarker.getLatLng().lng.toFixed(8);							
							 map.setView([lat,lon],zoom)
							
							});
					   myMarker.bindPopup(labelBusqueda,zoom).openPopup();
					}
				
				
				}

				function myFunction(arr)
				{
				 var i;
				 if(arr.length > 0)
				 {//Lista de resultados arr\[i]
				  for(i = 0; i < arr.length; i++){} 
				 }				
				}

				function addr_search(url,zoom)
				{
				 var xmlhttp = new XMLHttpRequest();
				
				 xmlhttp.onreadystatechange = function()
				 {
				   if (this.readyState == 4 && this.status == 200)
				   {
				    var myArr = JSON.parse(this.responseText);
				    if(myArr && myArr.length>0){
				    	chooseAddr(myArr[0].lat,myArr[0].lon,zoom);
				    }
				    
				   }
				 };
				 var url_encode=encodeURI(url);
				 xmlhttp.open("GET", url_encode, true);
				 xmlhttp.setRequestHeader("Content-Type", "text/json;charset=UTF-8");
				 xmlhttp.send();
				}
				
	

      </script>
</div>	