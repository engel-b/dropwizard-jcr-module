package com.github.justcoke.example.helloworld;

import com.hubspot.dropwizard.guice.GuiceBundle;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

	public static void main(String[] args) throws Exception {
		new HelloWorldApplication().run(args);
	}

	private GuiceBundle<HelloWorldConfiguration> guiceBundle = null;

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		// Guice
		guiceBundle = GuiceBundle.<HelloWorldConfiguration> newBuilder().addModule(new HelloWorldModule())
				.enableAutoConfig(getClass().getPackage().getName()).setConfigClass(HelloWorldConfiguration.class)
				.build();

		bootstrap.addBundle(guiceBundle);
	}

	@Override
	public void run(HelloWorldConfiguration helloWorldConfiguration, Environment environment) throws Exception {
	}
}
