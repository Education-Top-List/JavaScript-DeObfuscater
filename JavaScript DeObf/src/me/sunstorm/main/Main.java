package me.sunstorm.main;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {
	
	public static void main(String[] args) {
		Console console = System.console();
		if (console == null && !GraphicsEnvironment.isHeadless()) {
			String filename = Main.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
			try {
				File batch = new File("Launcher.bat");
				if(!batch.exists()){
	                batch.createNewFile();
	                PrintWriter writer = new PrintWriter(batch);
	                writer.println("@echo off");
	                writer.println("java -jar "+filename);
	                writer.println("pause");
	                writer.flush();
	                writer.close();
	            }
	            Runtime.getRuntime().exec("cmd /c start \"\" "+batch.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			
			System.out.print("--== SunStorm's JavaScript Deobfuscator ==--\nEnter the file location: ");
			BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				String path = userReader.readLine();
				File inFile = new File(path);
				if (!inFile.exists())
				    throw new FileNotFoundException("can't find the specified file: " + path);
				System.out.print("Enter the name of the output file without extension: ");
				String name = userReader.readLine() + ".js";
				//
				BufferedWriter writer = new BufferedWriter(new FileWriter(name));
				BufferedReader reader = new BufferedReader(new FileReader(inFile));
				
				Integer tabIndex = 1;
				int i = 0;
				boolean forLoop = false;
				boolean stringKey = false;
				StringBuilder finalLine = new StringBuilder();
				while ((i = reader.read()) != -1) {
					char c = (char) i;
					//tab modifiers
					if (c == '{') {
						tabIndex++;
					}
					
					if (c == '}') {
						if (tabIndex != 1)
							tabIndex--;
					}
					
					finalLine.append(c);
					
					//line modifiers
					if (c == ';' || c == '{' || c == '}' && !stringKey && !forLoop) {
						finalLine.append("\n");
						writer.write(finalLine.toString());
						finalLine = new StringBuilder();
						
						//tab append
						for (int i2 = 0; i2 < tabIndex; i2++) {
							if (tabIndex != 1)
								finalLine.append("\t");
						}
					}
						
				}
				reader.close();
				writer.close();
				System.out.println("====================\nDeobuscation finished");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
