package com.awarematics.postmedia.transform;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
 
 
/**
 * 1.CSV文件读取;
 * 2.将数据拆分成多个CSV文件;
 * 3.将数据导出到CSV文件;
 * @author Dabria_ly 2017年8月16日
 */
public class TestCSVUtil {
	public static void main(String[] args) throws IOException {
		 long startTime = System.currentTimeMillis();    //获取开始时间
		//1.读取CSV文件数据
		 BufferedReader br = new BufferedReader(
			   		new InputStreamReader(new FileInputStream("E:/trips.csv"),"GBK")//设置编码读取方式为GBK
			   	 );      					    	    				 			   
 
		 String line;
		 String mobile;
		 String borrowerid;
		 int k =0;
		 List<String> li = new ArrayList<String>();
		 while((line = br.readLine()) != null ) {
			   //读取获取CSV文件行、列数据
			 if(k<2000000){

			   //将数据拼成字符串拼接格式
			   li.add(new StringBuilder().append(line).toString());
			 }
			 k++;
 
		  }  
		   
		   //2.将33w条数据导出到11个CSV文件中
		   //设置11个CSV文件的文件名
		   String[] csvName = new String[1];
		   for (int i = 0; i < csvName.length; i++) {
			   csvName[i] = new StringBuilder().append("D:/test/2M").append(i+1).append(".csv").toString();
		   }
		   
		 
		   //3.将集合数据导出到11个小CSV文件中
		   boolean[] allSuccess = new boolean[csvName.length];
		   for(int i = 1; i<=csvName.length;i++){
			   boolean isSuccess = false;
			   	//每次导出一万条数据到CSV文件
			   if(i == 11){
				   isSuccess=exportCsv(new File(csvName[i-1]), li.subList((i-1)*300000, li.size()));
			   }else{
				   isSuccess=exportCsv(new File(csvName[i-1]), li.subList((i-1)*2000000, i*300000));  
			   }
				allSuccess[i-1] = isSuccess;
		   }
		   
		   //打印11个文件每次数据导出成功与否
		   System.out.println("11个文件每次数据导出成功与否-->");
		   for (int i = 0; i < allSuccess.length; i++) {
			   System.out.print(allSuccess[i]+",");
		   }
		   
		   long endTime = System.currentTimeMillis();    //获取结束时间
		   double userTime = ((double)endTime - startTime)/1000;//执行时长<秒>
		   System.out.println("\n"+String.format("执行完毕,共花时长:%s秒", userTime));//秒
	}
	
	 /**
    * 导出(到CSV文件)
    * @param file csv文件(路径+文件名),csv文件不存在会自动创建
    * @param dataList 数据
    * @return
    */
   public static boolean exportCsv(File file, List<String> dataList){
       boolean isSucess=false;
       
       FileOutputStream out=null;
       OutputStreamWriter osw=null;
       BufferedWriter bw=null;
       try {
           out = new FileOutputStream(file);
           osw = new OutputStreamWriter(out,"UTF-8");//设置输出编码方式为UTF-8
           //加上UTF-8文件的标识字符
           osw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
           
           bw =new BufferedWriter(osw);
           if(dataList!=null && !dataList.isEmpty()){
               for(String data : dataList){
                   bw.append(data).append("\r");
               }
           }
           isSucess=true;
       } catch (Exception e) {
           isSucess=false;
       }finally{
           if(bw!=null){
               try {
                   bw.close();
                   bw=null;
               } catch (IOException e) {
                   e.printStackTrace();
               } 
           }
           if(osw!=null){
               try {
                   osw.close();
                   osw=null;
               } catch (IOException e) {
                   e.printStackTrace();
               } 
           }
           if(out!=null){
               try {
                   out.close();
                   out=null;
               } catch (IOException e) {
                   e.printStackTrace();
               } 
           }
       }
       
       return isSucess;
   }
}