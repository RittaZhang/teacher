package com.vp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.vp.dao.EducationDao;
import com.vp.dao.PageBeanDao;
import com.vp.dao.TeacherDao;
import com.vp.dao.Impl.EducationDaoImpl;
import com.vp.dao.Impl.PageBeanDaoImpl;
import com.vp.dao.Impl.TeacherDaoImpl;
import com.vp.model.Education;
import com.vp.model.PageBean;
import com.vp.model.Teacher;

public class EducationAction extends SuperAction implements ModelDriven<Education>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Education education = new Education();
	private int page;
	private static List<Teacher> shareList = new ArrayList<Teacher>();
	private static String orderby = "asc";
	private static String order = "";
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	@Override
	public Education getModel() {
		// TODO Auto-generated method stub
		return this.education;
	}
	
	/**
	 * 学历页面初始方法，和查询方法
	 * @tname @tcode 查询条件
	 * @return
	 */
	public String educationSearch(){
		String tname = request.getParameter("tname");
		String tcode = request.getParameter("tcode");
		order = request.getParameter("order")==null?order:request.getParameter("order");
		System.out.println("order===="+order);
		Teacher searchModel = new Teacher();
		searchModel.setName(tname);
		searchModel.setCode(tcode);
		if(request.getParameter("order")!=null&&request.getParameter("order")!=""){
			if(orderby=="asc"){
				orderby="desc";
			}else{
				orderby="asc";
			}
		}
		
		if(order==null||order==""){
			order = "id";
		}
		String sqlorder = " order by "+order+" "+orderby;
		PageBean pageBean = getPageBean(searchModel,15, page,sqlorder);
		ActionContext ac = ActionContext.getContext();
        ac.put("pageBean", pageBean);
        ac.put("tname", tname);
        ac.put("tcode", tcode);
		return "education_search";
	}
	
	/**
	 * 跳转到学历编辑画面的方法
	 * @tid 教师id
	 * @return
	 */
	public String educationEdit(){
		String tid = request.getParameter("tid");
		TeacherDao teacherDao = new TeacherDaoImpl();
		Teacher edu_teacher = new Teacher();
		edu_teacher = teacherDao.getOneTeacher(tid);
		ActionContext ac = ActionContext.getContext();
        ac.put("edu_teacher", edu_teacher);
		return "education_edit";
		
	}
	
	/**
	 * 学历编辑画面提交按钮方法
	 * @graduate @major @degree @eid @graduateDate 通过页面传过来的额数组
	 * @return
	 */
	public String educationUpdate(){
		String[] graduate = request.getParameterValues("graduate");
		String[] major = request.getParameterValues("major");
		String[] degree = request.getParameterValues("degree");
		String[] graduateDate = request.getParameterValues("graduateDate");
		String tid = request.getParameter("tid");
		EducationDao eDao = new EducationDaoImpl();
		List<Education> addEduction = new ArrayList<Education>();
		boolean iserror = false;
		if(graduate!=null){
			for(Integer i=0;i<graduate.length;i++){
				Education education = new Education();
				if(!Util.isEmpty(graduate[i])){
					education.setGraduateInstitutions(graduate[i]);
				}else{
					this.addFieldError("error", "毕业院校不为空！");
					iserror = true;
					break;
				}
				if(!Util.isEmpty(degree[i])){
					education.setDegree(degree[i]);
				}else{
					this.addFieldError("error", "专业不可为空！");
					iserror = true;
					break;
				}
				if(!Util.isEmpty(major[i])){
					education.setMajor(major[i]);
				}else{
					this.addFieldError("error", "学历层次不可为空！");
					iserror = true;
					break;
				}
				if(!Util.isEmpty(graduateDate[i])){
					education.setGraduateDate(Date.valueOf(graduateDate[i]));
				}else{
					this.addFieldError("error", "毕业时间不为空！");
					iserror = true;
					break;
				}
				
				addEduction.add(education);
			}
		}
		if(iserror){
			educationEdit();
			request.setAttribute("graduate", graduate);
			request.setAttribute("major", major);
			request.setAttribute("degree", degree);
			request.setAttribute("graduateDate", graduateDate);
			return "education_edit";
		}else{
			if(addEduction.size()>0){
				eDao.addEducation(addEduction, Integer.valueOf(tid));
			}
			educationSearch();
			return "education_search";
		}
	}
	
	/**
	 * 学历编辑画面删除按钮方法
	 * @tid 教师id
	 * @eid 学历id
	 * @return
	 */
	public String educationDelete(){
		String tid = request.getParameter("tid");
		String eid = request.getParameter("eid");
		System.out.println("eid=="+eid);
		EducationDao eDao = new EducationDaoImpl();
		eDao.deleteEducation(Integer.valueOf(eid));
		
		TeacherDao teacherDao = new TeacherDaoImpl();
		Teacher edu_teacher = new Teacher();
		edu_teacher = teacherDao.getOneTeacher(tid);
		ActionContext ac = ActionContext.getContext();
        ac.put("edu_teacher", edu_teacher);
		return "education_edit";
	}
	
	
	/**
	 * 
	 * @param searchModel 查询条件的承载体
	 * @param pageSize 每页的数据数量
	 * @param page 当前页面数
	 * @return
	 */
	public PageBean getPageBean(Teacher searchModel, int pageSize, int page,String sqlorder){
		PageBeanDao pdao = new PageBeanDaoImpl();
        PageBean pageBean = new PageBean();
        String tCode = searchModel.getCode();
        String tName = searchModel.getName();
       
        String hql = "from Teacher where 1=1";
        if(tCode!=null && tCode!=""){
        	hql+=" and code='"+tCode+"' ";
		}
		if(tName!=null && tName!=""){
			hql+=" and name='"+tName+"' ";
		}
		hql = hql + sqlorder;
        int allRows = pdao.getAllRowCount(hql);
        
        int totalPage = pageBean.getTotalPages(pageSize, allRows);
        
        int currentPage = pageBean.getCurPage(page);
        
        int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);
        
        List<Teacher> list = (List<Teacher>) pdao.queryByPage(hql, offset, pageSize);
        List<Teacher> alllist = (List<Teacher>) pdao.queryByPage(hql, 0, allRows);
        shareList = alllist;
        pageBean.setList(list);
        pageBean.setAllRows(allRows);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        
        return pageBean;
    }
	
	/**
	 * 下载excel文件方法
	 * @return
	 */
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
            cell.setCellValue("学历");
            cell.setCellStyle(style);
            
            //合并单元格 （1,2,3,4）代表起始行，结束行，起始列，结束列，从0开始
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
            sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
            sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
            
            row = sheet.createRow(1);
            
        	cell = row.createCell(2);
            cell.setCellStyle(style);  
            cell.setCellValue("毕业院校");
            
            cell = row.createCell(3);
            cell.setCellStyle(style);  
            cell.setCellValue("所学专业");
            
            cell = row.createCell(4);
            cell.setCellStyle(style);  
            cell.setCellValue("学历层次");
            

            //第六步，写入实体数据，实际应用中这些数据从数据库得到
//            Date today = new Date();
//            long aDay = 1000L*60*60*24;
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("shareList.size()====="+shareList.size());
            Integer m = 2; //初始数据行数
    		for(Integer i=0;i<shareList.size();i++){
    			Teacher t = shareList.get(i);
    			//教师学历个数
    			Integer j = t.getEducations().size();
    			
    			if(j>0){
					row = sheet.createRow(m);
					row.createCell(0).setCellValue(t.getCode());
					row.createCell(1).setCellValue(t.getName());
					Integer eNo=0;
					for(Education e:t.getEducations()){
	    				row.createCell(2).setCellValue(e.getGraduateInstitutions());
	    				row.createCell(3).setCellValue(e.getMajor());
	    				row.createCell(4).setCellValue(e.getDegree());
	    				sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,0,0));
						sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,1,1));
						eNo ++;
						row = sheet.createRow(m+eNo);
					}
					m=m+j;
    			}else{
    				row = sheet.createRow(m);
    				row.createCell(0).setCellValue(t.getCode());
    				row.createCell(1).setCellValue(t.getName());
    				row.createCell(2).setCellValue("");
    				row.createCell(3).setCellValue("");
    				row.createCell(4).setCellValue("");
    				m=m+1;
    			}
    		}
//            
    		
            //将文件存到流中
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] fileContent = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);

            excelStream = is;             //文件流
            excelFileName = "xueli.xls"; //设置下载的文件名
        }catch(Exception e) {
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
