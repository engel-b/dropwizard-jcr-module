package com.github.justcoke.dropwizard.jcr;

import io.dropwizard.Bundle;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public abstract class JcrBundle<T extends Configuration> implements Bundle {
	public void initialize(Bootstrap<?> bootstrap) {
	}

	public void run(Environment environment) {
		// nothing doing
	}
}
