package com.telefonica.ssnn.qg.buscador.clientes.dto;
/**
 * Clase que alamacena el historial de la segmentacion de un cliente
 * @author jacastano
 *
 */
public class QGHistorialSegmentacionCliDto {
	
	private String codSegmento;
	
	private String desSegmento;
	
	private String codSubSegmento;
	
	private String desSubSegmento;
	
	private String fecMod;

	public String getCodSegmento() {
		return codSegmento;
	}

	public void setCodSegmento(String codSegmento) {
		this.codSegmento = codSegmento;
	}

	public String getDesSegmento() {
		return desSegmento;
	}

	public void setDesSegmento(String desSegmento) {
		this.desSegmento = desSegmento;
	}

	public String getCodSubSegmento() {
		return codSubSegmento;
	}

	public void setCodSubSegmento(String codSubSegmento) {
		this.codSubSegmento = codSubSegmento;
	}

	public String getDesSubSegmento() {
		return desSubSegmento;
	}

	public void setDesSubSegmento(String desSubSegmento) {
		this.desSubSegmento = desSubSegmento;
	}

	public String getFecMod() {
		return fecMod;
	}

	public void setFecMod(String fecMod) {
		this.fecMod = fecMod;
	}
	
	
	
	
}