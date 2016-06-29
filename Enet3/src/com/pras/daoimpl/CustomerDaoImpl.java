package com.pras.daoimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.pras.constant.Constants;
import com.pras.dao.CustomerDao;
import com.pras.dto.BranchDetailsDto;
import com.pras.dto.BranchDto;
import com.pras.dto.CustomerDetailsDto;
import com.pras.dto.CustomerDto;
import com.pras.dtohelper.BranchDtoHelper;
import com.pras.dtohelper.CustomerDtoHelper;
import com.pras.model.Branch;
import com.pras.model.Customer;
import com.pras.model.Website;
import com.pras.util.HibernateUtil;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public void addCustomer(CustomerDto customer) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        //start transaction
        session.beginTransaction();
        Customer c = CustomerDtoHelper.getEntityFromDto(customer);
        c.setStatus(Constants.CREATED);
        //Save the Model object
        session.save(c);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public void removeCustomer(long id) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        Customer cust = (Customer) session.get(Customer.class, id);
        cust.setStatus(Constants.DELETED);
        session.saveOrUpdate(cust);
        //session.delete(cust);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public void editCustomer(long id, CustomerDto customer) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        //start transaction
        session.beginTransaction();
        Customer c = (Customer) session.get(Customer.class, id);
        c.setAddress(customer.getAddress());
        c.setContact(customer.getContact());
        c.setEmail(customer.getEmail());
        c.setName(customer.getName());
        session.update(c);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public Collection<? extends CustomerDto> getCustomers() {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Customer.class);
		cr.add(Restrictions.eq("status", Constants.CREATED)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Customer> custs = cr.list();
		List<CustomerDto> dtos = new ArrayList<CustomerDto>();
		CustomerDto dto = null;
		for(int i=0;i<custs.size();i++) {
			dto = CustomerDtoHelper.getDtoFromEntity(custs.get(i));
			dto.setId(custs.get(i).getId());
			dtos.add(dto);
		}
		session.close();
		return dtos;
	}

	@Override
	public CustomerDto getCustomer(long id) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Customer c = new Customer();
		c= (Customer) session.get(Customer.class, id);
		CustomerDto dto = CustomerDtoHelper.getDtoFromEntity(c);
		session.close();
		return dto;
	}

	@Override
	public CustomerDetailsDto getCustomerDetails(long id) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Customer c = (Customer) session.get(Customer.class, id);
		CustomerDetailsDto dto = CustomerDtoHelper.getDetailsDtoFromEntity(c);
		return dto;
	}

}
