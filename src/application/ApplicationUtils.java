package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import javafx.scene.chart.XYChart.Data;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

public class ApplicationUtils {

	private static FeatureJSON featureJSON;
	
	//create a feature collection from a file
	@SuppressWarnings("unchecked")
	public static FeatureCollection<SimpleFeatureType, SimpleFeature> geoJsonToFeatureCollection(File featureCollectionFile) throws FileNotFoundException, IOException {
				
		featureJSON = new FeatureJSON();
		return featureJSON.readFeatureCollection(new FileInputStream(featureCollectionFile));
	}
	
	
	public static ArrayList<Data<Number, Number>> loadCoordinates(SimpleFeature sf){
		
		ArrayList<Data<Number, Number>> ret = new ArrayList<Data<Number, Number>>();
		Coordinate[] c = ((Geometry) sf.getDefaultGeometryProperty().getValue()).getCoordinates();
        double dist = 0;
        
    	ret.add(new Data<Number, Number>(dist, c[0].z));
        for(int i = 1; i < c.length; i++){
        	dist += getDistanceFromCoordinates(c[i-1], c[i]);
        	ret.add(new Data<Number, Number>(dist, c[i].z));
        }
		return ret;
	}
	
	/**
	 * Get the distance between two coordinates
	 * @param coordinate1 first coordinate
	 * @param coordinate2 second coordinate
	 * @return The distance traveled between the 2 points in meters
	 */
	public static double getDistanceFromCoordinates(Coordinate coordinate1, Coordinate coordinate2){
		double dist = 0;
		Geodesic geod = Geodesic.WGS84;
		GeodesicData d = geod.Inverse(coordinate1.y, coordinate1.x, coordinate2.y, coordinate2.x);
		return d.s12;
	}
}
