package cn.itcast.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.user.vo.User;

public class UserDao extends HibernateDaoSupport
{

	//�����β�ѯ�Ƿ��и��û�
	public User findByUserName(String username){
		String hql="from User where username=?";
		List<User> list=this.getHibernateTemplate().find(hql, username);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	//ע���û��������ݿ����ʵ��
	public void save(User user)
	{
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(user);
	}
    //���ݼ������ѯ�û�
	public User findByCode(String code)
	{
		// TODO Auto-generated method stub
		String  hql="from User where code =?";
	   List<User> list=this.getHibernateTemplate().find(hql,code);
	   if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
    //�޸��û�״̬�ķ���
	public void update(User existUser)
	{
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(existUser);
	}

	//�û���¼�ķ���
	public User login(User user)
	{
		// TODO Auto-generated method stub
		String hql="from User where username=? and password =? and state =?";
	    List<User> list=this.getHibernateTemplate().find(hql, user.getUsername(),user.getPassword(),1);
	    if(list!=null&&list.size()>0){
	    	return list.get(0);
	    }
		return null;
	}
	
}