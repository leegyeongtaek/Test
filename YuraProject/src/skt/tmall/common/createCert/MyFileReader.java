package skt.tmall.common.createCert;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class MyFileReader {

	public String getFileContent(String fileName) {

		File inFile   = null;
		FileInputStream inStream = null;
		FileChannel inChannel= null;
		ByteBuffer bBuffer  = null;
		String resultStr = "";

		try {
			// Open File Channel
			inFile   = new File(fileName);
			inStream = new FileInputStream(inFile);
			inChannel= inStream.getChannel();

			// buffer init...
			bBuffer = ByteBuffer.allocate((int)inChannel.size());
			inChannel.read(bBuffer);

			resultStr = new String(((ByteBuffer)bBuffer.flip()).array());
			//String[] strBuffer = new String(((ByteBuffer)bBuffer.flip()).array()).split("\r\n");

		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if( inStream != null )
				inStream.close();
				if( inChannel != null )
				inChannel.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		return resultStr;
	}
}
