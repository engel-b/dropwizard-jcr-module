package com.github.justcoke.example.helloworld;

import java.io.IOException;

import javax.inject.Named;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class HelloWorldModule extends AbstractModule {
	@Override
	protected void configure() {
	}

	@Provides
	@Named("jcr")
	public Repository provideRepository(HelloWorldConfiguration configuration) {
		try {
			return configuration.getRepository().build(null);
		} catch (RepositoryException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
