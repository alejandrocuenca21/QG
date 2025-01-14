package com.telefonica.ssnn.qg.informeExcel.dto;

public class QGConciliacionDireccionesDTO {

	//Datos Columna Conciliación de direcciones con QG
	private QGListadoDTO QG;
	
	//Datos Columna Conciliación de direcciones con NSCO
	private QGListadoDTO NSCO;
	

	public QGListadoDTO getQG() {
		return QG;
	}

	public void setQG(QGListadoDTO qg) {
		QG = qg;
	}

	public QGListadoDTO getNSCO() {
		return NSCO;
	}

	public void setNSCO(QGListadoDTO nsco) {
		NSCO = nsco;
	}
}
