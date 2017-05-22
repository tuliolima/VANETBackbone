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
	private static final float RANGE = 100;
	private int OBUCount;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			OBUCount = 0;
			
			mBorderPane = new BorderPane();
			Group root = new Group();
			
			map = new Canvas(600, 600);
			gc = map.getGraphicsContext2D();
			Singleton.setGraphicsContext(gc);
			
	        map.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	    			rede.addOBU((float) e.getX(), (float) e.getY(), RANGE, OBUCount + "");
	    			OBUCount++;
	            }
	        });
	        
	        Button atualizar = new Button("Atualizar");
	        
	        atualizar.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                rede.atualizarVizinhos();
	                rede.imprimirVizinhos();
	                rede.step();
	              
	            }
	        });
	        
	        Button limpar = new Button("Limpar");
	        
	        limpar.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                rede.clear();
	                gc.clearRect(0, 0, map.getWidth(), map.getHeight());
	                OBUCount=0;
	            }
	        });
	        
	        HBox menu = new HBox(atualizar, limpar);	        
	        
	        mBorderPane.setCenter(map);
	        mBorderPane.setTop(menu);
			root.getChildren().add(mBorderPane);
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("Backbone");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
