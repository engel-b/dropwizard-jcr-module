package com.github.justcoke.dropwizard.jackrabbit;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.enterprise.inject.Produces;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.validation.constraints.NotNull;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.justcoke.dropwizard.jcr.ManagedRepository;
import com.github.justcoke.dropwizard.jcr.ManagedRepositoryImpl;

public class JackrabbitRepositoryFactory {

	protected static final Logger LOGGER = LoggerFactory.getLogger(JackrabbitRepositoryFactory.class);

	/**
	 * cnd-configuration
	 */
	private String cndFileURL;

	/**
	 * file of xml-configuration of repository
	 */
	private String config = "defaultRepository.xml";

	/**
	 * Path, where to store the repository physically
	 */
	@NotNull
	private String homeDir = null;

	private ManagedRepository managedRepository = null;

	@Produces
	public Repository build(MetricRegistry metricRegistry) throws RepositoryException, IOException {

		if (managedRepository == null) {
			Repository repository = null;

			final RepositoryConfig config = RepositoryConfig.install(new File(getConfig()), new File(getHomeDir()));
			repository = RepositoryImpl.create(config);

			managedRepository = new ManagedRepositoryImpl(repository, metricRegistry);
		}

		return managedRepository;
	}

	@JsonProperty
	public String getCndFileURL() {
		return cndFileURL;
	}

	@JsonProperty
	public String getConfig() throws MalformedURLException {
		return config;
	}

	@JsonProperty
	public String getHomeDir() {
		return homeDir;
	}

	@JsonProperty
	public void setCndFileURL(String cndFileURL) {
		this.cndFileURL = cndFileURL;
	}

	@JsonProperty
	public void setConfig(String config) {
		this.config = config;
	}

	@JsonProperty
	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}
}
