package com.awarematics.postmedia.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

public class splitDocument {

	public static void main(String args[]) throws IOException, ParseException {

		BufferedReader File = new BufferedReader(new FileReader("D:\\tdrive.txt"));
		String lineDta = "";
		int k = 0;
		String splitString = "";
		int length = countLength(File);
		System.out.println(length);
		BufferedReader textFile = new BufferedReader(new FileReader("D:\\tdrive.txt"));
		while ((lineDta = textFile.readLine()) != null) {
			{
				//System.out.println("OK");
				splitString += "\r\n"+lineDta;
				k++;
				if(k%3000==0 || k ==length)
				{
					File file = new File("D:\\tdrive_."+k+"txt");
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter writer = new FileWriter(file);
					writer.write(splitString);
					writer.flush();
					splitString = "";
					writer.close();
				}
			}
		}
		 textFile.close();
	}

	private static int countLength(BufferedReader textFile) {
		int numberOfLine= 0;
		try{
		    String line = "";
		    while ((line = textFile.readLine()) != null){
		    	numberOfLine++;	    	
		    }
		   // textFile.close();
		}catch (FileNotFoundException e){
		    System.out.println("Path is incorrect");
		}catch (IOException e){
		    System.out.println("Wrong reade files");
		}
		return numberOfLine;
	}
}
