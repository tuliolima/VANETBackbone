package application;
import java.util.ArrayList;

public class OBU {
	
	private float x;
	private float y;
	private float range;
	private String name;
	private ArrayList<OBU> vizinhos;

	public OBU(float x, float y, float range) {
		this.x = x;
		this.y = y;
		this.range = range;
		vizinhos = new ArrayList<OBU>();
		this.name = this.toString();
		System.out.println(name);
	}
	
	public void addVizinho(OBU vizinho) {
		vizinhos.add(vizinho);
	}

	public void imprimeVizinhos() {
		System.out.println("Vizinhos:");
		for (OBU obu : vizinhos) {
			System.out.println(obu.getName());
		}
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public ArrayList<OBU> getVizinhos() {
		return vizinhos;
	}

	public void setVizinhos(ArrayList<OBU> vizinhos) {
		this.vizinhos = vizinhos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
