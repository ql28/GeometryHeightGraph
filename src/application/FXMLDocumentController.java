package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
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
    private LineChart chart;
    @FXML
    private Pane parameterPane;
	
	
    /****** PANEL PARAMETERS ******/
    @FXML
    private Spinner pointHeightSpinner;
    
    
    /************************/
    /*--------DATA----------*/
    /************************/
    
    //
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Bienvenue");

        //chargement des geojson
        //loadSavedSystem();
    }  
}
