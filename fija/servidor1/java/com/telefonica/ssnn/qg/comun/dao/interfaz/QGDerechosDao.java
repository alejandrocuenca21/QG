package com.telefonica.ssnn.qg.comun.dao.interfaz;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.util.QGDerechosDto;

public interface QGDerechosDao {
	/**
	 * Obtiene los derechos por linea de negocio
	 * @param lineaNegocio
	 * @return
	 */
	public QGCGlobalDto gestionarDerechos(QGDerechosDto derecho);
}
