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
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
      <!-- icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Material Design Lite CSS -->
<link rel="stylesheet" href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
    <!--bootstrap -->
    <link href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <!-- Material Design Lite CSS -->
    <link rel="stylesheet" href="<c:url value="/webjars/material-design-lite/1.1.0/material.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/material_style.css"/>"/>
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/bancalet/bancalet.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value="/resources/img/bancalet/appicon.ico"/>" />
  <!-- Slider con Jquery -->
  	  <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"  rel = "stylesheet">
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
</head>
<body class="page-header-fixed page-content-white page-md header-white logo-dark" >
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
                 <div class="col-sm-12">
                <c:if test="${listaSize == 0}">
	                <div class="card-box">
                            <div class="card-head">
                                <header>
                                		<spring:message code="contact.sin_mensajes"/>
                                </header>
                            </div>
	                  </div>
	                  <div class="card-body">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                    </div>
                                </div>
                     </div>
                  </c:if>
                   <c:if test="${listaSize != 0}">
                    <c:forEach items="${items}" var="item">
                        <div class="card-box">
                            <div class="card-head">
                                <header><img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="avatar" style="width:80px;height:80px;" ><spring:message code="contact.message_for" arguments="${item.name} "/></header>
                            </div>
                            <div class="card-body">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                        <div class="table-responsive">
                                            <table class="table table-striped custom-table">
                                                <thead class="text-left">
                                                <tr>
                                                    <th><spring:message code="contact.nombre_label"/></th>
                                                    <th><spring:message code="contact.fecha"/></th>
                                                    <th><spring:message code="contact.user_seller"/></th>
                                                    <th><spring:message code="contact.mensaje"/></th>
                                                </tr>
                                                </thead>
                                                <tbody> 
                                                <c:forEach items="${lista}" var="contac">       
                                                <c:if test="${contac.itemId == item.itemId }">                                      
                                                    <tr> 
														<td>
														
												           <div class="col-lg-12 text-center">
		                                        				<a href="<c:url value="/items/${contac.itemId}"/>"
		                                           				class="btn btn-default rebeccapurple-color"><spring:message
		                                               				 code="contact.ver_item"/></a><br>
	                                    					</div>
												        </td>
                                                        <td><c:out value="${contac.fechaContacto}"/></td>
                                                        <td> <c:forEach items="${users}" var="us">
                                                        		<c:if test="${contac.idVendedor == us.userId and item.itemId == contac.itemId }">
                                                        			<c:out value="${us.username}"/>
                                                        		</c:if>
                                                        </c:forEach>
                                                        </td>
                                                        <td><c:out value="${contac.mensaje}"/></td>
                                                    </tr>
                                                    </c:if>
                                              </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
        </div>
    </div>
    <!-- end page content -->

    <!-- start js include path -->
    <script src="<c:url value="/webjars/jquery/3.0.0/jquery.min.js"/>"></script>
    <script src="<c:url value="/webjars/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"/>"></script>
    <!-- bootstrap -->
    <script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/layout.js"/>"></script>
    <!-- Material -->
    <script src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
    <!-- end js include path -->
</body>
</html>