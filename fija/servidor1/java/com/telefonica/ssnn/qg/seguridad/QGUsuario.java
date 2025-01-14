package com.telefonica.ssnn.qg.seguridad;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;

import com.telefonica.ssnn.qg.seguridad.dto.AccesosDTO;

public class QGUsuario extends User {
	
	private static final long serialVersionUID = 3483686567312965530L;
	
	private String perfil;
	
	 private AccesosDTO[] accesos;

	public QGUsuario(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, GrantedAuthority[] authorities)
			throws IllegalArgumentException {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
	public QGUsuario(String username, String password, boolean enabled, 
			GrantedAuthority[] authorities) throws IllegalArgumentException {
		
		super(username, password, enabled, true, true,
				true, authorities);
		
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

    public AccesosDTO[] getAccesos() {
        return accesos;
    }

    public void setAccesos(AccesosDTO[] accesos) {
        this.accesos = accesos;
    }

}
