package com.shopping.search.service.impl;


import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.common.pojo.SearchItem;
import com.shopping.common.pojo.SearchResult;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.search.dao.SearchDao;
import com.shopping.search.mapper.SearchItemMapper;
import com.shopping.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchItemMapper mapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchDao searchdao;
	
//	@Resource
//	private Destination topicDestinatioon;
	@Override
	public shoppingResult importAllSearchItems() throws Exception {
		List<SearchItem> itemList = mapper.getSearchItemList();
		for(SearchItem searchItem : itemList) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());

			solrServer.add(document);
		}
		solrServer.commit();
		
		
		return shoppingResult.ok();
	}

	@Override
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
		//1.创建solrquery对象  
		SolrQuery query = new SolrQuery();
		//2.设置主查询条件
		if(StringUtils.isNotBlank(queryString)){
			query.setQuery(queryString);
		}else{
			query.setQuery("*:*");
		}
		//2.1设置过滤条件 设置分页
		if(page==null)page=1;
		if(rows==null)rows=60;
		query.setStart((page-1)*rows);//page-1 * rows
		query.setRows(rows);
		//2.2.设置默认的搜索域
		query.set("df", "item_keywords");
		//2.3设置高亮
		query.setHighlight(true);
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		query.addHighlightField("item_title");//设置高亮显示的域
		
		//3.调用dao的方法 返回的是SearchResult 只包含了总记录数和商品的列表
		SearchResult search = searchdao.search(query);
		//4.设置SearchResult 的总页数
		long pageCount = 0l;
		pageCount = search.getRecordCount()/rows;
		if(search.getRecordCount()%rows>0){
			pageCount++;
		}
		search.setPageCount(pageCount);
		//5.返回
		return search;
	}
	@Override
	public shoppingResult updateSearchItemById(Long itemId) throws Exception {
		return searchdao.updateSearchItemById(itemId);
	}
	
}
