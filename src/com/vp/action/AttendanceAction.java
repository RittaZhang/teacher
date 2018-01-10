package com.vp.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.vp.dao.AttendanceDao;
import com.vp.dao.PageBeanDao;
import com.vp.dao.TeacherDao;
import com.vp.dao.Impl.AttendanceDaoImpl;
import com.vp.dao.Impl.PageBeanDaoImpl;
import com.vp.dao.Impl.TeacherDaoImpl;
import com.vp.model.Attendance;
import com.vp.model.PageBean;
import com.vp.model.Teacher;

public class AttendanceAction extends SuperAction implements ModelDriven<Attendance>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Attendance attendance = new Attendance();
	private int page;
	private int page1;
	private static String orderby = "asc";
	private static String order = "";
	
	@Override
	public Attendance getModel() {
		// TODO Auto-generated method stub
		return this.attendance;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPage1() {
		return page1;
	}
	public void setPage1(int page1) {
		this.page1 = page1;
	}
	/**
	 * 跳转到签到检索页面方法
	 * @return
	 */
	public String attendanceSearch(){
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
		return "attendance_success";
		
	}
	/**
	 * 跳转到签到详细画面方法
	 * @return
	 */
	public String toDetail(){
		String tid = request.getParameter("tid");
		TeacherDao teacherDao = new TeacherDaoImpl();
		Teacher att_teacher = new Teacher();
		att_teacher = teacherDao.getOneTeacher(tid);
		String limit = " order by attendanceDate desc";
		PageBean pageBean = getAttPageBean(att_teacher,5,page1,limit);

		ActionContext ac = ActionContext.getContext();
        ac.put("pageBean", pageBean);
		
        ac.put("att_teacher", att_teacher);
		return "attendance_detail";
	}
	/**
	 * 添加教师签到信息方法
	 * @return
	 */
	public String attendanceUpdate(){
		String[] attendanceDate = request.getParameterValues("attendanceDate");
		String[] attendanceStatus = request.getParameterValues("attendanceStatus");
		String[] remarks = request.getParameterValues("remarks");
		
		String tid = request.getParameter("tid");
		List<Attendance> addAttList = new ArrayList<Attendance>();
		boolean iserror = false;
		AttendanceDao attDao = new AttendanceDaoImpl();
		if(attendanceDate!=null){
			if(attendanceDate.length>1){
				Set<String> dateSet = new HashSet<>(Arrays.asList(attendanceDate));
				if(dateSet.size()<attendanceDate.length){
					iserror = true;
					this.addFieldError("Error", "添加的签到时间有重复！");
				}
			}
			for(Integer i=0;i<attendanceDate.length;i++){
				Attendance att = new Attendance();
				if(!Util.isEmpty(attendanceDate[i])){
					if(attDao.isExist(Date.valueOf(attendanceDate[i]))){
						iserror = true;
						this.addFieldError("Error", "已有该时间签到信息！");
						break;
					}else{
						att.setAttendanceDate(Date.valueOf(attendanceDate[i]));
					}
				}else{
					iserror = true;
					this.addFieldError("Error", "签到时间不能为空！");
					break;
				}
				if(!Util.isEmpty(attendanceStatus[i])){
					
					att.setAttendanceStatus(attendanceStatus[i]);
				}else{
					iserror = true;
					this.addFieldError("Error", "签到状态不能为空！");
					break;
				}
				att.setRemarks(remarks[i]);
				addAttList.add(att);
				
			}
			if(iserror){
				selectAtt();
				request.setAttribute("attendanceDate", attendanceDate);
				request.setAttribute("attendanceStatus", attendanceStatus);
				request.setAttribute("remarks", remarks);
				return "attendance_detail";
			}else{
				if(addAttList.size()>0){
					attDao.addAttendance(addAttList,Integer.valueOf(tid));
				}
				return "update_success";
			}
		}else{
			attendanceSearch();
			return "attendance_success";
		}
	}
	/**
	 * 签到删除方法
	 * @return
	 */
	public String attendanceDelete(){
		//String tid = request.getParameter("tid");
		String aid = request.getParameter("aid");
		AttendanceDao attDao = new AttendanceDaoImpl();
		attDao.deleteAttendance(Integer.valueOf(aid));
//		TeacherDao teacherDao = new TeacherDaoImpl();
//		Teacher att_teacher = new Teacher();
//		att_teacher = teacherDao.getOneTeacher(tid);
//		
//		getAttPageBean(att_teacher,5, page ,"");
//		
//		ActionContext ac = ActionContext.getContext();
//        ac.put("att_teacher", att_teacher);
		selectAtt();
		return "attendance_detail";
		
	}
	/**
	 * 签到详细画面的检索方法
	 * @return
	 */
	public String selectAtt(){
		String tid = request.getParameter("tid");
		String stratDate = request.getParameter("stratDate");
		String endDate = request.getParameter("endDate");
		TeacherDao teacherDao = new TeacherDaoImpl();
		Teacher att_teacher = new Teacher();
		att_teacher = teacherDao.getOneTeacher(tid);
		String limit = "";
		if(!Util.isEmpty(stratDate)){
			limit+=" and a.attendanceDate >= '"+stratDate+"'";
		}
		if(!Util.isEmpty(endDate)){
			limit+=" and a.attendanceDate <= '"+endDate+"'";
		}
		limit += "  order by attendanceDate desc ";
		PageBean pageBean = getAttPageBean(att_teacher,5,page1,limit);
		
		ActionContext ac = ActionContext.getContext();
        ac.put("pageBean", pageBean);
        ac.put("stratDate", stratDate);
        ac.put("endDate", endDate);
        ac.put("att_teacher", att_teacher);
		return "attendance_detail";
	}
	/**
	 * 签到检索页面的分页方法
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
			hql+=" and name like '%"+tName+"%' ";
		}
		hql = hql + sqlorder;
        int allRows = pdao.getAllRowCount(hql);
        
        int totalPage = pageBean.getTotalPages(pageSize, allRows);
        
        int currentPage = pageBean.getCurPage(page);
        
        int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);
        
        List<Teacher> list = (List<Teacher>) pdao.queryByPage(hql, offset, pageSize);
       
        pageBean.setList(list);
        pageBean.setAllRows(allRows);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        
        return pageBean;
    }
	/**
	 * 签到详细页面的分页方法
	 * @param t 教师的对象
	 * @param pageSize 页面分页每页的数据条数
	 * @param page 当前页面的页面数
	 * @param sqlorder sql语句的条件
	 * @return
	 */
	public PageBean getAttPageBean(Teacher t,int pageSize, int page,String sqlorder){
		PageBeanDao pdao = new PageBeanDaoImpl();
        PageBean pageBean = new PageBean();
        
        String hql = "from Attendance a where 1=1 and a.teacher_id = "+t.getId();
        
        hql  = hql + sqlorder;
        
        int allRows = pdao.getAllRowCount(hql);
        
        int totalPage = pageBean.getTotalPages(pageSize, allRows);
        
        int currentPage = pageBean.getCurPage(page);
        
        int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);
        //查询当前页面的数据
        List<Attendance> list = (List<Attendance>) pdao.queryByPage(hql, offset, pageSize);
        //查询所有页面的数据
        List<Attendance> list1 = (List<Attendance>) pdao.queryByPage(hql, 0, allRows);
        
        pageBean.setList(list);
        pageBean.setAllRows(allRows);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        
        return pageBean;
    }
	

}
