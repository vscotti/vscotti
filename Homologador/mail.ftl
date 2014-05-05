<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<#setting locale="es_AR">
<html>
<head>
<title>${title}</title>
</head>
<body>
<h1>${title}</h1>
<h3>${time?string("EEEE dd 'de' MMMM 'de' yyyy, HH:mm:ss")?cap_first}</h3>

<ul>
	<#if correctedPhones != -1>
		<li>
			<#if correctedPhones != 0>
				Se corrigieron ${correctedPhones} teléfonos.
			<#elseif correctedPhones == 1>
				Se corrigió 1 teléfono.
			<#else>
				No se pudo corregir ningún teléfono.
			</#if>
		</li>
	</#if>
	
	<#if duplicatedPhones != -1>
		<li>
			<#if duplicatedPhones != 0>
				Se encontraron ${duplicatedPhones} teléfonos duplicados.
			<#elseif duplicatedPhones == 1>
				Se encontró 1 telófono duplicado.
			<#else>
				No se encontraron telófonos duplicados.
			</#if>
		</li>
	</#if>
	
	<#if invalidLocations != -1>
		<li>
			<#if invalidLocations != 0>
				No se pudieron reconocer ${invalidLocations} ubicaciones.
			<#elseif invalidLocations == 1>
				No se pudo reconocer 1 ubicación.
			<#else>
				Todas las ubicaciones fueron reconocidas.
			</#if>
		</li>
	</#if>
	
	<#if invalidPhones != -1>
		<li>
			<#if invalidPhones != 0>
				Se detectaron ${invalidPhones} teléfonos inválidos.
			<#elseif invalidPhones == 1>
				Se detectó 1 teléfono inválido.
			<#else>
				No se detectaron teléfonos inválidos.
			</#if>
		</li>
	</#if>
	
	<#if sosDetectec != -1>
		<li>
			Se detectaron teléfonos que requieren atención.
		</li>
	</#if>
</ul>

<label>${result}</label>

</body>
</html>