package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    /************************/
    /*------INTERFACE-------*/
    /************************/
	
	@FXML
	private BorderPane contentPane;
	
	/******  CONTENT PANEL   ******/
    @FXML
    private MenuBar mainMenu;
    @FXML
    private VBox parametersVBox;
    @FXML
    private LineChart<Number,Number> chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    
    /****** MENU BAR ******/
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveMenuItem; 
    @FXML
    private MenuItem closeMenuItem; 

    /****** PARAMETERS VBOX ******/
    @FXML
    private ListView<Object> featuresList;
    @FXML
    private Button saveFeatureCoordinatesButton;
    @FXML
    private Button graphGoLeftButton;
    @FXML
    private Button graphGoRightButton;
    @FXML
    private Button graphZoomInButton;
    @FXML
    private Button graphZoomOutButton;
    
    /****** PANEL PARAMETERS ******/
    @FXML
    private Spinner pointHeightSpinner;
    
    /************************/
    /*--------DATA----------*/
    /************************/
    
    private Stage stage;
    private Scene scene;
    Series<Number, Number> series;
    File selectedFile;
    File saveFile;

	FeatureCollection<SimpleFeatureType, SimpleFeature> fc;
    SimpleFeature selectedFeature;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Bienvenue");
        
        //affichage du graph (add parameter data)
        series = new Series<Number, Number>();
        chart.setAnimated(false);        
        chart.getData().add(series);
        chart.setLegendVisible(false);
        saveMenuItem.setDisable(true);
        featuresList.setVisible(false);
    }
	
    /****** MENU BAR METHODS ******/
    //load geojson
    @FXML
    private void openGeoJson(ActionEvent event) {
        System.out.println("Open GeoJson");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open GeoJson");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Json Files", "*.json"), new ExtensionFilter("All Files", "*.*"));
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
        	System.out.println(selectedFile.getName());
        	addActionClicList();
        	saveMenuItem.setDisable(false);
        }
    }
    
    //save new values in geojson
    //load geojson
    @FXML
    private void saveGeoJson(ActionEvent event) {
        System.out.println("Save GeoJson");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GeoJson");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Json Files", "*.json"), new ExtensionFilter("All Files", "*.*"));
        if(selectedFile != null){
        	fileChooser.setInitialDirectory(selectedFile.getParentFile());
        	fileChooser.setInitialFileName(selectedFile.getName());
        }
        saveFile = fileChooser.showSaveDialog(stage);
        
        if (saveFile != null){
        	System.out.println(saveFile.getPath());
        	System.out.println(saveFile.getName());
        	try {
				ApplicationUtils.featureCollectionToGeoJsonFile(fc, saveFile.getParentFile(), saveFile.getName());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }    
    
    /****** PARAMETERS VBOX METHODS ******/
    //load geojson
    @FXML
    private void graphGoLeft(ActionEvent event){
    	System.out.println(xAxis.getLowerBound());
    	xAxis.setLowerBound(xAxis.getLowerBound() -1);
    }
    
    @FXML
    private void saveFeatureCoordinates(){
    	int i = 0;
    	for (Data<Number, Number> data : series.getData()) {
        	ApplicationUtils.saveCoordinates(selectedFeature, i, (double)data.getYValue());
        	i++;
        }
    }    
    
    public void addActionDragPoint() {
        for (Data<Number, Number> data : series.getData()) {
            Node node = data.getNode() ;
            node.setCursor(Cursor.HAND);
            node.setOnMouseDragged(e -> {
                Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
               // double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
               // Number x = xAxis.getValueForDisplay(xAxisLoc);
                Number y = yAxis.getValueForDisplay(yAxisLoc);
               // data.setXValue(x);
                data.setYValue(y);               
            });
        }
	}
    
    public void addActionClicList() {
    	try {
    		fc = ApplicationUtils.geoJsonToFeatureCollection(selectedFile);		
			ObservableList<Object> observableList = FXCollections.observableArrayList(fc.toArray());
			featuresList.setVisible(true);
			featuresList.setItems(observableList);
			featuresList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	selectedFeature = (SimpleFeature) featuresList.getSelectionModel().getSelectedItem();
		        	series.getData().clear();
		            ArrayList<Data<Number, Number>> datas = ApplicationUtils.loadCoordinates(selectedFeature);			            	            
		            datas.forEach(data -> {
		            	series.getData().add(data);
		            });
		            addActionDragPoint();
		        }
		    });
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    void findStage(Stage stage) {
        this.stage = stage;
    }
    
    void findScene(Scene scene) {
        this.scene = scene;
    }
}
