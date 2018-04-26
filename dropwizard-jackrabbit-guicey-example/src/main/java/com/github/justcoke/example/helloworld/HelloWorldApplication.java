package com.github.justcoke.example.helloworld;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.justcoke.dropwizard.jackrabbit.JackrabbitBundle;
import com.github.justcoke.example.helloworld.resources.JcrResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.swagger.jaxrs.config.BeanConfig;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

	JackrabbitBundle<HelloWorldConfiguration> jackrabbitBundle;

	public static void main(final String[] args) throws Exception {
		new HelloWorldApplication().run(args);
	}

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(final Bootstrap<HelloWorldConfiguration> bootstrap) {
		jackrabbitBundle = JackrabbitBundle.<HelloWorldConfiguration>builder()
				.withConfiguration(HelloWorldConfiguration::getRepository).build();

		bootstrap.addBundle(jackrabbitBundle);
		bootstrap.addBundle(new SwaggerBundle<HelloWorldConfiguration>() {
			@Override
			protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
					final HelloWorldConfiguration sampleConfiguration) {
				// this would be the preferred way to set up swagger, you can also construct the
				// object here programtically if you want
				return sampleConfiguration.swaggerBundleConfiguration;
			}
		});
		bootstrap.addBundle(GuiceBundle.builder().enableAutoConfig(getClass().getPackage().getName()).useWebInstallers()
				.modules(new HelloWorldModule(jackrabbitBundle)).build());
	}

	@Override
	public void run(final HelloWorldConfiguration helloWorldConfiguration, final Environment environment)
			throws Exception {
		// final JcrDAO dao = new JcrDAO(jackrabbitBundle.getRepository());
		// environment.jersey().register(new JcrResource(new
		// JcrDAO(jackrabbitBundle.getRepository())));

//		environment.jersey().register(new JcrResource());
//		environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//		final BeanConfig config = new BeanConfig();
//		config.setTitle("Swagger sample app");
//		config.setVersion("1.0.0");
//		config.setResourcePackage("com.github.justcoke.example.helloworld.resources");
//		config.setScan(true);
	}
}
