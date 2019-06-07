package com.awarematics.postmedia.compareSECONDO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPolygon;

public class Intersects {
	static MGeometryFactory geometryFactory = new MGeometryFactory();
	static MWKTReader reader = new MWKTReader(geometryFactory);
	static long[] addtime;
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

		File file = new File("d://mfs/mpolygon1" + ".json");// 000f157f-dab3a407
															// //000f8d37-d4c09a0f
		String content = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = new JSONObject(content);
		MPolygon mpolygon1 = null;
		MPolygon mpolygon2 = null;
		GeometryFactory geom = new GeometryFactory();
		WKTReader readers = new WKTReader(geom);
		try {
			Polygon[] pol;
			long[] timeArray;

			JSONArray getJsonArray = jsonObject.getJSONArray("mpolygon");
			int num = getJsonArray.length();
			pol = new Polygon[num];
			timeArray = new long[num];

			for (int j = 0; j < num; j++) {
				String[] array = getJsonArray.get(j).toString().split(":");
				String pst1 = array[1].split(",\\\"")[0].replaceAll("\\\"", "");
				String time = array[2].split(",\\\"")[0].replaceAll("\\}", "");
				time = time.replaceAll("\\\"", "");
				pol[j] = (Polygon) readers.read(pst1);
				timeArray[j] = (Long.parseLong(time));
			}
			mpolygon1 = geometryFactory.createMPolygon(pol, timeArray);
		} catch (Exception e) {

		}

		File file2 = new File("d://mfs/mpolygon3" + ".json");// 000f157f-dab3a407
																// //000f8d37-d4c09a0f
		String content2 = FileUtils.readFileToString(file2, "UTF-8");
		JSONObject jsonObject2 = new JSONObject(content2);

		try {
			Polygon[] pol2;
			long[] timeArray2;

			JSONArray getJsonArray = jsonObject2.getJSONArray("mpolygon");
			int num = getJsonArray.length();
			pol2 = new Polygon[num];
			timeArray2 = new long[num];

			for (int j = 0; j < num; j++) {
				String[] array = getJsonArray.get(j).toString().split(":");
				String pst2 = array[1].split(",\\\"")[0].replaceAll("\\\"", "");
				String time2 = array[2].split(",\\\"")[0].replaceAll("\\}", "");
				time2 = time2.replaceAll("\\\"", "");
				pol2[j] = (Polygon) readers.read(pst2);
				timeArray2[j] = (Long.parseLong(time2));
			}
			mpolygon2 = geometryFactory.createMPolygon(pol2, timeArray2);
		} catch (Exception e) {

		}

		long startTime=System.currentTimeMillis(); 		
		MPolygon a = operation(mpolygon2, mpolygon2, "intersects");
		MPolygon b = operation(mpolygon2, mpolygon2, "intersects");
		String result = result(a, b);
		System.out.println(result);
		long endTime=System.currentTimeMillis();  
		System.out.println("running time:  "+(endTime-startTime)+"ms");  
		
		long startTime2=System.currentTimeMillis(); 		
		
		@SuppressWarnings("static-access")
		String result2 = mpolygon1.intersects(mpolygon2, mpolygon2).toGeoString();
		System.out.println(result2);
		long endTime2=System.currentTimeMillis();  
		System.out.println("running time:   "+(endTime2-startTime2)+"ms");  

	}

	public static MPolygon operation(MPolygon mpolygon1, MPolygon mpolygon2, String expression) {
		// -----------------------------------algorithm1
		Polygon[] pol1 = mpolygon1.getListPolygon();
		Polygon[]pol2 = mpolygon2.getListPolygon();
		long[] t1 = mpolygon1.getTimes();
		
		long[] doubletime = new long[mpolygon1.numOf() +  mpolygon2.numOf()];
		for( int i =0;i< mpolygon1.numOf();i++)
			doubletime[i] = mpolygon1.getTimes()[i];
		for( int i =0;i< mpolygon2.numOf();i++)
			doubletime[i+mpolygon1.numOf()] = mpolygon2.getTimes()[i];
		
		ArrayList<Long> doubletimes = new ArrayList<Long>();
		int tnum;
		for(int i =0;i<doubletime.length;i++){
			tnum =0;
			for(int j =i+1; j<doubletime.length;j++)
			{
				if(doubletime[j] == doubletime[i]){
					tnum =1;
				}
			}
			if(tnum !=1){
				doubletimes.add(doubletime[i]);
			}
		}
		
		addtime = doubletimes.stream().filter(i -> i != null).mapToLong(i -> i).toArray();
		Arrays.sort(addtime);
		
		ArrayList<Polygon> Lf = new ArrayList<Polygon>();

		for (int i = 0; i < pol1.length; i++)
			for (int j = 0; j < pol2.length; j++) {
				if (pol1[i].intersects(pol2[j])) {
					Lf.add((Polygon) pol1[i].intersection(pol2[j]));
				}
			}
		// -----------------------------------algorithm2
		
		ArrayList<Polygon> Sf = new ArrayList<Polygon>();
		ArrayList<Long> t = new ArrayList<Long>();

		for (int i = 0; i < pol1.length; i++) {
			for (int j = i; j < pol2.length; j++) {
				if (pol1[i].intersects(pol2[j]) && !pol1[i].within(pol2[j]) && !pol1[i].disjoint(pol2[j])) {
					// ----------intersects
					switch (expression) {
					case "union":
						break;
					case "intersects":
						Sf.add((Polygon) pol1[i].intersection(pol2[j]));
						t.add(t1[i]);
						// algorithm 6.1.2

						break;
					case "AdifferenceB":
						break;
					case "BdifferenceA":// null
						break;
					default:
						break;
					}
				} else { // inside
					if (pol1[i].within(pol2[j])) {
						switch (expression) {
						case "union":
							break;
						case "intersects":
							Sf.add(pol1[i]);//
							t.add(t1[i]);
							break;
						case "AdifferenceB":
							break;
						case "BdifferenceA":
							break;
						default:
							break;
						}
					} else { // outside
						switch (expression) {
						case "union":
							break;
						case "intersects":
							// null;
							break;
						case "AdifferenceB":
							break;
						case "BdifferenceA":
							break;
						default:
							break;
						}
						break;
					}
				}
			}
		}
		/*
		 * lexicograzphically
		 */
		ArrayList<Integer> ditto = new ArrayList<Integer>();
		
		for (int i = 0; i < t.size(); i++) {
			for (int j = i + 1; j < t.size(); j++) {
				if (t.get(i).equals(t.get(j))) {
					if (Sf.get(i).getArea() < Sf.get(j).getArea()) {
						ditto.add(i);						
					} else {
						ditto.add(j);						
					}
				}
			}
		}
		/*
		 * delete repeat time
		 */
		ArrayList<Integer> al = new ArrayList<Integer>();
		out: for (int i = 0; i < ditto.size(); i++) {
			for (int j = 0; j < al.size(); j++) {
				if (ditto.get(i) == al.get(j)) {
					continue out;
				}
			}
			al.add(ditto.get(i));
		}
		
		/*
		 * get result 
		 */
		ArrayList<Polygon> Df = new ArrayList<Polygon>();
		ArrayList<Long> te = new ArrayList<Long>();
		int temp=0;
		for(int i=0;i<Sf.size();i++)
		{
			temp =0;
			for(int j=0;j<al.size();j++)
			{
				if(al.get(j)==i) temp = 1;
			}
			if(temp == 0){
				Df.add(Sf.get(i));
				te.add(t.get(i));
			}
		}
		Polygon[] pl = new Polygon[Df.size()];
		long[] tl = new long[Df.size()];
		for(int i=0;i<Df.size();i++)
		{
			pl[i] = Df.get(i);
			tl[i] = te.get(i);
		}
		MPolygon mp = geometryFactory.createMPolygon(pl, tl);
		return mp;
	}

	// --------------------------------------------algorithm3

	public static String result(MPolygon sf1, MPolygon sf2) {
		/*
		 * union MPolygon 
		 */
		String result="MBOOL ()";
		if(sf1.numOf()>0 && sf2.numOf()>0)
		{
		Polygon[] pl = new Polygon[sf1.numOf()+sf2.numOf()];
		long[] tl = new long[sf1.numOf()+sf2.numOf()];
		for(int i=0;i< sf1.numOf() ;i++)
		{
			pl[i] = sf1.getListPolygon()[i];
			tl[i] = sf1.getTimes()[i];
		}
		for(int i=0;i< sf2.numOf() ;i++)
		{
			pl[sf1.numOf()+i] = sf2.getListPolygon()[i];
			tl[sf1.numOf()+i] = sf2.getTimes()[i];
		}	
		/*
		 * lexicograzphically
		 */
		ArrayList<Integer> ditto = new ArrayList<Integer>();
		
		for (int i = 0; i < tl.length; i++) {
			for (int j = i + 1; j < tl.length; j++) {
				if (tl[i]==(tl[j])) {
					if (pl[i].getArea() < pl[j].getArea()) {
						ditto.add(i);						
					} else {
						ditto.add(j);						
					}
				}
			}
		}
		/*
		 * delete repeat time
		 */
		ArrayList<Polygon> St = new ArrayList<Polygon>();
		ArrayList<Long> tt = new ArrayList<Long>();
		int temp=0;
		for(int i=0;i<pl.length;i++)
		{
			temp =0;
			for(int j=0;j<ditto.size();j++)
			{
				if(ditto.get(j)==i) temp = 1;
			}
			if(temp == 0){
				St.add(pl[i]);
				tt.add(tl[i]);
			}
		}
		/*
		 * get bottom t-coordinate [t0 - tn]
		 */
		
		
		long overlappedStartTime = Math.max(sf1.getTimes()[0], sf2.getTimes()[0]);
		long overlappedEndTime = Math.min(sf1.getTimes()[sf1.numOf() - 1], sf2.getTimes()[sf2.numOf() - 1]);
		ArrayList<Polygon> A = new ArrayList<Polygon>();
		ArrayList<Long> te = new ArrayList<Long>();
		
		for(int i=0;i< tt.size();i++)
		{
			if(tt.get(i)>=overlappedStartTime && tt.get(i)<= overlappedEndTime)
			{
				A.add(St.get(i));
				te.add(tt.get(i));
			}
		}
		ArrayList<Polygon> MS = new ArrayList<Polygon>();
		ArrayList<Long> stime = new ArrayList<Long>();
		ArrayList<Long> etime = new ArrayList<Long>();
		//second model    time ([t1 t2] ture, [t2 t3] false.......)
		Polygon noPolygon = null;
		Polygon yesPolygon = sf1.getListPolygon()[0];
		
		int num =0;
		for(int i=0;i<te.size()-1;i++)
		{
			num=0;
			for(int j=0;j<A.size();j++)
			{
				//System.out.println(sf1.snapshot(te.get(i)));
				if(A.get(j).intersects(sf1.snapshot(te.get(i)))){
					num=1;
				}				
				else if(A.get(j).intersects(sf2.snapshot(te.get(i)))){ 
					num=1;
				}				
			}
			if(num ==1)
			{
				MS.add(yesPolygon);
				stime.add(te.get(i));
				etime.add(te.get(i+1));
			}
			else{
				MS.add(noPolygon);
				stime.add(te.get(i));
				etime.add(te.get(i+1));
			}
		}
		
		/*
		 * get result   second mbool model
		 */
		result = result.replaceAll("\\)", "");
		for(int i =0;i<addtime.length-1;i++){
			if(addtime[i]<overlappedStartTime){
				result +=  "("+addtime[i]+" "+addtime[i+1]+" true false) " + "false, ";
			}
		}
		
		for(int i=0;i<MS.size();i++)
		{
			if(i==0)
			{
			if(MS.get(i).getArea()>0)
				result +=  "("+stime.get(i)+" "+etime.get(i)+" true false) " + "true";
			else 
				result +=  "("+stime.get(i)+" "+etime.get(i)+" true false) " + "false";
			}
			else if(i!=MS.size()-1)
			{
				if(MS.get(i).getArea()>0)
					result +=  ", ("+stime.get(i)+" "+etime.get(i)+" true false) " + "true";
				else 
					result +=  ", ("+stime.get(i)+" "+etime.get(i)+" true false) " + "false";
			}
			else{
				if(MS.get(i).getArea()>0)
					result +=  ", ("+stime.get(i)+" "+etime.get(i)+" true true) " + "true";
				else 
					result +=  ", ("+stime.get(i)+" "+etime.get(i)+" true true) " + "false";
			}
		}
		for(int i =0;i<addtime.length-1;i++){
			if(addtime[i]>overlappedEndTime){
				result +=  ", ("+stime.get(i)+" "+etime.get(i)+" true true) " + "false";
			}
		}
			result += ")"; 
		}
		else
		{
			result ="MBOOL (";
			for(int i=0;i<addtime.length-1;i++)
				if(i!= addtime.length-2){
					result +=  "("+addtime[i]+" "+addtime[i+1]+" true false) " + "false, ";
				 }
				else
					result +=  "("+addtime[i]+" "+addtime[i+1]+" true true) " + "false";
			result += ")";
		}
		return result;
	}
}
