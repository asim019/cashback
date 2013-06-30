package com.asim.filesplitter;

public class FileSplitterTestClass {
	public static void main(String[] args){
		FileSplitter fs = new FileSplitter("Spring.pdf", "asimjee", 3);
		fs.split();
	}
}
