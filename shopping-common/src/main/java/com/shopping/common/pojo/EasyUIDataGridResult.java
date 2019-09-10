package com.shopping.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable {
	
	private int total;
	
	private List rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(int l) {
		this.total = l;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
	
	
}
