package com.github.justcoke.dropwizard.jcr;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public abstract class JcrBundle<T extends Configuration>  implements ConfiguredBundle<T>, JcrRepositoryConfiguration<T> {
	@Override
	public void initialize(Bootstrap<?> bootstrap) {
	}

	@Override
	public void run(T arg0, Environment arg1) throws Exception {
		// TODO Auto-generated method stub
	}
}
