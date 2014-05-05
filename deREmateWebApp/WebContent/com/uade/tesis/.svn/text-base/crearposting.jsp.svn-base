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
	<h:form id="MAIN" enctype="multipart/form-data">
	
		<h:inputHidden id="hiddenIndex" value="#{crearpostingbean.form.hiddenIndex}"></h:inputHidden>
		
		<h:messages errorStyle="color: red;" globalOnly="true" showDetail="true" showSummary="false">
		</h:messages>
		
		<t:panelGrid columns="2">
			<f:facet name="Header">
				<h:outputText value="Crear Posting"></h:outputText>
			</f:facet>
			
			<h:panelGroup>
				<h:outputText value="Categor&iacute;a:<br>" escape="false"></h:outputText>
				<h:commandLink
						rendered="#{not empty crearpostingbean.form.imagesList}"
						actionListener="#{crearpostingbean.sugerirCategorias}" 
						onclick="document.getElementById('MAIN').submit();"
						value="Sugerir Nuevamente">
				</h:commandLink>
			</h:panelGroup>
			<h:panelGroup>
				<h:selectOneMenu 
						id="categoriaPosting"
						value="#{crearpostingbean.form.categoria}">
					<f:selectItems value="#{crearpostingbean.form.categorias}"/>
				</h:selectOneMenu>
				<h:outputText value="<br>" 
						rendered="#{not empty crearpostingbean.form.imagesList}"
						escape="false"></h:outputText>
				<t:dataTable var="item"
						id="categoriasSugeridasList"
						rowIndexVar="index"
						border="1"
						rendered="#{not empty crearpostingbean.form.imagesList &&
									crearpostingbean.form.haySugerencias}"
						value="#{crearpostingbean.form.categoriasSugeridas}">
					<t:column>
						<f:facet name="header">
							<h:outputText value="Descripcion"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.descripcion}" escape="false"></h:outputText>
					</t:column>
					<t:column>
						<f:facet name="header">
							<h:outputText value="Ventas"></h:outputText>
						</f:facet>
						<h:outputText value="1000" escape="false"></h:outputText>
					</t:column>
					<t:column>
						<f:facet name="header">
							<h:outputText value="Consultas"></h:outputText>
						</f:facet>
						<h:outputText value="200" escape="false"></h:outputText>
					</t:column>
					<t:column>
						<f:facet name="header">
							<h:outputText value=""></h:outputText>
						</f:facet>
						<h:commandLink 
								id="verPostingLink"
								value="Ver Postings Relacionados"
								onclick="return false;"></h:commandLink>
								
						<rich:componentControl 
							attachTo="verPostingLink"
							for="verPostingModalPanel"
				            operation="show" event="onclick"></rich:componentControl>
				        
				        <rich:modalPanel id="verPostingModalPanel" 
				        		autosized="true" resizeable="false" moveable="false" 
				        		 width="300" height="100" top="20">
							<f:facet name="header">
								<h:outputText value="#{index}"></h:outputText>
							</f:facet>
							
							<h:commandButton value="Close"
								id="closepanelbutton"
								onclick="return false;"></h:commandButton>

							<rich:componentControl 
								attachTo="closepanelbutton"
								for="verPostingModalPanel"
					            operation="hide" event="onclick"></rich:componentControl>
				        </rich:modalPanel>   
					</t:column>
					<t:column>
						<f:facet name="header">
							<h:outputText value=""></h:outputText>
						</f:facet>
						<h:commandLink 
								id="SeleccionarCategoria"
								onclick="document.getElementById('MAIN:categoriaPosting').value = '#{item.id}'; return false;"
								value="Seleccionar"></h:commandLink>
					</t:column>
				</t:dataTable>
				<h:outputText 
						rendered="#{not empty crearpostingbean.form.imagesList &&
									!crearpostingbean.form.haySugerencias}"
						value="No hay categorias a Sugerir"
						style="color: red;"></h:outputText>
			</h:panelGroup>
			
			<h:outputText value="T&iacute;tulo:" escape="false"></h:outputText>
			<h:inputText value="#{crearpostingbean.form.titulo}" maxlength="50" size="50"></h:inputText>

			<h:outputText value="Fotos" escape="false"></h:outputText>
			<t:panelGroup>
				<t:inputFileUpload 
				  		id="myUploadedFile" 
				  		storage="file" accept="image/*" 
				  		value="#{crearpostingbean.form.uploadedFile}"
				  		valueChangeListener="#{crearpostingbean.refreshImage}"
				  		disabled="#{crearpostingbean.form.maxImages}"
				  		onchange="document.getElementById('MAIN').submit();"/>
				<h:outputText value="Maximo de imagenes permitido" 
						rendered="#{crearpostingbean.form.maxImages}"
						escape="false" style="color: red;"></h:outputText>
			</t:panelGroup>

			<h:outputText value="&nbsp;" 
						rendered="#{not empty crearpostingbean.form.imagesList}" 
						escape="false"></h:outputText>
			<t:panelGroup rendered="#{not empty crearpostingbean.form.imagesList}">
				<t:dataList var="item"
						rowIndexVar="index"
						rowCountVar="count"
						id="images"
						style="vertical-align:text-top;"
						value="#{crearpostingbean.form.imagesList}">
					<t:panelGrid columns="4">
						<h:panelGroup>
							<h:commandLink 
									rendered="#{index != 0 && count != 1}"
									actionListener="#{crearpostingbean.subirFoto}" 
									onclick="document.getElementById('MAIN:hiddenIndex').value=#{index}; document.getElementById('MAIN').submit();">
								<h:graphicImage id="up1" 
										title="Subir Imagen"
										value="/img/up.jpg" width="40" height="40" />
							</h:commandLink>
							<h:outputText value="<br>" escape="false"></h:outputText>
							<h:commandLink 
									rendered="#{index != count - 1}"
									actionListener="#{crearpostingbean.bajarFoto}" 
									onclick="document.getElementById('MAIN:hiddenIndex').value=#{index}; document.getElementById('MAIN').submit();">
								<h:graphicImage id="down1" 
										title="Bajar Imagen"
										value="/img/down.jpg" width="40" height="40" />
							</h:commandLink>
						</h:panelGroup>
						<h:graphicImage id="image1" value="#{item}" width="150" height="100" />
						<h:commandLink 
								actionListener="#{crearpostingbean.eliminarFoto}" 
								onclick="document.getElementById('MAIN:hiddenIndex').value=#{index}; document.getElementById('MAIN').submit();">
							<h:graphicImage id="eliminar1" 
									title="Eliminar Imagen"
									value="/img/delete.png" width="40" height="40" />
						</h:commandLink>
						<h:outputText value="&nbsp;<br>" escape="false"></h:outputText>
					</t:panelGrid>
				</t:dataList>
			</t:panelGroup>
			
			<h:outputText value="Descripcion:" escape="false"></h:outputText>
			<h:inputTextarea value="#{crearpostingbean.form.descripcion}" rows="10" cols="60"></h:inputTextarea>
		
			<h:outputText value="Formato Descripcion:" escape="false"></h:outputText>
			<h:selectBooleanCheckbox value="#{crearpostingbean.form.formatoDescripcion}">
				<h:outputText value="HTML" escape="false"></h:outputText>
			</h:selectBooleanCheckbox>
		
			<h:outputText value="Precio:" escape="false"></h:outputText>
			<h:panelGroup> 
				<h:inputText value="#{crearpostingbean.form.precio}" maxlength="7" size="7"></h:inputText> 
				<h:outputText value="&nbsp; (sin comas, puntos y decimales)" escape="false"></h:outputText>
			</h:panelGroup>
			
			<h:outputText value="Fecha Finalizacion:" escape="false"></h:outputText>
			<t:inputCalendar id="Calendar" 
					value="#{crearpostingbean.form.finalizacion}"
					renderAsPopup="true" popupTodayString="Today is"
					popupWeekString="Wk" renderPopupButtonAsImage="false" 
					popupDateFormat="dd-MMM-yyyy" rendered="true" >
				<f:convertDateTime pattern="dd-MM-yyyy" />
			</t:inputCalendar>

	    	<h:commandButton value="Crear Posting" action="#{crearpostingbean.crearPosting}"></h:commandButton>
			<h:outputText value="&nbsp;" escape="false"></h:outputText>
		</t:panelGrid>
	</h:form>
</f:view>

</body>
</html>
