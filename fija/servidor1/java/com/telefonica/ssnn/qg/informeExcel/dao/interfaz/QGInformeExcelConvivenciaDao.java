package com.telefonica.ssnn.qg.informeExcel.dao.interfaz;
import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informeExcel.dto.QGEntradaInformeDto;

public interface QGInformeExcelConvivenciaDao {

	//Llamada al servicio host que recupera los datos para generar el informe de convivencia y conciliacion
	public QGCGlobalDto buscarInforme(QGEntradaInformeDto entrada);
	
}