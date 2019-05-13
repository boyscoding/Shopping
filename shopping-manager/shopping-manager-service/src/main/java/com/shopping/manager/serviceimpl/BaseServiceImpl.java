package com.shopping.manager.serviceimpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shopping.pojo.BasePojo;
import com.shopping.service.BaseService;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {
	@Autowired
	private Class<T> clazz;
	
	
	public BaseServiceImpl() {
		// 获取父类的type
		Type type = this.getClass().getGenericSuperclass();

		// 强转为ParameterizedType，可以使用获取泛型类型的方法
		ParameterizedType pType = (ParameterizedType) type;

		// 获取泛型的class
		this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
	}

	@Override
	public T queryById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryCountByWhere(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> queryListByWhere(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T queryOne(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(T t) {
		if(t.getUpdated() == null) {
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		}else if(t.getUpdated() == null) {
			t.setUpdated(t.getCreated());
		}
		//this.mapper.insert(t);
		
	}

	@Override
	public void saveSelective(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateById(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByIdSelective(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByIds(List<Object> ids) {
		// TODO Auto-generated method stub
		
	}
	

}
