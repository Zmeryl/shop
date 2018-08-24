package cn.itcast.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.MailUtils;
import cn.itcast.shop.utils.UUIDUtils;


@Transactional
public class UserService
{
//ע��UserDao
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao){
		this.userDao=userDao;
	}
	
	
	
	//���û�����ѯ�û��ķŷ�
	public User findByUserName(String username){
		return userDao.findByUserName(username);
	}


    //ҵ�������û�ע��
	public void save(User user)
	{
		// TODO Auto-generated method stub
		//�����ݴ��뵽���ݿ�
		user.setState(0);//0�����û�δ���1�����û��Ѽ���
		String code=UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		//���ͼ����ʼ�
		MailUtils.sendMail(user.getEmail(), code);
		
	}


     //ҵ�����ݼ������ѯ�û�
	public User findByCode(String code)
	{
		// TODO Auto-generated method stub
		
		return userDao.findByCode(code);
	}


          //�޸��û�״̬�ķ���
	public void update(User existUser)
	{
		// TODO Auto-generated method stub
		userDao.update(existUser);
	}


//�û���¼�ķ���
	public User login(User user)
	{
		// TODO Auto-generated method stub
		
		return userDao.login(user);
	}
}
