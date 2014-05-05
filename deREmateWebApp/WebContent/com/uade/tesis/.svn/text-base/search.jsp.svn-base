<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>

<f:view>
	<a4j:loadStyle src="/css/DeRemateTesis.css"></a4j:loadStyle>
	
	<h:form id="MAIN" enctype="multipart/form-data">
	
		<t:panelGrid columns="2" width="100%">
			<f:facet name="Header">
				<h:outputText value="Buscar Postings por fotografia"></h:outputText>
			</f:facet>
			
			<h:outputText value="Ingresar una Foto:" escape="false"></h:outputText>
			<t:panelGroup>
				<t:inputFileUpload 
				  		id="myUploadedFile" 
				  		storage="file" accept="image/*" 
				  		value="#{searchbean.form.uploadedFile}"
				  		valueChangeListener="#{searchbean.refreshImage}"
				  		onchange="document.getElementById('MAIN').submit();"/>
			</t:panelGroup>

			<h:outputText value="&nbsp;" 
						rendered="#{not empty searchbean.form.imagesList}" 
						escape="false"></h:outputText>
			<t:panelGroup rendered="#{not empty searchbean.form.imagesList}">
				<h:outputText value="Imagen Ingresada:&nbsp;" escape="false"></h:outputText>
				<h:graphicImage id="image1" value="#{searchbean.form.imagesList}" width="100" height="50" />
				<h:outputText value="&nbsp;<br>" escape="false"></h:outputText>
			</t:panelGroup>
			
			<t:panelGroup colspan="2" style="text-align: center;">			
				<h:commandButton value="Buscar" 
						rendered="#{not empty searchbean.form.imagesList}"
						action="#{searchbean.searchPosting}"></h:commandButton>
			</t:panelGroup>

			<t:panelGroup colspan="2" style="text-align: center;">			
				<h:outputText value="<HR>" escape="false"></h:outputText>
			</t:panelGroup>

			<t:panelGroup colspan="2" 
					rendered="#{not empty searchbean.form.postings}"
					style="text-align: center;">
				<t:dataTable var="item"
						width="90%"
						columnClasses=""
						rowIndexVar="index"
						cellpadding="0"
						cellspacing="0"
						value="#{searchbean.form.postings}">
					<t:column styleClass="#{(index % 2 == 0)?'busquedaMarcada':'busquedaNoMarcada'}">
						<f:facet name="header">
							<h:outputText value="Imagen Principal"></h:outputText>
						</f:facet>
						<h:outputLink value="/deREmateTesisWebApp/com/uade/tesis/posting.jsf?posting_id=#{item.id}">
							<h:graphicImage value="#{item.imagenPrincipal}" width="90" height="90" />
						</h:outputLink>
					</t:column>
					<t:column styleClass="#{(index % 2 == 0)?'busquedaMarcada':'busquedaNoMarcada'}">
						<f:facet name="header">
							<h:outputText value="Titulo"></h:outputText>
						</f:facet>
						<h:outputLink value="/deREmateTesisWebApp/com/uade/tesis/posting.jsf?posting_id=#{item.id}">
							<h:outputText value="#{item.titulo}"></h:outputText>
						</h:outputLink>
					</t:column>
					<t:column styleClass="#{(index % 2 == 0)?'busquedaMarcada':'busquedaNoMarcada'}">
						<f:facet name="header">
							<h:outputText value="Precio"></h:outputText>
						</f:facet>
						<h:outputText value="$ #{item.precio}.00"></h:outputText>
					</t:column>
					<t:column styleClass="#{(index % 2 == 0)?'busquedaMarcada':'busquedaNoMarcada'}">
						<f:facet name="header">
							<h:outputText value="Datos de venta"></h:outputText>
						</f:facet>
						<h:panelGroup>
							<h:outputText value="Articulo Nuevo" rendered="#{item.tipoProducto == 0}"></h:outputText>
							<h:outputText value="Articulo Usado" rendered="#{item.tipoProducto == 1}"></h:outputText>
							<h:outputText value="<br>" escape="false"></h:outputText>
							<h:outputText value="#{item.cantVentas} vendidos"></h:outputText>
							<h:outputText value="<br>" escape="false"></h:outputText>
							<h:outputText value="#{item.dueno.ubicacion}"></h:outputText>
						</h:panelGroup>
					</t:column>
				</t:dataTable>
			</t:panelGroup>			
		</t:panelGrid>
	</h:form>
</f:view>

</body>
</html>
