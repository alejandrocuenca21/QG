package com.telefonica.ssnn.qg.informeExcel.dto;

public class QGDepuracionDatosClientesDTO {

	//Datos Columna Depuración con TW
	private QGListadoDTO TW;
	
	private QGListadoDTO QGTW;
	
	//Datos Columna Depuración con NSCO
	private QGListadoDTO NSCO;
	
	private QGListadoDTO QGNSCO;

	//Datos Columna Depuración con PREPAGO
	private QGListadoDTO PREPAGO;
	
	private QGListadoDTO QGPREPAGO;	
	
	
	public QGListadoDTO getTW() {
		return TW;
	}

	public void setTW(QGListadoDTO tw) {
		TW = tw;
	}

	public QGListadoDTO getQGTW() {
		return QGTW;
	}

	public void setQGTW(QGListadoDTO qgtw) {
		QGTW = qgtw;
	}

	public QGListadoDTO getNSCO() {
		return NSCO;
	}

	public void setNSCO(QGListadoDTO nsco) {
		NSCO = nsco;
	}

	public QGListadoDTO getQGNSCO() {
		return QGNSCO;
	}

	public void setQGNSCO(QGListadoDTO qgnsco) {
		QGNSCO = qgnsco;
	}
	
	public QGListadoDTO getPREPAGO() {
		return PREPAGO;
	}

	public void setPREPAGO(QGListadoDTO prepago) {
		PREPAGO = prepago;
	}

	public QGListadoDTO getQGPREPAGO() {
		return QGPREPAGO;
	}

	public void setQGPREPAGO(QGListadoDTO qgprepago) {
		QGPREPAGO = qgprepago;
	}	
}
