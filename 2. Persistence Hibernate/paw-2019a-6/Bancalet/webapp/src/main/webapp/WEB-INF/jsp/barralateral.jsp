<%@ page language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="sidebar-container" style="width=100%">
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
					   <div class="sidebar-userpic">
					   	<sec:authorize access="hasRole('ROLE_USER')">
				   			<c:if test="${user != null}">
					   			<c:if test="${user.urlImg != null && user.urlImg != ''}">
									<img src="<c:url value="data:image/jpg;base64,${user.urlImg}"/>"  class="avatar" style="width:100px;height:100px;" >
								</c:if>
								<c:if test="${user.urlImg == null || user.urlImg == ''}">							
									<img src="<c:url value="/resources/img/avatar/avatar${user.numImg}.png"/>" >										
	                        	</c:if>	
	                        </c:if>	 
	                        <c:if test="${user == null}">
	                        	<img src="<c:url value="/resources/img/user.jpg"/>" >	                        		
	                       </c:if>
	                    </sec:authorize>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<c:if test="${user != null}">
								<c:if test="${user.urlImg != null && user.urlImg != ''}">
									<img src="<c:url value="data:image/jpg;base64,${user.urlImg}"/>"  class="avatar" style="width:100px;height:100px;" >
								</c:if>
								<c:if test="${user.urlImg == null || user.urlImg == ''}">							
									<img src="<c:url value="/resources/img/admin/admin${user.numImg}.png"/>"  class="avatar" style="width:100px;height:100px;" >                      											
	                        	</c:if>	
							</c:if>	 
							<c:if test="${user == null}">
								<img src="<c:url value="/resources/img/admin/admin.jpg"/>" >	 
							</c:if>
						</sec:authorize>
		                                               
		                </div>                       	
						<div class="profile-usertitle">
							<div class="sidebar-userpic-name">
								<sec:authentication property="principal.username" />
							</div>
							<!--${user.email}-->
							<div class="profile-usertitle-job"><spring:message code="sidebar.refran" /></div>
							</div>
							</header>
					</div>
				</li>
				<!-- admin page -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item"><a href="<c:url value="/admin/"/>"
						class="nav-link"> <i class="material-icons">dashboard</i> <span
							class="title"><spring:message code="sidebar.dashboard" /></span>
					</a></li>
				</sec:authorize>
				
				<!-- admin issues -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class="nav-item">
					<a href="<c:url value="/admin/issues"/>" class="nav-link "><i class="material-icons">report_problem</i>
						<c:if test="${totalAyudas != 0}">
							<span class="title mdl-badge" data-badge="${totalAyudas}">
					 	</c:if>
					 		<spring:message code="sidebar.isues" />
						<c:if test="${totalAyudas != 0}">
							</span>
						</c:if>
					</a>
					</li>
				</sec:authorize>
				
				<!-- admin archivedissues -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item"><a href="<c:url value="/admin/archivedissues"/>"
						class="nav-link"> <i class="material-icons">assignment</i><span
							class="title"><spring:message code="sidebar.archivedissues" /></span>
					</a></li>
				</sec:authorize>
				
				<!-- admin register users-->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item"><a
						href="<c:url value="/admin/registUser"/>"
						class="nav-link nav-toggle"> <i class="material-icons">dvr</i> <span
							class="title"><spring:message code="sidebar.regist_user" /></span>
					</a></li>
				</sec:authorize>
				
				<!-- admin list of users-->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item"><a href="<c:url value="/admin/userList"/>"
						class="nav-link "> <i class="material-icons">supervisor_account</i> <span
							class="title"><spring:message code="sidebar.user_list" /></span>
					</a></li>
				</sec:authorize>
				<!-- mis sugerencias-->
				<sec:authorize access="hasRole('ROLE_USER')">
					<li class="nav-item"><a href="<c:url value="/users/missugerencias"/>"
						class="nav-link "> <i class="material-icons">favorite</i><span
							class="title"><spring:message code="sidebar.my_sugestions" /></span>
					</a></li>
				</sec:authorize>
				<!-- User page-->
				<sec:authorize access="hasRole('ROLE_USER')">
					<li class="nav-item"><a
						href="<c:url value="/users/returnIndex"/>" class="nav-link "> 
						<i class="material-icons">account_circle</i>
						<span class="title"><spring:message code="sidebar.perfil" /></span>
					</a></li>
				</sec:authorize>
				
				<!-- Todos los Anuncios de la app-->
				<li class="nav-item"><a href="<c:url value="/items/"/>"
					class="nav-link "> <i class="large material-icons">local_grocery_store</i> <span
						class="title"><spring:message code="sidebar.item_list" /></span>
				</a></li>
				<sec:authorize access="hasRole('ROLE_USER')">
					<!-- Registrar anuncio-->
					<li class="nav-item"><a href="<c:url value="/items/register"/>"
						class="nav-link "> <i class="material-icons">add_shopping_cart</i> <span
							class="title"><spring:message code="sidebar.add_item" /></span>
					</a></li>
					
					<!-- mis anuncios-->
						<li class="nav-item"><a href="<c:url value="/items/myitems"/>"
							class="nav-link "> <i class="material-icons">style</i><span
								class="title"><spring:message code="sidebar.my_items" /></span>
						</a></li>
				
					<!-- historial compras user-->
					<li class="nav-item">
					<a href="<c:url value="/users/miscompras"/>" class="nav-link "><i class="material-icons">local_mall</i>
						<c:if test="${listaSizeRate != 0}">
							<span class="title mdl-badge" data-badge="${listaSizeRate}">
					 	</c:if>
					 		<spring:message code="sidebar.historial_compras_list" />
						<c:if test="${listaSizeRate != 0}">
							</span>
						</c:if>
					</a>
					</li>
	
					<!-- historial ventas user-->
					<li class="nav-item"><a href="<c:url value="/users/misventas"/>"
						class="nav-link "> <i class="material-icons">monetization_on</i> <span
							class="title"><spring:message code="sidebar.historial_ventas_list" /></span>
					</a></li>
					
					<!-- Contactados enviados-->
					<li class="nav-item"><a href="<c:url value="/users/miscontactose"/>"
						class="nav-link "> <i class="material-icons">mail</i> <span
							class="title"><spring:message code="sidebar.contactados_enviados" /></span>
					</a></li>
					
					<!-- Contactados recibidos-->
					<li class="nav-item">
					<a href="<c:url value="/users/miscontactosr"/>" class="nav-link "><i class="material-icons">message</i>
						<c:if test="${listaSizeMsg != 0}">
							<span class="title mdl-badge" data-badge="${listaSizeMsg}">
					 	</c:if>
					 		<spring:message code="sidebar.contactados_recibidos" />
						<c:if test="${listaSizeMsg != 0}">
							</span>
						</c:if>
					</a>
					</li>
					
					<!-- Ayuda-->
					<li class="nav-item"><a href="<c:url value="/users/help"/>"
						class="nav-link "><i class="material-icons">help</i><span
							class="title"><spring:message code="sidebar.ayuda" /></span>
					</a></li>
					
				</sec:authorize>
				<!-- logout page-->
				<li class="nav-item"><a href="<c:url value="/logout"/>"
					class="nav-link"> <i class="material-icons">exit_to_app</i> <span
						class="title"><spring:message code="sidebar.logout" /></span>
				</a></li>
			</ul>
		</div>
	</div>
</div>
