package com.awarematics.postmedia.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import org.postgresql.pljava.annotation.Function;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;

import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;

public class TransTDrive {
	static double[] coordinate1_x;
	static double[] coordinate1_y;
	static long[] timeArray;
	static MPoint mp = null;
	
	public static void main(String args[]) throws IOException, ParseException {
		String writerContent = "";
		String[] result = new String[10357];
		File file = new File("D:\\tdrive.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		for(int l=1;l<=10357;l++){

		result[l-1] = BddDataToPostgre("C:\\Users/Ding/Downloads/release/taxi_log_2008_by_id/"+l+".txt");	
				writerContent =  " UPDATE tdrive \r\n SET mp  = append(mp , '" + result[l-1]
						+ "')\r\n" + " WHERE id = " + l + ";\r\n";
				//System.out.println(writerContent);
				writer.write(writerContent);
				writer.flush();
				
				if(l%1000==0)
				{
					 System.out.println(l);
				}
		}	
		writer.close();
	}
	// @Function
	public static String BddDataToPostgre(String uris) throws IOException, ParseException {
		BufferedReader textFile = new BufferedReader(new FileReader(uris));
	    String lineDta = "";
	    ArrayList<Coordinate> points = new ArrayList<Coordinate>();
	    ArrayList<Long> times = new ArrayList<Long>();

	    while ((lineDta = textFile.readLine()) != null){
	    	Coordinate p = new Coordinate();
	    	p.x = Double.parseDouble(lineDta.split(",")[2]);
	    	p.y = Double.parseDouble(lineDta.split(",")[3]);
	    	points.add(p);
	    	
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt2 = sdf.parse(lineDta.split(",")[1]);
	    	times.add(dt2.getTime());
	    }	
	    
	    long[] atime = new long[times.size()];
	    Coordinate[] coo = new Coordinate[times.size()];
	    for(int i =0;i< coo.length;i++)
	    {
	    	atime[i] = times.get(i);
	    	coo[i] = points.get(i);
	    }
	    MGeometryFactory geometryFactory = new MGeometryFactory();				
		mp = geometryFactory.createMPoint(coo,atime);
	    lineDta = mp.toGeoString();			
	  //  System.out.println(lineDta);
		return lineDta;
	}
}