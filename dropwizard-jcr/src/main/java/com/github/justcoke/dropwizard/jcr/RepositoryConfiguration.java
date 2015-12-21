package com.github.justcoke.dropwizard.jcr;

import javax.jcr.Repository;

import io.dropwizard.Configuration;

public interface RepositoryConfiguration<T extends Configuration> {
	Repository getRepository(T configuration);
}
