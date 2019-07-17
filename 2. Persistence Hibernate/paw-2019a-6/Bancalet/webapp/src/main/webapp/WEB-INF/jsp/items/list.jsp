<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

  <!-- Material Design Lite CSS -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Material Design Lite CSS -->
<link rel="stylesheet" href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
  <!--Import materialize.css-->
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/materialize.min.css"/>"  media="screen,projection"/>
  <link type="text/css"  rel="stylesheet" href="<c:url value="/resources/css/nouislider.css"/>"  media="screen,projection"/>
  <!-- Template Styles -->
  <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet"
  	type="text/css" />
  <link href="<c:url value="/resources/css/plugins.min.css"/>"
  	rel="stylesheet" type="text/css" />
  <link href="<c:url value="/resources/css/responsive.css"/>"
  	rel="stylesheet" type="text/css">
  <link href="<c:url value="/resources/css/bancalet/bancalet.css"/>"
  	rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/js/nouislider.js"/>"></script>
  <!-- Favicon -->
  <link rel="shortcut icon" type="image/x-icon"
  	href="<c:url value="/resources/img/bancalet/appicon.ico"/>" />
      <script>
      $(document).ready(function(){
          $('.datepicker').datepicker({format:'yyyy-mm-dd'});
        });
      $(document).ready(function(){
          $('select').formSelect();
        });




      </script>

</head>
<body class="page-container page-header-fixed page-content-white page-md header-white logo-dark" data-customvalueone="${max}" >
	<div class="page-wrapper">

    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp"/>
    <!-- end header -->
	<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
    <!-- start page container -->
    <div class="page-container">
    <sec:authorize access="!isAuthenticated()">
	 	<jsp:include page="/WEB-INF/jsp/inicio/barralateralNologin.jsp" />
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
	 	<jsp:include page="/WEB-INF/jsp/barralateral.jsp"/>
	</sec:authorize>
        <!-- start page content -->
        <div class="page-content-wrapper">
            <div class="page-content">
            	<div class="row">
                    <div class="col-sm-12">
                        <div class="card-box">
							<div class="card-body">
			            		<div class="search">
								 <form action="">
								 <p>
								 	<b><spring:message code="item.search_form_label"/></b>
								 	</p>
						         	<input type="search" name="item_nombre" class="form-control" value="${item_nombre}" placeholder="<spring:message code="item.search_name_form_label"/>">
									<p>
										<b><spring:message code="item.tipo_form_label" /></b>
										<select name="item_tipo" >
											<option value="${tipo}" selected><spring:message code="item.tipo${tipo}"/></option>
			
											<c:if test="${tipo != 0}">
												<option value="0"><spring:message code="item.tipo0"/></option>
											</c:if>
			
											<c:if test="${tipo != 1}">
										    	<option value="1"><spring:message code="item.tipo1"/></option>
										    </c:if>
			
										    <c:if test="${tipo != 2}">
										    	<option value="2"><spring:message code="item.tipo2"/></option>
										    </c:if>
			
										    <c:if test="${tipo != 3}">
										    	<option value="3"><spring:message code="item.tipo3"/></option>
										    </c:if>
			
										    <c:if test="${tipo != 4}">
										    	<option value="4"><spring:message code="item.tipo4"/></option>
										    </c:if>
			
										    <c:if test="${tipo != 5}">
										    	<option value="5"><spring:message code="item.tipo5"/></option>
										    </c:if>
			
									  	</select>
								  	</p>
			
			              <!--antes o despues fecha_caducidad item -->
			              <p>
			                <b><spring:message code="item.eleccion_fecha_caducidad"/></b>
			                <select name="item_tipo_caducidad" >
			                  <option value="${tipoCad}" selected><spring:message code="item.tipoCaducidad${tipoCad}"/></option>
			
			                  <c:if test="${tipoCad != 0}">
			                    <option value="0"><spring:message code="item.tipoCaducidad0"/></option>
			                  </c:if>
			
			                  <c:if test="${tipoCad != 1}">
			                      <option value="1"><spring:message code="item.tipoCaducidad1"/></option>
			                    </c:if>
			
			                    <c:if test="${tipoCad != 2}">
			                      <option value="2"><spring:message code="item.tipoCaducidad2"/></option>
			                    </c:if>
			
			                  </select>
			                </p>
			
			              <!-- fecha_caducidad item -->
			                <p><input type="text" name="fecha_caducidad" value="${fecha_caducidad}" class="datepicker"></p>
			                <p>
			               		<b><spring:message code="item.range_price"/></b>
			                  </p>
			                  <br>
					               <div id = "test-slider"></div>
					               <input id="max" name="maximoSlaider" type="hidden" value="" />
					               <input id="min" name="minimoSlaider" type="hidden" value="" />   
								<p>
								 <br>
										<b><spring:message code="item.filtro" /></b>
										<c:if test="${tipo == 0}">
											<spring:message code="item.tipo0" />
										</c:if>
										<c:if test="${tipo == 1}">
											<spring:message code="item.tipo1" />
										</c:if>
										<c:if test="${tipo == 2}">
											<spring:message code="item.tipo2" />
										</c:if>
										<c:if test="${tipo == 3}">
											<spring:message code="item.tipo3" />
										</c:if>
										<c:if test="${tipo == 4}">
											<spring:message code="item.tipo4" />
										</c:if>
										<c:if test="${tipo == 5}">
											<spring:message code="item.tipo5" />
										</c:if>
										${item_nombre}
			              <c:if test= "${fecha_caducidadCheck == 1}">
			                <c:if test="${tipoCad == 1}">
			                  <spring:message code="item.tipoCaducidad1" />
			                  ${fecha_caducidad}
			                </c:if>
			                <c:if test="${tipoCad == 2}">
			                  <spring:message code="item.tipoCaducidad2" />
			                  ${fecha_caducidad}
			                </c:if>
			
			              </c:if>
						</p>
			
			            <button type="submit" class="btn btn-primary search-btn">
			              <spring:message code="item.search_form_button"/>
			            </button>
							</form>
				</div>
           	</div>
           	 </div>
           	  </div>
           	 </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card-box">
                            <div class="card-head">

                                <header><c:if test="${total_items != 0 }">
										<spring:message code="items.con_items" arguments=": ${total_items}"/>
									</c:if>
									<c:if test="${total_items == 0 }">
										<spring:message code="items.sin_items"/>
									</c:if></header>
                            </div>
                            <div class="card-body">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width" style="width:100%">
										<c:if test="${total_items != 0 }">
                                        <div class="table-responsive">
                                            <table class="table highlight table-striped custom-table">
                                                <thead class="text-left">
                                                <tr>
                                                	<th><spring:message code="item.tipo_form_label"/></th>
                                                	<th><spring:message code="item.image"/></th>
                                                    <th><spring:message code="item.name_form_label"/></th>
                                                    <th><spring:message code="item.description_form_label"/></th>
                                                    <th><spring:message code="item.price_form_label"/></th>

                                                    <th></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${items}" var="item">
                                                    <tr>
                                                    	<td><img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="avatar" style="width:80px;height:80px;" ></td>
                                                    	<td> 
                                                    		<c:if test="${item.urlImg != null && item.urlImg != ''}">
																<img id="myImg" style="border-radius: 15px;" class="z-depth-3" alt="Imagen del producto ${item.name}" src="<c:url value="data:image/jpg;base64,${item.urlImg}"/>"   width="150" height="100" >
															</c:if>
														</td>
                                                        <td><c:out value="${item.name}"/></td>
                                                        <td><c:out value="${item.description}"/></td>
                                                        <td><c:out value="${item.price}"/></td>
                                                        <td>
												           <div class="col-lg-12 text-center">

                                        				<a href="<c:url value="/items/${item.itemId}"/>"
                                           				class="btn btn-default rebeccapurple-color"><spring:message
                                               				 code="item.view"/></a><br>
                                    					</div>
												        </td>
                                                    </tr>
                                              </c:forEach>
                                                </tbody>
                                            </table>

                                        </div>
										</c:if>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
			</div>
        </div>
    </div>
    <!-- end page content -->
    <script src="<c:url value="/resources/js/wNumb.js"/>"></script>

    <!-- end footer -->

    <!-- start js include path -->
  <script>
     
  	 var maxim = "${maximo}";
     var slider = document.getElementById('test-slider');

    noUiSlider.create(slider, {
	   start: ["${min}", "${max}"],
	   connect: true,
	   step: 1,
	   orientation: 'horizontal',
	   range: {
	     'min': 0,
	     'max': parseInt(maxim,10)
   },
     behaviour: 'tap-drag',
     tooltips: true,
     format: wNumb({
         decimals: 0
     }),

 });
    
 slider.noUiSlider.on('update', function (values, handle) {
    document.getElementById("min").value=values[0];
    document.getElementById("max").value=values[1];
    console.log(document.getElementById("min").value)

});
  </script>

    <!-- bootstrap -->

<script src="<c:url value="/resources/js/materialize.min.js"/>"></script>

    <!-- end js include path -->
</body>
</html>
