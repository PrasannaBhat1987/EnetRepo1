package com.pras.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.pras.constant.Constants;
import com.pras.dao.UserDao;
import com.pras.dto.UserDetailsDto;
import com.pras.dto.UserDto;
import com.pras.dtohelper.UserDtoHelper;
import com.pras.model.Branch;
import com.pras.model.Customer;
import com.pras.model.User;
import com.pras.util.HibernateUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public int addUser(UserDto user) {
		// TODO Auto-generated method stub
		// Get Session
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		// start transaction
		session.beginTransaction();
		User u = UserDtoHelper.getEntityFromDto(user);
		
		User exist = getUserByEmail(u.getEmail());
		if(exist != null) {
			return 1;
		}
			
		u.setPassword(Constants.DEFAULT_PASSWORD);
		u.setStatus(Constants.CREATED);
		//u.setManager((User) session.get(User.class, user.getManagerId()));
		// Save the Model object
		session.save(u);
		session.getTransaction().commit();
		session.close();
		return 0;
	}


	@Override
	public int editUser(long id, UserDto user) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		// start transaction
		session.beginTransaction();
		// Save the Model object
		User u = (User) session.get(User.class, id);
		u.setAddress(user.getAddress());
		u.setName(user.getName());
		u.setContact(user.getContact());
		if(user.getManagerId() != null) {
			u.setManagerId(user.getManagerId());
		}
		
		if(user.getNewPassword() != null && user.getNewPassword().length() > 0) {
			if (user.getPassword().equals(u.getPassword())) {
				u.setPassword(user.getNewPassword());
			} else {
				return 1;
			}
		}
		if(user.getRole() != null) {
			u.setRole(user.getRole());
		}
		if(user.getBranchId()!= null) {
			Branch b = (Branch)session.get(Branch.class, user.getBranchId());
			u.setBranch(b);
		}
		if(user.getManagerId()!= null) {
			u.setManagerId(user.getManagerId());
		}
		session.update(u);
		session.getTransaction().commit();
		session.close();
		return 0;
	}

	@Override
	public UserDto getUser(long id) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		// start transaction
		session.beginTransaction();
		// Save the Model object
		User user = (User) session.get(User.class, id);
		
		UserDto dto = UserDtoHelper.getDtoFromEntity(user);
		session.close();
		return dto;
	}

	@Override
	public List<UserDto> getUsers() {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.ne("status", Constants.DELETED));
		List<User> users = criteria.list();
		//List<User> users = session.createCriteria(User.class).list();
		List<UserDto> dtos = new ArrayList<UserDto>();
		
		for(int i=0;i<users.size();i++) {
			dtos.add(UserDtoHelper.getDtoFromEntity(users.get(i)));
		}
		session.close();
		return dtos;
	}

	@Override
	public void removeUserById(long id) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, id);
		
		user.setStatus(Constants.DELETED);
		session.update(user);
		//session.delete(user);
		session.getTransaction().commit();
		session.close();
	}


	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		HibernateUtil.clearSessionFactory();
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("email", email));
		List<User> results = cr.list();
		User u = null;
		if(results.size() == 1) {
			u = results.get(0);
		}
		return u;
	}


	@Override
	public int getUserType(String role) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("status", Constants.CREATED)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//List<Customer> custs = cr.list();
		//Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("role", role));
		List<User> results = cr.list();
		session.close();
		return results.size();
	}


	@Override
	public UserDetailsDto getUserDetails(long id) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		// start transaction
		session.beginTransaction();
		// Save the Model object
		User user = (User) session.get(User.class, id);
		User manager = (User) session.get(User.class, user.getManagerId());
		
		UserDetailsDto dto = UserDtoHelper.getDetailsDtoFromEntity(user, manager);
		session.close();
		return dto;
	}

}
