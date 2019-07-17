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

    <!--bootstrap -->

    <!-- Material Design Lite CSS -->

    <link rel="stylesheet"	href="<c:url value="/resources/material-design-lite/material.min.css"/>" />
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
                        <div class="card-box">
                            <div class="card-head">
                               	<header>
									<c:if test="${total_items != 0 }">
										<spring:message code="items.mis_items" arguments=": ${total_items}"/>
									</c:if>
									<c:if test="${total_items == 0 }">
										<spring:message code="items.sin_mis_items"/>
									</c:if>
			                    </header>
                            </div>
                            <div class="card-body">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
										<c:if test="${total_items != 0 }">
                                        <div class="table-responsive">
                                            <table class="table table-striped custom-table">
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
                                                    	<td><img src="<c:url value="/resources/img/type_food/${item.tipo}.png"/>" class="avatar" style="width:100px;height:100px;" ></td>
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
	<jsp:include page="/WEB-INF/jsp/piepagina.jsp" />
        </div>
    </div>
    <!-- end page content -->

    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/layout.js"/>"></script>
    <!-- Material -->
    <script src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
    <!-- end js include path -->
</body>
</html>
