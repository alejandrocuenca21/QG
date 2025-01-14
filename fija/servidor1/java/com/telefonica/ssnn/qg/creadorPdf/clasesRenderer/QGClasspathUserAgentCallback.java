package com.telefonica.ssnn.qg.creadorPdf.clasesRenderer;


import java.net.MalformedURLException;
import java.net.URL;

import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.util.Uu;

/**
 * Clase ClasspathUserAgentCallback
 * @author jmartinv
 *
 */
public class QGClasspathUserAgentCallback extends ITextUserAgent {

	/**
	 * ClasspathUserAgentCallback
	 * @param outputDevice Output Device
	 */
	protected QGClasspathUserAgentCallback(ITextOutputDevice outputDevice) {
		super(outputDevice);
	}

	/**
	 * resolveURI
	 * @param uri Uri
	 * @return String
	 */
	public String resolveURI(String uri) {
		URL ref = null;
		
        if (uri == null) return this.getBaseURL();
        
        if (uri.trim().equals("")) return this.getBaseURL();//jar URLs don't resolve this right
        
        if (uri.startsWith("classpath:")) {
            
            String shortUrl = uri.substring(10);
           
            ref = Thread.currentThread().getContextClassLoader().getResource(shortUrl);
            Uu.p("ref = " + ref);
        } else {
            try {
                URL base;
                if (getBaseURL() == null || getBaseURL().length() == 0) {
                    ref = new URL(uri);
                } else {
                    base = new URL(getBaseURL());
                    ref = new URL(base, uri);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (ref == null)
            return null;
        else
            return ref.toExternalForm();

	}
		 

}
