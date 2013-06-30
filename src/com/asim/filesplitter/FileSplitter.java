package com.asim.filesplitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSplitter {
	private String inputFilePath;
	private String outputFilePath;
	private int outputFileChunks;
	private boolean isSplittingInProcess;

	private FileInputStream inputFileStream;
	private FileOutputStream outputFileStream;
	
	private int fileChunkSize;
	private int outputFileChunkNumber;
	
	//***********************
	// Constructor
	//***********************
	
	public FileSplitter(){
		this(null);
	}

	public FileSplitter(String inputFilePath){
		this(inputFilePath, "temporary");
	}

	public FileSplitter(String inputFilePath, String outputFilePath){
		this(inputFilePath, outputFilePath, 1);
	}

	public FileSplitter(String inputFilePath, String outputFilePath, int noOfOutputFileChunks){
		this.setInputFilePath(inputFilePath);
		this.setOutputFilePath(outputFilePath);
		this.setOutputFileChuncks(noOfOutputFileChunks);
		
		this.isSplittingInProcess = false;
		this.outputFileChunkNumber = 1;
	}

	//***********************
	// Getter Setter
	//***********************
	
	public String getInputFilePath() {
		return inputFilePath;
	}

	public boolean setInputFilePath(String inputFilePath) {
		if(this.isSplittingInProcess){
			return false;
		}
		
		this.inputFilePath = inputFilePath;
		return true;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public boolean setOutputFilePath(String outputFilePath) {
		if(this.isSplittingInProcess){
			return false;
		}

		this.outputFilePath = outputFilePath;
		return true;
	}

	public int getOutputFileChuncks() {
		return outputFileChunks;
	}

	public boolean setOutputFileChuncks(int outputFileChuncks) {
		if(this.isSplittingInProcess){
			return false;
		}

		if(outputFileChuncks < 1){
			this.outputFileChunks = 1;
			return true;
		}
		
		this.outputFileChunks = outputFileChuncks;
		return true;
	}

	public boolean isSplittingInProcess() {
		return isSplittingInProcess;
	}
	

	//***********************
	// Methods
	//***********************
	
	private boolean loadinputFileStream(){
		try {
			File inputFile = new File(inputFilePath);
			this.inputFileStream = new FileInputStream(inputFile);
			
			// calculate file chunk size
			this.fileChunkSize = (int) inputFile.length() / this.outputFileChunks;
			if((inputFile.length() / this.outputFileChunks) != 0 ){
				this.fileChunkSize++;
			}
			
			return true;

		} catch (NullPointerException fileNotFound) {
			System.out.println("Input file not found: "+fileNotFound);
		} catch (Exception e){
			System.out.println(e);
		}

		return false;
	}
	
	private boolean loadOutputFileStream(){
		try {
			File outputFile = new File(outputFilePath+"-"+this.outputFileChunkNumber);
			this.outputFileStream = new FileOutputStream(outputFile);
			this.outputFileChunkNumber++;
			
			return true;
		} catch (NullPointerException fileNotFound) {
			System.out.println("Output file not found: "+fileNotFound);
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	private boolean unloadOutputStream(){
		try {
			this.outputFileStream.close();
		} catch (IOException e) {
			System.out.println("Closing file was unsuccessful: " + e);
		}

		return true;
	}

	/*	private void putFileInfoInInstance(){
		if(this.loadinputFile()){
			inputFile.length();
		}
	}
*/	
	public boolean split(){
		if(this.isSplittingInProcess()){
			return false;
		}

		this.isSplittingInProcess = true;
		
		if(!this.loadinputFileStream()){
			this.isSplittingInProcess = false;
			return false;
		}
		
		int numberOFBytes;
		int inputFileOffset = 0;
		int outputFileOffset = 0;
		int readNumberOfBytes = 524288;     /*/ 512 * 1024 = 524288 or 1.5KB/*/

		byte[] fileData = new byte[readNumberOfBytes];
		
/*		for(int i = 0; i < this.outputFileChunks; i++){
			if(!this.loadOutputFileStream()){
				this.isSplittingInProcess = false;
				return false;
			}
			
			//load data from input file to output file
			for( outputFileOffset = 0 ; outputFileOffset < this.fileChunkSize ; outputFileOffset += readNumberOfBytes){
				inputFileOffset += readNumberOfBytes;

				if(readNumberOfBytes > this.fileChunkSize - outputFileOffset){
					readNumberOfBytes = this.fileChunkSize - outputFileOffset;
				}
				
				try {
					numberOFBytes = this.inputFileStream.read(fileData, inputFileOffset, readNumberOfBytes);
					this.outputFileStream.write(fileData, outputFileOffset, numberOFBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			readNumberOfBytes = 524288;
		}
*/
		this.loadOutputFileStream();
/*		for( outputFileOffset = 0 ; outputFileOffset < this.fileChunkSize ; outputFileOffset += readNumberOfBytes){
			inputFileOffset += readNumberOfBytes;

			if(readNumberOfBytes > this.fileChunkSize - outputFileOffset){
				readNumberOfBytes = this.fileChunkSize - outputFileOffset;
			}
			
			try {
				numberOFBytes = this.inputFileStream.read(fileData, inputFileOffset, readNumberOfBytes);
				this.outputFileStream.write(fileData, outputFileOffset, numberOFBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
*/		
		try {
			numberOFBytes = this.inputFileStream.read(fileData, 524288, readNumberOfBytes);
			this.outputFileStream.write(fileData, outputFileOffset, numberOFBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
/*		inputFileOffset =+ readNumberOfBytes;
		outputFileOffset += readNumberOfBytes;

		try {
			numberOFBytes = this.inputFileStream.read(fileData, inputFileOffset, readNumberOfBytes);
			this.outputFileStream.write(fileData, outputFileOffset, numberOFBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
*/	
		
		this.isSplittingInProcess = false;
		
		return true;
	}
}
