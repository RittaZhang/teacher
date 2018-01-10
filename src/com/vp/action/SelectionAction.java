package com.vp.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.vp.dao.PageBeanDao;
import com.vp.dao.SelectionDao;
import com.vp.dao.TeacherDao;
import com.vp.dao.Impl.PageBeanDaoImpl;
import com.vp.dao.Impl.SelectionDaoImpl;
import com.vp.dao.Impl.TeacherDaoImpl;
import com.vp.model.Selection;
import com.vp.model.PageBean;
import com.vp.model.Teacher;


public class SelectionAction extends SuperAction implements ModelDriven<Selection>{
	private static final long serialVersionUID = 1L;
	private int page;
	Util util = new Util(); 
	private Selection selection = new Selection();
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public Selection getModel() {
		// TODO Auto-generated method stub
		return this.selection;
	}
	
	public String addSchool(){
		String school = request.getParameter("school");
		String tid = request.getParameter("tid");
		if(!school.isEmpty()){
			SelectionDao selectDao = new SelectionDaoImpl();
			Selection sel = new Selection();
			sel.setGroupname("毕业院校");
			sel.setOptionvalue(school);
			selectDao.saveSchool(sel);
		}
		List<String> allSchool = util.getAllValueByName("毕业院校");
		List<String> allMajor = util.getAllValueByName("所学专业");
		List<String> allDegree = util.getAllValueByName("学历层次");
		TeacherDao teacherDao = new TeacherDaoImpl();
		Teacher edu_teacher = new Teacher();
		edu_teacher = teacherDao.getOneTeacher(tid);
		ActionContext ac = ActionContext.getContext();
        ac.put("edu_teacher", edu_teacher);
        request.setAttribute("allSchool", allSchool);
        request.setAttribute("allMajor", allMajor);
        request.setAttribute("allDegree", allDegree);
		return "education_edit";
	}
	
	public String query(){
		SelectionDao sdao = new SelectionDaoImpl();
		List<String> groupList = sdao.queryAllGroupname();
		if(groupList!=null&&groupList.size()>0){
			request.setAttribute("groupName_list", groupList);
		}
		String groupname = request.getParameter("groupname");
		System.out.println("groupname初始化-------"+groupname);
		if(groupname==null||groupname.equals("")){
			groupname = "毕业院校";
		}
		Selection searchModel = new Selection();
		searchModel.setGroupname(groupname);
		PageBean pageBean = getPageBean(searchModel,5, page);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("groupname_value", groupname);
		return "query_success";
	}
	
	public String addSelection(){
		SelectionDao sdao = new SelectionDaoImpl();
		List<Selection> searchSelections = null;
		String groupname = request.getParameter("groupname");
		System.out.println("groupname-------"+groupname);
		String optionvalue = request.getParameter("optionvalue");
		if(optionvalue!=null && optionvalue!=""){
			if(!sdao.isExistValue(optionvalue)){
				Selection sel = new Selection();
				sel.setGroupname(groupname);
				sel.setOptionvalue(optionvalue);
				sdao.addSelection(sel);
				 
			}else{
				this.addFieldError("errorMessage", "输入的选项值已存在，请重新输入！");
				query();
				return "add_failure";
			}
			
		}
		query();
		return "query_success";
		
	}
	
	public String modify(){
		SelectionDao sdao = new SelectionDaoImpl();
		String sid = request.getParameter("sid");
		selection = sdao.querySelectionById(Integer.parseInt(sid));

		System.out.println("aaaa0-------"+selection.getId());
		session.setAttribute("modify_selection", selection);
		return "modify_success";
	}
	
	public String update(){
		SelectionDao sdao = new SelectionDaoImpl();
		System.out.println("aaaa1-------"+selection.getId());
		sdao.updateSelection(selection);
		return "update_success";
	}
	//分页功能
	public PageBean getPageBean(Selection searchModel, int pageSize, int page){
		PageBeanDao pdao = new PageBeanDaoImpl();
        PageBean pageBean = new PageBean();
        String groupName = searchModel.getGroupname();
        String hql = "from Selection where 1=1";
		if(groupName!=null && groupName!=""){
			hql+=" and groupName='"+groupName+"' ";
		}
  
        int allRows = pdao.getAllRowCount(hql);
        
        int totalPage = pageBean.getTotalPages(pageSize, allRows);
        
        int currentPage = pageBean.getCurPage(page);
        
        int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);
        
        List<Selection> list = (List<Selection>) pdao.queryByPage(hql, offset, pageSize);
        
        pageBean.setList(list);
        pageBean.setAllRows(allRows);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        
        return pageBean;
    }

}
