package com.telefonica.ssnn.qg.informeExcel.servicio.impl;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informeExcel.dao.interfaz.QGInformeExcelConvivenciaDao;
import com.telefonica.ssnn.qg.informeExcel.dto.QGEntradaInformeDto;
import com.telefonica.ssnn.qg.informeExcel.servicio.interfaz.QGInformeExcelServicio;

public class QGInformeExcelServicioImpl implements QGInformeExcelServicio {

	private QGInformeExcelConvivenciaDao informesConvivenciaExcelDao;

	
	//Llamada al servicio host que recupera los datos para generar el informe de convivencia y conciliacion
	public QGCGlobalDto buscarInforme(QGEntradaInformeDto entrada){
		return this.getInformesConvivenciaExcelDao().buscarInforme(entrada);
	}
		
	public QGInformeExcelConvivenciaDao getInformesConvivenciaExcelDao() {
		return informesConvivenciaExcelDao;
	}

	public void setInformesConvivenciaExcelDao(
			QGInformeExcelConvivenciaDao informesConvivenciaExcelDao) {
		this.informesConvivenciaExcelDao = informesConvivenciaExcelDao;
	}

}
