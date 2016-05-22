package com.pras.dao;

import com.pras.model.LineItem;

public interface LineItemDao {

	public void addLineItem(LineItem lineItem);
	public void removeLineItem(LineItem lineItem);
	public void editLineItem(LineItem lineItem);
	
}
