package cn.itcast.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.MailUtils;
import cn.itcast.shop.utils.UUIDUtils;


@Transactional
public class UserService
{
//注入UserDao
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao){
		this.userDao=userDao;
	}
	
	
	
	//按用户名查询用户的放法
	public User findByUserName(String username){
		return userDao.findByUserName(username);
	}


    //业务层完成用户注册
	public void save(User user)
	{
		// TODO Auto-generated method stub
		//将数据存入到数据库
		user.setState(0);//0代表用户未激活，1代表用户已激活
		String code=UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		//发送激活邮件
		MailUtils.sendMail(user.getEmail(), code);
		
	}


     //业务层根据激活码查询用户
	public User findByCode(String code)
	{
		// TODO Auto-generated method stub
		
		return userDao.findByCode(code);
	}


          //修改用户状态的方法
	public void update(User existUser)
	{
		// TODO Auto-generated method stub
		userDao.update(existUser);
	}


//用户登录的方法
	public User login(User user)
	{
		// TODO Auto-generated method stub
		
		return userDao.login(user);
	}
}
