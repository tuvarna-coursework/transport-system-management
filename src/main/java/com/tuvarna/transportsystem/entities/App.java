package com.tuvarna.transportsystem.entities;

import com.tuvarna.transportsystem.services.CrudService;
import com.tuvarna.transportsystem.services.CrudServiceImpl;

public class App {

	public static void main(String[] args) {
		CrudService roleService = new CrudServiceImpl();
		
		roleService.create(new Role("User"));
		roleService.create(new Role("Admin"));
	}
}
