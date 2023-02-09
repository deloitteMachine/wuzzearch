package com.deloitte.wuzzearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {

	public List<String> read(File file) {
		List<String> lines = new ArrayList<String>();
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		return lines;
	}
}