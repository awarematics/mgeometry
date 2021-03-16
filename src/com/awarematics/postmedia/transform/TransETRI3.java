package com.awarematics.postmedia.transform;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//import org.postgresql.pljava.annotation.Function;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MPolygon;

public class TransETRI3 {

	static ArrayList<String> ArrayString = new ArrayList<String>();

	/*
	 * Generate PostGreSQL query
	 */
	public static void main(String args[]) throws IOException, NumberFormatException, ParseException {

		ArrayList<String> result = CSVToDB();
		String writerContent = "";
		for (int i = 1; i <= 8224; i++) {
			//writerContent = writerContent + "insert into track_etri_1207_1 values ("+i+",'mt" + String.format("%04d", i) + "') ;\r\n";
			writerContent = writerContent + "insert into track_etri_1207_1 values ("+result+") ;\r\n";//INSERT
		}
		File file = new File("D:\\track_etri_1207_1.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		writer.write(writerContent);
		writer.flush();
		writer.close();
	}
	// @Function
	/*
	 * CSVToDB
	 */
	
	public static ArrayList<String> CSVToDB() throws IOException, NumberFormatException, ParseException {
		File csv = new File("E:\\trips.csv");	 
		try{
		    BufferedReader textFile = new BufferedReader(new FileReader(csv));
		    String lineDta = "";
		    int[] arraytrackid =  new int[8224];
		    long[] arrayframeid =  new long[8224];
		    long[] arraytimeid =  new long[8224];
		    String[] point = new String[8224];
		    int k= 0;
		 
		    while ((lineDta = textFile.readLine()) != null){
		    	if(k==8224)
			    	break;
		    	if(k>0){
		    	
		 
		    	if(getTimes(lineDta) !=null ){
		    		arraytrackid[k-3] = Integer.valueOf(getTrackid(lineDta));
		    		//System.out.println(arraytrackid[k]);
		    	
		    		arrayframeid[k-3] = Long.parseLong(getFrameid(lineDta));
		    		//System.out.println(arrayframeid[k]);
		    	
		    		//point[k-3] =  getPoint(lineDta).toString();
			    	//System.out.println(lineDta);
			    	//System.out.println(point[k]+"\t"+k);
			    	
		    		arraytimeid[k-3] = Long.parseLong(getTimes(lineDta));
		    		String coox = lineDta.split(",")[4];
		    		String cooy = lineDta.split(",")[5];
		    		String temp = "";
		    		temp = "'"+ arraytrackid[k-3]+"','"+ arrayframeid[k-3] +"','"+ arraytimeid[k-3]+"','"+ coox +"','"+cooy+ "'";
		    		ArrayString.add(temp);
		    		k++;
		    		}
		    	}
		    	else{k++;}
		    }
		    //for(int i =0;i<30;i++)
			//	System.out.println(arraytrackid[i]+"\t"+arrayframeid[i]+"\t"+arraytimeid[i]+"\t"+point[i]);
		  
		    System.out.println( arraytrackid.length-1);

		    textFile.close();
		}catch (FileNotFoundException e){
		    System.out.println("Path is incorrect");
		}catch (IOException e){
		    System.out.println("Wrong reade files");
		}
		
		return ArrayString;
	}
	
	private static String getTimes(String lineDta) throws ParseException {
		try{String res="";
		int i = 10;
		if(lineDta.split(",")[3].length()>=i+1)
			res = lineDta.split(",")[3];
		else
			res = lineDta.split(",")[2];
		String str = res.substring(0,i) + " " + res.substring(i+1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date dt2 = sdf.parse(str);
		return String.valueOf(dt2.getTime());
		}catch(Exception e){}
		return null;
		
	}
	private static String getFrameid(String lineDta) {
		String res = lineDta.split(",")[1];
		return res;
		
	}
	private static String getTrackid(String lineDta) {
		String res = lineDta.split(",")[0];
		return res;
		
	}

	private static String getPoint(String lineDta) {
		String coox = lineDta.split(",")[4];
		String cooy = lineDta.split(",")[5];
		String coo = coox+" "+cooy;
		return coo;
	}
}
	