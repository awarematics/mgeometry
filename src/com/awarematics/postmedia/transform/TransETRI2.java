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

public class TransETRI2 {

	static ArrayList<String> ArrayString = new ArrayList<String>();

	/*
	 * Generate PostGreSQL query
	 */
	public static void main(String args[]) throws IOException, NumberFormatException, ParseException {

		ArrayList<String> result = CSVToDB();
		String writerContent = "";
		for (int i = 0; i < result.size(); i++) {
			writerContent = writerContent + "UPDATE track_ SET mt = append(mt, '" + result.get(i) + "') WHERE id = " +(i+1)+ ";\r\n";
		}
		File file = new File("D:\\TrackMPoint.txt");
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
		    int[] arraytrackid =  new int[3000000];
		    long[] arrayframeid =  new long[3000000];
		    long[] arraytimeid =  new long[3000000];
		    String[] point = new String[3000000];
		    int k= 0;
		 
		    while ((lineDta = textFile.readLine()) != null){
		    	if(k==3000000)
			    	break;
		    	if(k>2){
		    	
		 
		    	if(getTimes(lineDta) !=null ){
		    		arraytrackid[k-3] = Integer.valueOf(getTrackid(lineDta));
		    		//System.out.println(arraytrackid[k]);
		    	
		    		arrayframeid[k-3] = Long.parseLong(getFrameid(lineDta));
		    		//System.out.println(arrayframeid[k]);
		    	
		    		point[k-3] =  getPoint(lineDta).toString();
			    	//System.out.println(lineDta);
			    	//System.out.println(point[k]+"\t"+k);
			    	
		    		arraytimeid[k-3] = Long.parseLong(getTimes(lineDta));
		    		k++;
		    		}
		    	}
		    	else{k++;}
		    }
		    //for(int i =0;i<30;i++)
			//	System.out.println(arraytrackid[i]+"\t"+arrayframeid[i]+"\t"+arraytimeid[i]+"\t"+point[i]);
		  
		    System.out.println( arraytrackid.length-1);
			int j = 0;
	    	ArrayList<Long> stringframeid = new ArrayList<Long>();
	    	ArrayList<Long> stringtimes = new ArrayList<Long>();
	    	ArrayList<String> stringpoint = new ArrayList<String>();
	    	
		    for(int i =0; i< arraytrackid.length-1; i++)
		    {
	    		stringframeid.add(arrayframeid[i]);
	    		//System.out.println(arrayframeid[i]);
	    		stringtimes.add(arraytimeid[i]);
	    		stringpoint.add("("+point[i]+") "+ arraytimeid[i]);
	    		//System.out.println(stringpoint);
		    	if(arraytrackid[i] == arraytrackid[i+1])
		    	{
		    		j++;
		    	}
		    	
		    	else if(i != arraytrackid.length-2){
		    		System.out.println(j+"\t sub");
		    		long[] subframeid = new long[j+1];
	    			long[] subtimes = new long[j+1];
	    			String[] subpoint = new String[j+1];
	    			
	    	    	
		    		for(int l=0;l<j+1;l++)
		    		{
		    			subframeid[l] =  l;    	
		    			subtimes[l] = stringtimes.get(i-j+l);
		    			subpoint[l] = stringpoint.get(i-j+l); 	    	
		    		}
		    		
		    		
		    		String temp= "MPOINT (";
		    		for(int l=0;l<j+1;l++)
		    			temp+= subpoint[l]+", ";
		    		temp = temp.substring(0, temp.length()-2);
		    		temp += "))";
		    		ArrayString.add(temp);
		    		j=0;
		    	}
		    }
		
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	