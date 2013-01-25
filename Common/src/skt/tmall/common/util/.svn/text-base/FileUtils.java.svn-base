package skt.tmall.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
	
	// path 경로 마지막에 separator 존재 유무를 검증하여 없을 경우 separator 를 포함한 path 를 리턴
	public static String addFileSeparator(String arg) {
		String path = arg;
		if (path.length() - 1 != path.lastIndexOf(File.separator))
			return path + File.separator;
		
		return path;
	}
	
	// 임시 폴더에서 실제 폴더로 파일 복사
	@SuppressWarnings("finally")
	public static boolean copyFile(File tempFile, File currentFile) throws IOException {
		boolean flag = true;
		
		// 스트림, 채널 선언
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		
		try {
			// 스트림 생성
			inputStream = new FileInputStream(tempFile);
			outputStream = new FileOutputStream(currentFile);
			
			// 채널 생성
			fcin = inputStream.getChannel();
			fcout = outputStream.getChannel();
			
			// 채널을 통한 스트림 전송
			// original -- apparently has trouble copying large files on Windows
			// magic number for Windows, 64Mb - 32Kb)
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = fcin.size();
            long position = 0;
            while ( position < size )
            {
               position += fcin.transferTo( position, maxCount, fcout );
            }
		} catch (Exception e) {
			flag = false;
			
			e.printStackTrace();
		} finally {
			fcout.close();
			fcin.close();
			outputStream.close();
			inputStream.close();
			
			return flag;
		}
	}
	
	public static void copyFile2(File tempFile, File currentFile) throws IOException {

	    FileReader in = new FileReader(tempFile);
	    FileWriter out = new FileWriter(currentFile);
	    int c;

	    while ((c = in.read()) != -1)
	      out.write(c);

	    in.close();
	    out.close();
	}
}
