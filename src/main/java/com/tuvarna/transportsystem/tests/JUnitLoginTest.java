package com.tuvarna.transportsystem.tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.tuvarna.transportsystem.controllers.LoginController;

import javafx.application.Application;


public class JUnitLoginTest {

	@BeforeClass
	public void createThread() throws InterruptedException {
		System.out.printf("About to launch FX App\n");
	    Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	            Application.launch(com.tuvarna.transportsystem.main.App.class, new String[0]);
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	    Thread.sleep(500);    
	}
}
