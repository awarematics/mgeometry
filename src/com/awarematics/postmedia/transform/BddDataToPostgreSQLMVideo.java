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
import com.awarematics.postmedia.types.mediamodel.Frame;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MVideo;

public class BddDataToPostgreSQLMVideo {
	static double[] coordinate1_x;
	static double[] coordinate1_y;
	static long[] timeArray;
	static MVideo mv = null;
	// columnName, 'MVideo((uri , startTime, mpoint, frames, fov),(...))'
	/*
	 * 
CREATE TYPE mvideo AS
(
   moid oid,
   uri text,
   annotations json[],
   mgeo mpoint, //alternative mpoint3d
   startTime timestamp without time zone[],
   frames frame[],
   afov fov, // Initial or static FoV.              
);
	 */
	public static void main(String args[]) throws IOException {
		String[] result = BddDataToPostgre("D://train/", "mvideo");
		String writerContent = "";
		//System.out.println(result.length);
		for (int i = 0; i < result.length; i++) {
			writerContent = writerContent + " UPDATE uservideos \r\n SET mv  = append(mv , '" + result[i]
					+ "')\r\n" + " WHERE id = " + (i + 1) + ";\r\n\n\n";
		}
		File file = new File("D:\\append_mvideo.txt");
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
		for (int numof = 1; numof <= 100; numof++) {
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
				String videoName = "http://data.u-gis.net/"+ name1+"-"+name2+".mp4";
				//MVIDEO (uri, MPOINT ((0.0 0.0) 1481480632123, (2.0 5.0) 1481480635123, (34.0 333.0) 1481480638000), FRAME ((1 1 1 1 1), (1 1 1 1 2), (1 1 1 1 3))
				int num = getJsonArray.length();
				coordinate1_x = new double[num];
				coordinate1_y = new double[num];
				Coordinate[] coordinate1 = new Coordinate[num];
				timeArray = new long[num];			
				double[] direction2d = new double[num];
				Frame[] frame = new Frame[num];
				
				for (int j = 0; j < num; j++) {
					String[] array = getJsonArray.get(j).toString().split(":");
					String result_x = array[3].split(",")[0];
					String result_y = array[6].split(",")[0];
					String time = array[5].split(",")[0];
					coordinate1_x[j] = Double.valueOf(result_x);
					coordinate1_y[j] = Double.valueOf(result_y);
					timeArray[j] = Long.parseLong(time);
					coordinate1[j] = new Coordinate();
					coordinate1[j].y = coordinate1_x[j];
					coordinate1[j].x = coordinate1_y[j];			
				}
				direction2d = printDirection(coordinate1_x,coordinate1_y);
				
				for (int j = 0; j < num; j++) {					
					FoV f = new FoV();
					f.setDirection2d(direction2d[j]);
					f.setDistance(0.001);
					f.setHorizontalAngle(60);
					frame[j] = new Frame();
					frame[j].setCoos(coordinate1[j]);
					frame[j].setFov(f);
					if(j !=0)
						frame[j].setRelativeTime(timeArray[j]-timeArray[j-1]);
					else
						frame[j].setRelativeTime(0);
				}
				FoV fov = new FoV();
				fov.setDirection2d(direction2d[0]);
				fov.setDistance(0.001);
				fov.setHorizontalAngle(60);
				
				MGeometryFactory geometryFactory = new MGeometryFactory();				
				MPoint mp = geometryFactory.createMPoint(coordinate1,timeArray);
				mv = geometryFactory.createMVideo(videoName, mp, null, timeArray , fov, frame);
				if (mv != null) {
					bddString.add(mv.toGeoString());
				}
			} catch (Exception e) {
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
				k[0] =  Math.asin((y[i] -start_y)/Math.sqrt(((x[i] -start_x) * (x[i] -start_x)) + (y[i] - start_y) * (y[i] - start_y))); num=1;
				result[0] = Double.valueOf((k[0]* 180 / Math.PI));
			}
		}
		for (int i = 1; i < x.length; i++) {
			if( Math.sqrt(((x[i] -x[i-1]) * (x[i] - x[i-1])) + (y[i] - y[i-1]) * (y[i] - y[i-1]))!=0){
				k[i] = Math.asin((y[i] - y[i-1])/ Math.sqrt(((x[i] -x[i-1]) * (x[i] - x[i-1])) + (y[i] - y[i-1]) * (y[i] - y[i-1])));
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