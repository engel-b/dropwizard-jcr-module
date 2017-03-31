package com.github.justcoke.example.helloworld;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.justcoke.dropwizard.jackrabbit.JackrabbitConfiguration;

import io.dropwizard.Configuration;

public class HelloWorldConfiguration extends Configuration {
	  @JsonProperty
	  protected JackrabbitConfiguration repository;

	  public JackrabbitConfiguration getRepository() {
	    return repository;
	  }

//	@Valid
//	@NotNull
//	@JsonProperty
//	private JackrabbitRepositoryFactory repository = new JackrabbitRepositoryFactory();
//
//    @JsonProperty("repository")
//	public JackrabbitRepositoryFactory getRepository() {
//		return repository;
//	}
//    @JsonProperty("repository")
//    public void setRepository(JackrabbitRepositoryFactory factory) {
//		this.repository = factory;
//	}
}
