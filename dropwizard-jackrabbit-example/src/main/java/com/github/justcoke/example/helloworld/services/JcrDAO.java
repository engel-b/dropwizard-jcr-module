package com.github.justcoke.example.helloworld.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.jcr.Binary;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.ValueFactory;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.value.ValueFactoryImpl;

public class JcrDAO {

	private Repository repository;

	@Inject
	public JcrDAO(Repository repository) {
		this.repository = repository;
	}

	private String[] explode(String path) {
		if (path == null || path.isEmpty()) {
			throw new IllegalArgumentException("path must not be empty");
		} else if (path.endsWith("/")) {
			throw new IllegalArgumentException("path must not end with '/'");
		} else if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return path.split("/");
	}

	public byte[] fetch(final String path) throws LoginException, RepositoryException, IOException {
		if (repository == null) {
			throw new RepositoryException("repo failed to initialize");
		}
		return get(path);
	}

	private byte[] get(final String path) throws LoginException, RepositoryException, IOException {
		byte[] ret = null;
		Session session = null;
		try {
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
			Binary b = session.getRootNode().getProperty(path).getBinary();
			ret = IOUtils.toByteArray(b.getStream());
			b.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.logout();
			}
		}
		return ret;
	}

	private void set(final String path, final byte[] content) throws LoginException, RepositoryException {
		Session session = null;
		try {
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
			String[] parts = explode(path);
			Node node = session.getRootNode();
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if (i == parts.length - 1) {
					ValueFactory vf = ValueFactoryImpl.getInstance();
					Binary b = vf.createBinary(new ByteArrayInputStream(content));
					node.setProperty(part, b);
				} else {
					// TODO disallow same-name siblings.
					try {
						node = node.getNode(part);
					} catch (PathNotFoundException e) {
						node = node.addNode(part);
					}
				}
			}
			session.save();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public void store(final String path, final byte[] content) throws LoginException, RepositoryException {
		if (repository == null) {
			throw new RepositoryException("repo failed to initialize");
		}
		set(path, content);
	}
}
