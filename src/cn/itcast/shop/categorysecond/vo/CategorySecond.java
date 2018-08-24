package cn.itcast.shop.categorysecond.vo;

import java.util.HashSet;
import java.util.Set;

import cn.itcast.shop.category.vo.Category;
import cn.itcast.shop.product.vo.Product;

//二级分类的实体
public class CategorySecond
{

	private Integer csid;
	private String csname;
	//一级分类中存放的是二级分类的集合：
	
	private Category category;
	//配置商品的集合
	private Set<Product> products=new HashSet<Product>();
	public Integer getCsid()
	{
		return csid;
	}
	public void setCsid(Integer csid)
	{
		this.csid = csid;
	}
	public String getCsname()
	{
		return csname;
	}
	public void setCsname(String csname)
	{
		this.csname = csname;
	}
	public Category getCategory()
	{
		return category;
	}
	public void setCategory(Category category)
	{
		this.category = category;
	}
	public Set<Product> getProducts()
	{
		return products;
	}
	public void setProducts(Set<Product> products)
	{
		this.products = products;
	}

	
	
}
