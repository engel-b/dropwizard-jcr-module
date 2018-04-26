package com.github.justcoke.example.helloworld;

import javax.jcr.Repository;

import com.github.justcoke.dropwizard.jackrabbit.JackrabbitBundle;

import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class HelloWorldModule extends DropwizardAwareModule<HelloWorldConfiguration> {
	private final JackrabbitBundle<HelloWorldConfiguration> jackrabbitBundle;

	public HelloWorldModule(final JackrabbitBundle<HelloWorldConfiguration> jackrabbitBundle) {
		this.jackrabbitBundle = jackrabbitBundle;
	}

	@Override
	protected void configure() {
		bind(Repository.class).toInstance(jackrabbitBundle.getRepository());
	}
}
