package com.github.justcoke.dropwizard.jcr;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;

public class ManagedRepositoryImpl implements ManagedRepository {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ManagedRepositoryImpl.class);

	private Session adminSession;

	// TODO
	private final MetricRegistry metricRegistry;

	private final Repository repository;

	public ManagedRepositoryImpl(Repository repository, MetricRegistry metricRegistry) {
		this.repository = repository;
		this.metricRegistry = metricRegistry;
	} 

	@Override
	public String getDescriptor(String arg0) {
		return repository.getDescriptor(arg0);
	}

	@Override
	public String[] getDescriptorKeys() {
		return repository.getDescriptorKeys();
	}

	@Override
	public Value getDescriptorValue(String arg0) {
		return repository.getDescriptorValue(arg0);
	}

	@Override
	public Value[] getDescriptorValues(String arg0) {
		return repository.getDescriptorValues(arg0);
	}

	@Override
	public boolean isSingleValueDescriptor(String arg0) {
		return repository.isSingleValueDescriptor(arg0);
	}

	@Override
	public boolean isStandardDescriptor(String arg0) {
		return repository.isStandardDescriptor(arg0);
	}

	@Override
	public Session login() throws LoginException, RepositoryException {
		return adminSession = repository.login();
	}

	@Override
	public Session login(Credentials arg0) throws LoginException, RepositoryException {
		return adminSession = repository.login(arg0);
	}

	@Override
	public Session login(Credentials arg0, String arg1)
			throws LoginException, NoSuchWorkspaceException, RepositoryException {
		return adminSession = repository.login(arg0, arg1);
	}

	@Override
	public Session login(String arg0) throws LoginException, NoSuchWorkspaceException, RepositoryException {
		return adminSession = repository.login(arg0);
	}

	@Override
	public void start() throws Exception {
		LOGGER.info("Creating transient repository");
	}

	@Override
	public void stop() throws Exception {
		// try {
		if (adminSession != null) {
			adminSession.logout();
			adminSession = null;
		}
	}
}
