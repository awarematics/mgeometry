package com.awarematics.postmedia.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;

//import org.postgresql.pljava.annotation.Function;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MPolygon;

public class TransETRI {

	static ArrayList<String> ArrayString = new ArrayList<String>();

	/*
	 * Generate PostGreSQL query
	 */
	public static void main(String args[]) throws IOException {

		ArrayList<String> result = CSVToDB();
		
		 System.out.println("OK");
			String writerContent = "";
		for (int i = 1; i <= result.size(); i++) {
		
			//writerContent = writerContent + "insert into track_etri_1207_1 values ("+i+",'mt" + String.format("%04d", i) + "') ;\r\n";
			writerContent = writerContent + "insert into track_etri_1207_2 values ("+result.get(i-1)+") ;\r\n";//INSERT
		}
		File file = new File("D:\\track_etri_1207_2.txt");
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
	
	public static ArrayList<String> CSVToDB() throws IOException {
		File csv = new File("C:\\Users\\Ding\\Downloads\\track3.csv");	 
		try{
		    BufferedReader textFile = new BufferedReader(new FileReader(csv));
		    String lineDta = "";
		    int[] arraytrackid =  new int[29407];
		    long[] arrayframeid =  new long[29407];
		    long[] arraytimeid =  new long[29407];
		    String[] arrayclassid = new String[29407];
		    Coordinate[] point = new Coordinate[29407];
		    String[] arrayvideoid = new String[29407];
		    int k= 0;
		    while ((lineDta = textFile.readLine()) != null){
		    	if(k>0){
		    	point[k-1] = new Coordinate();
		    	point[k-1] = getPoint(lineDta);
		 
		    	//getPolygon(lineDta);
		    	arraytrackid[k-1] = Integer.valueOf(getTrackid(lineDta));
		    	//System.out.println(arraytrackid[k]);
		    	
		    	arrayframeid[k-1] = Long.parseLong(getFrameid(lineDta));
		    	//System.out.println(arrayframeid[k]);
		    	
		    	arraytimeid[k-1] = Long.parseLong(getTimes(lineDta));
		    	//System.out.println(arraytimeid[k]);
		    	
		    	arrayclassid[k-1] = getClassid(lineDta);
		    	
		    	arrayvideoid[k-1] = getVideoUriid(lineDta);
		    	k++;
		    	}
		    	//System.out.println(arrayvideoid[k]);
		    	else k++;
		    }
		    
		    System.out.println("OK");
		    quickSort(arraytrackid, arrayframeid, arraytimeid,arrayclassid, point, arrayvideoid);
//for(int i =0;i<10;i++)
//	System.out.println(arraytrackid[i]);
		  
			int j = 0;

		    String stringvideoid="";
		    String stringclassid="";
	    	int stringtrackid = 0;
	    	ArrayList<Long> stringframeid = new ArrayList<Long>();
	    	ArrayList<Long> stringtimes = new ArrayList<Long>();
	    	ArrayList<String> stringpoint = new ArrayList<String>();
	    	
		    for(int i =0; i< arraytrackid.length-1; i++)
		    {
		    	stringtrackid = arraytrackid[i];
	    		stringframeid.add(arrayframeid[i]);
	    		stringtimes.add(arraytimeid[i]);
	    		stringvideoid = arrayvideoid[i];
	    		stringpoint.add("("+point[i].x+" "+point[i].y+") "+ arraytimeid[i]);
	    		stringclassid = arrayclassid[i];
	    
		    	if(arraytrackid[i] == arraytrackid[i+1])
		    	{
		    		j++;
		    	}

		    	else if(i != arraytrackid.length-2){
		    		long[] subframeid = new long[j+1];
	    			long[] subtimes = new long[j+1];
	    			String[] subpoint = new String[j+1];
	    			
	    	    	
		    		for(int l=0;l<j+1;l++)
		    		{
		    			subframeid[l] =  stringframeid.get(i-j+l);    	
		    			subtimes[l] = stringtimes.get(i-j+l);
		    			subpoint[l] = stringpoint.get(i-j+l); 	    	
		    		}
		    		
		    		quickSort(subtimes, subframeid, subpoint, 0, subtimes.length-1);
		    		
		    		String temp= "'"+stringtrackid+"','{";
		    		for(int l=0;l<j+1;l++)
		    			temp+= subframeid[l]+",";
		    		temp = temp.substring(0, temp.length()-1);
		    		temp += "}','{";
		    		for(int l=0;l<j+1;l++)
		    			temp+= subtimes[l]*1000+",";
		    		temp = temp.substring(0, temp.length()-1);
		    		
		    		
		    		temp = temp +"}','"+ stringclassid+"'";
		    		
		    		
		    		temp += ",append_mpoint('MPOINT (";
		    		for(int l=0;l<j+1;l++)
		    			temp+= subpoint[l]+", ";
		    		temp = temp.substring(0, temp.length()-2);
		    		temp += ")'), null, null,null,'";
		    		temp = temp + stringvideoid+"'";
		    		ArrayString.add(temp);
		    		//System.out.println(temp);
		    		j=0;
		    	}
		    	else{
		    		//ArrayString.add(  "'"+arraytrackid[i]+"','"+arrayframeid[i]+"','"+ arraytimeid[i]  +"','"+ "MPOINT ("+point[i].x+" "+point[i].y+") "+ arraytimeid[i] +")','"+ stringvideoid  +"'");
		    		ArrayString.add(  "'"+arraytrackid[i]+"','"+ arrayframeid[i]+"','"+ arraytimeid[i]*1000 +"','"+ arrayclassid[i]  +"',append_mpoint('"+ "'MPOINT (("+point[i].x+" "+point[i].y+") "+ arraytimeid[i] +")'),null, null,null,'"+ stringvideoid  +"'");
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
	//8+10+10
		//frame_id,timestamp_ms,track_id,object_class,x,y,real_x,real_y,
		//vx,vy,real_vx,real_vy,psi_rad,real_psi_rad,length,width,real_length,real_width,
		//3d_box_height,video_x1,video_y1,video_x2,video_y2,video_x3,video_y3,video_x4,video_y4,video_uri_id
		//0,0,3,Car,498.96,765.0,127.37889009727215,36.38567021912014,
		//34.51,-3.14,2.88,0.26,-0.01,0.01,63.03,29.1,5.27,2.43,
		//46.01,795.08,225.28,829.3,276.83,949.94,275.89,900.79,225.29,SC0004604_카메라(4번)_20200619_160000(한국표준과학연구원방면)_2분
	private static String getClassid(String lineDta) {
		String res = lineDta.split(",")[3];
		return res;
	}
	private static String getVideoUriid(String lineDta) {
		//System.out.println(lineDta);
		String res = lineDta.split(",")[12];
		//System.out.println(res);
		return res;
		
	}
	private static String getTimes(String lineDta) {
		String res = lineDta.split(",")[2];
		return res;
		
	}
	private static String getFrameid(String lineDta) {
		String res = lineDta.split(",")[1];
		return res;
		
	}
	private static String getTrackid(String lineDta) {
		String res = lineDta.split(",")[0];
		return res;
		
	}
	private static String getPolygon(String lineDta) {
		return null;		
	}
	private static Coordinate getPoint(String lineDta) {
		// System.out.println(lineDta);
		String coox = lineDta.split(",")[4];
		String cooy = lineDta.split(",")[5];
		Coordinate coo = new Coordinate();
		coo.x = Double.valueOf(coox);
		coo.y = Double.valueOf(cooy);
		return coo;
	}
	
	public static void sort(int a[], long[] arrayframeid, long[] arraytimeid, String[] arrayclassid, Coordinate[] point, String[] arrayvideoid, int low, int hight) {
        int i, j, index;
        if (low >= hight) {
            return;
        }
        i = low;
        j = hight;
        index = a[j];
        while (i < j) { 
            
            
            while (i < j && a[i] <= index)
            {i++;}
        
            if(i < j){
            	 int temp = a[j];
                 a[j] = a[i];
                 a[i] = temp;
                 
                 long tempframe = arrayframeid[j];
                 arrayframeid[j] = arrayframeid[i];
                 arrayframeid[i] = tempframe;
                 
                 long tempt = arraytimeid[j];
                 arraytimeid[j] = arraytimeid[i];
                 arraytimeid[i] = tempt;
                 
                 Coordinate coo = new Coordinate();
                 coo.x = point[j].x;
                 coo.y = point[j].y;
                 point[j] = point[i];
                 point[i] = coo;
                 
                 String tempclass = arrayclassid[j];
                 arrayclassid[j] = arrayclassid[i];
                 arrayclassid[i] = tempclass;

                 
                 String temps = arrayvideoid[j];
                 arrayvideoid[j] = arrayvideoid[i];
                 arrayvideoid[i] = temps;
                 j--;
            }   
            
            while (i < j && a[j] >= index)
            {j--;}
           
            if(i < j){
           	 int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
                
                long tempframe = arrayframeid[j];
                arrayframeid[j] = arrayframeid[i];
                arrayframeid[i] = tempframe;
                
                long tempt = arraytimeid[j];
                arraytimeid[j] = arraytimeid[i];
                arraytimeid[i] = tempt;
                
                Coordinate coo = new Coordinate();
                coo.x = point[j].x;
                coo.y = point[j].y;
                point[j] = point[i];
                point[i] = coo;
                
                String tempclass = arrayclassid[j];
                arrayclassid[j] = arrayclassid[i];
                arrayclassid[i] = tempclass;

                
                String temps = arrayvideoid[j];
                arrayvideoid[j] = arrayvideoid[i];
                arrayvideoid[i] = temps;
                i++;
           }   
        }
        a[i] = index;
        sort(a,arrayframeid, arraytimeid,arrayclassid, point, arrayvideoid, low, i - 1); 
        sort(a,arrayframeid, arraytimeid,arrayclassid, point, arrayvideoid, i + 1, hight);

    }

    public static void quickSort(int a[], long[] arrayframeid, long[] arraytimeid, String[] arrayclassid, Coordinate[] point, String[] arrayvideoid) {
        sort(a,arrayframeid, arraytimeid,arrayclassid, point, arrayvideoid, 0, a.length-1);
    }
    
    
    public static void quickSort(long[] arraytimeid, long[] arrayframeid, String[] subpoint, int low, int hight) {
        int i, j, index;
        if (low >= hight) {
            return;
        }
        i = low;
        j = hight;
        index = (int) arraytimeid[j];
        while (i < j) { 
            
            
            while (i < j && arraytimeid[i] <= index)
            {i++;}
        
            if(i < j){
            	 long temp = arraytimeid[j];
            	 arraytimeid[j] = arraytimeid[i];
            	 arraytimeid[i] = temp;
                 
                 long tempframe = arrayframeid[j];
                 arrayframeid[j] = arrayframeid[i];
                 arrayframeid[i] = tempframe;
                 
              
                 String coo = subpoint[j];
                 subpoint[j] = subpoint[i];
                 subpoint[i] = coo;
                 
        
                 j--;
            }   
            
            while (i < j && arraytimeid[j] >= index)
            {j--;}
           
            if(i < j){
            	 long temp = arraytimeid[j];
            	 arraytimeid[j] = arraytimeid[i];
            	 arraytimeid[i] = temp;
                 
                 long tempframe = arrayframeid[j];
                 arrayframeid[j] = arrayframeid[i];
                 arrayframeid[i] = tempframe;
                 
              
                 String coo = subpoint[j];
                 subpoint[j] = subpoint[i];
                 subpoint[i] = coo;
                 
                i++;
           }   
        }
        arraytimeid[i] = index;
        quickSort(arraytimeid,arrayframeid, subpoint, low, i - 1); 
        quickSort(arraytimeid,arrayframeid, subpoint, i + 1, hight);

    }
}