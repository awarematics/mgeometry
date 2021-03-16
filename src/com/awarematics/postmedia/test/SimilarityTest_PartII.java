package com.awarematics.postmedia.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;

import com.awarematics.postmedia.algorithms.similarity.MEDVVS;
//import com.awarematics.postmedia.algorithms.similarity.MLCSS;
import com.awarematics.postmedia.algorithms.similarity.MLCVS;
import com.awarematics.postmedia.algorithms.similarity.MVVS;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.FoV;
import com.awarematics.postmedia.types.mediamodel.Frame;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MVideo;

public class SimilarityTest_PartII {

	public static final int ARRAY_SIZE = 100;
	public static final double EPSILON_R = 1;
	//private static MLCSS lcss;
	private static MLCVS lcvs;
	private static MVVS vvs;
	private static MEDVVS edvvs;
	static double[] coordinate1_x;
	static double[] coordinate1_y;
	static long[] timeArray;
	static MVideo mv = null;
	public static void main(String[] args) throws org.locationtech.jts.io.ParseException, ParseException, IOException {
		/*
		 * test for bbd data straight direction
		 */

		//lcss = new MLCSS();
		lcvs = new MLCVS();
		vvs = new MVVS();
		edvvs = new MEDVVS();

		// bootup
		MVideo[] videos = new MVideo[1000];
		MVideo[] videos1 = new MVideo[1000];
		
		for (int i = 1; i < 9; i++) {
			//File file = new File("d://RandomDirection/" + i + ".json");
			//File files = new  File("d://StraightDirection/" + i + ".json");
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			videos[i] = (MVideo) reader.read(BddDataToPostgre("D://train/", "mvideo")[i]);
			
			videos1[i] = (MVideo) reader.read(BddDataToPostgre("D://train/", "mvideo")[i]);
		}
		//double accvvs =0.0;
		//double accedvvs =0.0; 

		double res1 =0;
		double res2 = 0;
		double res3 =0;
	
		for (int i = 1; i < 5; i++) {
			for (int j = 1+i; j < 5; j++) {
				
				 res1 += doExperiementLCVS(videos[i],videos1[j] ,1);
				 res2 += doExperiementEDVVS(videos[i],videos1[j],EPSILON_R, 1);
				 res3 += doExperiementVVS(videos[i],videos1[j], 1);		
			}
			System.out.println(res1+"\t"+res2+"\t"+res3);		
		}
		
		//accvvs = res1/res3;
		//accedvvs = res1/res2;

		//System.out.println(EPSILON_R+"\t"+accedvvs);
		//System.out.println(EPSILON_R+"\t"+accvvs);	
	}


public static double doExperiementLCVS( MVideo videos, MVideo videos1, int delta )
{
	
	Double lcvsdata = 0.0;
	//long start1=System.currentTimeMillis(); 			 
	lcvsdata =lcvsdata+ lcvs.similarity(videos, videos1, delta );	
	//System.out.println(lcvsdata+"\tsimialrity");
	//long end1=System.currentTimeMillis(); 
	//long times= end1- start1;
	//System.out.println("LCVS\t"+times);
	return lcvsdata;
}

public static double doExperiementEDVVS( MVideo videos, MVideo videos1,double epsilon, int delta )
{
	
	Double vvsdata = 0.0;
	//long start1=System.currentTimeMillis(); 			 
	vvsdata += vvs.calculate(videos, videos1);	
	//long end1=System.currentTimeMillis(); 
	//long times= end1- start1;
	//System.out.println("LCSS\t"+times);
	return vvsdata;
}

public static double doExperiementVVS( MVideo videos,  MVideo videos1,int delta )
{
	
	Double edvvsdata = 0.0;
	//long start1=System.currentTimeMillis(); 			 
	edvvsdata += edvvs.calculate(videos, videos1 );	
	//System.out.println(lcvsmbtdata+"\tsimialrity");
	
	//long end1=System.currentTimeMillis(); 
	//long times= end1- start1;

	//System.out.println("LCVSMBT\t"+times);
	return edvvsdata;
}

public static String[] BddDataToPostgre(String uris, String type) throws IOException {
	String[] bdd = null;
	ArrayList<String> bddString = new ArrayList<String>();
	for (int numof = 1; numof <= 10; numof++) {
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