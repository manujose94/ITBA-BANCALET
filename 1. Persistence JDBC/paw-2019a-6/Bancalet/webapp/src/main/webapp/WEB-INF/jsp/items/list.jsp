<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.io.*,java.util.*" %>
<html>
<head>
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
    <!-- icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--bootstrap -->

    <!-- Material Design Lite CSS -->
    <link rel="stylesheet"
	href="<c:url value="/resources/material-design-lite/material.min.css"/>" />

    <link rel="stylesheet" href="<c:url value="/resources/css/material_style.css"/>"/>
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/bancalet/bancalet.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="/resources/img/bancalet/appicon.ico"/>
  <!-- Slider con Jquery -->
  	  <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"  rel = "stylesheet">
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      <!-- Slider Javascript -->
      <script>
         $(function() {
        	 var maximo = $("body").attr("data-customvalueone")
            $( "#slider-3" ).slider({
               range:true,
               min: 0,
               max: maximo*2,
               values: [ 0, maximo ],
               slide: function( event, ui ) {
                  $( "#price" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
               }
            });
            $( "#price" ).val( "$" + $( "#slider-3" ).slider( "values", 0 ) +
               " - $" + $( "#slider-3" ).slider( "values", 1 ) );
         });

      </script>

</head>
<body class="page-header-fixed page-content-white page-md header-white logo-dark" data-customvalueone="${max}" >
	<div class="page-wrapper">

    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/encabezadopagina.jsp"/>
    <!-- end header -->

    <!-- start page container -->
    <div class="page-container">
       <jsp:include page="/WEB-INF/jsp/barralateral.jsp"/>

        <!-- start page content -->
        <div class="page-content-wrapper">
            <div class="page-content">
            	<div class="row">
			         <form class="form-inline" method="post" action="">
			         <div class="search">
			         	<input type="text" name="item_nombre" class="form-control" placeholder="<spring:message code="item.search_name_form_label"/>">
						<p>
							<spring:message code="item.tipo_form_label" />
							<select name="item_tipo" >
								<option value="0"><spring:message code="item.tipo0"/></option>
							    <option value="1"><spring:message code="item.tipo1"/></option>
							    <option value="2"><spring:message code="item.tipo2"/></option>
							    <option value="3"><spring:message code="item.tipo3"/></option>
							    <option value="4"><spring:message code="item.tipo4"/></option>
							    <option value="5"><spring:message code="item.tipo5"/></option>
						  	</select>
					  	</p>
					  	<p>
							<spring:message code="item.filtro" />
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
						</p>
			    		<p>
			         		<label class="label" for = "price"><spring:message code="item.range_price" /></label>
			         		<input type="text" class="label" id = "price" name="slider" style = "border:0; color:#00000; font-weight:bold;" ></input>

			      		</p>
						<button type="submit" class="btn btn-primary search-btn">
							<spring:message code="item.search_form_label"/>
						</button>
			     	 <div id = "slider-3"></div>
		     	 </div>
				</form>
           	</div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card-box">
                            <div class="card-head">

                                <header><spring:message code="item.lista_label" arguments=": ${total_items}"/></header>
                            </div>
                            <div class="card-body">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                        <div class="table-responsive">
                                            <table class="table table-striped custom-table">
                                                <thead class="text-left">
                                                <tr>
                                                	<th><spring:message code="item.tipo_form_label"/></th>
                                                    <th><spring:message code="item.name_form_label"/></th>
                                                    <th><spring:message code="item.description_form_label"/></th>
                                                    <th><spring:message code="item.price_form_label"/></th>

                                                    <th></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${items}" var="item">
                                                    <tr>
                                                    	<td><img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="avatar" style="width:100px;height:100px;" ></td>
                                                        <td><c:out value="${item.name}"/></td>
                                                        <td><c:out value="${item.description}"/></td>
                                                        <td><c:out value="${item.price}"/></td>
                                                        <td>
												           <div class="col-lg-12 text-center">
                                        				<a href="<c:url value="/items/${item.itemid}"/>"
                                           				class="btn btn-default rebeccapurple-color"><spring:message
                                               				 code="item.view"/></a><br>
                                    					</div>
												        </td>
                                                    </tr>
                                              </c:forEach>
                                                </tbody>
                                            </table>

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
    </div>
    <!-- end page content -->


    <!-- start footer -->
    <jsp:include page="/WEB-INF/jsp/piepagina.jsp"/>
    <!-- end footer -->

    <!-- start js include path -->
  
    <!-- bootstrap -->

    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/layout.js"/>"></script>
    <!-- Material -->
    <script src="<c:url value="/resources/material-design-lite/material.min.js"/>"></script>
    <!-- end js include path -->
</body>
</html>
