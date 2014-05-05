package com.rolling.code.accionesargentina.parsers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.annotation.SuppressLint;
import android.util.Log;

public class HtmlParser {

	private Map<String,List<String>> resultados = new LinkedHashMap<String, List<String>>();
	
	
	public Map<String, List<String>> getResultados() {
		return resultados;
	}

	@SuppressLint("DefaultLocale")
	public void parseAcciones(String indexName) {
		parse(indexName, "http://www.puentenet.com/cotizaciones/accionesCotizaciones!listaAccionesPorIndice.action?indiceAccionId=", false);
	}

	@SuppressLint("DefaultLocale")
	public void parseBonos(String indexName) {
		parse(indexName, "http://www.puentenet.com/cotizaciones/bonosCotizaciones!listaBonosPorCategoria.action?categoriaBonoId=", true);
	}

	public List<List<String>> generateResultList(boolean isBono) {
		List<List<String>> results = new ArrayList<List<String>>();
		for (String key : getResultados().keySet()) {
			List<String> list = getResultados().get(key);
			List<String> aux = new ArrayList<String>();
			if(!isBono) {
					aux.add(list.get(0));
					aux.add(list.get(1));
					aux.add(list.get(2));
					aux.add(list.get(3));
			} else {
					aux.add(list.get(0));
					aux.add(list.get(2));
					aux.add(list.get(1));
					aux.add(list.get(7));
					aux.add(list.get(8));
			}
			results.add(aux);
		}
		return results;
	}
	
	@SuppressLint("DefaultLocale")
	public void parse(String indexName, String URL, boolean isBonos) {
		try {	
			resultados = new LinkedHashMap<String, List<String>>();
			
			HttpParams params = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(params, 600000);
	        HttpConnectionParams.setSoTimeout(params, 600000);
	        
			DefaultHttpClient httpclient = new DefaultHttpClient(params);
			
			HttpGet httpget = new HttpGet(URL + indexName);
			HttpResponse response = httpclient.execute(httpget);
			
			if(response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity(); 
				InputStream is = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	
				String line = null;
				boolean tableBody = false;
				boolean isTitle = false;
				boolean isRest = false;
				List<String> list = new ArrayList<String>();
				String accionTitle = null;
				while((line = reader.readLine()) != null) {
					if(line.toLowerCase().indexOf("<tbody>") != -1) {
						tableBody = true;
					}
					if(line.toLowerCase().indexOf("</tbody>") != -1) {
						tableBody = false;
						if(accionTitle == null) {
							accionTitle = Long.valueOf(new Date().getTime()).toString();
						} else {
							accionTitle = list.get(1);
							if(isBonos) {
								accionTitle = list.get(2);
							}
						}
						resultados.put(accionTitle, list);
						accionTitle = null;
					}
					if(tableBody) {
						if(line.toLowerCase().indexOf("<tr") != -1) {
							list = new ArrayList<String>();
						}
						if(line.toLowerCase().indexOf("</tr>") != -1) {
							if(accionTitle == null) {
								accionTitle = Long.valueOf(new Date().getTime()).toString();
							} else {
								accionTitle = list.get(1);
								if(isBonos) {
									accionTitle = list.get(2);
								}
							}
							resultados.put(accionTitle, list);
							accionTitle = null;
						}
						if(line.toLowerCase().indexOf("<td") != -1 && line.toLowerCase().indexOf("</td>") != -1) {
							String str = line.toUpperCase().substring(0, line.toLowerCase().indexOf("</td>"));
							str = str.substring(str.lastIndexOf(">")+1, str.length());
							list.add(str);
						}
						if(line.toLowerCase().indexOf("<td") != -1 && line.toLowerCase().indexOf("</td>") == -1) {
							isTitle = true;
							isRest = true;
						}
						if(line.toLowerCase().indexOf("<td") == -1 && line.toLowerCase().indexOf("</td>") != -1) {
							isTitle = false;
							isRest = false;
						}
						if(!line.trim().isEmpty()) {
							if(isTitle) {
								if(line.toLowerCase().indexOf("<a") != -1) {
									accionTitle = line.toUpperCase().substring(0, line.toLowerCase().indexOf("</a>"));
									accionTitle = accionTitle.substring(accionTitle.lastIndexOf(">")+1, accionTitle.length());
									list.add(accionTitle);
									isRest = false;
								}													
							}
							if(isRest) {
								if(line.toLowerCase().indexOf("<td") == -1 && line.toLowerCase().indexOf("</td>") == -1) {
									list.add(line.trim());
									isTitle = false;
									isRest = false;
								}
							}
						}
						
					}
				}
				
				is.close();
			} else {
				Log.e(this.getClass().getName(), "Error de coneccion");
			}
			
		} catch(Exception e) {
			Log.e(this.getClass().getName(), e.getMessage());
		}
	}
}
