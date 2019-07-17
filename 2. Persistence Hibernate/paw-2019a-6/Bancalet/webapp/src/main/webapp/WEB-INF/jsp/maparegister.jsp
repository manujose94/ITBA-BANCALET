<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div>				
<div id = "map" style = "width:100%; height:400px; border-radius: 25px"></div>
<script>		 
	var startlat = 40.75637123;
	var startlon = -73.98545321;
	var myMarker;
	var map; var options;var labelBusqueda;

	var elementLat=document.getElementById("lat");
	var elementLon=document.getElementById("lon");
	var busquedaCoordenadas=0;
	var busquedaRegion=0;
	var elementDireccion =document.getElementById("direccion");
	var elementCode =document.getElementById("code");
	var elementCountry =document.getElementById("country");
	var elementCity =document.getElementById("city");
	var direccion;
	var code;
	var city;
	var country;
	var lat;var lon;
	findUbicacion(lat,lon);
	function findUbicacion(lat,lon){
		console.log("launch maparegister function ubicacion...");
		this.lat=lat;
		this.lon=lon;			
		if(elementLat)
			this.lat=elementLat.value;
		if(elementLon)
			this.lon=elementLon.value;
		if(elementDireccion)
			direccion=elementDireccion.value;
		if(elementCode)
			code=elementCode.value;
		if(elementCountry)
			country=elementCountry.value;
		if(elementCity)
			city=elementCity.value
			
		if(this.lat && this.lon && this.lat !="0.0" && this.lon!="0.0" ){
			//console.log("search point by lat/lon...")	
			busquedaCoordenadas=1;
		}else if(city || country){
			//console.log("city and country found")
			busquedaRegion=1;
		}else{
			// initialize the map
			//console.log("initialize the map...")	
			mapInit(38.9722409, -0.3909504, 8)
		}
		
		var xmlhttp = new XMLHttpRequest();
		
		var url = "https://nominatim.openstreetmap.org/search?format=json&limit=3&q="+direccion+","+code+","+city+","+country;
	
		 if(!busquedaCoordenadas && busquedaRegion){
			 //Busqueda direccion
			 labelBusqueda="<p><strong><spring:message code="mapa.label_busqueda_seller_lat_lon" /><strong></p>"+direccion+"<p>"+code+","+city+"</p>"+country;
			  var no_encuentra_nada = new Promise(
					    // La funcion resolvedora es llamada con la
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
							    	labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_user_ciudad" /><br><b>'+city+"</b>";
							    	url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" +city+", "+country;
							    	resolve(url);								    
							    }						    
							   }else{
								   labelBusqueda='<i class="material-icons">location_city</i><b><spring:message code="mapa.label_busqueda_user_ciudad" /><br><b>'+city+"</b>";
							    	url="https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" +city +", "+country;
							    	resolve(url);								   
							   } 
							 };
							 var url_encode=encodeURI(url);
							 xmlhttp.open("GET", url_encode, true);
							 xmlhttp.setRequestHeader("Content-Type", "text/json;charset=UTF-8");
							 xmlhttp.send();
					    }
					  );
			  
			  no_encuentra_nada.then(// Registrar el valor de la promesa cumplida
					    function(val){
				  				addr_search(val,16);
				  		}).catch(// Registrar rechazo
					    function(reason){ //no se cumplio la persona debido a "reason"
						  });
		}else if(busquedaCoordenadas){
			//Busqueda con  lat and lon
			labelBusqueda='<p><i class="material-icons">location_on</i><b><spring:message code="mapa.label_busqueda_user_lat_lon" /></b><br />Lat:'+this.lat+' Lon:'+this.lon+'</p>';
			chooseAddr(this.lat, this.lon, 15)
		}				 
	}
	
	
	function chooseAddr(lat1, lng1, zoom)
	{
	
		if(myMarker){				
			 myMarker.closePopup();
			 map.setView([lat1, lng1],zoom);
			 myMarker.setLatLng([lat1, lng1]);
			 this.lat = lat1;
			 this.lon = lng1;		
			 if(document.getElementById("lat"))
			 	document.getElementById("lat").value=this.lat;
			 if(document.getElementById("lon"))
			 	document.getElementById("lon").value=this.lon;
			 console.log("1LON "+document.getElementById("lon").value)
			 myMarker.bindPopup(labelBusqueda,zoom).openPopup();					 
		}else{
			
			// Primera Addr, Inicializar	
		
			mapInit(lat1, lng1, zoom)	
			if(document.getElementById("lat"))document.getElementById("lat").value=lat1;
			 if(document.getElementById("lon"))document.getElementById("lon").value=lng1;

			myMarker = L.marker([lat1, lng1], {title: "Coordinates", alt: "Coordinates", draggable: true}).addTo(map).on('dragend', function() {
				 this.lat = myMarker.getLatLng()
				 this.lon = myMarker.getLatLng();	
			
				 map.setView([lat,lon],zoom)							
				});
		   
		   myMarker.bindPopup(labelBusqueda,zoom).openPopup();
		}
	
	
	}

	function mapInit(lat1, lng1, zoom)
	{				
		options = {
		            center: [lat1, lng1],
		            zoom: zoom
		         };
		if(map){
			 map.setView([lat1, lng1],zoom);
			
		}else{
			map = L.map('map',options);			
			L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {attribution: 'Bancalet'}).addTo(map);				
		}
		map.on('click', function(e) {
		
			if(myMarker)myMarker.closePopup();        
			var popLocation= e.latlng;							
			this.lat = popLocation.lat.toFixed(8);
			this.lon = popLocation.lng.toFixed(8);
			
			if(document.getElementById("lat"))
			document.getElementById("lat").value=this.lat;
			if(document.getElementById("lon"))
			document.getElementById("lon").value=this.lon;
	
			var popup = L.popup()					
			.setLatLng(popLocation)
			.setContent('<p><i class="material-icons">location_on</i><b><spring:message code="mapa.label_busqueda_user_lat_lon" /></b><br />Lat:'+this.lat+' Lon:'+this.lon+'</p>')
			.openOn(map);        
		});
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