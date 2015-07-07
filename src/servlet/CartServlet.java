package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemDao;
import entity.Cart;
import entity.Item;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//定义一个变量用来区分客户端的请求类型,add为添加商品进购物车，show为显示购物车商品信息，delete为从购物车中删除商品
	private String action="";
	//引入商品数据库访问类变量
	private ItemDao iDao = new ItemDao();
    /**
     * Default constructor. 
     */
    public CartServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		action = request.getParameter("action");
		if(action!=null){
			if(action.equals("add")){//添加商品进购物车
				if(addToCart(request,response)){
					request.getRequestDispatcher("/success.jsp").forward(request, response);
				}
				else{
					request.getRequestDispatcher("/failure.jsp").forward(request,response);
				}
			}
			if(action.equals("show")){//显示购物车信息
				request.getRequestDispatcher("/cart.jsp").forward(request, response);
			}
			if(action.equals("delete")){//从购物车中删除商品信息
				if(removeFromCart(request,response)){
					request.getRequestDispatcher("/cart.jsp").forward(request, response);
				}else{
					request.getRequestDispatcher("/cart.jsp").forward(request, response);
				}
			}
		}
	}
	//添加商品进购物车
	private Boolean addToCart(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String number = request.getParameter("num");
		//如果是第一次添加商品进购物车，必须先创建购物车设置session变量
		if(request.getSession().getAttribute("cart")==null){
			Cart cart = new Cart();
			request.getSession().setAttribute("cart",cart);
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Item item = iDao.getItemById(Integer.parseInt(id));
		if(cart.addGoodsInCart(item, Integer.parseInt(number))){
			return true;
		}else{
			return false;
		}
	}
	//从购物车中删除商品
	private Boolean removeFromCart(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		Item item = iDao.getItemById(Integer.parseInt(id));
		if(item==null){
			return false;
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart==null){
			return false;
		}
		if(cart.removeGoodsFromCart(item)){
			return true;
		}else{
			return false;
		}
	}

}
