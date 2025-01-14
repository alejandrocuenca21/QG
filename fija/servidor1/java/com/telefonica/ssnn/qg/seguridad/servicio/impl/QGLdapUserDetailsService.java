package com.telefonica.ssnn.qg.seguridad.servicio.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.telefonica.ssnn.qg.seguridad.QGUsuario;
import com.telefonica.ssnn.qg.seguridad.dao.QGUsuariosDAO;
import com.telefonica.ssnn.qg.seguridad.dao.impl.QGUsuariosDAOLdap;
import com.telefonica.ssnn.qg.seguridad.dto.QGUsuarioDTO;


public class QGLdapUserDetailsService implements UserDetailsService {
	
	private static Logger logger = Logger.getLogger(QGLdapUserDetailsService.class);
	
	private static final long serialVersionUID = 3757846156874694361L;
	
	private QGUsuariosDAO usuariosDAO;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		QGUsuarioDTO usuarioDTO = null;
		
		try {
			usuarioDTO = usuariosDAO.obtenerUsuario(username);
		} catch (Exception ex) {
			throw new UsernameNotFoundException("");
		}
		
		if (usuarioDTO == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		GrantedAuthority[] roles = contruirRolesPorPerfil(usuarioDTO.getAccesos()[0].getPerfiles());
		
		if (roles == null || roles.length == 0) {
			throw new UsernameNotFoundException("Usuario no tiene roles");
		}
		String password = usuarioDTO.getPassword();
		QGUsuario usuario = new QGUsuario(username, password, true, roles);
		
		usuario.setAccesos(usuarioDTO.getAccesos());
		
		logger.info("Perfil final asignado: " + usuarioDTO.getAccesos()[0].getPerfiles());
		
		usuario.setPerfil(usuarioDTO.getAccesos()[0].getPerfiles()[0]);
		
		return usuario;
		
	}

	public void test()
			throws UsernameNotFoundException, DataAccessException {
		logger.error("Test");
		
	}
	
	public void setUsuariosDAO(QGUsuariosDAO usuariosDAO) {
		this.usuariosDAO = usuariosDAO;
	}
	
	private GrantedAuthority[] contruirRolesPorPerfil(String[] perfiles) {
		
		List listaAuthorities = new ArrayList();
		
		if (perfiles != null) {
			for (int i = 0; i < perfiles.length; i++) {
				if ("AC".equals(perfiles[i])) {
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_AC"));
				} else if ("AD".equals(perfiles[i])) {
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_AD"));
				} else if ("AU".equals(perfiles[i])) {
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_AU"));
				} else if ("CO".equals(perfiles[i])) {
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_CO"));
				} else if("AS".equals(perfiles[i])){
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_AS"));					
				} else if("AM".equals(perfiles[i])){
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_AM"));		 
				} else if("AB".equals(perfiles[i])){
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_AB"));		
				} else if("SA".equals(perfiles[i])){
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_SA"));		
				} else if("AP".equals(perfiles[i])){
					listaAuthorities.add(new GrantedAuthorityImpl("ROLE_AP"));		
				}
			}
		}
		
		return (GrantedAuthority[]) listaAuthorities.toArray(new GrantedAuthorityImpl[listaAuthorities.size()]);
	}

}
