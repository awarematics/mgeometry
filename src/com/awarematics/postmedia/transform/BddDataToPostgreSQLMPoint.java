package com.awarematics.postmedia.transform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//import org.postgresql.pljava.annotation.Function;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;

import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;

public class BddDataToPostgreSQLMPoint {
	static double[] coordinate1_x;
	static double[] coordinate1_y;
	static long[] timeArray;
	static MPoint mv = null;
	// columnName, 'MVideo((uri , startTime, mpoint, frames, fov),(...))'
	public static void main(String args[]) throws IOException {
		//ArrayList<MPoint> result = BddDataToPostgre("D://val/", "mpoint");
		String javasource="";
		//System.out.println(result.size());
		
		
		for(int i =0;i<=1998;i++)
		{
			javasource = javasource+"lineStringDraw(coordinate"+i+");\r\n";
		}
		/*for (int i = 0; i < result.size(); i++) {
			
			String writerContent = " var coordinate" + i + " = ";
			Coordinate[] temp = result.get(i).getCoords();
			writerContent = writerContent + Arrays.toString(temp)+"\r\n";
			writerContent = writerContent.replaceAll(", NaN", "");
			writerContent = writerContent.replaceAll("\\(", "\\[");
			writerContent = writerContent.replaceAll("\\)", "\\]");
			
			javasource += writerContent;
			
			/*
				writerContent = writerContent + " UPDATE usertrajs \r\n SET mt  = append(mt , '" + result[i]
						+ "')\r\n" + " WHERE id = " + (i + 1) + ";\r\n";
				*///}
		
		File file = new File("E:\\mpoint.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter writer = new FileWriter(file);
				writer.write(javasource);
				writer.flush();
				writer.close();
		
	}
	// @Function
	public static ArrayList<MPoint> BddDataToPostgre(String uris, String type) throws IOException {
		String[] bdd = null;
		ArrayList<MPoint> bddString = new ArrayList<MPoint>();
		for (int numof = 1; numof <= 10000; numof++) {
			long[] timeArray;
			try {
				File file = new File(uris + "/1 (" + numof + ").json");
				String content = FileUtils.readFileToString(file, "UTF-8");
				JSONObject jsonObject = new JSONObject(content);
				JSONArray getJsonArray = jsonObject.getJSONArray("locations"); // locations
				
				int num = getJsonArray.length();
				coordinate1_x = new double[num];
				coordinate1_y = new double[num];
				Coordinate[] coordinate1 = new Coordinate[num];
				timeArray = new long[num];
				
				for (int j = 0; j < num; j++) {
					
					String[] array = getJsonArray.get(j).toString().split(":");
					
					
				
					String result_x = array[1].split(",")[0];
					String result_y = array[6].split(",")[0].replace("}", "");
					//System.out.println(result_y);
					String time = array[5].split(",")[0];
					coordinate1_x[j] = Double.valueOf(result_x);
					coordinate1_y[j] = Double.valueOf(result_y);
					timeArray[j] = Long.parseLong(time);
					coordinate1[j] = new Coordinate();
					coordinate1[j].y = coordinate1_x[j];
					coordinate1[j].x = coordinate1_y[j];
				}
				MGeometryFactory geometryFactory = new MGeometryFactory();				
				mv = geometryFactory.createMPoint(coordinate1,timeArray);
				if (mv.numOf() != 0) {
					bddString.add(mv);
				}
			} catch (Exception e) {
				continue;
			}
		}
		return bddString;
	}
}