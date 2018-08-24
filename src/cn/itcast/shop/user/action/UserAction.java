package cn.itcast.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.omg.PortableInterceptor.ACTIVE;

import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.user.vo.User;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User>
{
   //ģ������ʹ�õĶ���
	
	private User user=new User();
	@Override
	public User getModel()
	{
		// TODO Auto-generated method stub
		return user;
	}
	//������֤��
	private String checkcode;
	
	public void setCheckcode(String checkcode)
	{
		this.checkcode = checkcode;
	}
	//ע��UserService
	private UserService userService;
	public void setUserService(UserService userService){
		this.userService=userService;
	}
	/*
	 * ��ת��ע��ҳ���ִ�зŷ�
	 */
	public String registPage(){
		return "registPage";
	}
	/*
	 * ʹ��AJAX�����첽У���û�����ִ�з���
	 * @throws IOException
	 */
	public String findByName() throws IOException{
		User existUser=userService.findByUserName(user.getUsername());
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		if(existUser!=null){
			//��ѯ�����û����û��Ѵ���
			response.getWriter().print("<font color='red'>�û����Ѵ���</font>");
		}else{
			//û�в�ѯ�����û�:�û�������ʹ��
			response.getWriter().print("<font color='green'>�û�������ʹ��</font>");

		}
		return NONE;
	}
	/*
	 * �û�ע��ķ���
	 * 
	 */
	public String regist(){
		//�ж���֤�����
		//��session�л����֤��
	String checkcode1=	(String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
	if(!checkcode1.equalsIgnoreCase(checkcode1)){
		this.addActionError("��֤���������");
		return "checkcodeFail";
	}
		userService.save(user);
		this.addActionMessage("ע��ɹ�����ȥ���伤�");
		return "msg";
	}
	/*
	 * �û�����ķ���
	 * 
	 */
	public String active(){
		//���ݼ������ѯ�û�
		User existUser = userService.findByCode(user.getCode());
		//�ж�
		if(existUser==null){
			//����������
			this.addActionMessage("����ʧ�ܣ����������");
		}else{
			//����ɹ�
			//�޸��û���״̬
			existUser.setState(1);
			existUser.setCode(null);
			userService.update(existUser);
			this.addActionMessage("����ɹ�����ȥ��¼��");
		}
		return "msg";
	}
	/*
	 * ��ת����¼ҳ��
	 */
	public String loginPage(){
		return "loginPage";
	}
	/*
	 * ��¼�ķ���
	 * 
	 */
	public String login(){
	User existUser=userService.login(user);
	//�ж�
	if(existUser==null){
		//��¼ʧ��
		this.addActionError("��¼ʧ�ܣ��û��������������û�δ���");
		
		return LOGIN;
	}else {
		//��¼�ɹ�
		//���û�����Ϣ����session��
	    ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);	
		//ҳ����ת
	    return "loginSuccess";
	}
		
}
	/*
	 * �û��˳��ķ���
	 */

	public String quit(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "quit";
	}
}
