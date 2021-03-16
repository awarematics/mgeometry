package com.awarematics.postmedia.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

public class OutputJson {

	private static JSONArray jsonArray;
	private static JSONArray jsonArray2;

	public static String getresult(double[] x, double[] y,String[] time, int file) {
		System.out.println("visit"+x.length);
		jsonArray = new JSONArray();
		jsonArray2 = new JSONArray();
		JSONObject outData = new JSONObject();
		outData.put("mlinestring", jsonArray);		
		//double[] direction = new double[x.length]; 
		// straight direction in front of dashcam
		//direction = printDirection(x,y);
		// random direction 30% 30% 20% 20%
		//direction = printRandomDirection(x,y);
		//Polygon[] pol = new Polygon[x.length]; 
		LineString[] line = new LineString[x.length];
		
		for (int i = 1; i < x.length; i++) {
			System.out.println("???");
			JSONObject data = new JSONObject();
			data.put("time", time[i]);
			//data.put("x", x[i]);
			//data.put("y", y[i]);
			line[i] = createLine(x,y,i);
			System.out.println("rrrr");
			System.out.println(line[i]+"\t\tyy");
			data.put("linestring", line[i]);
			jsonArray.put(data);	
			
		}
		JSONObject data2 = new JSONObject();
		data2.put("id", 00001);
		jsonArray2.put(data2);
		outData.put("id", jsonArray2);
		return outData.toString();
	}

	public static void saveDataToFile(String fileName, String data) {
		BufferedWriter writer = null;
		File file = new File("d:\\mfs\\mlinestring"  +"1" + ".json");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// print direction-------------
	public static double[] printDirection(double[] x, double[] y) {
		double[] k = new double[x.length];
		double start_x = x[0];
		double start_y = y[0];
		
		double[] result = new double[x.length];
		/*
		 * k[0]  start point maybe is a stop point for several seconds
		 */
		int num =0;
		for(int i=1;i<x.length;i++){

			if((x[i]!=start_x||y[i]!=start_y)&& num==0)
			{
				k[0] =  Math.asin(y[i] -start_y)/Math.sqrt(((x[i] -start_x) * (x[i] -start_x)) + (y[i] - start_y) * (y[i] - start_y)); num=1;;
			}
			result[0] = Double.valueOf((k[0]* 180 / Math.PI));
		}
		for (int i = 1; i < x.length; i++) {
			if( Math.sqrt(((x[i] -x[i-1]) * (x[i] - x[i-1])) + (y[i] - y[i-1]) * (y[i] - y[i-1]))!=0){
				k[i] = Math.asin(y[i] - y[i-1])/ Math.sqrt(((x[i] -x[i-1]) * (x[i] - x[i-1])) + (y[i] - y[i-1]) * (y[i] - y[i-1]));
			}
			else
			{
				k[i]=k[i-1];
			}
			//DecimalFormat df = new DecimalFormat("0.000000000");df.format
			result[i] = Double.valueOf((k[i]* 180 / Math.PI));
		}
		return result;
	}
	
	public static double[] printRandomDirection(double[] x, double[] y) {
		double[] k = new double[x.length];
		double start_x = x[0];
		double start_y = y[0];
		
		double[] result = new double[x.length];
		/*
		 * k[0]  start point maybe is a stop point for several seconds
		 */
		int num =0;
		for(int i=1;i<x.length;i++){

			if((x[i]!=start_x||y[i]!=start_y)&& num==0)
			{
				k[0] =  Math.asin(y[i] -start_y)/Math.sqrt(((x[i] -start_x) * (x[i] -start_x)) + (y[i] - start_y) * (y[i] - start_y)); num=1;;
			}
			result[0] = Double.valueOf((k[0]* 180 / Math.PI));
		}
		for (int i = 1; i < x.length; i++) {
			if( Math.sqrt(((x[i] -x[i-1]) * (x[i] - x[i-1])) + (y[i] - y[i-1]) * (y[i] - y[i-1]))!=0){
				k[i] = Math.asin(y[i] - y[i-1])/ Math.sqrt(((x[i] -x[i-1]) * (x[i] - x[i-1])) + (y[i] - y[i-1]) * (y[i] - y[i-1]));
			}
			else
			{
				k[i]=k[i-1];
			}
			/*
			 * random direction 30% 30% 20% 20%
			 */
			if( i >= x.length*0.2 && i < x.length*0.4 )
				result[i] = Double.valueOf((k[i]* 180 / Math.PI))+90;
			else if( i >= x.length*0.4 && i < x.length*0.7 )
				result[i] = Double.valueOf((k[i]* 180 / Math.PI))+180;
			else if( i >= x.length*0.7 )
				result[i] = Double.valueOf((k[i]* 180 / Math.PI))+270;
			//System.out.println(result[i]);
		}
		return result;
	}
	@SuppressWarnings("unused")
	private static Polygon createPolygon(double d, double e) {
		Coordinate[] coo = new Coordinate[5];
		coo[0] = new Coordinate();
		coo[0].x = d - 0.000001;
		coo[0].y = e - 0.000003;
		
		coo[1] = new Coordinate();
		coo[1].x = d + 0.000001;
		coo[1].y = e - 0.000003;
		
		coo[2] = new Coordinate();
		coo[2].x = d + 0.000001;
		coo[2].y = e + 0.000003;
		
		coo[3] = new Coordinate();
		coo[3].x = d - 0.000001;
		coo[3].y = e + 0.000003;
		
		coo[4] = new Coordinate();
		coo[4].x = d - 0.000001;
		coo[4].y = e - 0.000003;
		
		GeometryFactory g = new GeometryFactory();
		Polygon p= g.createPolygon(coo);
		return p;
	}
	public static String getresult(double[] x, String name, String[] time, int file) {
		jsonArray = new JSONArray();
		jsonArray2 = new JSONArray();
		JSONObject outData = new JSONObject();
		outData.put("mdouble", jsonArray);
			
		for (int i = 0; i < x.length; i++) {
			JSONObject data = new JSONObject();			
			data.put("time", time[i]);
			data.put(name, x[i]);			
			jsonArray.put(data);		
		}		
		
		JSONObject data2 = new JSONObject();
		data2.put("id", 00002);
		jsonArray2.put(data2);
		outData.put("id", jsonArray2);
		
		return outData.toString();
	}
	public static LineString createLine(double[] x, double[] y, int i) {
		Coordinate[]  coo = new Coordinate[i+1];
		System.out.println(i);
		for(int j =0;j<i+1;j++)
		{
			coo[j] = new Coordinate();
			coo[j].x = x[j];
			coo[j].y = y[j];
			System.out.println(coo[j].x);
		}
		if(i==0) return null;
		else
		{
		GeometryFactory g = new GeometryFactory();
		LineString line = g.createLineString(coo);
		//System.out.println("lene"+line);
		return line;}
	}
}
