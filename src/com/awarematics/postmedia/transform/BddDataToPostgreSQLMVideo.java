package com.awarematics.postmedia.transform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import org.postgresql.pljava.annotation.Function;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.FoV;
import com.awarematics.postmedia.types.mediamodel.MVideo;

public class BddDataToPostgreSQLMVideo {
	static double[] coordinate1_x;
	static double[] coordinate1_y;
	static long[] timeArray;
	static MVideo mv = null;
	
	public static void main(String args[]) throws IOException {
		String[] result = BddDataToPostgre("D://train/", "mvideo");
		String writerContent = "";
		//System.out.println(result.length);
		for (int i = 0; i < result.length; i++) {
			writerContent = writerContent + " UPDATE uservideos \r\n SET mv  = append(mv , '" + result[i]
					+ "')\r\n" + " WHERE id = " + (i + 1) + ";\r\n\n\n";
		}
		File file = new File("D:\\mvideo.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		writer.write(writerContent);
		writer.flush();
		writer.close();
	}

	// @Function
	public static String[] BddDataToPostgre(String uris, String type) throws IOException {
		String[] bdd = null;
		ArrayList<String> bddString = new ArrayList<String>();
		for (int numof = 1; numof <= 6000; numof++) {
			long[] timeArray;
			try {
				File file = new File(uris + "/1 (" + numof + ").json");
				String content = FileUtils.readFileToString(file, "UTF-8");
				JSONObject jsonObject = new JSONObject(content);
				JSONArray getJsonArray = jsonObject.getJSONArray("gps");
				
				String[] rideid = content.split("rideID");
				String name1= rideid[1].split("\",")[0].replaceAll("\"\\: \"", "").substring(0,8);
				String[] filename = content.split("filename");
				String name2= filename[1].split("\",")[0].replaceAll("\"\\: \"", "").substring(0,8);
				String videoName = name1+"-"+name2+".mov";
			
				//String videoName ="test";
				int num = getJsonArray.length();
				coordinate1_x = new double[num];
				coordinate1_y = new double[num];
				Coordinate[] coordinate1 = new Coordinate[num];
				timeArray = new long[num];
				
				String[] uri = new String[num];
				double[] viewAngle = new double[num];
				double[] verticalAngle = new double[num];
				double[] distance = new double[num];
				double[] direction = new double[num];
				double[] direction3d = new double[num];
				double[] altitude = new double[num];
				String[] annotationJson = new String[num];
				String[] exifJson = new String[num];
				Polygon[] listPolygon = new Polygon[num];
				FoV[] fov = new FoV[num];
				
				for (int j = 0; j < num; j++) {
					String[] array = getJsonArray.get(j).toString().split(":");
					String result_x = array[3].split(",")[0];
					String result_y = array[6].split(",")[0];
					String time = array[5].split(",")[0];
					coordinate1_x[j] = Double.valueOf(result_x);
					coordinate1_y[j] = Double.valueOf(result_y);
					timeArray[j] = Long.parseLong(time);
					coordinate1[j] = new Coordinate();
					coordinate1[j].x = coordinate1_x[j];
					coordinate1[j].y = coordinate1_y[j];
					
					
					uri[j] = videoName+"?t="+(j+1);
					viewAngle[j] = 60;
					distance[j] = 0.001;//10m
					verticalAngle[j] = -1;
					direction3d[j] = -1;
					altitude[j] = -1;
					annotationJson[j] = "null";
					exifJson[j] = "null";
					listPolygon[j] = null;
					fov[j] =null;
				}
				direction = printDirection(coordinate1_x,coordinate1_y);
				MGeometryFactory geometryFactory = new MGeometryFactory();
				//uri,viewAngle,verticalAngle, distance, direction,direction3d, altitude,annotationJson,exifJson,coords,creationTime
				mv = geometryFactory.createMVideo(uri,viewAngle,verticalAngle, distance, direction,direction3d, altitude,annotationJson,exifJson,coordinate1,timeArray, listPolygon, fov);
				//System.out.println(mv.toGeoString());
				if (mv != null) {
					bddString.add(mv.toGeoString());
				}
			} catch (Exception e) {
			//	System.out.println(numof);
				continue;
			}
		}
		bdd = new String[bddString.size()];
		for (int i = 0; i < bdd.length; i++)
			bdd[i] = bddString.get(i);
		return bdd;

	}

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
				k[0] =  Math.asin(y[i] -start_y)/Math.sqrt(((x[i] -start_x) * (x[i] -start_x)) + (y[i] - start_y) * (y[i] - start_y)); num=1;
				result[0] = Double.valueOf((k[0]* 180 / Math.PI));
			}
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
	
}