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

import me.mrexplode.buffer.CharBuf;
import me.mrexplode.buffer.LineBuilder;
import me.mrexplode.buffer.TabModifier;

public class Main {
	
	public static void main(String[] args) {
			//if (!startConsole()) return;
			System.out.print("--== MrExplode's JavaScript Deobfuscator ==--\nEnter the file location: ");
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
				BufferedReader sourceReader = new BufferedReader(new FileReader(inFile));
				
				LineBuilder builder = new LineBuilder(writer);
				
				int i = 0;
				while ((i = sourceReader.read()) != -1) {
					char c = (char) i;
					builder.apply(c);
				}
				sourceReader.close();
				writer.close();
				System.out.println("====================\nDeobuscation finished");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	
	public static boolean startConsole() {
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
	            return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return true;
		}
	}

}
