package com.pras.dao;

import java.util.List;

import com.pras.dto.WebsiteDetailsDto;
import com.pras.dto.WebsiteDto;
import com.pras.model.Website;

public interface WebsiteDao {

	public void addWebsite(WebsiteDto website);
	public void removeWebsite(long id);
	public List<WebsiteDto> getWebsites();
	public void editWebsite(long id, WebsiteDto website);
	public WebsiteDetailsDto getWebsiteDetails(long id);
	
}
