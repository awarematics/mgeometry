package com.awarematics.postmedia.transform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import org.postgresql.pljava.annotation.Function;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MDouble;

public class BddDataToPostgreSQLMDouble {

	public static void main(String args[]) throws IOException {
		String[] result = BddDataToPostgre("D://train/", "mdouble_gyroz");
		String writerContent = "";
		for (int i = 0; i < result.length; i++) {
			writerContent = writerContent + " UPDATE carsensors \r\n SET gyroz  = append(gyroz , '" + result[i]
					+ "')\r\n" + " WHERE id = " + (i + 1) + ";\r\n\n\n";
		}
		File file = new File("D:\\gyroz.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		writer.write(writerContent);
		writer.flush();
		writer.close();

	}
	/*
	 * type: mdouble_accx mdouble_accy mdouble_accz mdouble_gyrox mdouble_gyroy
	 * mdouble_gyroz
	 */
	// @Function
	public static String[] BddDataToPostgre(String uri, String type) throws IOException {
		String[] bdd = null;
		ArrayList<String> bddString = new ArrayList<String>();
		for (int numof = 1; numof <= 100; numof++) {
			long[] timeArray;
			double[] doubleaccx;
			double[] doubleaccy;
			double[] doubleaccz;
			double[] doublegyrox;
			double[] doublegyroy;
			double[] doublegyroz;
			MDouble mb = null;
			try {
				File file = new File(uri + "/1 (" + numof + ").json");
				String content = FileUtils.readFileToString(file, "UTF-8");
				JSONObject jsonObject = new JSONObject(content);
				if (type == "mdouble_accx") {
					// JSONArray getJsonArray2 = jsonObject.getJSONArray("gps");
					JSONArray getJsonArray = jsonObject.getJSONArray("accelerometer"); // gyro
																						// accelerometer
					int num = getJsonArray.length();
					if (num > 0) {
						doubleaccy = new double[num];
						timeArray = new long[num];

						for (int j = 0; j < num; j++) {
							String[] array = getJsonArray.get(j).toString().split(":");
							String result_x = array[1].split(",")[0];// 2 //3
							String time = array[4].split("\\}")[0];
							doubleaccy[j] = Double.valueOf(result_x);
							timeArray[j] = Long.parseLong(time);
						}
						MGeometryFactory geometryFactory = new MGeometryFactory();
						mb = geometryFactory.createMDouble(doubleaccy, timeArray);
						if (mb != null) {
							bddString.add(mb.toGeoString());
						}
					} else
						continue;
				} else if (type == "mdouble_accy") {
					// JSONArray getJsonArray2 = jsonObject.getJSONArray("gps");
					JSONArray getJsonArray = jsonObject.getJSONArray("accelerometer"); // gyro
																						// accelerometer
					int num = getJsonArray.length();
					if (num > 0) {
						doubleaccx = new double[num];
						timeArray = new long[num];

						for (int j = 0; j < num; j++) {
							String[] array = getJsonArray.get(j).toString().split(":");
							String result_x = array[2].split(",")[0];// 2 //3
							String time = array[4].split("\\}")[0];
							doubleaccx[j] = Double.valueOf(result_x);
							timeArray[j] = Long.parseLong(time);
						}
						MGeometryFactory geometryFactory = new MGeometryFactory();
						mb = geometryFactory.createMDouble(doubleaccx, timeArray);
						if (mb != null) {
							bddString.add(mb.toGeoString());
						}
					} else
						continue;
				}
				else if (type == "mdouble_accz") {
					// JSONArray getJsonArray2 = jsonObject.getJSONArray("gps");
					JSONArray getJsonArray = jsonObject.getJSONArray("accelerometer"); // gyro
																						// accelerometer
					int num = getJsonArray.length();
					if (num > 0) {
						doubleaccz = new double[num];
						timeArray = new long[num];

						for (int j = 0; j < num; j++) {
							String[] array = getJsonArray.get(j).toString().split(":");
							String result_x = array[3].split(",")[0];// 2 //3
							String time = array[4].split("\\}")[0];
							doubleaccz[j] = Double.valueOf(result_x);
							timeArray[j] = Long.parseLong(time);
						}
						MGeometryFactory geometryFactory = new MGeometryFactory();
						mb = geometryFactory.createMDouble(doubleaccz, timeArray);
						if (mb != null) {
							bddString.add(mb.toGeoString());
						}
					} else
						continue;
				} else if (type == "mdouble_gyrox") {
					// JSONArray getJsonArray2 = jsonObject.getJSONArray("gps");
					JSONArray getJsonArray = jsonObject.getJSONArray("gyro"); // gyro
																				// accelerometer
					int num = getJsonArray.length();
					if (num > 0) {
						doublegyrox = new double[num];
						timeArray = new long[num];

						for (int j = 0; j < num; j++) {
							String[] array = getJsonArray.get(j).toString().split(":");
							String result_x = array[1].split(",")[0];// 2 //3
							String time = array[4].split("\\}")[0];
							doublegyrox[j] = Double.valueOf(result_x);
							timeArray[j] = Long.parseLong(time);
						}
						MGeometryFactory geometryFactory = new MGeometryFactory();
						mb = geometryFactory.createMDouble(doublegyrox, timeArray);
						if (mb != null) {
							bddString.add(mb.toGeoString());
						}
					} else
						continue;
				} else if (type == "mdouble_gyroy") {
					// JSONArray getJsonArray2 = jsonObject.getJSONArray("gps");
					JSONArray getJsonArray = jsonObject.getJSONArray("gyro"); // gyro
																				// accelerometer
					int num = getJsonArray.length();
					if (num > 0) {
						doublegyroy = new double[num];
						timeArray = new long[num];

						for (int j = 0; j < num; j++) {
							String[] array = getJsonArray.get(j).toString().split(":");
							String result_x = array[2].split(",")[0];// 2 //3
							String time = array[4].split("\\}")[0];
							doublegyroy[j] = Double.valueOf(result_x);
							timeArray[j] = Long.parseLong(time);
						}
						MGeometryFactory geometryFactory = new MGeometryFactory();
						mb = geometryFactory.createMDouble(doublegyroy, timeArray);
						if (mb != null) {
							bddString.add(mb.toGeoString());
						}
					} else
						continue;
				} else if (type == "mdouble_gyroz") {
					// JSONArray getJsonArray2 = jsonObject.getJSONArray("gps");
					JSONArray getJsonArray = jsonObject.getJSONArray("gyro"); // gyro
																				// accelerometer
					int num = getJsonArray.length();
					if (num > 0) {
						doublegyroz = new double[num];
						timeArray = new long[num];

						for (int j = 0; j < num; j++) {
							String[] array = getJsonArray.get(j).toString().split(":");
							String result_x = array[3].split(",")[0];// 2 //3
							String time = array[4].split("\\}")[0];
							doublegyroz[j] = Double.valueOf(result_x);
							timeArray[j] = Long.parseLong(time);
						}
						MGeometryFactory geometryFactory = new MGeometryFactory();
						mb = geometryFactory.createMDouble(doublegyroz, timeArray);
						if (mb != null) {
							bddString.add(mb.toGeoString());
						}
					} else
						continue;
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
}