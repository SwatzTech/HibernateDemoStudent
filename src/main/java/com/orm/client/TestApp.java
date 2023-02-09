package com.orm.client;

import java.util.Date;

import org.hibernate.Session;    
import org.hibernate.SessionFactory;    
import org.hibernate.Transaction;  
import org.hibernate.boot.Metadata;  
import org.hibernate.boot.MetadataSources;  
import org.hibernate.boot.registry.StandardServiceRegistry;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.orm.entity.Student;
import com.orm.entity.Student.GENDER;  


public class TestApp {

	public static void main(String[] args) {
/*		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
*/		
		Date d = new Date();
	  
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		
		Session session = factory.openSession();  
		Transaction t1 = session.beginTransaction();
		
		Student s1 = new Student();
		s1.setName("ABC");
		s1.setGender(GENDER.FEMALE);
		s1.setBirthDate(d);
		s1.setAge(57);
		
		session.save(s1);
		t1.commit();
		System.out.println("successfully saved");
		
		// Use 2 different session instances when working on same entity instance
		// Because org.hibernate.Session uses 'Unit of Work' pattern
		Session session2 = factory.openSession();  
		t1 = session2.beginTransaction();

		Student s=new Student();   
		s.setId(1);
		s.setName("XYZ"); 
		s.setGender(GENDER.FEMALE);
		s.setBirthDate(d);

		session2.update(s);

		t1.commit();  
		System.out.println("successfully updated");    
		factory.close();  
		session.close();  
		session2.close();
	}
}
