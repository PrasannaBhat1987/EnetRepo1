package com.pras.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.pras.constant.Constants;
import com.pras.dao.WebsiteDao;
import com.pras.dto.CustomerDetailsDto;
import com.pras.dto.WebsiteDetailsDto;
import com.pras.dto.WebsiteDto;
import com.pras.dtohelper.CustomerDtoHelper;
import com.pras.dtohelper.WebsiteDtoHelper;
import com.pras.model.Branch;
import com.pras.model.Rao;
import com.pras.model.Website;
import com.pras.util.HibernateUtil;

public class WebsiteDaoImpl implements WebsiteDao {

	@Override
	public void addWebsite(WebsiteDto website) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        Website w = WebsiteDtoHelper.getEntityFromDto(website);
        w.setStatus(Constants.CREATED);
        //Save the Model object
        session.save(w);
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
        website.setStatus(Constants.DELETED);
        //session.delete(website);
        session.getTransaction().commit();
	}

	@Override
	public List<WebsiteDto> getWebsites() {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Website.class);
		cr.add(Restrictions.eq("status", Constants.CREATED)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Website> websites  = cr.list();
		List<WebsiteDto> dtos = new ArrayList<WebsiteDto>();
		
		for(int i=0;i<websites.size();i++) {
			dtos.add(WebsiteDtoHelper.getDtoFromEntity(websites.get(i)));
		}
		return dtos;
	}

	@Override
	public void editWebsite(long id, WebsiteDto website) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Website w = (Website) session.get(Website.class, id);
		w.setName(website.getName());
		session.saveOrUpdate(w);
		session.getTransaction().commit();
	}

	@Override
	public WebsiteDetailsDto getWebsiteDetails(long id) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Website w = (Website) session.get(Website.class, id);
		WebsiteDetailsDto dto = WebsiteDtoHelper.getDetailsDtoFromEntity(w);
		return dto;
	}

}
