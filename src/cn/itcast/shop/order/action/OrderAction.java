package cn.itcast.shop.order.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.vo.Cart;
import cn.itcast.shop.cart.vo.CartItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.PageBean;
import cn.itcast.shop.utils.PaymentUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ����Action��
 * 
 *
 * 
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
	// ģ������ʹ�õĶ���
	private Order order = new Order();

	public Order getModel() {
		return order;
	}

	// ����֧��ͨ������:
		private String pd_FrpId;

		public void setPd_FrpId(String pd_FrpId) {
			this.pd_FrpId = pd_FrpId;
		}
		// ���ո���ɹ���Ĳ���:
		private String r3_Amt;
		private String r6_Order;
		
		
		public void setR3_Amt(String r3_Amt) {
			this.r3_Amt = r3_Amt;
		}

		public void setR6_Order(String r6_Order) {
			this.r6_Order = r6_Order;
		}

	// ����page
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// ע��OrderService
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	// ���ɶ�����ִ�еķ���:
	public String saveOrder() throws ParseException {

		// ����Service������ݿ����Ĳ���:
		// Order order = new Order();
		// ���ö������ܽ��:�������ܽ��Ӧ���ǹ��ﳵ���ܽ��:
		// ���ﳵ��session��,��session�ܻ�ù��ﳵ��Ϣ.
		Cart cart = (Cart) ServletActionContext.getRequest().getSession()
				.getAttribute("cart");
		if (cart == null) {
			this.addActionMessage("��!����û�й���!");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		// ���ö�����״̬
		order.setState(1); // 1:δ����.
		// ���ö���ʱ��
		Date date=new Date();                             
        SimpleDateFormat temp=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String date2=temp.format(date);  
        Date date3=temp.parse(date2);  
       order.setOrdertime(date3);
		
		// ���ö��������Ŀͻ�:
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("existUser");
		if (existUser == null) {
			this.addActionMessage("��!����û�е�¼!");
			return "msg";
		}
		order.setUser(existUser);
		// ���ö������:
		for (CartItem cartItem : cart.getCartItems()) {
			// ���������Ϣ�ӹ������õ�.
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
		}
		orderService.save(order);
		// ��չ��ﳵ:
		cart.clearCart();

		// ҳ����Ҫ���Զ�����Ϣ:
		// ʹ��ģ�������� ���п��Բ�ʹ��ֵջ������
		// ActionContext.getContext().getValueStack().set("order", order);

		return "saveOrder";
	}

	// ��ѯ�ҵĶ���:
	public String findByUid() {
		// ����û���id.
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("existUser");
		// ����û���id
		Integer uid = existUser.getUid();
		// �����û���id��ѯ����:
		PageBean<Order> pageBean = orderService.findByUid(uid, page);
		// ��PageBean���ݴ���ҳ����.
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUid";
	}

	// ���ݶ���id��ѯ����:
	public String findByOid() {
		order = orderService.findByOid(order.getOid());
		return "findByOid";
	}

	// Ϊ��������:
	public String payOrder() throws IOException  {
		// 1.�޸�����:
		Order currOrder = orderService.findByOid(order.getOid()); 
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		// �޸Ķ���
		orderService.update(currOrder);
		// 2.��ɸ���:
		// ������Ҫ�Ĳ���:
		String p0_Cmd ="Buy"; // ҵ������:
		String p1_MerId ="10001126856";// �̻����:
		String p2_Order =order.getOid().toString();// �������:
		String p3_Amt ="0.01"; // ������:
		String p4_Cur ="CNY"; // ���ױ���:
		String p5_Pid =""; // ��Ʒ����:
		String p6_Pcat =""; // ��Ʒ����:
		String p7_Pdesc =""; // ��Ʒ����:
		String p8_Url ="http://localhost:8080/shop1/order_callBack.action"; // �̻�����֧���ɹ����ݵĵ�ַ:
		String p9_SAF =""; // �ͻ���ַ:
		String pa_MP =""; // �̻���չ��Ϣ:
		String pd_FrpId=this.pd_FrpId;// ֧��ͨ������:
		String pr_NeedResponse="1"; // Ӧ�����:
		String keyValue="69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // ��Կ
		String hmac=PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // hmac
		// ���ױ���������:
		StringBuffer sb =new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		// �ض���:���ױ�����:
		ServletActionContext.getResponse().sendRedirect(sb.toString());
	
		return NONE;
	}

	// ����ɹ�����ת������·��:
		public String callBack(){
			// �޸Ķ�����״̬:
			Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
			// �޸Ķ���״̬Ϊ2:�Ѿ�����:
			currOrder.setState(2);
			orderService.update(currOrder);
			this.addActionMessage("֧���ɹ�!�������Ϊ: "+r6_Order +" ������Ϊ: "+r3_Amt);
			return "msg";
		}
		
		// �޸Ķ�����״̬:
		public String updateState(){
			Order currOrder = orderService.findByOid(order.getOid());
			currOrder.setState(4);
			orderService.update(currOrder);
			return "updateStateSuccess";
		}
	}
