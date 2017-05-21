package application;
import java.util.ArrayList;

public class Vanet {

	private ArrayList<OBU> mapa;

	public Vanet() {
		mapa = new ArrayList<OBU>();
	}

	public void addOBU(OBU carro) {
		mapa.add(carro);
	}
	
	public void atualizarVizinhos() {
		for (OBU obu : mapa) {
			for (OBU obuViz : mapa) {
				if (!obu.equals(obuViz)) {
					if (Math.sqrt(Math.pow(obuViz.getX() - obu.getX(), 2) + Math.pow(obuViz.getY() - obu.getY(), 2)) <= obu.getRange() ) {
						obu.addVizinho(obuViz);
					}
				}
			}
		}
	}
	
	public void imprimirVizinhos() {
		for (OBU obu : mapa) {
			System.out.println("Carro: " + obu.getName());
			obu.imprimeVizinhos();
			
		}
	}
}
