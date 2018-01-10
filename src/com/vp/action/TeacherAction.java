package com.vp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.vp.dao.AttendanceDao;
import com.vp.dao.DepartmentDao;
import com.vp.dao.EducationDao;
import com.vp.dao.PageBeanDao;
import com.vp.dao.TeacherDao;
import com.vp.dao.Impl.AttendanceDaoImpl;
import com.vp.dao.Impl.DepartmentDaoImpl;
import com.vp.dao.Impl.EducationDaoImpl;
import com.vp.dao.Impl.PageBeanDaoImpl;
import com.vp.dao.Impl.TeacherDaoImpl;
import com.vp.db.MyHibernateSessionFactory;
import com.vp.model.Attendance;
import com.vp.model.Department;
import com.vp.model.Education;
import com.vp.model.PageBean;
import com.vp.model.Teacher;

public class TeacherAction extends SuperAction implements ModelDriven<Teacher>{
	
	private static final long serialVersionUID = 1L;
	private int page;
	private Teacher teacher = new Teacher();
	private static List<Teacher> shareList = new ArrayList<Teacher>();
	private static String orderby = "asc";
	private static String order = "";
	Util util = new Util(); 
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	@Override
	public Teacher getModel() {
		// TODO Auto-generated method stub
		return this.teacher;
	}
	/**
	 * 教师查询界面方法，通过获取页面的条件参数来查询所有的教师并分页；
	 * @pageBean 分页对象
	 * @tname 页面教师名字查询条件.
	 * @depart1 页面一级部门查询条件
	 * @depart2 页面二级部门查询条件
	 * @title 页面职称查询条件
	 * @rank 页面行政名称查询条件
	 * 
	 */
	public String teacherSearch(){
		String tname = request.getParameter("tname")==null?"":request.getParameter("tname");
		String depart1 = request.getParameter("department1")==null?"":request.getParameter("department1");
		String depart2 = request.getParameter("department2")==null?"":request.getParameter("department2");
		String title = request.getParameter("ttitle")==null?"":request.getParameter("ttitle");
		String rank = request.getParameter("trank")==null?"":request.getParameter("trank");
		order = request.getParameter("order")==null?order:request.getParameter("order");
		System.out.println("order===="+order);
		System.out.println("rank===="+rank);
		DepartmentDao departdao = new DepartmentDaoImpl();
		List<Integer> departIdList = new ArrayList<Integer>();
//		TeacherDao teacherDao = new TeacherDaoImpl();
		Teacher t = new Teacher();
		t.setName(tname);
		t.setTitle(title);
		t.setRank(rank);
			if(depart2.isEmpty() && !depart1.isEmpty() ){
				List<Integer> depart1Id = new ArrayList<Integer>();
				depart1Id.add(Integer.valueOf(depart1));
				departIdList = departdao.getDepartment2IdByDepart1Id(depart1Id);
				departIdList.add(Integer.valueOf(depart1));
			}else if(!depart2.isEmpty() && !depart1.isEmpty()){
				departIdList.add(Integer.valueOf(depart2));
			}
		ActionContext ac = ActionContext.getContext();
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
		PageBean pageBean = getPageBean(departIdList,t,15, page,sqlorder);
		ac.put("pageBean", pageBean);
		
		request.setAttribute("tname", tname);
		request.setAttribute("depart1", depart1);
		request.setAttribute("depart2", depart2);
		request.setAttribute("title", title);
		request.setAttribute("rank", rank);
		return "teacher_success";
	}
	/**
	 * 新添加教师页面提交按钮方法
	 * @depart1 页面选择的一级部门
	 * @depart2 页面选择的二级部门
	 * @return
	 */
	public String add(){
		TeacherDao teacherDao = new TeacherDaoImpl();
		String depart1 = request.getParameter("depart1");
		String depart2 = request.getParameter("depart2");
		DepartmentDao departDao = new DepartmentDaoImpl();
		boolean iserror = false;
		if(depart2.isEmpty()&& !depart1.isEmpty()){
			teacher.setDepartment(departDao.queryDepartmentById(Integer.valueOf(depart1)));
		}else if(!depart2.isEmpty()){
			teacher.setDepartment(departDao.queryDepartmentById(Integer.valueOf(depart2)));
		}
		if(teacher.getCode().trim().equals("")){
			this.addFieldError("codeError", "教师编号不能为空！");
			//return "save_fail";
			iserror = true;
		}
		if(teacher.getCode().getBytes().length>20){
			this.addFieldError("codeError", "教师编号过长！");
			//return "save_fail";
			iserror = true;
		}
		if(teacher.getName().trim().equals("")){
			this.addFieldError("nameError", "教师姓名不能为空！");
			//return "save_fail";
			iserror = true;
		}
		if(teacher.getName().getBytes().length>50){
			this.addFieldError("nameError", "教师姓名过长！");
			//return "save_fail";
			iserror = true;
		}
		if(teacher.getDepartment()==null){
			this.addFieldError("departmentError", "教师部门不能为空！");
			//return "save_fail";
			iserror = true;
		}
		if(iserror){
			request.setAttribute("teacher", teacher);
			return "save_fail";
		}else{
			if(!teacherDao.isExist(teacher)){
				teacherDao.add(teacher);
				teacherSearch();
				return "save_success";
			}else{
				this.addFieldError("errorMessage", "输入的教师编号有误，请重新输入！");
				request.setAttribute("teacher", teacher);
				return "save_fail";
			}
		}
	}
	
	/**
	 * 删除教师方法
	 * @return
	 */
	public String delete(){
		return null;
	}
	
	/**
	 * 跳转到教师详细页面方法
	 * @tid 教师的id
	 * @return
	 */
	public String toDetail(){
		String tid = request.getParameter("tid");
		TeacherDao teacherDao = new TeacherDaoImpl();
		teacher = teacherDao.getOneTeacher(tid);
		System.out.println("tid===="+tid);
		System.out.println("teacher.name===="+teacher.getName());
		if(teacher!=null){
			session.setAttribute("modify_teacher", teacher);
		}else{
			
		}
		return "detail";
	}
	
	/**
	 * 跳转到教师编辑画面的方法
	 * @tid 教师的id
	 * @return
	 */
	public String toupdate(){
		String tid = request.getParameter("tid");
		TeacherDao teacherDao = new TeacherDaoImpl();
		teacher = teacherDao.getOneTeacher(tid);
		System.out.println("tid===="+tid);
		if(teacher!=null){
			session.setAttribute("modify_teacher", teacher);
		}else{
			
		}
		request.setAttribute("teacher", teacher);
		return "modify";
	}
	
	/**
	 * 更新教师页面的提交方法
	 * @depart1 页面选择的一级部门
	 * @depart2 页面选择的二级部门
	 * @return
	 */
	public String update(){
		String depart1 = request.getParameter("depart1");
		String depart2 = request.getParameter("depart2");
		TeacherDao teacherDao = new TeacherDaoImpl();
		DepartmentDao departDao = new DepartmentDaoImpl();
		EducationDao edao = new EducationDaoImpl();
		AttendanceDao attDao = new AttendanceDaoImpl();
		Set<Education> educations = edao.getEducationsByTId(teacher.getId());
		Set<Attendance> attendances = attDao.getAttendancesByTid(teacher.getId());
		teacher.setEducations(educations);
		teacher.setAttendances(attendances);
		if(depart2.isEmpty()&& !depart1.isEmpty()){
			teacher.setDepartment(departDao.queryDepartmentById(Integer.valueOf(depart1)));
		}else if(!depart2.isEmpty()){
			teacher.setDepartment(departDao.queryDepartmentById(Integer.valueOf(depart2)));
		}
		teacherDao.updateOneTeacher(teacher);
		teacherSearch();
		return "update_success";
	}
	
	
	/**
	 * 教师查询画面的批量处理提交方法
	 * @checkbox 需要更新的教师id数组
	 * @title @rank @depart1 @depart2  需要更新的字段
	 * @return
	 */
	public String teacherBatch(){
		String[] checkbox = request.getParameterValues("checkbox");
		String title = request.getParameter("title-1");
		String rank = request.getParameter("rank-1");
		String depart1 = request.getParameter("depart1-1");
		String depart2 = request.getParameter("depart2-1");
		TeacherDao teacherDao = new TeacherDaoImpl();
		DepartmentDao departdao = new DepartmentDaoImpl();
		List<Integer> tidList = new ArrayList<Integer>();
		Department department = null;
		System.out.println("title+++=="+title);
		System.out.println("rank+++=="+rank);
		System.out.println("checkbox="+checkbox);
		if(checkbox!=null && checkbox.length>0){
			for(Integer i=0;i<checkbox.length;i++){
				System.out.println("checkbox==="+checkbox[i]);
				tidList.add(Integer.valueOf(checkbox[i]));
			}
			List<Teacher> teacherList = teacherDao.getTeacherByIdlist(tidList);
			if(!depart1.isEmpty() && depart2.isEmpty()){
				department = departdao.queryDepartmentById(Integer.valueOf(depart1));
			}else if(!depart1.isEmpty() && !depart2.isEmpty()){
				department = departdao.queryDepartmentById(Integer.valueOf(depart2));
			}
			for(Teacher t:teacherList){
				if(!depart1.isEmpty() || !depart2.isEmpty()){
					t.setDepartment(department);
				}
				if(!title.isEmpty()){
					t.setTitle(title);
				}
				if(!rank.isEmpty()){
					t.setRank(rank);
				}
				teacherDao.updateOneTeacher(t);
			}
		}
		teacherSearch();
		return "teacher_success";
	}
	
	/**
	 * 获取教师花名册的方法并分页
	 * 
	 * @return
	 */
	public String getTeacherInfo(){
		List<Integer> departId = new ArrayList<Integer>();
		Teacher t = new Teacher();
        PageBean pageBean = getPageBean(departId,t,15, page,"");
        System.out.println("pageBean.list------"+pageBean.getList().size());
        ActionContext ac = ActionContext.getContext();
        ac.put("pageBean", pageBean);
		return "teacherInfo_success";
	}
	//测试插入1000条数据
	public String testInsert() {
		long begin = System.currentTimeMillis(); 
		DepartmentDao ddao = new DepartmentDaoImpl();
		Department dept = ddao.queryDepartmentById(12);
		System.out.println("dept------"+dept);
		TeacherDao teacherDao = new TeacherDaoImpl();
		EducationDao edao = new EducationDaoImpl();
		for(int i=0;i<1000;i++){
			Teacher teacher = new Teacher();
			teacher.setCode(String.valueOf(1000+i));
			teacher.setName("姓名"+i);
			teacher.setDepartment(dept);
			teacher.setGender("男");
			teacher = teacherDao.add1(teacher);
			List<Education> eList = new ArrayList<Education>();
			Education education = new Education();
			education.setGraduateInstitutions("吉林大学");
			education.setMajor("网络工程");
			education.setDegree("本科");
			education.setGraduateDate(new Date());
			eList.add(education);
			Education education1 = new Education();
			education1.setGraduateInstitutions("吉林大学1");
			education1.setMajor("网络工程1");
			education1.setDegree("本科1");
			education1.setGraduateDate(new Date());
			eList.add(education1);
			edao.addEducation(eList, teacher.getId());
		}
		long end = System.currentTimeMillis();  
	    System.out.println("插入数据所用时间------"+(end-begin)/1000.0);  
		return "teacher_success";
	}

	/**
	 * 
	 * @param departId 教师关联的部门id list
	 * @param t 获取教师的相关条件对象承载体
	 * @param pageSize 页面分页每页的数据条数
	 * @param page 当前页面的页面数
	 * @return
	 */
	public PageBean getPageBean(List<Integer> departId,Teacher t,int pageSize, int page,String sqlorder){
		PageBeanDao pdao = new PageBeanDaoImpl();
        PageBean pageBean = new PageBean();
        
        String hql = "from Teacher t where 1=1 ";
		if(departId.size()>0){
			hql+=" and (";
		}
		
		for(Integer i=0;i<departId.size();i++){
			if(i==0){
				hql += " t.department.id ="+departId.get(i);
			}else{
				hql += " or t.department.id ="+departId.get(i);
			}
		}
		if(departId.size()>0){
			hql+=") ";
		}
		if(t.getName()!=null && !t.getName().isEmpty()){
			hql += " and t.name like '%"+t.getName()+"%'";
		}
		if(t.getTitle()!=null && !t.getTitle().isEmpty()){
			hql += " and t.title ='"+t.getTitle()+"'";
		}
		if(t.getRank()!=null && !t.getRank().isEmpty()){
			hql += " and t.rank ='"+t.getRank()+"'";
		}
		System.out.println("hql----------"+hql);
		hql = hql + sqlorder;
        
        int allRows = pdao.getAllRowCount(hql);
        
        int totalPage = pageBean.getTotalPages(pageSize, allRows);
        
        int currentPage = pageBean.getCurPage(page);
        
        int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);
        //查询当前页面的数据
        List<Teacher> list = (List<Teacher>) pdao.queryByPage(hql, offset, pageSize);
        //查询所有页面的数据
        List<Teacher> list1 = (List<Teacher>) pdao.queryByPage(hql, 0, allRows);
        //用于下载的list
        shareList = list1;
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
		String[] checkbox = request.getParameterValues("checkbox1");
		List<String> checkbox1 = new ArrayList<String>();
		Collections.addAll(checkbox1, checkbox);
		boolean b = checkbox1.contains("姓名");
		try {
            //第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet
            HSSFSheet sheet = wb.createSheet("测试表格1");
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制
            HSSFRow row = sheet.createRow(0);
            HSSFRow row1 = sheet.createRow(1);
            //第四步，创建单元格样式：居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            //第五步，创建表头单元格，并设置样式
            HSSFCell cell;
            Integer c = 0;
            if(checkbox1.contains("部门")){
	            cell = row.createCell(c);
	            cell.setCellValue("部门");
	            cell.setCellStyle(style);
	            sheet.addMergedRegion(new CellRangeAddress(0,0,c,c+1));
	            cell = row1.createCell(c);
	            cell.setCellValue("部门1");
	            cell.setCellStyle(style);
	            cell = row1.createCell(c+1);
	            cell.setCellValue("部门2");
	            cell.setCellStyle(style);
	            c=c+2;
            }
            if(checkbox1.contains("姓名")){
	            cell = row.createCell(c);
	            cell.setCellValue("姓名");
	            cell.setCellStyle(style);
	            if(checkbox1.contains("部门")||checkbox1.contains("学历")||checkbox1.contains("行政职务")){
	            	sheet.addMergedRegion(new CellRangeAddress(0,1,c,c));
	            }
	            c=c+1;
            }
            if(checkbox1.contains("性别")){
	            cell = row.createCell(c);
	            cell.setCellValue("性别");
	            cell.setCellStyle(style);
	            if(checkbox1.contains("部门")||checkbox1.contains("学历")||checkbox1.contains("行政职务")){
	            	sheet.addMergedRegion(new CellRangeAddress(0,1,c,c));
	            }
	            c=c+1;
            }
            if(checkbox1.contains("出生日期")){
	            cell = row.createCell(c);
	            cell.setCellValue("出生日期");
	            cell.setCellStyle(style);
	            if(checkbox1.contains("部门")||checkbox1.contains("学历")||checkbox1.contains("行政职务")){
	            	sheet.addMergedRegion(new CellRangeAddress(0,1,c,c));
	            }
	            c=c+1;
            }
            if(checkbox1.contains("学历")){
	            cell = row.createCell(c);
	            cell.setCellValue("学历");
	            cell.setCellStyle(style);
	            sheet.addMergedRegion(new CellRangeAddress(0,0,c,c+2));
	            cell = row1.createCell(c);
	            cell.setCellValue("毕业院校");
	            cell.setCellStyle(style);
	            cell = row1.createCell(c+1);
	            cell.setCellValue("所学专业");
	            cell.setCellStyle(style);
	            cell = row1.createCell(c+2);
	            cell.setCellValue("学历层次");
	            cell.setCellStyle(style);
	            c=c+3;
            }
            if(checkbox1.contains("职称")){
	            cell = row.createCell(c);
	            cell.setCellValue("职称");
	            cell.setCellStyle(style);
	            if(checkbox1.contains("部门")||checkbox1.contains("学历")||checkbox1.contains("行政职务")){
	            	sheet.addMergedRegion(new CellRangeAddress(0,1,c,c));
	            }
	            c=c+1;
            }
            if(checkbox1.contains("晋职时间")){
	            cell = row.createCell(c);
	            cell.setCellValue("晋职时间");
	            cell.setCellStyle(style);
	            if(checkbox1.contains("部门")||checkbox1.contains("学历")||checkbox1.contains("行政职务")){
	            	sheet.addMergedRegion(new CellRangeAddress(0,1,c,c));
	            }
	            c=c+1;
            }
            if(checkbox1.contains("行政职务")){
	            cell = row.createCell(c);
	            cell.setCellValue("行政职务");
	            cell.setCellStyle(style);
	            sheet.addMergedRegion(new CellRangeAddress(0,0,c,c+1));
	            cell = row1.createCell(c);
	            cell.setCellValue("级别名称");
	            cell.setCellStyle(style);
	            cell = row1.createCell(c+1);
	            cell.setCellValue("任职时间");
	            cell.setCellStyle(style);
	            c=c+2;
            }
            

            //第六步，写入实体数据，实际应用中这些数据从数据库得到
            
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("shareList.size()====="+shareList.size());
            Integer m ;//实体数据初始行数
            if(checkbox1.contains("部门")||checkbox1.contains("学历")||checkbox1.contains("行政职务")){
            	m = 2;
            }else{
            	m = 1;
            }
    		for(Integer i=0;i<shareList.size();i++){
    			
                Integer n=0;//数据初始列数
    			
    			Teacher t = shareList.get(i);
    			//教师学历个数
    			Integer j = t.getEducations().size();
    			if(j==0|| !checkbox1.contains("学历")){
    				j=1;
    			}
    			
				row = sheet.createRow(m);
				if(checkbox1.contains("部门")){
					if(t.getDepartment().getDepartmentLevel().equals("一级")){
						cell = row.createCell(n);
						cell.setCellValue(t.getDepartment().getDepartmentName());
						cell.setCellStyle(style);
						cell = row.createCell(n+1);
						cell.setCellValue("");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(n);
						cell.setCellValue(t.getDepartment().getFatherDept().getDepartmentName());
						cell.setCellStyle(style);
						cell = row.createCell(n+1);
						cell.setCellValue(t.getDepartment().getDepartmentName());
						cell.setCellStyle(style);
					}
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n,n));
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n+1,n+1));
					n=n+2;
				}
				if(checkbox1.contains("姓名")){
					cell = row.createCell(n);
					cell.setCellValue(t.getName());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n,n));
					n=n+1;
				}
				if(checkbox1.contains("性别")){
					cell = row.createCell(n);
					cell.setCellValue(t.getGender());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n,n));
					n=n+1;
				}
				if(checkbox1.contains("出生日期")){
					if(t.getBirthday()!=null){
						cell = row.createCell(n);
						cell.setCellValue(fmt.format(t.getBirthday()));
						cell.setCellStyle(style);
					}else{
						row.createCell(n).setCellValue("");
					}
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n,n));
					n=n+1;
				}
				if(checkbox1.contains("学历")){
					Integer eNo=0;
					for(Education e:t.getEducations()){
						cell = row.createCell(n);
						cell.setCellValue(e.getGraduateInstitutions());
						cell.setCellStyle(style);
						cell = row.createCell(n+1);
						cell.setCellValue(e.getMajor());
						cell.setCellStyle(style);
						cell = row.createCell(n+2);
						cell.setCellValue(e.getDegree());
						cell.setCellStyle(style);
						eNo ++;
						row = sheet.createRow(m+eNo);
					}
					if(j==0){
						row.createCell(n).setCellValue("");
	    				row.createCell(n+1).setCellValue("");
	    				row.createCell(n+2).setCellValue("");
					}
					n=n+3;
				}
				if(checkbox1.contains("职称")){
					cell = row.createCell(n);
					cell.setCellValue(t.getTitle());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n,n));
					n=n+1;
				}
				if(checkbox1.contains("晋职时间")){
					if(t.getTitleDate()!=null){
						cell = row.createCell(n);
						cell.setCellValue(fmt.format(t.getTitleDate()));
						cell.setCellStyle(style);
					}else{
						row.createCell(n).setCellValue("");
					}
					
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n,n));
					n=n+1;
				}
				if(checkbox1.contains("行政职务")){
					cell = row.createCell(n);
					cell.setCellValue(t.getRank());
					cell.setCellStyle(style);
					if(t.getRankDate()!=null){
						cell = row.createCell(n+1);
						cell.setCellValue(fmt.format(t.getRankDate()));
						cell.setCellStyle(style);
					}else{
						row.createCell(n+1).setCellValue("");
					}
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n,n));
					sheet.addMergedRegion(new CellRangeAddress(m,m+j-1,n+1,n+1));
				}
				m=m+j;
    			
    		}
    		
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
