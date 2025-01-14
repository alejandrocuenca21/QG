package com.telefonica.ssnn.qg.auditoria.servicio.impl;

import com.telefonica.ssnn.qg.auditoria.dao.interfaz.QGAuditorDao;
import com.telefonica.ssnn.qg.auditoria.servicio.interfaz.QGAuditorServicio;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;

public class QGAuditorServicioImpl implements QGAuditorServicio{
	
	private QGAuditorDao auditorDao;

	public void altaAuditoria (String operacionDetalle, String resultadoOperacion, String resultadoNavegaci�n) {
		this.getAuditorDao().altaAuditoria(operacionDetalle, resultadoOperacion, resultadoNavegaci�n);
	}

	public QGAuditorDao getAuditorDao() {
		return auditorDao;
	}

	public void setAuditorDao(QGAuditorDao auditorDao) {
		this.auditorDao = auditorDao;
	}
	
	
}
