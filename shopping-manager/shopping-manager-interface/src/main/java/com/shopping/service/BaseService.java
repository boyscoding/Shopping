package com.shopping.service;

import java.util.List;

public interface BaseService<T> {
	public T queryById(Long id);
	public List<T> queryAll();
	public Integer queryCountByWhere(T t);

	/**
	 * 根据条件查询数据
	 * 
	 * @param t
	 * @return
	 */
	public List<T> queryListByWhere(T t);

	/**
	 * 分页查询数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<T> queryByPage(Integer page, Integer rows);

	/**
	 * 根据条件查询一条数据
	 * 
	 * @param t
	 * @return
	 */
	public T queryOne(T t);

	/**
	 * 新增
	 * 
	 * @param t
	 * @return
	 */
	public void save(T t);

	/**
	 * 新增，忽略空参数
	 * 
	 * @param t
	 * @return
	 */
	public void saveSelective(T t);

	/**
	 * 根据主键更新
	 * 
	 * @param t
	 * @return
	 */
	public void updateById(T t);

	/**
	 * 根据主键更新，忽略空参数
	 * 
	 * @param t
	 * @return
	 */
	public void updateByIdSelective(T t);

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 * @return
	 */
	public void deleteById(Long id);

	/**
	 * 根据ids批量删除数据
	 * 
	 * @param ids
	 * @return
	 */
	public void deleteByIds(List<Object> ids);

}
