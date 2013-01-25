package skt.tmall.common.createCert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MyFileWriter {

	public void write(String content, String fileName) {
	    File aFile = new File(fileName);
	    FileOutputStream outputFile = null;

	    try {
	    	outputFile = new FileOutputStream(aFile, false);
		    FileChannel outChannel = outputFile.getChannel();
		    int size = content.length();
		    System.out.println("size : "+size);
		    ByteBuffer buf = ByteBuffer.allocate(1000000);
		    System.out.println("New buffer:           position = " + buf.position()
		                       + "\tLimit = " + buf.limit() + "\tcapacity = "
		                       + buf.capacity());

		    byte[] contentByte = content.getBytes();
		    for(int i=0; i<contentByte.length; i++) {
		    	buf.put(contentByte[i]);
		    }

		    System.out.println("Buffer after loading: position = " + buf.position()
		                       + "\tLimit = " + buf.limit() + "\tcapacity = "
		                       + buf.capacity());
		    buf.flip();
		    System.out.println("Buffer after flip:    position = " + buf.position()
		                       + "\tLimit = " + buf.limit() + "\tcapacity = "
		                       + buf.capacity());

		    outChannel.write(buf);
			outputFile.close();
			System.out.println("Buffer contents written to file.");
	    } catch (FileNotFoundException e) {
	    	System.out.println("FileNotFound Exception");
	    	e.printStackTrace(System.err);
	    } catch(IOException e) {
	    	System.out.println("IO Exception");
	    	e.printStackTrace(System.err);
	    } catch(Exception e) {
	    	System.out.println("Exception");
	    	e.printStackTrace();
	    }

	}
}


