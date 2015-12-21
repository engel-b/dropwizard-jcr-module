package com.github.justcoke.example.helloworld;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.justcoke.dropwizard.jackrabbit.JackrabbitRepositoryFactory;

import io.dropwizard.Configuration;

public class HelloWorldConfiguration extends Configuration {
	@Valid
	@NotNull
	@JsonProperty
	private JackrabbitRepositoryFactory repository = new JackrabbitRepositoryFactory();

	public JackrabbitRepositoryFactory getRepository() {
		return repository;
	}
}
