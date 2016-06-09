package com.pras.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.pras.dao.UserDao;
import com.pras.dto.UserDto;
import com.pras.dtohelper.UserDtoHelper;
import com.pras.model.User;
import com.pras.util.HibernateUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public void addUser(UserDto user) {
		// TODO Auto-generated method stub
		// Get Session
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		// start transaction
		session.beginTransaction();
		User u = UserDtoHelper.getEntityFromDto(user);
		
		u.setPassword("Password123");
		//u.setManager((User) session.get(User.class, user.getManagerId()));
		// Save the Model object
		session.save(u);
		session.getTransaction().commit();
		session.close();
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
		u.setManagerId(user.getManagerId());
		if(user.getPassword() != null && (user.getPassword().equals(u.getPassword()))) {
			
		}
		if(user.getNewPassword() != null && (user.getPassword().equals(u.getPassword()))) {
			u.setPassword(user.getNewPassword());
		} else {
			return 1;
		}
		u.setRole(user.getRole());
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
		List<User> users = session.createCriteria(User.class).list();
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
		session.delete(user);
		session.getTransaction().commit();
		session.close();
	}


	@Override
	public List<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("email", email));
		List results = cr.list();
		return results;
	}


	@Override
	public int getUserType(String role) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("role", role));
		List results = cr.list();
		session.close();
		return results.size();
	}

}
