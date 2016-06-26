package com.pras.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pras.model.Branch;
import com.pras.model.User;
import com.pras.model.Website;

public class HibernateMain {

	public static void main(String[] args) {
		
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		//start transaction
        session.beginTransaction();
        
		Website website1 = new Website();
		website1.setName("Flipkart");
		Website website2 = new Website();
		website2.setName("SnapDeal");
		Website website3 = new Website();
		website3.setName("Amazon");
		Website website4 = new Website();
		website4.setName("Shopclues");
		
		session.save(website1);
		session.save(website2);
		session.save(website3);
		session.save(website4);
		
		User user1 = new User();
		user1.setName("Prasanna");
		user1.setAddress("PrasannaAddress");
		user1.setContact("prasContact");
		user1.setEmail("p@s.com");
		user1.setPassword("bhat");
		user1.setRole("Admin");
		
		
		User user2 = new User();
		user2.setName("Prabhat");
		user2.setAddress("PrabhatAddress");
		user2.setContact("prabhatContact");
		user2.setEmail("prabhatEmail");
		user2.setPassword("prabhat");
		user2.setManagerId(user1.getId());
		user2.setRole("Manager");
		
		User user3 = new User();
		user3.setName("Naveen");
		user3.setAddress("NaveenAddress");
		user3.setContact("naveenContact");
		user3.setEmail("naveenEmail");
		user3.setPassword("naveen");
		user3.setManagerId(user1.getId());
		user3.setRole("Representative");
		
		
		session.save(user1);
		session.save(user2);
		session.save(user3);
		
		Branch branch1 = new Branch();
		branch1.setPlace("Shiralkoppa");
		branch1.setAddress("Shiralkoppa Address");
		branch1.setContact("COntactSlkp");
		branch1.setPincode(111111);
		
		branch1.setBranchmanager(user1);
		
		session.save(branch1);
		
		user1.setBranch(branch1);
		user2.setBranch(branch1);
		user3.setBranch(branch1);
		
		List<User> employees = new ArrayList<User>();
		employees.add(user1);
		employees.add(user2);
		employees.add(user3);
		branch1.setBranchEmployees(employees);
		
        //Commit transaction
        session.getTransaction().commit();
        
        //terminate session factory, otherwise program won't end
        HibernateUtil.getSessionAnnotationFactory().close();
	}
	
	public static void init() {
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
		//start transaction
        session.beginTransaction();
        
		Website website1 = new Website();
		website1.setName("Flipkart");
		Website website2 = new Website();
		website2.setName("SnapDeal");
		Website website3 = new Website();
		website3.setName("Amazon");
		Website website4 = new Website();
		website4.setName("Shopclues");
		
		session.save(website1);
		session.save(website2);
		session.save(website3);
		session.save(website4);
		
		User user1 = new User();
		user1.setName("Prasanna");
		user1.setAddress("Sirsi");
		user1.setContact("9743101916");
		user1.setEmail("pras.jnnce@gmail.com");
		user1.setPassword("bhat");
		user1.setRole("Admin");
		
		
			
		
		session.save(user1);
		
		Branch branch1 = new Branch();
		branch1.setPlace("Shiralkoppa");
		branch1.setAddress("Shiralkoppa Address");
		branch1.setContact("9743101916");
		branch1.setPincode(581472);
		
		branch1.setBranchmanager(user1);
		
		session.save(branch1);
		
		user1.setBranch(branch1);
		
		List<User> employees = new ArrayList<User>();
		employees.add(user1);
		branch1.setBranchEmployees(employees);
		
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        //terminate session factory, otherwise program won't end
        HibernateUtil.getSessionAnnotationFactory().close();
	}

}
