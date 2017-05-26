package application;

import javafx.scene.canvas.GraphicsContext;

public class Singleton {
	
	private static Singleton instance = null;
	private static GraphicsContext gc;
	private static boolean debugMode = false;
	
	protected Singleton() {
		// Exists only to defeat instantiation.
	}
   
	public static Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
	
	public static void setGraphicsContext(GraphicsContext gc) {
		Singleton.gc = gc;
	}
	
	public static GraphicsContext getGraphicsContext() {
		return gc;
	}

	public static boolean isDebugMode() {
		return debugMode;
	}

	public static void setDebugMode(boolean debugMode) {
		Singleton.debugMode = debugMode;
	}
}