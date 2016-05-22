package com.pras.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pras.dao.WebsiteDao;
import com.pras.dto.WebsiteDto;
import com.pras.dtohelper.WebsiteDtoHelper;
import com.pras.model.Branch;
import com.pras.model.Website;
import com.pras.util.HibernateUtil;

public class WebsiteDaoImpl implements WebsiteDao {

	@Override
	public void addWebsite(Website website) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.save(website);
        session.getTransaction().commit();
	}

	@Override
	public void removeWebsite(long id) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        Website website = (Website) session.get(Website.class, id);
        //Save the Model object
        session.delete(website);
        session.getTransaction().commit();
	}

	@Override
	public List<WebsiteDto> getWebsites() {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		List<Website> websites = session.createCriteria(Website.class).list();
		List<WebsiteDto> dtos = new ArrayList<WebsiteDto>();
		
		for(int i=0;i<websites.size();i++) {
			dtos.add(WebsiteDtoHelper.getDtoFromEntity(websites.get(i)));
		}
		return dtos;
	}

}
