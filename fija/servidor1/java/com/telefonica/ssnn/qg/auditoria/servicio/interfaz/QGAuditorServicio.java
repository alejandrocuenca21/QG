package com.telefonica.ssnn.qg.auditoria.servicio.interfaz;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

public interface QGAuditorServicio {
	
	public void altaAuditoria (String operacionDetalle, String resultadoOperacion, String resultadoNavegación);
}
