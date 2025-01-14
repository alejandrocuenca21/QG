/**
 * 
 */
package com.telefonica.ssnn.qg.informes.servicio.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.telefonica.ssnn.qg.base.dto.QGCGlobalDto;
import com.telefonica.ssnn.qg.informes.dao.interfaz.QGInformesConvivenciaDao;
import com.telefonica.ssnn.qg.informes.dto.QGBuscadorDto;
import com.telefonica.ssnn.qg.informes.dto.QGEstadisticasDto;
import com.telefonica.ssnn.qg.informes.dto.QGInformeDto;

/**
 * @author vsierra
 *
 */
public class QGInformesConvivenciaServicioImpl implements
		com.telefonica.ssnn.qg.informes.servicio.interfaz.QGInformesConvivenciaServicio {

	private QGInformesConvivenciaDao informesConvivenciaDao;
	
	/**
	 * Se llamará al método del dao que lleva el mismo nombre, pasándole la lista de registros modificados
	 */
	
	public QGCGlobalDto actualizarDuplicados(List buscadorDto) {
		return getInformesConvivenciaDao().actualizarDuplicados(buscadorDto);
	}

	/**
	 * Se llamará al método del dao que lleva el mismo nombre pasándole como parámetros la fecha de inicio y la fecha de fin.
	 */
	
	public QGCGlobalDto buscarClientesEstadisticas(QGEstadisticasDto estadisticasDto){
		QGInformeDto informeDto = new QGInformeDto();
   		QGBuscadorDto buscadorDto = new QGBuscadorDto();
   		
   		buscadorDto.setFxInicio(estadisticasDto.getFxInicio());
   		buscadorDto.setFxFin(estadisticasDto.getFxFin());
   		
   		List listaMensajes = new ArrayList();
   		
		QGCGlobalDto QGCGlobalDto = getInformesConvivenciaDao().buscadorClientesDuplicados(buscadorDto );
   		
		informeDto.setListaClientes(QGCGlobalDto.getlistaDatos());		
		listaMensajes.addAll(QGCGlobalDto.getlistaMensajes());
		
		QGCGlobalDto = getInformesConvivenciaDao().obtenerEstadisticas(estadisticasDto);
		listaMensajes.addAll(QGCGlobalDto.getlistaMensajes());
		
   		informeDto.setEstadisticasDto((QGEstadisticasDto)QGCGlobalDto.getlistaDatos().get(0));
   		
   		QGCGlobalDto = new QGCGlobalDto();
   		
   		QGCGlobalDto.setlistaMensajes(listaMensajes);
   		List listaDatos = new ArrayList();
   		listaDatos.add(informeDto);   		
   		QGCGlobalDto.setlistaDatos(listaDatos);
   		
   		return QGCGlobalDto;
	}
	
	public QGCGlobalDto buscadorClientesDuplicados(QGBuscadorDto buscadorDto) {
		
		return getInformesConvivenciaDao().buscadorClientesDuplicados(buscadorDto);
	}

	/**
	 * Se llamará al método del dao que lleva el mismo nombre pasándole como parámetros la fecha de inicio y la fecha de fin.
	 */
	
	public QGCGlobalDto buscadorErrores(QGBuscadorDto buscadorDto) {
		
		QGCGlobalDto errores = getInformesConvivenciaDao().buscadorErrores(buscadorDto);
		
		errores.setlistaDatos(filtrarErroresPorCodigos(errores.getlistaDatos()));
		
		return errores;
	}
	
	public QGCGlobalDto buscarErroresEstadisticas(QGEstadisticasDto estadisticasDto){
		QGInformeDto informeDto = new QGInformeDto();
   		QGBuscadorDto buscadorDto = new QGBuscadorDto();
   		
//   		buscadorDto.setFxInicio(estadisticasDto.getFxInicio());
//   		buscadorDto.setFxFin(estadisticasDto.getFxFin());
   		
   		buscadorDto.setFxBusqueda(estadisticasDto.getFxBusqueda());
   		
   		List listaMensajes = new ArrayList();
   		
		QGCGlobalDto QGCGlobalDto = getInformesConvivenciaDao().buscadorErrores(buscadorDto);
   				
		
		//CAMBIO CLGL105-CAMBIO_INFORME_ERRORES 01/02/2011 rgsimon
		QGCGlobalDto.setlistaDatos(
				filtrarErroresPorCodigos(
						QGCGlobalDto.getlistaDatos()));		
		
		informeDto.setListaClientes(QGCGlobalDto.getlistaDatos());		
		listaMensajes.addAll(QGCGlobalDto.getlistaMensajes());
		
		QGCGlobalDto = getInformesConvivenciaDao().obtenerEstadisticas(estadisticasDto);
		listaMensajes.addAll(QGCGlobalDto.getlistaMensajes());
		
   		informeDto.setEstadisticasDto((QGEstadisticasDto)QGCGlobalDto.getlistaDatos().get(0));
   		
   		QGCGlobalDto = new QGCGlobalDto();
   		
   		QGCGlobalDto.setlistaMensajes(listaMensajes);
   		List listaDatos = new ArrayList();
   		listaDatos.add(informeDto);   		
   		QGCGlobalDto.setlistaDatos(listaDatos);
   		
   		return QGCGlobalDto;
	}
	//codigos de error por los que se filtrará
	
	
	//CAMBIO CLGL105-CAMBIO_INFORME_ERRORES 01/02/2011 rgsimon
	
	//LISTA DE CODIGOS DE ERROR QUE SE MOSTRARAN EN LA PANTALLA (INFORMES ERRORES)
	private static final String[] LISTA_CODIGOS_ERRORES_VALIDOS = {"QGC002","QGC003","QGC004","QGC005",
			"QGC017","QGC018","QGC019","QGC020","QGC021","QGC022",
			"QGC023","QGC024","QGC025","QGC026","QGC027","QGC028",
			"QGC029","QGC032","QGC033","QGC034","QGO007"};
	
	private List filtrarErroresPorCodigos(List listaCompleta){
		
		String[] listaValidos = LISTA_CODIGOS_ERRORES_VALIDOS;
		
		HashMap codigosErrorValidos = new HashMap();
		
		for (int i = 0; i < listaValidos.length; i++) {
			codigosErrorValidos.put(listaValidos[i], "");
		}
		    		
		ArrayList listaDatosFiltrados = new ArrayList();
		
		for (int i = 0; i < listaCompleta.size(); i++) {
			
			QGBuscadorDto elemento = new QGBuscadorDto();
			elemento = (QGBuscadorDto)listaCompleta.get(i);
			
			if(codigosErrorValidos.containsKey(elemento.getCodigoError())){
				listaDatosFiltrados.add(elemento);
			}    			    			    	
		}
		
		
		return listaDatosFiltrados;
		
	}
	
	
	
	
	/**
	 * Se llamará al método del dao que lleva el mismo nombre pasándole como parámetros la fecha de inicio, la fecha de fin y 
	 * se informará el parámetro tipoInforme con una "D"
	 */
	
	public QGCGlobalDto obtenerEstadisticas(QGEstadisticasDto estadisticasDto) {
		return getInformesConvivenciaDao().obtenerEstadisticas(estadisticasDto);
	}

	public QGInformesConvivenciaDao getInformesConvivenciaDao() {
		return informesConvivenciaDao;
	}

	public void setInformesConvivenciaDao(
			QGInformesConvivenciaDao informesConvivenciaDao) {
		this.informesConvivenciaDao = informesConvivenciaDao;
	}

}
