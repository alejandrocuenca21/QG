package com.telefonica.ssnn.qg.informeExcel.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.telefonica.ssnn.qg.util.QGInformeUtils;

public class QGInformeConciliacionDTO {
	
	private static final Logger logger = Logger.getLogger(QGInformeConciliacionDTO.class);	
	
	//Listado Pestaña NSCO
	private List NSCO;
	
	//Listado Pestaña TW
	private List TW;
	
	//Listado Pestaña PREPAGO
	private List PREPAGO;	
	
	//Fecha de la Solicitud
	private String fechaSolicitud;
	
	//Pestaña Informe
	//Datos Depuración Datos Clientes
	QGDepuracionDatosClientesDTO depuracionDatosClientes;
	
	//Datos Clientes Conciliados
	QGClientesConciliadosDTO clientesConciliados;
	
	//Datos Conciliación Direcciones
	QGConciliacionDireccionesDTO conciliacionDirecciones;
	
	//Métodos públicos
	
	public void rellenarListadosNSCOTWPREPAGO (List info){
		
		logger.info("******---rellenarListadosNSCOTWPREPAGO de QGInformeConciliacionDTO---******");		
		
		NSCO = new ArrayList();
		TW = new ArrayList();
		PREPAGO = new ArrayList();		
		
		for (int i=0;i<info.size();i++){
			logger.info("******---" + (String) info.get(i) + "---******");
			if (i < 20)//Los 20 primeros elementos corresponden a NSCO
			{
				logger.info("******---entra en NSCO:" + (String) info.get(i) + "---******");				
				NSCO.add(QGInformeUtils.toInteger((String) info.get(i)));
			}
			else if (i >= 20 && i < 33) //del elemento 21 al 33 corresponden a TW
			{
					logger.info("******---entra en TW:" + (String) info.get(i) + "---******");				
					TW.add(QGInformeUtils.toInteger((String) info.get(i)));
			}
			else //y del elemento 34 al 45 corresponden a PREPAGO
			{
				logger.info("******---entra en PREPAGO:" + (String) info.get(i) + "---******");
				PREPAGO.add(QGInformeUtils.toInteger((String) info.get(i)));
				logger.info("******---entra en PREPAGO:" + QGInformeUtils.toInteger((String) info.get(i)) + "---" + PREPAGO.size() + "---******");			
			}
		}

		logger.info("******---FINAL rellenarListadosNSCOTWPREPAGO de QGInformeConciliacionDTO---******");		
	}
	
	public void aniadirDatosDepuracionDatosClientes (){
		
		logger.info("******---aniadirDatosDepuracionDatosClientes de QGInformeConciliacionDTO---******");		
		
		//Depuración Datos Clientes		
		depuracionDatosClientes = new QGDepuracionDatosClientesDTO();
		
		//Rellenamos la lista de datos (los identificamos con un numero para saber que se está rellenando)
		//0 - Numero clientes recibidos
		//1 - Número de clientes descartados por no cumplir condiciones de carga y haber quedado excluidos de QG
		//2 - Número de clientes descartados por fecha modif. Posterior a conciliación. 
		//3 - Número de clientes descartados por tipo de doc. o nombre / razón social erróneo
		//4 - Clientes de QG que no existen en origen por regularizaciones en sistemas origen
		//5 - Número de clientes para el proceso de conciliación
		
		//COLUMNA TW
		QGListadoDTO tw = new QGListadoDTO();
		
		List listaDatos = new ArrayList();
		List listaPorcentajes = new ArrayList();
		
		Integer total = (Integer) TW.get(0);
		
		listaDatos.add((Integer)TW.get(0)); // 0
		listaDatos.add((Integer)TW.get(1)); // 1
		listaDatos.add((Integer)TW.get(2)); // 2
		listaDatos.add(QGInformeUtils.sumar((Integer)TW.get(3),(Integer)TW.get(4))); //3
		listaDatos.add(null); // 4
		listaDatos.add((Integer)TW.get(8)); // 5
		
		tw.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(1),total)); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(null); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5

		tw.setListaPorcentajes(listaPorcentajes);
		depuracionDatosClientes.setTW(tw);
		
		//COLUMNA QG (TW)
		
		QGListadoDTO qgtw = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = (Integer)TW.get(5);
		
		listaDatos.add((Integer)TW.get(5)); // 0
		listaDatos.add(null); // 1
		listaDatos.add((Integer)TW.get(7)); // 2
		listaDatos.add(null); // 3
		listaDatos.add((Integer)TW.get(6)); // 4
		listaDatos.add((Integer)TW.get(9)); // 5
		
		qgtw.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(null); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(null); // 3
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(4),total)); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5

		qgtw.setListaPorcentajes(listaPorcentajes);
		depuracionDatosClientes.setQGTW(qgtw);	

		//COLUMNA NSCO
		QGListadoDTO nsco = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = (Integer)NSCO.get(0);
		
		listaDatos.add((Integer)NSCO.get(0)); // 0
		listaDatos.add((Integer)NSCO.get(1)); // 1
		listaDatos.add((Integer)NSCO.get(2)); // 2
		listaDatos.add(QGInformeUtils.sumar((Integer)NSCO.get(3),(Integer)NSCO.get(4))); //3		
		listaDatos.add(null); // 4
		listaDatos.add((Integer)NSCO.get(8)); // 5
		
		nsco.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(1),total)); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(null); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5

		nsco.setListaPorcentajes(listaPorcentajes);
		depuracionDatosClientes.setNSCO(nsco);
		
		//COLUMNA QG (NSCO)
		
		QGListadoDTO qgnsco = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = (Integer)NSCO.get(5);
		
		listaDatos.add((Integer)NSCO.get(5)); // 0
		listaDatos.add(null); // 1
		listaDatos.add((Integer)NSCO.get(7)); // 2
		listaDatos.add(null); // 3
		listaDatos.add((Integer)NSCO.get(6)); // 4
		listaDatos.add((Integer)NSCO.get(9)); // 5
		
		qgnsco.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(null); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(null); // 3
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(4),total)); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5

		qgnsco.setListaPorcentajes(listaPorcentajes);
		depuracionDatosClientes.setQGNSCO(qgnsco);
		
		//COLUMNA PREPAGO
		QGListadoDTO prepago = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = (Integer)PREPAGO.get(0);
		
		listaDatos.add((Integer)PREPAGO.get(0)); // 0
		listaDatos.add(null); // 1
		listaDatos.add((Integer)PREPAGO.get(1)); // 2
		listaDatos.add((Integer)PREPAGO.get(2)); //3
		listaDatos.add(null); // 4
		listaDatos.add((Integer)PREPAGO.get(6)); // 5
		
		prepago.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(null); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(null); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5

		prepago.setListaPorcentajes(listaPorcentajes);
		depuracionDatosClientes.setPREPAGO(prepago);
		
		//COLUMNA QG (PREPAGO)
		
		QGListadoDTO qgprepago = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = (Integer)PREPAGO.get(3);
		
		listaDatos.add((Integer)PREPAGO.get(3)); // 0
		listaDatos.add(null); // 1
		listaDatos.add((Integer)PREPAGO.get(5)); // 2
		listaDatos.add(null); // 3
		listaDatos.add((Integer)PREPAGO.get(4)); // 4
		listaDatos.add((Integer)PREPAGO.get(7)); // 5
		
		qgprepago.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(null); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(null); // 3
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(4),total)); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5

		qgprepago.setListaPorcentajes(listaPorcentajes);
		depuracionDatosClientes.setQGPREPAGO(qgprepago);		
		
		logger.info("******---FINAL aniadirDatosDepuracionDatosClientes de QGInformeConciliacionDTO---******");		
	}
	
	public void aniadirDatosClientesConciliados (){
		
		logger.info("******---aniadirDatosClientesConciliados de QGInformeConciliacionDTO---******");		
		
		//Clientes Conciliados
		clientesConciliados = new QGClientesConciliadosDTO();
		
		//Rellenamos la lista de datos (los identificamos con un numero para saber que se está rellenando)
		//0 - Número de clientes para el proceso de conciliación
		//1 - Clientes correctos sin discrepancias
		//2 - Clientes a dar de alta en QG
		//3 - Clientes a modificar en QG
		//4 - Clientes a dar de baja en QG		

		//COLUMNA TW		
		QGListadoDTO tw = new QGListadoDTO();
		
		List listaDatos = new ArrayList();
		List listaPorcentajes = new ArrayList();
		
		Integer total = QGInformeUtils.sumar(
						QGInformeUtils.sumar((Integer)TW.get(10), (Integer)TW.get(12)),
						(Integer)TW.get(11));
		
		listaDatos.add(total); // 0
		listaDatos.add((Integer)TW.get(10)); // 1
		listaDatos.add((Integer)TW.get(12)); // 2
		listaDatos.add((Integer)TW.get(11)); //3
		listaDatos.add(null); //4
		
		tw.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(1),total)); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(null); // 4

		tw.setListaPorcentajes(listaPorcentajes);
		clientesConciliados.setTW(tw);
		//COLUMNA QG (TW)
		
		QGListadoDTO qgtw = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = QGInformeUtils.sumar((Integer)TW.get(10), (Integer)TW.get(11));

		listaDatos.add(total); // 0
		listaDatos.add((Integer)TW.get(10)); // 1
		listaDatos.add(null); // 2
		listaDatos.add((Integer)TW.get(11)); //3
		listaDatos.add(null); // 4
		
		qgtw.setListaDatos(listaDatos);
		qgtw.setListaPorcentajes(null);
		
		clientesConciliados.setQGTW(qgtw);	

		//COLUMNA NSCO		
		QGListadoDTO nsco = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = QGInformeUtils.sumar(
						QGInformeUtils.sumar((Integer)NSCO.get(10), (Integer)NSCO.get(12)),
						(Integer)NSCO.get(11));
		
		listaDatos.add(total); // 0
		listaDatos.add((Integer)NSCO.get(10)); // 1
		listaDatos.add((Integer)NSCO.get(11)); // 2
		listaDatos.add((Integer)NSCO.get(12)); //3
		listaDatos.add(null); //4
		
		nsco.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(1),total)); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(null); //4

		nsco.setListaPorcentajes(listaPorcentajes);
		clientesConciliados.setNSCO(nsco);
		//COLUMNA QG (NSCO)
		
		QGListadoDTO qgnsco = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = QGInformeUtils.sumar((Integer)NSCO.get(10), (Integer)NSCO.get(12));

		listaDatos.add(total); // 0
		listaDatos.add((Integer)NSCO.get(10)); // 1
		listaDatos.add(null); // 2
		listaDatos.add((Integer)NSCO.get(12)); //3
		listaDatos.add(null); //4
		
		qgnsco.setListaDatos(listaDatos);
		qgnsco.setListaPorcentajes(null);
		
		clientesConciliados.setQGNSCO(qgnsco);
		
		//COLUMNA PREPAGO		
		QGListadoDTO prepago = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = QGInformeUtils.sumar(
						QGInformeUtils.sumar((Integer)PREPAGO.get(8), (Integer)PREPAGO.get(10)),
						(Integer)PREPAGO.get(9));
		total = QGInformeUtils.sumar(total,	(Integer)PREPAGO.get(11));
		
		listaDatos.add(total); // 0
		listaDatos.add((Integer)PREPAGO.get(8)); // 1
		listaDatos.add((Integer)PREPAGO.get(9)); // 2
		listaDatos.add((Integer)PREPAGO.get(10)); //3
		listaDatos.add((Integer)PREPAGO.get(11)); //3
		
		prepago.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(1),total)); // 1
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(4),total)); // 4		

		prepago.setListaPorcentajes(listaPorcentajes);
		clientesConciliados.setPREPAGO(prepago);
		//COLUMNA QG (PREPAGO)
		
		QGListadoDTO qgprepago = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = QGInformeUtils.sumar((Integer)PREPAGO.get(8), (Integer)PREPAGO.get(10));

		listaDatos.add(total); // 0
		listaDatos.add((Integer)PREPAGO.get(8)); // 1
		listaDatos.add(null); // 2
		listaDatos.add((Integer)PREPAGO.get(10)); //3
		listaDatos.add(null); // 4
		
		qgprepago.setListaDatos(listaDatos);
		qgprepago.setListaPorcentajes(null);
		
		clientesConciliados.setQGPREPAGO(qgprepago);		
		
		logger.info("******---FINAL aniadirDatosClientesConciliados de QGInformeConciliacionDTO---******");		
	}

	public void aniadirDatosConciliacionDirecciones (){
		
		logger.info("******---aniadirDatosConciliacionDirecciones de QGInformeConciliacionDTO---******");		
		
		//Conciliacion Direcciones		
		conciliacionDirecciones = new QGConciliacionDireccionesDTO();
		
		//Rellenamos la lista de datos (los identificamos con un numero para saber que se está rellenando)
		//0 - Número de direcciones recibidas 
		//1 - Direcciones no conciliadas por pertenecer a exclientes no cargados en QG
		//2 - Direcciones a conciliar
		//3 - Direcciones correctas sin discrepancias
		//4 - Direcciones a dar de alta en QG 
		//5 - Direcciones a modificar en QG
		//6 - Direcciones a dar de baja en QG 

		//COLUMNA NSCO		
		QGListadoDTO nsco = new QGListadoDTO();
		
		List listaDatos = new ArrayList();
		List listaPorcentajes = new ArrayList();
		
		Integer total = (Integer)NSCO.get(13);
		
		listaDatos.add((Integer)NSCO.get(13)); // 0
		listaDatos.add((Integer)NSCO.get(19)); // 1
		
		listaDatos.add(QGInformeUtils.restar((Integer)NSCO.get(13), (Integer)NSCO.get(19))); // 2
		listaDatos.add((Integer)NSCO.get(15)); //3
		listaDatos.add((Integer)NSCO.get(17)); //4
		listaDatos.add((Integer)NSCO.get(16)); //5
		listaDatos.add(null); //6
		
		nsco.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(1),total)); // 1
		
		total = (Integer) listaDatos.get(2);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(4),total)); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5
		listaPorcentajes.add(null); // 6
		nsco.setListaPorcentajes(listaPorcentajes);
		
		conciliacionDirecciones.setNSCO(nsco);
		
		//COLUMNA QG
		QGListadoDTO qg = new QGListadoDTO();
		
		listaDatos = new ArrayList();
		listaPorcentajes = new ArrayList();
		
		total = (Integer)NSCO.get(14);
		
		listaDatos.add((Integer)NSCO.get(14)); // 0
		listaDatos.add(null); // 1
		listaDatos.add((Integer)NSCO.get(14)); // 2
		listaDatos.add((Integer)NSCO.get(15)); //3
		listaDatos.add(null); //4
		listaDatos.add((Integer)NSCO.get(16)); //5
		listaDatos.add((Integer)NSCO.get(18)); //6
		
		qg.setListaDatos(listaDatos);
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCiento(total,total)); // 0
		listaPorcentajes.add(null); // 1
		
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(2),total)); // 2
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(3),total)); // 3
		listaPorcentajes.add(null); // 4
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(5),total)); // 5
		listaPorcentajes.add(QGInformeUtils.tantoPorCientoBase0Round2((Integer) listaDatos.get(6),total)); // 6
		
		qg.setListaPorcentajes(listaPorcentajes);
		
		conciliacionDirecciones.setQG(qg);
		
		logger.info("******---FINAL aniadirDatosConciliacionDirecciones de QGInformeConciliacionDTO---******");		
	}
	
	
	//GETTERS & SETTERS
	public List getNSCO() {
		return NSCO;
	}

	public void setNSCO(List nsco) {
		NSCO = nsco;
	}

	public List getTW() {
		return TW;
	}

	public void setTW(List tw) {
		TW = tw;
	}
	
	public List getPREPAGO() {
		return PREPAGO;
	}

	public void setPREPAGO(List prepago) {
		PREPAGO = prepago;
	}	

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public QGDepuracionDatosClientesDTO getDepuracionDatosClientes() {
		return depuracionDatosClientes;
	}

	public void setDepuracionDatosClientes(
			QGDepuracionDatosClientesDTO depuracionDatosClientes) {
		this.depuracionDatosClientes = depuracionDatosClientes;
	}

	public QGClientesConciliadosDTO getClientesConciliados() {
		return clientesConciliados;
	}

	public void setClientesConciliados(QGClientesConciliadosDTO clientesConciliados) {
		this.clientesConciliados = clientesConciliados;
	}

	public QGConciliacionDireccionesDTO getConciliacionDirecciones() {
		return conciliacionDirecciones;
	}

	public void setConciliacionDirecciones(
			QGConciliacionDireccionesDTO conciliacionDirecciones) {
		this.conciliacionDirecciones = conciliacionDirecciones;
	}

}