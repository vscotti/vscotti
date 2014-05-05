<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>  
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Examenes generados</title>
	
	<script type="text/javascript" src="js/jquery-2.0.3.js"></script>

</head>

<body>

	<script type="text/javascript">
		$(document).ready(function(){
			$("a").click(function() {
					alert($(this).attr('id'));
					$("#examenSeleccionado").val($(this).attr('id'));
					$("#examenesInfoForm").submit();
			}
		});
	</script>

	
	<f:form id="examenesInfoForm" commandName="examenesInfo" action="gotToExamen.htm" method="get">
		
		<f:hidden id="examenSeleccionado" path="examenSeleccionado" />
		
		<table width="80%">
			<tr>
				<td width="15%">Identificador Pregunta</td>
				<td width="15">Cantidad Preguntas</td>
				<td width="15%">Cantidad Categorias</td>
				<td width="55%">Categorias</td>
			</tr>
			<c:forEach items="${examenesInfo.examenes}" var="examen" varStatus="count">
				
				<tr>
					<td>
						<a id="${examen.numeroIdentificador}" onclick="return false;">
							${examen.numeroIdentificador}
						</a>
					</td>
					<td>
						${examen.cantidadPreguntas}
					</td>
					<td>
						${examen.cantidadCategorias}
					</td>
					<td>
						${examen.categorias}
					</td>
				</tr>
			</c:forEach>
		</table>
	</f:form>
</body>
</html>