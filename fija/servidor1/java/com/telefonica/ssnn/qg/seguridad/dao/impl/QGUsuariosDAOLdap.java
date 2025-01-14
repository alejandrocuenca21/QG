package com.telefonica.ssnn.qg.seguridad.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.telefonica.ssnn.qg.seguridad.dao.QGUsuariosDAO;
import com.telefonica.ssnn.qg.seguridad.dto.AccesosDTO;
import com.telefonica.ssnn.qg.seguridad.dto.QGUsuarioDTO;

import com.telefonica.ssnn.qg.exceptions.QGApplicationException;

import tesa.dh.gu.Aplicacion;
import tesa.dh.gu.Entorno;
import tesa.dh.gu.ExcepcionDH;
import tesa.dh.gu.Usuario;

/**
 * Implementacion del dao de usuarios para LDAP.
 * @author jacangas
 *
 */
public class QGUsuariosDAOLdap implements QGUsuariosDAO {
	
	private static Logger logger = Logger.getLogger(QGUsuariosDAOLdap.class);
	
	/**
	 * Datos para el acceso al LDAP de EDOMUS
	 */
	private static Entorno 		m_entorno;
	private static Aplicacion	m_aplicacion;
	private static Entorno 		m_entorno_TW;
	private static Aplicacion	m_aplicacion_TW;
	private static Entorno 		m_entorno_D4;
	private static Aplicacion	m_aplicacion_D4;
	
	
	private QGUsuariosDAOLdap() throws Exception {
		// Obtenemos el Entorno y la aplicación del anagrama
		try {
			m_entorno 		= Entorno.obtenerEntorno("qg");	
			m_aplicacion 	= m_entorno.obtenerAplicacion("qg");
			m_entorno_TW 		= Entorno.obtenerEntorno("tw");	
			m_aplicacion_TW 	= m_entorno.obtenerAplicacion("tw");
			m_entorno_D4 		= Entorno.obtenerEntorno("d4");	
			m_aplicacion_D4 	= m_entorno.obtenerAplicacion("d4");
		} catch (ExcepcionDH edh) {
			logger.debug("EXCEPCION: "+edh.obtenerCodigo() + ":"+ edh.obtenerMensaje());
			logger.error("Error al obtener la instancia de la aplicación",edh);
			throw new Exception("Ha sido imposible obtener el perfil por problemas técnicos");
		}
	}
	
	public QGUsuarioDTO obtenerUsuario(String nombre) {
		logger.debug("Obtenemos los datos del usuario "+nombre);
		List listaUsuarios = new ArrayList();
		
		QGUsuarioDTO usuarioLDAP = new QGUsuarioDTO();
		Usuario 	 usuario;
		AccesosDTO accesoQG  = new AccesosDTO();
		QGUsuarioDTO usuarioLDAPTW = new QGUsuarioDTO();
		Usuario 	 usuarioTW;
		AccesosDTO accesoTW = new AccesosDTO();
		QGUsuarioDTO usuarioLDAPD4 = new QGUsuarioDTO();
		Usuario 	 usuarioD4;
		AccesosDTO accesoD4 = new AccesosDTO();	
		int accesos = 0;
		try {
			usuario  = m_entorno.obtenerUsuario(nombre);
			if(null != usuario) {
				usuarioLDAP.setNombre(usuario.obtenerAtributo("cn")[0]);
				usuarioLDAP.setDni(usuario.obtenerAtributo("telefonicadni")[0]);
				//usuarioLDAP.setPassword(usuario.obtenerAtributo("userPassword")[0]);
				usuarioLDAP.setPassword("password");
				logger.debug("PASSWORD USUARIO LDAP: " + usuarioLDAP.getPassword());
				usuarioLDAP.setEmpresa(usuario.obtenerAtributo("telefonicaempresa")[0]);				
				accesoQG.setAplicacion("QG");
				accesoQG.setPerfiles(usuario.obtenerPerfiles(m_aplicacion));
				if(accesoQG.getPerfiles().length==0) {
					accesoQG = null;
				} else {
					accesos++;
				}
			}
		} catch (ExcepcionDH e) {
			logger.error("Error al obtener los datos del usuario "+nombre, e);
			throw new QGApplicationException("Error al obtener los datos del usuario "+nombre);
		}
		try {
			usuarioTW  = m_entorno_TW.obtenerUsuario(nombre);
			if(null != usuarioTW) {
				usuarioLDAPTW.setNombre(usuarioTW.obtenerAtributo("cn")[0]);
				usuarioLDAPTW.setDni(usuarioTW.obtenerAtributo("telefonicadni")[0]);
				//usuarioLDAP.setPassword(usuario.obtenerAtributo("userPassword")[0]);
				usuarioLDAPTW.setPassword("password");
				logger.debug("PASSWORD USUARIO LDAP: " + usuarioLDAPTW.getPassword());
				usuarioLDAPTW.setEmpresa(usuarioTW.obtenerAtributo("telefonicaempresa")[0]);		
				accesoTW.setAplicacion("TW");
				accesoTW.setPerfiles(usuario.obtenerPerfiles(m_aplicacion_TW));		
				if(accesoTW.getPerfiles().length==0) {
					accesoTW = null;
				} else {
					accesos++;
				}
			}
		} catch (ExcepcionDH e) {
			logger.error("Error al obtener los datos del usuario "+nombre, e);
			throw new QGApplicationException("Error al obtener los datos del usuario "+nombre);
		}
		
		try {
			
			usuarioD4  = m_entorno_D4.obtenerUsuario(nombre);
			if(null != usuarioD4) {
				usuarioLDAPD4.setNombre(usuarioD4.obtenerAtributo("cn")[0]);
				usuarioLDAPD4.setDni(usuarioD4.obtenerAtributo("telefonicadni")[0]);
				//usuarioLDAP.setPassword(usuario.obtenerAtributo("userPassword")[0]);
				usuarioLDAPD4.setPassword("password");
				logger.debug("PASSWORD USUARIO LDAP: " + usuarioLDAPTW.getPassword());
				usuarioLDAPD4.setEmpresa(usuarioD4.obtenerAtributo("telefonicaempresa")[0]);			
				accesoD4.setAplicacion("D4");
				accesoD4.setPerfiles(usuario.obtenerPerfiles(m_aplicacion_D4));
				if(accesoD4.getPerfiles().length==0) {
					accesoD4 = null;
				} else {
					accesos++;
				}
			}
		} catch (ExcepcionDH e) {
			logger.error("Error al obtener los datos del usuario "+nombre, e);
			throw new QGApplicationException("Error al obtener los datos del usuario "+nombre);
		}
		
		//ACF La logica para revisar accesos y realizar la pagina de selección se realizara aqui y segun esto navegaremos a una carga u otra
					
			AccesosDTO[] accesosDTO = new AccesosDTO[accesos];
			accesos = 0;
			if(accesoQG != null) {
				accesosDTO[accesos] = new AccesosDTO();
				accesosDTO[accesos].setAplicacion("QG");
				accesosDTO[accesos].setPerfiles(accesoQG.getPerfiles());
				accesos++;
			}
			if(accesoTW != null) {
				accesosDTO[accesos] = new AccesosDTO();
				accesosDTO[accesos].setAplicacion("TW");
				accesosDTO[accesos].setPerfiles(accesoTW.getPerfiles());
				accesos++;
			}
			if(accesoD4 != null) {
				accesosDTO[accesos] = new AccesosDTO();
				accesosDTO[accesos].setAplicacion("D4");
				accesosDTO[accesos].setPerfiles(accesoD4.getPerfiles());
				accesos++;
			}
			usuarioLDAP.setAccesos(accesosDTO);
		listaUsuarios.add(usuarioLDAP);		
		
		if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
			if (listaUsuarios.size() > 1) {
				throw new QGApplicationException("Existe mas de un usuario");
			}
			return (QGUsuarioDTO) listaUsuarios.get(0);
		}
		return null;
	}
}
