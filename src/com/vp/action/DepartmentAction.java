package com.vp.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.vp.dao.DepartmentDao;
import com.vp.dao.PageBeanDao;
import com.vp.dao.Impl.DepartmentDaoImpl;
import com.vp.dao.Impl.PageBeanDaoImpl;
import com.vp.model.Department;
import com.vp.model.PageBean;
import com.vp.model.Teacher;


public class DepartmentAction extends SuperAction implements ModelDriven<Department>{
	private static final long serialVersionUID = 1L;
	private int page;
    private Department department = new Department();
    private static String orderby = "asc";
    private static String order = "";
    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page = page;
    }
	@Override
	public Department getModel() {
		// TODO Auto-generated method stub
		return this.department;
	}
	/**
	 * 部门查询界面方法，通过获取页面的条件参数来查询所有的部门并分页；
	 */
	public String query(){
		String departmentCode = request.getParameter("departmentCode");
		String departmentName = request.getParameter("departmentName");
		String departmentLevel = request.getParameter("departmentLevel");
		System.out.println("departmentLevel-------"+departmentLevel);
		order = request.getParameter("order")==null?order:request.getParameter("order");
		System.out.println("order===="+order);
		Department searchModel = new Department(); 
		searchModel.setDepartmentCode(departmentCode);
		searchModel.setDepartmentName(departmentName);
		searchModel.setDepartmentLevel(departmentLevel);
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
		PageBean pageBean = getPageBean(searchModel,5, page,sqlorder);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("departmentCode", departmentCode);
		request.setAttribute("departmentName", departmentName);
		request.setAttribute("departmentLevel", departmentLevel);
		return "query_success";
	}
	//删除部门
	public String delete(){
		DepartmentDao ddao = new DepartmentDaoImpl();
		List<Integer> depart1Id = new ArrayList<Integer>();
		List<Integer> department2Id = new ArrayList<Integer>();
		List<Teacher> teacherList = new ArrayList<Teacher>();
		String did = request.getParameter("did");
		
		department = ddao.queryDepartmentById(Integer.parseInt(did));
		if(department.getDepartmentLevel().equals("一级")){
			depart1Id.add(Integer.parseInt(did));
			department2Id = ddao.getDepartment2IdByDepart1Id(depart1Id);
			if(department2Id.size()==0){
				teacherList = ddao.getTeacherByDepartId(depart1Id);
				if(teacherList.size()==0){
					ddao.deleteDepartment(Integer.parseInt(did));
					return "delete_success";
				}else{
					System.out.println("删除的部门下有教师-----"+department2Id);
					this.addFieldError("errorMessage", "删除的部门下有教师，不可删除！");
					query();
					return "delete_failure";
				}
			}else{
				System.out.println("删除的部门下有二级部门-----"+department2Id);
				this.addFieldError("errorMessage", "删除的部门下有二级部门，不可删除！");
				query();
				return "delete_failure";
			}
		}else{
			department2Id.add(Integer.parseInt(did));
			teacherList = ddao.getTeacherByDepartId(department2Id);
			if(teacherList.size()==0){
				ddao.deleteDepartment(Integer.parseInt(did));
				return "delete_success";
			}else{
				System.out.println("删除的部门下有教师-----"+department2Id);
				this.addFieldError("errorMessage", "删除的部门下有教师，不可删除！");
				query();
				return "delete_failure";
			}
		}

	}
	
	public String add(){
		DepartmentDao ddao = new DepartmentDaoImpl();
		String depart1 = request.getParameter("depart1");
		System.out.println("depart1-----"+depart1);
		boolean iserror = false;
		if(depart1!=null && depart1!=""){
			department.setFatherDept(ddao.queryDepartmentById(Integer.parseInt(depart1)));
		}
		if(department.getDepartmentCode().trim().equals("")){
			this.addFieldError("codeError", "部门编号不能为空！");
			//return "add_failure";
			iserror = true;
		}
		if(department.getDepartmentName().trim().equals("")){
			this.addFieldError("nameError", "部门名称不能为空！");
			//return "add_failure";
			iserror = true;
		}
		//新规
		if(iserror){
			request.setAttribute("department", department);
			return "add_failure";
		}else{
			if(!ddao.isExist(department)){
				ddao.addDepartment(department);
				return "add_success";
			}else{
				this.addFieldError("errorMessage", "输入的部门编号有误，请重新输入！");
				request.setAttribute("department", department);
				return "add_failure";
			}
		}
		
	}
	public String save(){
		DepartmentDao ddao = new DepartmentDaoImpl();
		String depart1 = request.getParameter("depart1");
		System.out.println("depart1-----"+depart1);
		if(depart1!=null && depart1!=""){
			department.setFatherDept(ddao.queryDepartmentById(Integer.parseInt(depart1)));
		}
		ddao.updateDepartment(department);
		return "update_success";
	}
	public String modify(){
		DepartmentDao ddao = new DepartmentDaoImpl();
		String did = request.getParameter("did");
		department = ddao.queryDepartmentById(Integer.parseInt(did));
		Department fatherDept = new Department();
		if(department.getFatherDept()!=null){
			fatherDept = ddao.queryDepartmentById(department.getFatherDept().getId());
		}
		System.out.println("aaaa0-------"+department.getId());
		session.setAttribute("modify_dept", department);
		request.setAttribute("modify_fatherDept", fatherDept);
		return "modify_success";
	}
	//分页功能
	public PageBean getPageBean(Department searchModel, int pageSize, int page,String sqlorder){
		PageBeanDao pdao = new PageBeanDaoImpl();
        PageBean pageBean = new PageBean();
        String deptCode = searchModel.getDepartmentCode();
        String deptName = searchModel.getDepartmentName();
        String deptLevel = searchModel.getDepartmentLevel();
        String hql = "from Department where 1=1";
        if(deptCode!=null && deptCode!=""){
        	hql+=" and departmentCode='"+deptCode+"' ";
		}
		if(deptName!=null && deptName!=""){
			hql+=" and departmentName='"+deptName+"' ";
		}
		if(deptLevel!=null && deptLevel!=""){
			hql+=" and departmentLevel='"+deptLevel+"' ";
		}
		hql = hql + sqlorder;
		
        int allRows = pdao.getAllRowCount(hql);
        
        int totalPage = pageBean.getTotalPages(pageSize, allRows);
        
        int currentPage = pageBean.getCurPage(page);
        
        int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);
        
        List<Department> list = (List<Department>) pdao.queryByPage(hql, offset, pageSize);
        
        pageBean.setList(list);
        pageBean.setAllRows(allRows);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalPage(totalPage);
        
        return pageBean;
    }
	

}
