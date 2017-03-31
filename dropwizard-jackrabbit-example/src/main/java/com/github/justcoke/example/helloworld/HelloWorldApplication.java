package com.github.justcoke.example.helloworld;

import com.github.justcoke.dropwizard.jackrabbit.JackrabbitBundle;
import com.github.justcoke.example.helloworld.resources.JcrResource;
import com.github.justcoke.example.helloworld.services.JcrDAO;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

	JackrabbitBundle<HelloWorldConfiguration> jackrabbitBundle;
	
	public static void main(String[] args) throws Exception {
		new HelloWorldApplication().run(args);
	}

//	private final JackrabbitBundle<HelloWorldConfiguration> jackrabbitBundle = new JackrabbitBundle<HelloWorldConfiguration>();

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		// bootstrap.addBundle(guiceBundle);
		bootstrap.addBundle(jackrabbitBundle =
		        JackrabbitBundle.<HelloWorldConfiguration> builder()
		            .withConfiguration(HelloWorldConfiguration::getRepository).build());
	}

	@Override
	public void run(HelloWorldConfiguration helloWorldConfiguration, Environment environment) throws Exception {
		final JcrDAO dao = new JcrDAO(jackrabbitBundle.getRepository());
		environment.jersey().register(new JcrResource(dao));
	}
}
