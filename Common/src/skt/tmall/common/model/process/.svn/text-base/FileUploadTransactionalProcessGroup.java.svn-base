package skt.tmall.common.model.process;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import skt.tmall.common.core.ICommonConstants;

import skt.tmall.common.db.TransactionTemplateManager;
import skt.tmall.common.util.FileUtils;


/**
 * @author leegt80
 * File Upload 관련 Process들의 Transaction관리
 */
public class FileUploadTransactionalProcessGroup extends AssociatedProcessGroup {
	
	public static final String FILE_FOLDER_PATH = "_FILE_FOLDER_PATH";		//임시 파일 폴더 경로
	
	public static final String REAL_FOLDER_PATH = "_REAL_FOLDER_PATH";		//실제 폴더 경로
	
	public static final String FILE_SIZE = "_FILE_SIZE";					//파일 사이즈

	public static final String FILE_EXTEND = "_FILE_EXTEND";				//파일 확장명
	
	public static final String CURRENT_FILE_NAME = "_CURRENT_FILE_NAME";	//업로드 시 파일명

	public static final String TEMP_FILE_NAME	= "_TEMP_FILE_NAME";	//업로드 후 임시 파일명
	
	public static final String AFTER_FILE_NAME	= "_AFTER_FILE_NAME";	//서버 변경 파일명 (변경할 파일명을 context에 담는다.)
	
	public static final int MAX_MEMORY_SIZE = 1024 * 10;
	
	public static final long MAX_REQUEST_SIZE = 1024 * 1024 * 100;
	
	private String tempPath;
	
	private String realPath;
	
	private String tempFileFolder;
	
	private String _serverRoot;
	
	class Transaction extends TransactionCallbackWithoutResult {

		private HashMap<String, Object> context;

		public Transaction(HashMap<String, Object> context) {
			this.context = context;
		}

		/* (non-Javadoc)
		 * @see org.springframework.transaction.support.TransactionCallbackWithoutResult#doInTransactionWithoutResult(org.springframework.transaction.TransactionStatus)
		 * return값이 존재하지 않는 트랜잭션이므로 doInTransaction을 쓰지 않고 doInTransactionWithoutResult를 사용함.
		 */
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus status) {
			try {
				beforeFileUploadProcess(context);
				
				for (IProcess<HashMap<String,Object>> process : processList) {
					process.process(context);
				}
				
				afterFileUploadProcess(context);
			} catch (Exception e) {
				if (log.isFatalEnabled()) {
					log.fatal(e);
				}
				status.setRollbackOnly();
				
				// RollBack 프로세스 처리
				for (IProcess<HashMap<String,Object>> process : processList) {
					process.rollback(context);
				}
				
				// RollBack시 temp file 삭제
				deleteFile(context);
				
				context.put(ERROR, e);
			}
		}
	}

	private static final String ERROR = "error";

	private static Log log = LogFactory.getLog(TransactionalProcessGroup.class);

	private TransactionTemplate transactionTemplate;

	private String transactionAlias = "default";

	public String getTransactionAlias() {
		return transactionAlias;
	}

	@Override
	public void proceed(HashMap<String, Object> context) throws ProcessException {

		transactionTemplate.execute(new Transaction(context));
		Exception error = (Exception) context.get(ERROR);
		if (error != null) {
			context.remove(ERROR);
			throw new ProcessException(error);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void beforeFileUploadProcess(HashMap<String, Object> context) throws Exception{
		HttpServletRequest request 	= (HttpServletRequest) context.get(ICommonConstants.HTTP_SERVLET_REQUEST);
		
		setServerRoot(request.getSession().getServletContext().getRealPath("/"));
		
		tempFileFolder = getServerRoot() + tempPath;
		
		context.put(FILE_FOLDER_PATH, tempFileFolder);	// 임시 폴더 경로
		context.put(REAL_FOLDER_PATH, realPath);	// 실제 폴더 경로
		
		String tempFilePath	= null;
		String fileName		= null;
		long sizeInBytes	= 0;
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart)
		{
			File tempDirectory = new File(tempFileFolder);
			if(!tempDirectory.exists()) 
			{
				tempDirectory.mkdirs();
			}
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MAX_MEMORY_SIZE);
			factory.setRepository(tempDirectory);
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			
			List<FileItem> items 	= upload.parseRequest(request);
			
			Iterator<FileItem> iter	= items.iterator();
			
			while (iter.hasNext()) 
			{
				FileItem tempFileItem = (FileItem) iter.next();
				
				if (!tempFileItem.isFormField())
				{
					tempFilePath 	= ((DiskFileItem) tempFileItem).getStoreLocation().getPath();
					fileName  		= tempFileItem.getName();
					sizeInBytes		= tempFileItem.getSize();
					
					// 확장명
					String extendFile = fileName.substring(fileName.lastIndexOf("."), 
							fileName.length()).toLowerCase();
					// 현재 파일명
					String currentFile = fileName.substring(fileName.lastIndexOf("\\") + 1,
							fileName.length());
					// 임시 파일명
					String tempFile	  = tempFilePath.substring(tempFilePath.lastIndexOf("\\") + 1,
							tempFilePath.lastIndexOf(".")) + extendFile;
					
					context.put(FILE_SIZE, sizeInBytes);			// 파일 사이즈
					context.put(FILE_EXTEND, extendFile);			// 확장명
					context.put(CURRENT_FILE_NAME, currentFile);	// 현재 파일명
					context.put(TEMP_FILE_NAME, tempFile);			// 임시 파일명
					
					File tempUploadedFile = new File(FileUtils.addFileSeparator(tempFileFolder)+ tempFile);
					
					tempFileItem.write(tempUploadedFile);
				}
			}
		}
	}
	
	private void afterFileUploadProcess(HashMap<String, Object> context) throws Exception {
		String afterFileName = (String) context.get(AFTER_FILE_NAME);
		
		if (afterFileName == null) 
		{
			afterFileName = (String) context.get(CURRENT_FILE_NAME);
		} 
		
		String tempFileName = (String) context.get(TEMP_FILE_NAME);
		
		String realFileFolder = getServerRoot() + (String) context.get(REAL_FOLDER_PATH);
		
		if (tempFileName != null)
		{
			File realDirectory = new File(realFileFolder);
			if(!realDirectory.exists()) 
			{
				realDirectory.mkdirs();
			}
			
			File afterFile = new File(FileUtils.addFileSeparator(realFileFolder) + afterFileName);
			
			if (afterFile.exists())
			{
				afterFile.delete();
			}
			
			File tempFile = new File(FileUtils.addFileSeparator(tempFileFolder) + tempFileName);
			
//			tempFile.renameTo(afterFile);
			
			FileUtils.copyFile(tempFile, afterFile);
		}
		
		deleteFile(context);
	}
	
	// RollBack시 temp file 삭제
	private void deleteFile(HashMap<String, Object> context) {
		String tempFileName = (String) context.get(TEMP_FILE_NAME);
		
		if (tempFileName != null)
		{
			File tempFile = new File(FileUtils.addFileSeparator(tempFileFolder) + tempFileName);
			
			if(tempFile.exists())
			{
				tempFile.delete();
			} 
		}
	}
	
	public void setTransactionAlias(String transactionAlias) {
		this.transactionAlias = transactionAlias;
	}

	// @daynamic inject
	//
	public void setTransactionTemplate(
			TransactionTemplateManager transactionTemplateManager) {
		this.transactionTemplate = transactionTemplateManager
				.getTransactionTemplate(transactionAlias);
	}
	
	protected void setServerRoot(String path) {
		_serverRoot = path;
	}
	
	protected String getServerRoot() {
		return _serverRoot;
	}

	@Override
	public boolean isInit() {
		return super.isInit() && transactionTemplate != null;
	}
	
	public String getRealPath() {
		return realPath;
	}
	
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	
}
