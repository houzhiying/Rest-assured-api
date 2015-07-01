package com.lufax.apitest.utility.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.mapping.Array;

public class DAO {
	Connection conn =null;
	Statement statement = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ResultSetMetaData rsm = null;
	
	/**
	 * 更新数据库：update insert delete
	 * @param sql
	 * @param args
	 */
	public void updateDB(String sql,Object ... args){
		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);
			
			for(int i = 0;i < args.length;i++){
				ps.setObject(i + 1, args[i]);
			}
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseBD(ps, conn);
		}
	}
	
	/**
	 * 查询一条记录，返回对应的对象
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> T getData(Class<T> clazz,String sql,Object ... args){
		T entity = null;
		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);
			
			for (int i = 0;i < args.length;i++){
				ps.setObject(i + 1, args[i]);
			}
			
			rs = ps.executeQuery();
			rsm = rs.getMetaData();
			
			Map<String,Object> map = new HashMap<>();
			
			if(rs.next()){
				for (int i = 0;i < rsm.getColumnCount();i++){
					String columnLabel = rsm.getColumnLabel(i + 1);
					Object columnValue = rs.getObject(i + 1);
					map.put(columnLabel, columnValue);
				}
			}
			if(map.size() > 0){
				entity = clazz.newInstance();
				for (Map.Entry<String, Object> entry:map.entrySet()){
					String propertyName = entry.getKey();
					Object value = entry.getValue();
					
					BeanUtils.setProperty(entity, propertyName, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseBD(rs, ps, conn);
		}
		return entity;
	}
	
	/**
	 * 查询多条数据，返回对应对象的集合
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> List<T> getDataForList(Class<T> clazz,String sql,Object ... args){
		T entity = null;
		List<T> list = new ArrayList<>();
		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);
			
			for (int i = 0;i < args.length;i++){
				ps.setObject(i + 1, args[i]);
			}
			
			rs = ps.executeQuery();
			List<Map<String,Object>> values = new ArrayList<>();
			
			rsm = rs.getMetaData();
			Map<String,Object> map = new HashMap<>();
			
			while (rs.next()) {
				for (int i = 0;i < rsm.getColumnCount();i++) {
					String columnLabel = rsm.getColumnLabel(i + 1);
					Object columnValue = rs.getObject(i + 1);
					map.put(columnLabel, columnValue);
				}
				values.add(map);
			}
			
			if (values.size() > 0) {
				for (Map<String,Object> m:values) {
					entity = clazz.newInstance();
					
					for (Map.Entry<String, Object> entry:m.entrySet()){
						String propertyName = entry.getKey();
						Object value = entry.getValue();
						
						BeanUtils.setProperty(entity, propertyName, value);
					}
					
					list.add(entity);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseBD(rs, ps, conn);
		}
		
		return list;
	}
	
	/**
	 * 查询一条记录的某个字段
	 */
	public <E> E getDataForValue(String sql,Object ... args){
		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);
			
			for (int i = 0;i < args.length;i++){
				ps.setObject(i + 1, args[i]);
			}
			
			rs = ps.executeQuery();
			if(rs.next()){
				return (E) rs.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseBD(rs, ps, conn);
		}
		return null;
	}
	

}
