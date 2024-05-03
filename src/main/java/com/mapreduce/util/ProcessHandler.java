package com.mapreduce.util;

import java.io.IOException;
import java.util.Scanner;

public class ProcessHandler {
	public static void run(String cmd) throws IOException {
		Process process = new ProcessBuilder(cmd).start();
		Scanner in = new Scanner(process.getInputStream());
		Scanner err = new Scanner(process.getErrorStream());
		System.out.println("\n\n");
		while (in.hasNextLine() || err.hasNextLine()) {
			if (in.hasNextLine()) System.out.println(in.nextLine());
			else System.err.println(err.nextLine());
		}
		in.close();
		err.close();
	}
	
	public static boolean pingVM(String ip,int pingCount) throws IOException {
	    Process process = new ProcessBuilder("ping", ip).start(); // Separate command and arguments
		Scanner in = new Scanner(process.getInputStream());
	    Scanner err = new Scanner(process.getErrorStream());
	    System.out.println("Process " + "ping " + ip + " Running...");
	    int i = pingCount;
	    String out = "";
	    while ((in.hasNextLine() || err.hasNextLine()) && pingCount > 0) {
	        if (in.hasNextLine()) {
	            out = in.nextLine() ;
	            System.out.println(out);
	            pingCount = pingCount - 1;
	            if(out.contains("bytes")){
	            	i = i-1;
	            }
	        } else
	            System.err.println(err.nextLine());
	    }
	    in.close();
	    err.close();
	    System.out.println("i: "+i);
	    System.out.println("ping count: "+pingCount);

	    return i == pingCount;
	}
}