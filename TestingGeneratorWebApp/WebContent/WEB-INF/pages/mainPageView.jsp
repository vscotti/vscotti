<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>  
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Test Entrevista Generator</title>
	
	<script type="text/javascript" src="js/jquery-2.0.3.js"></script>

</head>

<body>

	<script type="text/javascript">
		$(document).ready(function(){
			$("input[type=text]").keydown(function(event) {
				var suffix = "Number";
				if($(this).attr('id').indexOf(suffix, this.length - suffix.length) !== -1) {
			        if(event.keyCode == 46 || 
			        	event.keyCode == 8 || 
			        	event.keyCode == 9 || 
			        	event.keyCode == 27 || 
			        	event.keyCode == 13 || 
			            (event.keyCode == 65 && event.ctrlKey === true) || 
			            (event.keyCode >= 35 && event.keyCode <= 39)) {
			                 return;
			        } else {
			        	if(event.shiftKey || 
			            		(event.keyCode < 48 || event.keyCode > 57) && 
			            		(event.keyCode < 96 || event.keyCode > 105 )) {
			        		event.preventDefault();
			        	}
			        }
				}
		    });
			
			$("#SubmitSeleccion").click(function() {
				$('#resultInfoForm').attr("action", "generarPDF/seleccion.htm");
				$("#resultInfoForm").submit();
			});
			
			$("#SubmitTodos").click(function() {
				$('#resultInfoForm').attr("action", "generarPDF/completo.htm");
				$("#resultInfoForm").submit();
			});
			
			$("#SubmitTodasExamen").click(function() {
				$('#resultInfoForm').attr("action", "generarPDF/completoExamen.htm");
				$("#resultInfoForm").submit();
			});

			$("#SubmitSeleccionExamen").click(function() {
				$('#resultInfoForm').attr("action", "generarPDF/seleccionExamen.htm");
				$("#resultInfoForm").submit();
			});
			
			$("#GuardarTodo").click(function() {
				if($('#propuestaGuardarExamenNoSpaces').val() == '') {
					alert("Ingrese el nombre propuesto");
				} else {
					$('#resultInfoForm').attr("action", "grabarExamenes.htm");
					$("#resultInfoForm").submit();
				}
			});
			
			$("#GuardarSeleccion").click(function() {
				if($('#propuestaGuardarExamenNoSpaces').val() == '') {
					alert("Ingrese el nombre propuesto");
				} else {
					$('#resultInfoForm').attr("action", "grabarSeleccionExamenes.htm");
					$("#resultInfoForm").submit();
				}
			});			 			
			
			$("#GenerarExamenes").click(function() {
				if($('#cantidadExamenNumber').val() == '' ||
						$('#propuestaExamenNoSpaces').val() == '') {
					var msg = "Ingrese ";
					if($('#cantidadExamenNumber').val() == '') {
						msg += "la cantidad de examenes a generar";
					}
					if($('#propuestaExamenNoSpaces').val() == '') {
						if($('#cantidadExamenNumber').val() == '') {
							msg += " y ";
						}
						msg += "el nombre propuesto";
					}
					alert(msg);
				} else {
					$('#searchInfoForm').attr("action", "generarExamen.htm");
					$("#searchInfoForm").submit();
				}
			});
			
			$("img").click(function() {
				if($(this).attr("src") == 'img/down_arrow.png') {
					$(this).attr("src","img/up_arrow.png");
					$("#" + $(this).attr('id') + "Div").hide();
				} else if($(this).attr("src") == 'img/up_arrow.png') {
					$(this).attr("src","img/down_arrow.png");
					$("#" + $(this).attr('id') + "Div").show();
				}
			});
				
			$("#SubmitSeleccion").hide();
			$("#SubmitSeleccionExamen").hide();
			$("#GuardarSeleccion").hide();
			
			$("input[type=checkbox]").each(function() { 
				$(this).change(function() {
					$("#SubmitSeleccion").hide();
					$("#SubmitSeleccionExamen").hide();
					$("#GuardarSeleccion").hide();
				  	if($("#chkSelect:checked").length > 0) {
						$("#SubmitSeleccion").show();
						$("#SubmitSeleccionExamen").show();
						$("#GuardarSeleccion").show();
					}
				});
			 });
		});
	</script>

	<f:form id="searchInfoForm" commandName="searchInfo" action="processSearchInfo.htm" method="post">
		<table width="70%">
			<tr>
				<td colspan="4">
					<c:if test="${not empty resultInfo.preguntas}">
						<img src="img/down_arrow.png" id="searchCriteria">
					</c:if>
					<c:if test="${empty resultInfo.preguntas}">
						<img src="img/fixed_down_arrow.png">
					</c:if>
					Criterios de busqueda
				</td>
			</tr>
		</table>
		<div id="searchCriteriaDiv">
			<table width="70%">
				<tr>
					<td width="25%">Categoria</td>
					<td width="25%">Seniority</td>
					<td width="25%">Resultados por Consulta</td>
					<td width="25%">Preguntas por Categoria</td>
				</tr>
				<tr>
					<td valign="top">
						<f:select path="idCategoriaSeleccionada" multiple="true" >
							<c:forEach items="${categorias}" var="categoria">
								<f:option id="${categoria.id}" value="${categoria.id}" label="${categoria.nombre}"></f:option>
							</c:forEach>
						</f:select>
					</td>
					<td valign="top">
						<f:select path="idSenioritySeleccionado">
							<c:forEach items="${seniorities}" var="seniority">
								<f:option id="${seniority.id}" value="${seniority.id}">${seniority.nombre}</f:option>
							</c:forEach>
						</f:select>
					</td>
					<td valign="top">
						<f:input id="preguntasPorConsultaNumber" path="preguntasPorConsulta" size="5"/>
					</td>
					<td valign="top">
						<f:input id="preguntasPorCategoriaNumber" path="preguntasPorCategoria" size="5"/>
					</td>
				</tr>
				<tr>
					<td>
						<INPUT value="Mostrar Consulta" type=submit>
					</td>
				</tr>
			</table>
		</div>
		<hr>
		   
		<c:if test="${not empty resultInfo.preguntas}">
			<table width="80%">
				<tr>
					<td>
						<img src="img/down_arrow.png" id="generarExamenes">
						Generar Examenes: (Los examenes se generaran basados en los criterios seleccionados de la seccion anterior)
					</td>
				</tr>
			</table>
			<div id="generarExamenesDiv">
				<table width="80%">
					<tr>
						<td>Cantidad:
							<f:input id="cantidadExamenNumber" path="cantidadExamen" size="5"/>
							Propuesta:
							<f:input id="propuestaExamenNoSpaces" path="propuestaExamen" size="5"/>
							<INPUT id="GenerarExamenes" value="Generar Examenes" type="button" width="80%">
						</td>
					</tr>
				</table>   
			</div>
			<hr>
		</c:if>
	</f:form>
	
	<c:if test="${not empty resultInfo.preguntas}">
		<f:form id="resultInfoForm" commandName="resultInfo" action="grabarExamenes.htm" method="post">
			<table width="80%">
				<tr>
					<td>
						<img src="img/down_arrow.png" id="generarPDF">
						Generar PDF:
					</td>
				</tr>
			</table>
			<div id="generarPDFDiv">
				<table width="80%">
					<tr>
						<td>
							<INPUT id="SubmitTodos" value="Todas" type="button" width="80%">
							<INPUT id="SubmitTodasExamen" value="Todas Formato Examen" type="button" width="80%">
							<INPUT id="SubmitSeleccion" value="Seleccionadas" type="button" width="80%">
							<INPUT id="SubmitSeleccionExamen" value="Seleccionadas Formato Examen" type="button" width="80%">
						</td>
					</tr>
				</table>
			</div>
			<hr>
			
			<table width="80%">
				<tr>
					<td>
						<img src="img/down_arrow.png" id="guardarExamen">
						Guardar:
					</td>
				</tr>
			</table>
			<div id="guardarExamenDiv">
				<table width="80%">
					<tr>
						<td>
							Propuesta:
							<f:input id="propuestaGuardarExamenNoSpaces" path="propuestaExamen" size="5"/>
							<INPUT id="GuardarTodo" value="Guardar Todas" type="button" width="80%">
							<INPUT id="GuardarSeleccion" value="Guardar Seleccion" type="button" width="80%">
						</td>
					</tr>
				</table>   
			</div>
			
			<hr>
	
			<table width="100%">
				<tr>
					<td width="5%"></td>
					<td width="8%">Categoria</td>
					<td width="40%">Pregunta</td>
					<td width="40%">Respuesta</td>
					<td width="7%">Seniority</td>
				</tr>
				<c:forEach items="${resultInfo.preguntas}" var="pregunta" varStatus="count">
				
					<f:hidden path="preguntas[${count.index}].pregunta.id" />
					<f:hidden path="preguntas[${count.index}].pregunta.categoria.nombre" />
					<f:hidden path="preguntas[${count.index}].pregunta.pregunta" />
					<f:hidden path="preguntas[${count.index}].pregunta.respuesta" />
					<f:hidden path="preguntas[${count.index}].pregunta.seniority.nombre" />
					
					<tr>
						<td>
							<f:checkbox id="chkSelect" path="preguntas[${count.index}].seleccionado" />
						</td>
						<td>${pregunta.pregunta.categoria.nombre}</td>
						<td>
							<span>
								${pregunta.pregunta.pregunta}
							</span>
						</td>
						<td>
							<span>
								${pregunta.pregunta.respuesta}
							</span>
						</td>
						<td>${pregunta.pregunta.seniority.nombre}</td>
					</tr>
				</c:forEach>
			</table>
		</f:form>
	</c:if>
	<c:if test="${empty resultInfo.preguntas}">
		${searchInfo.mensaje}
	</c:if>
</body>
</html>