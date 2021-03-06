package application;
import java.util.ArrayList;

public class Vanet {

	private ArrayList<OBU> mapa;
	private OBU firstOBU = null;

	public Vanet() {
		mapa = new ArrayList<OBU>();
	}

	public void addOBU(float x, float y, float range, String name) {
		if (firstOBU == null) {
			firstOBU = new OBU(x, y, range, name);
			mapa.add(firstOBU);
		} else {
			mapa.add(new OBU(x, y, range, name));			
		}
	}
	
	public void atualizarVizinhos() {
		for (OBU obu : mapa) {
			for (OBU obuViz : mapa) {
				if (!obu.equals(obuViz)) {
					if (Math.sqrt(Math.pow(obuViz.getX() - obu.getX(), 2) + Math.pow(obuViz.getY() - obu.getY(), 2)) <= obu.getRange() ) {
						if(!obu.isVizinho(obuViz))
							obu.addVizinho(obuViz);
					}
				}
			}
		}
	}
	
	public void imprimirVizinhos() {
		System.out.println("Imprimindo vizinhos:");
		for (OBU obu : mapa) {
			System.out.print("= " + obu.getName());
			obu.imprimeVizinhos();
			System.out.println();
		}
	}
	
	public void step() {
		if (firstOBU != null)
			firstOBU.initBackbone();
		for (OBU obu : mapa) {
			obu.step();
		}
	}
	
	public void clear() {
		firstOBU = null;
		mapa = new ArrayList<OBU>();
	}
}
