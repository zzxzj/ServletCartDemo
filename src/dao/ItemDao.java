package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DBHelper;
import entity.Item;

/*
 * 商品的数据库访问类
 */
public class ItemDao {
	//获得所有的商品信息
	public ArrayList<Item> getAllItems(){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Item> list = new ArrayList<Item>();//商品集合
		try{
			conn = DBHelper.getConnection();
			String sql = "select * from item;";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setCity(rs.getString("city"));
				item.setNumber(rs.getInt("number"));
				item.setPrice(rs.getInt("price"));
				item.setPicture(rs.getString("picture"));
				list.add(item);
			}
			return list;//返回集合
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		finally{
			//释放数据集对象
			if(rs!=null){
				try{
					rs.close();
					rs = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			//释放语句对象
			if(stmt!=null){
				try{
					stmt.close();
					stmt = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	//根据商品编号获得商品资料
	public Item getItemById(int id){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBHelper.getConnection();
			String sql = "select * from item where id=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()){
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setCity(rs.getString("city"));
				item.setNumber(rs.getInt("number"));
				item.setPrice(rs.getInt("price"));
				item.setPicture(rs.getString("picture"));
				return item;
			}else{
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		finally{
			//释放数据集对象
			if(rs!=null){
				try{
					rs.close();
					rs = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			//释放语句对象
			if(stmt!=null){
				try{
					stmt.close();
					stmt = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	//获取最近浏览的前五条商品信息
	public ArrayList<Item> getViewList(String list){
		ArrayList<Item> itemList = new ArrayList<Item>();
		int iCount = 5;//每次返回前五条记录
		if(list !=null&&list.length()>0){
			String[] arr = list.split(",");
			if(arr.length>=iCount){
				for(int i=arr.length-1;i>=arr.length-iCount;i--){
					itemList.add(getItemById(Integer.parseInt(arr[i])));
				}
			}else{
				for(int i=arr.length-1;i>=0;i--){
					itemList.add(getItemById(Integer.parseInt(arr[i])));
				}
			}
			return itemList;
		}else{
			return null;
		}
	}
}
