<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>

<f:view>

	<a4j:loadStyle src="/css/DeRemateTesis.css"></a4j:loadStyle>
	
	<h:form id="MAIN" enctype="multipart/form-data">
	
		<t:panelGrid columns="2"
				width="90%"
				headerClass="headerStyle"
				columnClasses="postingColumn,postingColumn"
				styleClass="blueBorder"
				cellpadding="0" 
				cellspacing="0"
				style="margin: 0px; padding: 0px;">
			<f:facet name="header">
				<h:panelGroup>
					<h:outputText value="&nbsp;" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.titulo}"></h:outputText>
				</h:panelGroup>
			</f:facet>
			
			<t:panelGrid columns="2">
				<h:outputText value="Fotos" escape="false"></h:outputText>
				<t:panelGroup rendered="#{not empty postingbean.form.imagesList}">
					<t:dataList var="item"
							rowIndexVar="index"
							rowCountVar="count"
							id="images"
							style="vertical-align:text-top;"
							value="#{postingbean.form.imagesList}">
						<t:panelGrid columns="4">
							<h:graphicImage id="image1" value="#{item}" width="150" height="100" />
							<h:outputText value="&nbsp;<br>" escape="false"></h:outputText>
						</t:panelGrid>
					</t:dataList>
				</t:panelGroup>
			</t:panelGrid>
			
			<t:panelGrid columns="1" width="100%"
					style="margin: 0px; padding: 0px;">
				<t:panelGrid columns="2" width="100%"
						columnClasses="postingColumn2,postingColumn3"
						style="margin: 0px; padding: 0px; background-color: RGB(231,231,231); font-size: 16px; font-family: 'arial';">
					<h:outputText value="Precio Final:" escape="false"></h:outputText>
					<h:outputText value="$ #{postingbean.form.precio}.00"></h:outputText> 
				</t:panelGrid> 
					
				<t:panelGrid columns="2" width="100%">
					<h:outputText value="Categor&iacute;a:<br>" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.categoriaDesc}"></h:outputText>
				</t:panelGrid> 
					
				<t:panelGrid columns="2" width="100%"
						columnClasses="postingColumn2,postingColumn3"
						style="margin: 0px; padding: 0px; background-color: RGB(231,231,231); font-size: 16px; font-family: 'arial';">
					<h:outputText value="Cantidad:" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.cantProductos - postingbean.form.ventas} disponibles"></h:outputText> 

					<h:outputText value="Tu Oferta:" escape="false"></h:outputText>
					<h:outputText value="$ #{postingbean.form.precio}.00 c/u"></h:outputText> 

					<t:panelGroup colspan="2" style="vertical-align: middle; text-align: center; height: 35px;">
					</t:panelGroup>
				</t:panelGrid> 

				<t:panelGrid columns="2" width="100%"
						columnClasses="postingColumn2,postingColumn3"
						style="margin: 0px; padding: 0px; background-color: RGB(244,244,244); font-size: 16px; font-family: 'arial';">
					<h:outputText value="Tipo de producto:" escape="false"></h:outputText>
					<h:outputText value="Nuevo" rendered="#{postingbean.form.tipoProducto == 0}"></h:outputText>
					<h:outputText value="Usado" rendered="#{postingbean.form.tipoProducto == 1}"></h:outputText>
					
					<h:outputText value="Cant. de ofertas:" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.ventas}"></h:outputText>
					
					<h:outputText value="Cant. de visitas:" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.consultas}"></h:outputText>
					
					<h:outputText value="Fecha Creacion:" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.creacion}">
						<f:convertDateTime pattern="dd-MM-yyyy" />
					</h:outputText> 
					
					<h:outputText value="Fecha Finalzacion:" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.finalizacion}">
						<f:convertDateTime pattern="dd-MM-yyyy" />
					</h:outputText>
				</t:panelGrid> 

				<t:panelGrid columns="2" width="100%"
						columnClasses="postingColumn2,postingColumn3"
						style="margin: 0px; padding: 0px; background-color: RGB(231,231,231); font-size: 16px; font-family: 'arial';">
					<h:outputText value="Vendedor:" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.finalizacion}"></h:outputText>
				</t:panelGrid> 

				<t:panelGrid columns="2" width="100%"
						columnClasses="postingColumn2,postingColumn3"
						style="margin: 0px; padding: 0px; background-color: RGB(244,244,244); font-size: 16px; font-family: 'arial';">
					<h:outputText value="Ubicacion:" escape="false"></h:outputText>
					<h:outputText value="#{postingbean.form.finalizacion}"></h:outputText>
				</t:panelGrid>

			</t:panelGrid>
			
		</t:panelGrid>

		<br>
		
		<t:panelGrid columns="1"
				width="40%"
				headerClass="headerStyleDescripcion"
				styleClass="blueBorder"
				cellpadding="0" 
				cellspacing="0"
				style="margin: 0px; padding: 0px;">
			<f:facet name="header">
				<h:panelGroup>
					<h:outputText value="&nbsp;" escape="false"></h:outputText>
					<h:outputText value=" DESCRIPCION DEL ARTíCULO"></h:outputText>
				</h:panelGroup>
			</f:facet>
		</t:panelGrid>		
		<t:panelGrid columns="1"
				width="90%"
				styleClass="blueBorder"
				cellpadding="0" 
				cellspacing="0"
				style="margin: 0px; padding: 0px;">
			<h:outputText value="#{postingbean.form.descripcion}" escape="false"></h:outputText>
		</t:panelGrid>		
	</h:form>
</f:view>

</body>
</html>
