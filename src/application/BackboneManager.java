package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class BackboneManager extends Application {
	
	private BorderPane mBorderPane;
    private Canvas map;
    private GraphicsContext gc;
	private static Vanet rede = new Vanet();
	private static final float RANGE = 250;
	private int OBUCount;
	@Override
	public void start(Stage primaryStage) {
		try {
			OBUCount = 0;

			map = new Canvas(1200, 600);
			gc = map.getGraphicsContext2D();
			//Salva o Graphics Context para ser usado em outras classes
			Singleton.setGraphicsContext(gc);
			
			//Adiciona um novo OBU quando clica no mapa
	        map.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	    			rede.addOBU((float) e.getX(), (float) e.getY(), RANGE, OBUCount + "");
	    			OBUCount++;
	            }
	        });
	        
	        HBox menu = menuCreate();

			mBorderPane = new BorderPane();
	        mBorderPane.setCenter(map);
	        mBorderPane.setTop(menu);
	        
			Group root = new Group();
			root.getChildren().add(mBorderPane);
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("Backbone");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Cria menu
	HBox menuCreate () {
		//Faz 1 iteração
        Button step1 = new Button("Step+1");
        step1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                rede.atualizarVizinhos();
                rede.imprimirVizinhos();
                rede.step();
              
            }
        });
        
        //Faz 10 iterações
        Button step10 = new Button("Step+10");
        step10.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	for (int i = 0; i<10; i++) {
	                rede.atualizarVizinhos();
	                rede.imprimirVizinhos();
	                rede.step();
            	}
            }
        });
        
        //Apaga todos os OBUs colocados
        Button limpar = new Button("Limpar");
        limpar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                rede.clear();
                gc.clearRect(0, 0, map.getWidth(), map.getHeight());
                OBUCount=0;
            }
        });
        
      //Apaga todos os OBUs colocados
        Button debug = new Button("Debug off");
        debug.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if (Singleton.isDebugMode()) {
                	Singleton.setDebugMode(false);
                	debug.setText("Debug off");
				} else {
                	Singleton.setDebugMode(true);
                	debug.setText("Debug on");
				}
            }
        });
        
        return new HBox(step1, step10, limpar, debug);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
