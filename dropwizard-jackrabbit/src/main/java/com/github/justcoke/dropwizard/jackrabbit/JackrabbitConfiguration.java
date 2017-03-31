package com.github.justcoke.dropwizard.jackrabbit;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JackrabbitConfiguration {
	public static Logger LOGGER = LoggerFactory.getLogger(JackrabbitBundle.class);

	@NotNull
	protected String homeDir;

	@NotNull
	protected String repositoryXml;

	public String getHomeDir() {
		return homeDir;
	}

	public void setHomeDir(final String homeDir) {
		this.homeDir = homeDir;
	}

	public String getRepositoryXml() {
		return repositoryXml;
	}

	public void setRepositoryXml(final String repositoryXml) {
		this.repositoryXml = repositoryXml;
	}
}
