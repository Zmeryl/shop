package cn.itcast.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.category.vo.Category;

/*
 * һ������־ò�Ķ���
 */
public class CategoryDao extends HibernateDaoSupport
{
    //DAO���ѯ����һ������ķ���
	public List<Category> findAll()
	{
		
		String hql="from Category";
	    List<Category> list=this.getHibernateTemplate().find(hql);
		return list;
	}

	
}
