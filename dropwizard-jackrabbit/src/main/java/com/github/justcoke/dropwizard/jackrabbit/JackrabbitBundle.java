package com.github.justcoke.dropwizard.jackrabbit;

import java.io.File;
import java.util.function.Function;

import javax.jcr.Repository;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;
import com.github.justcoke.dropwizard.jcr.JcrBundle;
import com.github.justcoke.dropwizard.jcr.ManagedRepository;
import com.github.justcoke.dropwizard.jcr.ManagedRepositoryImpl;

import io.dropwizard.Configuration;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class JackrabbitBundle<C extends Configuration> extends JcrBundle<C> {
	public static class Builder<C extends Configuration> {
		protected Function<C, JackrabbitConfiguration> configurationAccessor;
		protected String healthCheckName = "jackrabbit";

		public JackrabbitBundle<C> build() {
			if (configurationAccessor == null) {
				throw new IllegalArgumentException("configuration accessor is required.");
			}
			return new JackrabbitBundle<C>(configurationAccessor, healthCheckName);
		}

		public Builder<C> withConfiguration(Function<C, JackrabbitConfiguration> configurationAccessor) {
			this.configurationAccessor = configurationAccessor;
			return this;
		}

		public Builder<C> withHealthCheckName(String healthCheckName) {
			this.healthCheckName = healthCheckName;
			return this;
		}
	}

	public static Logger LOGGER = LoggerFactory.getLogger(JackrabbitBundle.class);

	public static <C extends Configuration> Builder<C> builder() {
		return new Builder<C>();
	}

	protected Function<C, JackrabbitConfiguration> configurationAccessor;
	protected String healthCheckName;
	protected JackrabbitConfiguration jackrabbitConfiguration;

	Repository repository = null;

	public JackrabbitBundle(Function<C, JackrabbitConfiguration> configurationAccessor, String healthCheckName) {
		this.configurationAccessor = configurationAccessor;
		this.healthCheckName = healthCheckName;
	}

	Repository buildRepository(final JackrabbitConfiguration configuration) {
		try {
			ManagedRepository managedRepository = null;
			MetricRegistry metricRegistry = null;

			final RepositoryConfig config = RepositoryConfig.install(new File(configuration.getRepositoryXml()),
					new File(configuration.getHomeDir()));
			repository = RepositoryImpl.create(config);

			managedRepository = new ManagedRepositoryImpl(repository, metricRegistry);

			// final RepositoryConfig config1 = getRepositoryConfig();
			// LOGGER.info("Connecting to local repository at {}",
			// config1.getHomeDir());
			// Repository rep1 = RepositoryImpl.create(config1);

			return managedRepository;
		} catch (Exception e) {
			throw new RuntimeException("Could not create repository.", e);
		}
	}

	public Repository getRepository() {
		return repository;
	}

	@Override
	public void initialize(Bootstrap<?> bootstrap) {
	}

	@Override
	public void run(C configuration, Environment environment) throws Exception {
		jackrabbitConfiguration = configurationAccessor.apply(configuration);
		repository = buildRepository(jackrabbitConfiguration);
		environment.lifecycle().manage(new Managed() {
			@Override
			public void start() throws Exception {
			}

			@Override
			public void stop() throws Exception {
			}
		});
		environment.healthChecks().register(healthCheckName, new JackrabbitHealthCheck(repository));
	}
}
