package com.vp.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.vp.dao.TeacherDao;
import com.vp.dao.Impl.TeacherDaoImpl;
import com.vp.model.Teacher;

public class excelExportAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/** 导出Excel测试 */
	public String exportExcel() {
        try {
            //第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet
            HSSFSheet sheet = wb.createSheet("测试表格1");
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);
            //第四步，创建单元格样式：居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //第五步，创建表头单元格，并设置样式
            HSSFCell cell;

            cell = row.createCell(0);
            cell.setCellValue("员工工号");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("员工姓名");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("所属部门");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("职位");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("入职日期");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("电话");
            cell.setCellStyle(style);

            //第六步，写入实体数据，实际应用中这些数据从数据库得到
            Date today = new Date();
            long aDay = 1000L*60*60*24;
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            TeacherDao teacherDao = new TeacherDaoImpl();
    		List<Teacher> allTeachers = teacherDao.getAllTeacher();
    		for(Integer i=0;i<allTeachers.size();i++){
    			Teacher t = allTeachers.get(i);
    			row = sheet.createRow(i+1);
    			row.createCell(0).setCellValue(t.getCode());
    			row.createCell(1).setCellValue(t.getName());
    			row.createCell(2).setCellValue(t.getDepartment().getDepartmentName());
    			row.createCell(3).setCellValue(t.getTitle());
    			if(t.getBirthday()==null){
    				row.createCell(4).setCellValue("");
    			}else{
    				row.createCell(4).setCellValue(fmt.format(t.getBirthday()));
    			}
    			row.createCell(5).setCellValue(t.getPhone());
    		}
//            for (int i = 1; i <= 10; i++) {
//                row = sheet.createRow(i);
//                row.createCell(0).setCellValue(i);
//                row.createCell(1).setCellValue("员工" + i);
//                row.createCell(2).setCellValue("总公司");
//                row.createCell(3).setCellValue("普通员工");
//                row.createCell(4).setCellValue(fmt.format(new Date(today.getTime() + i * aDay)));
//                row.createCell(5).setCellValue("员工备注");
//            }
            //第七步，将文件存到流中
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] fileContent = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);

            excelStream = is;             //文件流
            excelFileName = "xxxxxxx.xls"; //设置下载的文件名
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return "success";
    }


    //-------------------------------------------------------------
    private InputStream excelStream;  //输出流变量
    private String excelFileName; //下载文件名

    public InputStream getExcelStream() {
        return excelStream;
    }
    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }
    public String getExcelFileName() {
        return excelFileName;
    }
    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }
    

}
