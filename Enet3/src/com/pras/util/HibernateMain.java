package com.pras.util;

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
		user1.setEmail("prasannaEmail");
		user1.setPassword("prasannna");
		session.save(user1);
		
		User user2 = new User();
		user2.setName("Prabhat");
		user2.setAddress("PrabhatAddress");
		user2.setContact("prabhatContact");
		user2.setEmail("prabhatEmail");
		user2.setPassword("prabhat");
		user2.setManager(user1);
		session.save(user2);
		
		User user3 = new User();
		user3.setName("Naveen");
		user3.setAddress("NaveenAddress");
		user3.setContact("naveenContact");
		user3.setEmail("naveenEmail");
		user3.setPassword("naveen");
		user3.setManager(user1);
		session.save(user3);
		
		
		Branch branch1 = new Branch();
		branch1.setPlace("Shiralkoppa");
		branch1.setAddress("Shiralkoppa Address");
		branch1.setContact("COntactSlkp");
		branch1.setPincode(111111);
		session.save(branch1);
		
		Branch branch2 = new Branch();
		branch2.setPlace("Soraba");
		branch2.setAddress("Soraba Address");
		branch2.setContact("COntactSoraba");
		branch2.setPincode(222222);
		session.save(branch2);
		
		Branch branch3 = new Branch();
		branch3.setPlace("Shikaripura");
		branch3.setAddress("Shikaripura Address");
		branch3.setContact("COntactSkpr");
		branch3.setPincode(333333);
		session.save(branch3);
        
        
        //Commit transaction
        session.getTransaction().commit();
        
        //terminate session factory, otherwise program won't end
        HibernateUtil.getSessionAnnotationFactory().close();
	}

}
