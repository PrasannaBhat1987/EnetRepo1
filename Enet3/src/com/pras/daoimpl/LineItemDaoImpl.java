package com.pras.daoimpl;

import org.hibernate.Session;

import com.pras.dao.LineItemDao;
import com.pras.model.LineItem;
import com.pras.util.HibernateUtil;

public class LineItemDaoImpl implements LineItemDao{

	@Override
	public void addLineItem(LineItem lineItem) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.save(lineItem);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public void removeLineItem(LineItem lineItem) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.delete(lineItem);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public void editLineItem(LineItem lineItem) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.update(lineItem);
        session.getTransaction().commit();
        session.close();
	}

}
