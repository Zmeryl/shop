package cn.itcast.shop.cart.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.vo.Cart;
import cn.itcast.shop.cart.vo.CartItem;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;

import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport
{
	//����pid
	private Integer pid;
	//��������count
	private Integer count;
	//ע����ƷService
	private ProductService productService;
	

public void setProductService(ProductService productService)
	{
		this.productService = productService;
	}



public void setPid(Integer pid)
	{
		this.pid = pid;
	}



	public void setCount(Integer count)
	{
		this.count = count;
	}



	//����������ӵ����ﳵ��ִ�еķ���
	public String addCart(){
		
		//��װһ��CartItem����
		CartItem cartItem=new CartItem();
		//��������
		cartItem.setCount(count);
		//����pid��ѯ��Ʒ
		Product product=productService.findByPid(pid);
		//������Ʒ
		cartItem.setProduct(product);
		//����������ӵ����ﳵ
		//���ﳵӦ�ô���session��
		Cart cart=getCart();
		cart.addCart(cartItem);
		
		return "addCart";
	}

	//��չ��ﳵ�ķ���
	public String clearCart(){
		//��ù��ﳵ����
		Cart cart=getCart();
		//���ù��ﳵ��շ���
		cart.clearCart();
		return "clearCart";
	}
	
	//�ӹ��ﳵ���Ƴ�������ķ���
	public String removeCart(){
		//��ù��ﳵ�Ķ���
		Cart cart=getCart();
		//���ù��ﳵ�Ƴ�����
		cart.removeCart(pid);
		return  "removeCart";
	}

	//�ҵĹ��ﳵִ�з���
	public String myCart(){
		return "myCart";
	}
	
	
/*
 * ��ù��ﳵ�ķ���
 */
	private Cart getCart()
	{
		// 
	Cart cart=(Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
	  if(cart==null){
		  cart=new Cart();
		  ServletActionContext.getRequest().getSession().setAttribute("cart",cart);
	  }
	
	return cart;
	}
}
