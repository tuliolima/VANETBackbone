package application;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class OBU {
	
	private float x;
	private float y;
	private float range;
	private String name;
	private ArrayList<OBU> vizinhos;
	private final static int cWMax = 50;
	private final static int cWMin = 5;
	private int backoff;
	private boolean bMState = false;
	GraphicsContext gc;
	private OBU OBUCandidature;
	private int lastBeaconTime;
	private boolean candidate = true;
	private boolean bbStarted = false;

	public OBU(float x, float y, float range, String name) {
		this.x = x;
		this.y = y;
		this.range = range;
		vizinhos = new ArrayList<OBU>();
		this.name = name;
		System.out.println(name);
		lastBeaconTime = (int) (Math.random() * 101);
		backoff = -1;
		gc = Singleton.getGraphicsContext();
		
		//Desenhando o OBU na tela
		gc.setFill(Paint.valueOf("BLACK"));
        gc.fillOval(x-10,y-10,20,20);
        if (Singleton.isDebugMode()) {
    		gc.setStroke(Paint.valueOf("BLACK"));
        	gc.strokeOval(x-range, y-range, range*2, range*2);
		}
        gc.setFont(Font.font(20));
		gc.fillText(name, x-30, y+10);
	}
	
	public void addVizinho(OBU vizinho) {
		vizinhos.add(vizinho);
	}

	public void imprimeVizinhos() {
		System.out.print("-> ");
		for (OBU obu : vizinhos) {
			System.out.print(obu.getName() + ", ");
		}
	}
	
	public boolean isVizinho(OBU obu) {
		return vizinhos.contains(obu);
	}
	
	public void message(OBU source, int code, OBU destination) {
		switch (code) {
		case 1: //Beacon
			if (!bMState && candidate) {
				candidate = false;
				OBUCandidature = source;
				lastBeaconTime = 150;
				//Calculando o backoff
				System.out.println("Valores OBU " + this.name);
				double fitFactor = Math.sqrt(Math.pow(source.getX() - this.x, 2) + Math.pow(source.getY() - this.y, 2)) / range;
				System.out.println("FF: " + fitFactor);
				int cW = (int) ((1 - fitFactor) * (cWMax - cWMin) + cWMin);
				System.out.println("CW: " + cW);
				backoff = (int) (Math.random() * (cW + 1));
				System.out.println("Backoff: " + backoff);
			}
			break;
		case 2: //Ack_Winner
			if (this.equals(destination)) {
				gc.setStroke(Paint.valueOf("RED"));
		        gc.setLineWidth(2);
		        gc.strokeLine(this.x, this.y, source.getX(), source.getY());
				becomeBM();
				sendMessage(1, null);
			}
			break;
		case 3: //Candidature message
			if (bMState) {
				if (this.equals(destination)) {
					System.out.println("Recebi uma candidatura. OBU " + this.name);
					source.message(this, 2, source);
				}
			} else {
				if (destination.equals(OBUCandidature)) {
					backoff = -1;
					inTheNet();
				} else {
					//Nothing to do
				}
			}
		default:
			break;
		}
	}
	
	public void step() {
		doBackoff();
		//doLastTimeBackoff();
	}
	
	private void inTheNet() {
		lastBeaconTime = -1;
		candidate = false;
		gc.setFill(Paint.valueOf("BLUE"));
        gc.fillOval(x-10,y-10,20,20);
	}
	
	private void doLastTimeBackoff() {
		//Imprimindo valor de lastBeaconTime na tela
        if (Singleton.isDebugMode()) {
			gc.setFill(Paint.valueOf("BLUE"));
			gc.fillRect(x-30, y-30, 30, 20);
			gc.setFill(Paint.valueOf("RED"));
			gc.setFont(Font.font(20));
			gc.fillText("" + lastBeaconTime, x-30, y-10);
        }
		if (lastBeaconTime==0 && !bMState) {
			becomeBM();
			sendMessage(1, null);
		}
		if (lastBeaconTime > 0)
			lastBeaconTime--;
	}
	
	private void doBackoff() {
		//Imprimindo valor de backoff na tela
        if (Singleton.isDebugMode()) {
			gc.setFill(Paint.valueOf("BLUE"));
			gc.fillRect(x, y-30, 30, 20);
			gc.setFill(Paint.valueOf("RED"));
			gc.setFont(Font.font(20));
			gc.fillText("" + backoff, x, y-10);
        }
		if (backoff == 0 && !bMState) {
			sendMessage(3, OBUCandidature);
		}
		if (backoff > 0)
			backoff--;
	}
	
	public void becomeBM() {
		bMState = true;
		gc.setFill(Paint.valueOf("GREEN"));
        gc.fillOval(x-10,y-10,20,20);
	}
	
	private void sendMessage(int code, OBU destination) {
		for (OBU obu : vizinhos) {
			obu.message(this, code, destination);
		}
	}
	
	public void initBackbone() {
		if (!bbStarted) {
			bbStarted = true;
			becomeBM();
			sendMessage(1, null);
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
