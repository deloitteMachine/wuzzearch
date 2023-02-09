package com.deloitte.wuzzearch;

import java.io.File;

public class CustomFile {

	File file;
	String name;
	String extension;
	
	public CustomFile(File file, String name) {
		super();
		this.file = file;
		this.name = name;
	}
	public CustomFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
	public String getExtension() {
		String fileName  = file.getName();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1, file.getName().length());
		return fileExtension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
	
}
