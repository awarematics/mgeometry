package com.awarematics.postmedia.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
//import org.postgresql.pljava.annotation.Function;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;

import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;

public class BerlinmodToMPoint {
	static double[] coordinate1_x;
	static double[] coordinate1_y;
	static long[] timeArray;
	static MPoint mv = null;

	public static void main(String args[]) throws IOException,
			NumberFormatException, ParseException {
		ArrayList<String> result = BerlinmodToPostgre("D://trips", "mpoint");
		
		System.out.println(result.size());
		String writerContent = "";
		for (int i = 0; i < result.size(); i++) {
			Connection c = null;
		      Statement stmt = null;
		      try {
		         Class.forName("org.postgresql.Driver");
		         c = DriverManager
		            .getConnection("jdbc:postgresql://202.31.147.183:5432/BerlinMod_Benchmark",
		            "postgres", "mcalab3408");
		        // System.out.println("连接数据库成功！");
		         stmt = c.createStatement();
		         String sql = result.get(i);
		         stmt.executeUpdate(sql);
		         stmt.close();
		         c.close();
		         
		      } catch (Exception e) {
		         e.printStackTrace();
		         System.err.println(e.getClass().getName()+": "+e.getMessage());
		         System.exit(0);
		      }
		     // System.out.println("新表创建成功！");
	
			//writerContent = writerContent + result.get(i) + "\r\n";
		}

	

	}

	// @Function
	public static ArrayList<String> BerlinmodToPostgre(String uris, String type)
			throws IOException, NumberFormatException, ParseException {
		File file = new File(uris + ".csv");
		String lineDta = "";
		ArrayList<String> berlinmodString = new ArrayList<String>();
		BufferedReader textFile = new BufferedReader(new FileReader(file));

		int[] arraytrackid = new int[2969652];
		long[] arrayframeid = new long[2969652];
		long[] arraytimeid = new long[2969652];
		String[] endpoint = new String[2969652];
		String[] point = new String[2969652];
		long[] endtime = new long[2969652];

		int k = 0;

		while ((lineDta = textFile.readLine()) != null) {
			// System.out.println(k);
			if ( k >= 1) {

				arraytrackid[k - 1] = Integer.valueOf(getMpid(lineDta));
				// System.out.println(arraytrackid[k - 1]);

				arrayframeid[k - 1] = Long.parseLong(getSegid(lineDta));
				// System.out.println(arrayframeid[k - 1]);

				point[k - 1] = getPoint(lineDta).toString();
				// System.out.println(point[k - 1] + "\t" + k);
				endpoint[k - 1] = getePoint(lineDta).toString();

				arraytimeid[k - 1] = Long.parseLong(getTimes(lineDta));
				endtime[k - 1] = Long.parseLong(geteTimes(lineDta));
				k++;

			} else
				k++;
		}

		/*
		 * point to trajectory
		 */

		ArrayList<String> ArrayString = new ArrayList<String>();
		int j = 0;
		ArrayList<Long> stringframeid = new ArrayList<Long>();
		ArrayList<Long> stringtimes = new ArrayList<Long>();
		ArrayList<String> stringpoint = new ArrayList<String>();

		for (int i = 0; i < arraytrackid.length - 1; i++) {
			stringframeid.add(arrayframeid[i]);			
			stringtimes.add(arraytimeid[i]);

			stringpoint.add("(" + point[i] + ") " + arraytimeid[i]);

			if (arrayframeid[i] == arrayframeid[i + 1]) {
				j++;
			} else {
				if (i != arrayframeid.length - 2) {
					long[] subframeid = new long[j + 1];
					long[] subtimes = new long[j + 1];
					String[] subpoint = new String[j + 1];

					for (int l = 0; l < j + 1; l++) {
						subframeid[l] = l;
						subtimes[l] = stringtimes.get(i - j + l);
						subpoint[l] = stringpoint.get(i - j + l);
					}
					/*
					 * find the end point of the trajectory
					 */
					String endpoints = endpoint[i];
					long endtimes = endtime[i];

					String temp = "MPOINT (";
					for (int l = 0; l < j + 1; l++)
						temp += subpoint[l] + ", ";
					temp = temp.substring(0, temp.length() - 2);
					temp += ", (" + endpoints + ") " + endtimes + ")";
					//System.out.println(temp);
					temp = "insert into mpoint_120324 values("+arraytrackid[i] + ", " + arrayframeid[i]+", "+"st_makebox2d(st_point(ST_XMin(m_spatial('"+temp+"')::geometry),"
							 +"ST_YMin(m_spatial('"+temp+"')::geometry))"
							 +",st_point(ST_XMax(m_spatial('"+temp+"')::geometry)"
							 +", ST_YMax(m_spatial('"+temp+"')::geometry)"
							+" ))"
							 +", int8range(m_starttime('"+temp+"'), m_endtime('"+temp+"')), m_spatial('"+temp+"')::geometry, '"+ temp+"');";
					
					berlinmodString.add(temp);
					// System.out.println(temp);
					j = 0;
				}
			}
		}
		return berlinmodString;
	}

	private static String getTimes(String lineDta) throws ParseException {
		try {
			String res = "";
			String[] starttime = lineDta.split(",")[2].split(" ");
			if (starttime.length <= 1)
				res = lineDta.split(",")[2] + " 00:00:00.000";
			else if (lineDta.split(",")[2].length() == 19) {
				res = lineDta.split(",")[2] + ".000";
			} else if (lineDta.split(",")[2].length() == 16) {
				res = lineDta.split(",")[2] + ":00.000";
			} else {
				res = lineDta.split(",")[2];
			}

			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");

			Date dt2 = sdf.parse(res);

			// System.out.println(dt2.getTime());
			return String.valueOf(dt2.getTime());
		} catch (Exception e) {
		}
		return null;

	}

	private static String geteTimes(String lineDta) throws ParseException {
		try {
			String res = "";
			String[] starttime = lineDta.split(",")[3].split(" ");
			if (starttime.length <= 1)
				res = lineDta.split(",")[3] + " 00:00:00.000";
			else if (lineDta.split(",")[3].length() == 19) {
				res = lineDta.split(",")[3] + ".000";
			} else if (lineDta.split(",")[3].length() == 16) {
				res = lineDta.split(",")[3] + ":00.000";
			} else {
				res = lineDta.split(",")[3];
			}

			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");

			Date dt2 = sdf.parse(res);

			// System.out.println(dt2.getTime());
			return String.valueOf(dt2.getTime());
		} catch (Exception e) {
		}
		return null;

	}

	private static String getSegid(String lineDta) {
		String res = lineDta.split(",")[1];
		return res;

	}

	private static String getMpid(String lineDta) {
		String res = lineDta.split(",")[0];
		return res;

	}

	private static String getPoint(String lineDta) {
		String coox = lineDta.split(",")[4];
		String cooy = lineDta.split(",")[5];
		String coo = coox + " " + cooy;
		return coo;
	}

	private static String getePoint(String lineDta) {
		String coox = lineDta.split(",")[6];
		String cooy = lineDta.split(",")[7];
		String coo = coox + " " + cooy;
		return coo;
	}

}