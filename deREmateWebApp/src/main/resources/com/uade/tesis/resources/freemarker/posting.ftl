<b>Proceso de Posting</b>
<br>
<br>

<form method="POST" action="posting.htm">

	1_ Seleccionar categor&iacute;a:
	<#list categorias as categoria>
	<input type="radio" name="categoria" value="${categoria}">${categoria} &nbsp;
	</#list>
	<br><br>
	2_ Ingregar T&iacute;tulo: <input type="text" maxlength="50" size="50"><br><br>
	
	3_ Ingresar Fotos<br><br><div id="images"></div>
	
	<iframe id="ifrUpload" name="ifrUpload" style="display:none" src="" ></iframe>
	
	<form target="ifrUpload" 
			method="post" 
			action="upload.htm" 
			onsubmit="if(document.getElementById('file1').value.length==0)return false; " 
			name="frm" 
			id="fileForm1" 
			enctype="multipart/form-data">
		<input type="file" 
			onchange="javascript: if(this.value.length>0) document.getElementById('fileForm1').submit();" 
			name="file" 
			id="file1"
			value="" 
			onbeforeeditfocus="return false;" 
			onkeydown="return false;" 
			class="XL file"/>
	</form>
	
	
	<br>
	4_ Ingresar Descripcion: <br><br><textarea rows="10" cols="60"></textarea><br><br> 
	
	5_ Ingresar Precio: <input type="text" size="7" maxlength="7"> (sin comas, puntos y decimales)
	
	<input type="submit" value="Crear"/>
	
</form>

