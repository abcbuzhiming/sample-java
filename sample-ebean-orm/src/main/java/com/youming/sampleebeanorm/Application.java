package com.youming.sampleebeanorm;

import com.youming.sampleebeanorm.domain.Customer;

import io.ebean.DB;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello Ebean ORM!");
		insert();
	}

	public static void insert() {
		Customer customer = new Customer();
		customer.setName("Hello world");

		// insert the customer in the DB
		DB.save(customer);
	}
}
