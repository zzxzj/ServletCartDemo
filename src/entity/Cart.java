package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Cart {
	private HashMap<Item, Integer> goods;
	private double totalPrice;
	public Cart(){
		goods = new HashMap<Item,Integer>();
		totalPrice = 0.0;
	}
	public HashMap<Item, Integer> getGoods() {
		return goods;
	}
	public void setGoods(HashMap<Item, Integer> goods) {
		this.goods = goods;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	//添加商品进购物车
	public Boolean addGoodsInCart(Item item,int num){
		if(goods.containsKey(item)){
			num += goods.get(item);
		}
		goods.put(item, num);
		calTotalPrice();
		return true;
	}
	
	//从购物车中删除商品
	public Boolean removeGoodsFromCart(Item item){
		goods.remove(item);
		calTotalPrice();
		return true;
	}
	
	//计算购物车中商品总金额
	public double calTotalPrice(){
		double sum = 0.0;
		Set<Item> set = goods.keySet();//获取购物车键的集合
		Iterator<Item> it = set.iterator();//获取该集合的迭代器
		while(it.hasNext()){
			Item i = it.next();
			sum += i.getPrice() * goods.get(i);
		}
		setTotalPrice(sum);
		return getTotalPrice();
	}
}
