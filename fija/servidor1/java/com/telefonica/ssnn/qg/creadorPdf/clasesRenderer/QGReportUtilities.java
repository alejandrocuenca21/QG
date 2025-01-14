package com.telefonica.ssnn.qg.creadorPdf.clasesRenderer;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.InputSource;

import com.telefonica.ssnn.qg.creadorPdf.exception.QGCreadorPdfException;




/**
 * Genera informes pdf con xhtmlrenderer y itext.
 * @author jacangas
 *
 */
public class QGReportUtilities {

	/**
	 * Expresion regular para sustituir los parametros de los documentos.
	 */
	private final Pattern params = Pattern.compile("\\$P\\{(\\w+)\\}");
	private final Pattern paramsL = Pattern.compile("\\$L\\{(\\w+,[0-9]+)\\}");
	/**
	 * Renderizador para pdf y rtf.
	 */
	ITextRenderer renderer;
	
	
	/**
	 * Inicializacion del bean.
	 * @throws QGCreadorPdfException 
	 */
	protected void init(String fontDir) throws QGCreadorPdfException {
	
		
		boolean reintentar = false;
		
		if(fontDir == null){
			if(QGClasesRendererUtil.obtenerSistemaOperativo() == 1){
				fontDir = "c:/windows/fonts";
			}else{
				fontDir = "/usr/share/fonts";
				reintentar = true;
			}
		}
		renderer = new ITextRenderer();
		
		try {
		
			//Obtenemos el directorio de fuentes
			//String fontDir = es_indra_crm_directorio_fuentes;//bundle.getString("es.indra.crm.directorio.fuentes");
			
			if (fontDir != null && fontDir.trim().length() > 0) {
				
				//Se lo añadimos al renderer
				try {
					renderer.getFontResolver().addFontDirectory(fontDir.trim(), true);
				} catch (Exception e) {
					if(reintentar){
						try{
						fontDir = "/usr/share/fonts/truetype";
						renderer.getFontResolver().addFontDirectory(fontDir.trim(), true);
						}catch (Exception e2) {
							throw new QGCreadorPdfException("No se han podido añadir las fuentes");
						}
					}else{
						throw new QGCreadorPdfException("No se han podido añadir las fuentes");
					}					
				}  
				
			}
		} catch (MissingResourceException mrEx) {			 
				throw new QGCreadorPdfException("No se ha establecido un directorio de fuentes.");			 
		}
		
	}
	
	
	/**
	 * Obtiene un informe.
	 * @param plantilla Plantilla de entrada
	 * @param parametros Parametros
	 * @param baseURL URL
	 * @param fontDir directorio de fuentes del sistema operativo
	 * @return Devuelve array de bytes
	 * @throws Exception Excepcion que lanza
	 */
	protected ByteArrayOutputStream obtenerInforme(InputStream plantilla, Map parametros, String baseURL,String fontDir) throws Exception {
		
		this.init(fontDir);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		//Constructor del documento
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	
		builder.setEntityResolver(FSEntityResolver.instance());
		
		//Documento en string
		String documento = eliminarComentariosHTML(readAndReplace(plantilla, parametros));
		
		//Creamos el documento
		Document doc = builder.parse(new InputSource(new StringReader(documento)));
		
		//Añadimos un UserAgent para que pueda buscar adecuadamente las imagenes
		QGClasspathUserAgentCallback callback = new QGClasspathUserAgentCallback(renderer.getOutputDevice());	
		callback.setSharedContext(renderer.getSharedContext());	
		renderer.getSharedContext().setUserAgentCallback(callback);

		// Establecemos el documento
		renderer.setDocument(doc, baseURL);
		
		
		renderer.layout();
		renderer.createPDF(baos);
		
		
		return baos;
	}

	
	
	
	/**
	 * readFileAsStringFromClasspath
	 * @param is InputStream Archivo leido
	 * @return Devuelve una cadena
	 * @throws IOException Excepcion que lanza
	 */
	private String readFileAsStringFromClasspath(InputStream is) throws IOException {
		
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();

		
	}
	/**
	 * readAndReplace
	 * @param plantilla Plantilla de entrada
	 * @param parameters Parametros
	 * @return Devuelve una cadena
	 * @throws IOException Excepcion que lanza
	 */
	private String readAndReplace(InputStream plantilla, Map parameters) throws IOException {
		
		String tpl = readFileAsStringFromClasspath(plantilla);
		
		Matcher m = params.matcher(tpl);
		
		StringBuffer buffer = new StringBuffer();
		boolean metidas = false;
		if (m.find()) {
						
			do {			
			
				if (parameters.containsKey(m.group(1))) {
					String valor = (String) parameters.get(m.group(1));
					if (valor==null) {
						valor = "";
					}
					m.appendReplacement(buffer, valor);
				} else {
					m.appendReplacement(buffer, "");
				}
			} while (m.find());
			
			m.appendTail(buffer);
			
			metidas = true;
			
			//metemos en tpl lo que hemos obtenido de la sustituci�n para
			//poder hacer la sustituci�n de los datos de las listas.			
			tpl = buffer.toString();
		}
		
		Matcher L = paramsL.matcher(tpl);			
		if (L.find()) {
			buffer = new StringBuffer();
			do {								
				//cogemos el monbre de la lista y el indice del parametro.
				String[] refDato = L.group(1).split(",");
				
				if (parameters.containsKey(refDato[0])) {
					//si la lista esta en el mapa, la cogemos
					List lista =  (List) parameters.get(refDato[0]);
					//cogemos el parametro de la lista, segun el indice.
					Object valorOb = lista.get(Integer.parseInt(refDato[1]));
					String valor ="";
					if(valorOb instanceof Integer){
						valor = String.valueOf((Integer)valorOb);
					}else if(valorOb instanceof String){
						valor = (String) valorOb;
					}else if(valorOb instanceof Double){
						valor = String.valueOf((Double)valorOb);
					}
					if (valor==null) {
						valor = "";
					}
					L.appendReplacement(buffer, valor);
				} else {
					L.appendReplacement(buffer, "");
				}
			} while (L.find());
			
			L.appendTail(buffer);
			
			metidas = true;
		}
		
		if(metidas){
			return buffer.toString();
		}else{
			return tpl;
		}
	}
	/**
	 * eliminarComentariosHTML
	 * @param documento Cadena
	 * @return Cadena
	 */
	private String eliminarComentariosHTML(String documento) {
		
		String newDoc = null;
		
		if (documento != null) {
			newDoc = documento.replaceAll("(?s)<!--.*?-->", "");
			newDoc = newDoc.replaceAll("(?s)&lt;!--.*?--&gt;", "");
			
		} 
		
		return newDoc;
		
	}

	
}
