package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.vividsolutions.jts.geom.Geometry;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FXMLDocumentController implements Initializable {

    /************************/
    /*------INTERFACE-------*/
    /************************/
	
	@FXML
    private Pane mainPane;
	@FXML
	private BorderPane contentPane;
	
	/******  CONTENT PANEL   ******/
    @FXML
    private MenuBar mainMenu;
    @FXML
    private Pane parameterPane;
    @FXML
    private LineChart<Number,Number> chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    
    /****** PANEL PARAMETERS ******/
    @FXML
    private Spinner pointHeightSpinner;
    
    /************************/
    /*--------DATA----------*/
    /************************/
    
    private Scene scene;
    Series<Number, Number> series;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Bienvenue");

        //chargement des geojson
        //loadGeometries();
        
        //affichage du graph (add parameter data)
        displayGraph();
    }
    
    public Series<Number, Number> loadGeometries(File f){
    	
    	Geometry geom;
    	
    	return null;
    }


	public void displayGraph() {
        series = new Series<Number, Number>();
        series.setName("serie1");
      
        chart.setAnimated(false);
        
        //init dummy data
        for(int i=0; i<10; i++){
            series.getData().add(new Data<Number, Number>(i, i));
        }
        chart.getData().add(series);
        
        for (Data<Number, Number> data : series.getData()) {
            Node node = data.getNode() ;
            node.setCursor(Cursor.HAND);
            node.setOnMouseDragged(e -> {
                Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
                double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
                Number x = xAxis.getValueForDisplay(xAxisLoc);
                Number y = yAxis.getValueForDisplay(yAxisLoc);
               // data.setXValue(x);
                data.setYValue(y);
            });
        }
	}
	
    void findScene(Scene scene) {
        this.scene = scene;
    }
}
