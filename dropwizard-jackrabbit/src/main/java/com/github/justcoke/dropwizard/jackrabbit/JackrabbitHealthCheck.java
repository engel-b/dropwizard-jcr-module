package com.github.justcoke.dropwizard.jackrabbit;

import javax.jcr.Repository;
import javax.jcr.Session;

import com.codahale.metrics.health.HealthCheck;

public class JackrabbitHealthCheck extends HealthCheck {

	Repository repository;

	public JackrabbitHealthCheck(final Repository repository) {
		this.repository = repository;
	}

	@Override
	protected Result check() throws Exception {
		try {
			Session session = repository.login();
			session.logout();
			return Result.healthy();
		} catch (Exception me) {
			return Result.unhealthy("cannot access repository");
		}
	}
}
