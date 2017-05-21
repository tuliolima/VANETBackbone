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
import javafx.stage.Stage;


public class BackboneManager extends Application {
	
	private BorderPane mBorderPane;
    private Canvas map;
    private GraphicsContext gc;
	private static Vanet rede = new Vanet();
	private final float range = 50;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			mBorderPane = new BorderPane();
			Group root = new Group();
			
			map = new Canvas(600, 600);
			gc = map.getGraphicsContext2D();
			
	        map.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {          
	                gc.fillOval(e.getX()-10,e.getY()-10,20,20);
	    			rede.addOBU(new OBU((float) e.getX(), (float) e.getY(), range));
	            }
	        });
	        
	        Button atualizar = new Button("Atualizar");
	        
	        atualizar.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                rede.atualizarVizinhos();
	                rede.imprimirVizinhos();
	            }
	        });
	        
	        
	        mBorderPane.setCenter(map);
	        mBorderPane.setTop(atualizar);
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
