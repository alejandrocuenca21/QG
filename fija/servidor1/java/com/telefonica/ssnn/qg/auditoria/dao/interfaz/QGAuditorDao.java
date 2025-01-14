package com.telefonica.ssnn.qg.auditoria.dao.interfaz;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

public interface QGAuditorDao {

	public void altaAuditoria (String operacionDetalle, String resultadoOperacion, String resultadoNavegación);
}
