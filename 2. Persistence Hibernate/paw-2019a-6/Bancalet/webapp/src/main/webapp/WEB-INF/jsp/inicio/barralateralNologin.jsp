<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="sidebar-container">
	<div class="sidemenu-container navbar-collapse collapse fixed-menu">
		<div id="remove-scroll">
			<ul class="sidemenu page-header-fixed p-t-20"
				data-keep-expanded="false" data-auto-scroll="true"
				data-slide-speed="200">
				<li class="sidebar-toggler-wrapper hide">
					<div class="sidebar-toggler">
						<span></span>
					</div>
				</li>
				<li class="sidebar-user-panel">
					<div class="user-panel">
					   <header class="drawer-header">
					   	
					   </header>
					</div>
				</li>
				
				<!--Inicio-->
				<li class="nav-item">
					<a href="<c:url value="/index"/>"class="nav-link "> 
						<i class="material-icons">store</i> 
						<span class="title"><spring:message code="sidebar.start" /></span>
					</a>
				</li>
				
				<!-- Anuncios de la app -->
				<li class="nav-item">
					<a href="<c:url value="/items"/>"class="nav-link "> 
						<i class="material-icons">local_grocery_store</i>
						<span class="title"><spring:message code="sidebar.anuncios" /></span>
					</a>
				</li>
				
				
				<!--Register-->
				<li class="nav-item">
					<a href="<c:url value="/users/register"/>"class="nav-link "> 
						<i class="material-icons">person_add</i> 
						<span class="title"><spring:message code="sidebar.register" /></span>
					</a>
				</li>
				
				<!--Login-->
				<li class="nav-item">
					<a href="<c:url value="/users/login"/>"class="nav-link "> 
						<i class="material-icons">account_circle</i>
						<span class="title"><spring:message code="sidebar.login" /></span>
					</a>
				</li>
				
				<!--Contacto-->
				<li class="nav-item">
					<a href="<c:url value="/contact"/>"class="nav-link "> 
						<i class="material-icons">help</i>
						<span class="title"><spring:message code="sidebar.help" /></span>
					</a>
				</li>
				
			</ul>
		</div>
	</div>
</div>
