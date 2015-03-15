package com.ggg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;

public class IOSample {
	
	public static final String FILE_INPUT_PATH = "C:\\gilbert\\infiles\\";
	public static final String FILE_OUTPUT_PATH = "C:\\gilbert\\outfiles\\";
	
	public static final String textFileName = "wordlist.txt";
	public static final String imageFileName = "photo.JPG"; //"ifsod.PNG";
	public static final String pdfFileName = "Are_You_Prepared.pdf";
	
	public static void main(String[] args) {
		
		System.out.println("Copy using java io non-buffered...");
		readAndWriteFileByByte(pdfFileName);
		
		System.out.println("Copy using Apache IOUtils.copy(InputStream, OutputStream)...");
		apacheCopy(pdfFileName);
	}
	
	
	public static void apacheCopy(String fileName) {
		
		DateUtils.setStart();
		
		try {
			InputStream is = new FileInputStream(FILE_INPUT_PATH + fileName);
			OutputStream os = new FileOutputStream(new File(FILE_OUTPUT_PATH + "copy_" + fileName));
			
			IOUtils.copy(is, os);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Duration: " + DateUtils.getDuration() + " ms");
	}
	
	public static void readAndWriteFileByByte(String fileName) {
		
		DateUtils.setStart();
	
		//System.out.println("Start: " + DateUtils.getCurrentDateTime());
		
		try {
		
			InputStream is = new FileInputStream(FILE_INPUT_PATH + fileName);
			OutputStream os = new FileOutputStream(new File(FILE_OUTPUT_PATH + "copy_" + fileName));
			
			int aByte = 0;
			int ctr = 0;
			
			while((aByte = is.read()) != -1) {
				ctr++;
				os.write(aByte);
				//System.out.print((char) aByte);
				
			}
			is.close();
			os.flush();
			os.close();
			System.out.println(ctr + " bytes read!");

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("End: " + DateUtils.getCurrentDateTime());
		
		System.out.println("Duration: " + DateUtils.getDuration() + " ms");
		
		
	}

}
