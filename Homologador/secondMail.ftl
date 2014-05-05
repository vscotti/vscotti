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
	<#if noDBDetected != -1>
		<li>
			No se detectaron bases en un lapso de ${noDBDetected} horas para ${campaignCountry}.
		</li>
	</#if>
	
	<#if averaragePhones != -1>
		<li>
			Se detectaron menos de ${averaragePhones} para ${campaignCountry} en el archivo ${filename}.
		</li>
	</#if>
</ul>

<label>${result}</label>

</body>
</html>