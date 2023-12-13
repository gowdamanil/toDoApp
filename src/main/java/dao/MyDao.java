package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dto.Customer;
import dto.TaskDB;

public class MyDao {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
	EntityManager manager = factory.createEntityManager();
	
	public void saveCustomer(Customer customer) {
		manager.getTransaction().begin();
		manager.persist(customer);
		manager.getTransaction().commit();
	}
	
	public void saveTask(TaskDB task) {
		manager.getTransaction().begin();
		manager.persist(task);
		manager.getTransaction().commit();
	}
	
	public List<TaskDB> fetchTask(int customer_id){
		return manager.createQuery("select x from TaskDB x where customer_id=?1").setParameter(1, customer_id).getResultList();
	}
	
//	public void delete(List<TaskDB> task) {
//		TaskDB taskname = manager.find(TaskDB.class, task);
//		manager.getTransaction().begin();
//		manager.remove(taskname);
//		manager.getTransaction().commit();
//	}
	public Customer findByEmail(String email) {
		List<Customer> customers = manager.createQuery("select x from Customer x where email = ?1").setParameter(1, email).getResultList();
		
		if(customers.isEmpty()) {
			return null;
		}
		else {
			return customers.get(0);
		}
	}

	public TaskDB findById(int id) {
		// TODO Auto-generated method stub
		return manager.find(TaskDB.class, id);
	}

	public void updateTask(TaskDB task) {
		// TODO Auto-generated method stub
		manager.getTransaction().begin();
		manager.merge(task);
		manager.getTransaction().commit();
	}

	public void deleteTask(TaskDB task) {
		// TODO Auto-generated method stub
		manager.getTransaction().begin();
		manager.remove(task);
		manager.getTransaction().commit();
	}

	public void editTask(int id, String taskUpdatedName, String taskUpdatedDesc) {
		// TODO Auto-generated method stub
		TaskDB tasks = manager.find(TaskDB.class, id);
		tasks.setTaskName(taskUpdatedName);
		tasks.setTaskDescription(taskUpdatedDesc);
		manager.getTransaction().begin();
		manager.merge(tasks);
		manager.getTransaction().commit();
		
	}
	
	
}
