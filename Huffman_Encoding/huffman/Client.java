package huffman;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Client {
	public static String read_file(String file) {
		StringBuilder sb=new StringBuilder();
		File filename = new File(file);
		try {
			BufferedReader br=new BufferedReader(new FileReader(filename));
			String st= br.readLine();
			while(st!=null) {
				sb.append(st+"\n");
				st=br.readLine();
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		return sb.toString();
	}
	public static void main(String args[]) {
		String file= "C:\\Users\\Armaan Thakur\\Downloads\\avengers_summary.txt";
		String original_string=read_file(file);
		
		Huffman h=new Huffman(original_string);
		
		System.out.println("Word Frequency: ");
		for(Map.Entry<Character,Integer> entry:h.hmap_count.entrySet()) {
			String key = entry.getKey().toString();
			int value = entry.getValue();
			if (key.equals("\n")) {
				key = "\\n";
			}
			else if(key.equals(" ")) {
				key = "Whitespace";
			}
			System.out.println(key+" -> "+value);
		}
		System.out.println();
		System.out.println("Huffman Code: ");
		for(Map.Entry<Character,String> entry:h.hmap_code.entrySet()) {
			String key = entry.getKey().toString();
			String code = entry.getValue();
			if (key.equals("\n")) {
				key = "\\n";
			}
			else if(key.equals(" ")) {
				key = "Whitespace";
			}
			System.out.println(key+" -> "+code);
		}
		System.out.println();
		System.out.println("Encoding text:");
		String encode=h.encode();
		System.out.println("Done");
		System.out.println();
		System.out.println("Decoding text");
		String decode=h.decode();
		System.out.println("Done");
		System.out.println("Both text data equal:"+original_string.equals(decode));
		System.out.println();
		int org=original_string.length()*8;
		int com=encode.length();
		System.out.println("Original String cost: "+org+" bits");
		System.out.println("Reduced String cost: "+com+" bits");
		System.out.println("Reduction %: "+(((com-org)/(double)org)*-100));
		System.out.println();
		System.out.println("Decoded Text:");
		System.out.println(decode);
	}
}
