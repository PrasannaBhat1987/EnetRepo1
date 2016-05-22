package com.pras.dao;

import java.util.List;

import com.pras.dto.WebsiteDto;
import com.pras.model.Website;

public interface WebsiteDao {

	public void addWebsite(Website website);
	public void removeWebsite(long id);
	public List<WebsiteDto> getWebsites();
	
}
