package com.francescomalagrino.zopa.CVS.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import com.francescomalagrino.zopa.models.Loaner;
public class CSVParser {
	 public static List<Loaner> parseCSV(String path) throws IOException {
	        File csv = new File(path);
	        FileInputStream fis = new FileInputStream(csv);
	        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	        @SuppressWarnings("resource")
			List<Loaner> loaners = br.lines().skip(1).map(line -> {
	            String[] args = line.split(",");
	            return new Loaner(args[0], Double.valueOf(args[1]), Integer.valueOf(args[2]));
	        }).collect(Collectors.toList());
	        br.close();
	        return loaners;
	    }
}
