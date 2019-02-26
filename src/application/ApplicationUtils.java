package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class ApplicationUtils {

	private static FeatureJSON featureJSON;
	
	//create a feature collection from a file
	@SuppressWarnings("unchecked")
	public static FeatureCollection<SimpleFeatureType, SimpleFeature> geoJsonToFeatureCollection(File featureCollectionFile) throws FileNotFoundException, IOException {
				
		featureJSON = new FeatureJSON();
		return featureJSON.readFeatureCollection(new FileInputStream(featureCollectionFile));
	}
	
	
	public static void loadCoordinates(SimpleFeature sf){
		
        Coordinate[] c = ((Geometry) sf.getDefaultGeometryProperty().getValue()).getCoordinates();
        System.out.println(c[0].z);
	}
	
	public static String test(){
		return "test";
	}
}
