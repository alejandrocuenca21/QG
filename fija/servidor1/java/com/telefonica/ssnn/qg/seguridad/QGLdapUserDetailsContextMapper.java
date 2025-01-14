package com.telefonica.ssnn.qg.seguridad;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.ldap.UserDetailsContextMapper;

public class QGLdapUserDetailsContextMapper implements UserDetailsContextMapper {

	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, GrantedAuthority[] authority) {
		
		
		
		String contrato = ctx.getStringAttribute("contrato");
		
		
		
		GrantedAuthority[] authorities = {new GrantedAuthorityImpl("ROLE_LOG")};
		
		QGUsuario usuario = new QGUsuario(username, "a", true, authorities);
		
		return usuario;
	}

	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {


	}

}
