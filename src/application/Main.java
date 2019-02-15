package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
    @Override
    public void start(Stage stage) throws Exception {
		
		stage.setTitle("test chart");

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = (Parent)loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
        
        //affichage de l'animation au lancement
        //FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
        //controller.findScene(scene); 
        //controller.displayGraph();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.geometry.Point2D;
//import javafx.scene.Cursor;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import javafx.scene.chart.XYChart.Data;
//import javafx.scene.control.Button;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        //defining the axes
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        //creating the chart
//        final LineChart<Number,Number> lineChart = 
//                new LineChart<Number,Number>(xAxis,yAxis);
//
//        lineChart.setAnimated(false);
//
//        //defining a series
//        XYChart.Series<Number, Number> series = new XYChart.Series<>();
//        //populating the series with data
//        for(int i=0; i<10; i++){
//            series.getData().add(new XYChart.Data<Number, Number>(i, i));
//        }
//        lineChart.getData().add(series);
//        Scene scene  = new Scene(new AnchorPane(lineChart),800,600);
//        
//        for (Data<Number, Number> data : series.getData()) {
//            Node node = data.getNode() ;
//            node.setCursor(Cursor.HAND);
//            node.setOnMouseDragged(e -> {
//                Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
//                double xAxisLoc = xAxis.sceneToLocal(pointInScene).getX();
//                double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
//                Number x = xAxis.getValueForDisplay(xAxisLoc);
//                Number y = yAxis.getValueForDisplay(yAxisLoc);
//                //data.setXValue(x);
//                data.setYValue(y);
//            });
//        }
//
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}