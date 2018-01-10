package com.vp.action;

import com.opensymphony.xwork2.ModelDriven;
import com.vp.dao.ManagerDao;
import com.vp.dao.Impl.ManagerDaoImpl;
import com.vp.model.Manager;

public class ManagerAction extends SuperAction implements ModelDriven<Manager>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Manager manager = new Manager();
	
	@Override
	public Manager getModel() {
		// TODO Auto-generated method stub
		return this.manager;
	}
	
	public String login(){
		ManagerDao mdao = new ManagerDaoImpl();
		if("".equals(manager.getAccount().trim())){
			this.addFieldError("managernameError", "用户账号不能为空！");
		}
		if(manager.getPassword().length()<6){
			this.addFieldError("passwordError", "密码长度不少于6位");
		}
		if(mdao.managerLogin(manager)){
			//在session中保存用户名
			session.setAttribute("loginManagerName", manager.getAccount());
			return "login_success";
		}else{
			this.addFieldError("errorMessage", "输入的账号或密码有误，请重新输入！");
			return "login_failure";
		}
	}

	public String logout(){
		if(session.getAttribute("loginmanagerName")!=null){
			session.removeAttribute("loginmanagerName");
		}
		return "logout_success";
	}

}
