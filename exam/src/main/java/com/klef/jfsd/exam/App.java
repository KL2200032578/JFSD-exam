package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Configure Hibernate
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory sf = md.getSessionFactoryBuilder().build();

        // Open a session
        Session session = sf.openSession();
        // Begin transaction
        Transaction tr = session.beginTransaction();

        try {
            // Insert Client records
            Client c1 = new Client();
            c1.setName("John Doe");
            c1.setGender("Male");
            c1.setAge(30);
            c1.setLocation("New York");
            c1.setEmail("john.doe@example.com");
            c1.setMobileNumber("1234567890");

            Client c2 = new Client();
            c2.setName("Jane Smith");
            c2.setGender("Female");
            c2.setAge(25);
            c2.setLocation("Los Angeles");
            c2.setEmail("jane.smith@example.com");
            c2.setMobileNumber("9876543210");

            Client c3 = new Client();
            c3.setName("Robert Brown");
            c3.setGender("Male");
            c3.setAge(40);
            c3.setLocation("Chicago");
            c3.setEmail("robert.brown@example.com");
            c3.setMobileNumber("1231231234");

            session.save(c1);
            session.save(c2);
            session.save(c3);

            // Commit the transaction for insertion
            tr.commit();

            // Fetch all records using HQL
            System.out.println("\n=== Fetching all Client records ===");
            session.beginTransaction();
            String hql = "FROM Client";
            Query<Client> query = session.createQuery(hql, Client.class);
            List<Client> clients = query.list();

            for (Client client : clients) {
                System.out.println("ID: " + client.getId());
                System.out.println("Name: " + client.getName());
                System.out.println("Gender: " + client.getGender());
                System.out.println("Age: " + client.getAge());
                System.out.println("Location: " + client.getLocation());
                System.out.println("Email: " + client.getEmail());
                System.out.println("Mobile Number: " + client.getMobileNumber());
                System.out.println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            // Close the session
            session.close();
            sf.close();
        }
    }
}
