package com.vp.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import com.opensymphony.xwork2.ActionSupport;

public class UpLoadAction extends SuperAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private File file1 ; //具体上传文件的 引用 , 指向临时目录中的临时文件
	private String file1FileName ;  // 上传文件的名字 ,FileName 固定的写法
	private String file1ContentType ; //上传文件的类型， ContentType 固定的写法
	
	
	public File getFile1() {
		return file1;
	}
	public void setFile1(File file1) {
		this.file1 = file1;
	}
	public String getFile1FileName() {
		return file1FileName;
	}
	public void setFile1FileName(String file1FileName) {
		this.file1FileName = file1FileName;
	}
	public String getFile1ContentType() {
		return file1ContentType;
	}
	public void setFile1ContentType(String file1ContentType) {
		this.file1ContentType = file1ContentType;
	}

	public String toUpload(){
		return "upload";
	}
	
	public String uploadExcel() throws Exception{
		String path = ServletActionContext.getServletContext().getRealPath("/upload");
		System.out.println("path==="+path);
		File destFile = new File(path,file1FileName);
		if(destFile.exists()){
			destFile.delete();
		}
		FileUtils.copyFile(file1, destFile);
		System.out.println("destFile==="+destFile);
		System.out.println("destFile==="+destFile.getName());
		System.out.println("destFile==="+destFile.getPath());
		loadRoleInfo(file1FileName);
		
		return "upload";
	}
	public void loadRoleInfo(String uploadFileFileName){ 
		String directory = "/upload";
		String targetDirectory = ServletActionContext.getServletContext().getRealPath(directory);
		File target = new File(targetDirectory,uploadFileFileName);
		List roleList = new ArrayList();
		try{
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			HSSFSheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum()+1;
			for(int i=0;i<rowNum;i++){
               
                Row row = sheet.getRow(i);  
                int cellNum = row.getLastCellNum();  
                for(int j=0;j<cellNum;j++){  
                    Cell cell = row.getCell(j);  
                    String cellValue = null;  
                    System.out.println(cell.getStringCellValue());
                }
                
            }  
            
		}catch(IOException e){  
            e.printStackTrace(); 
		}
	}
	
	
}
