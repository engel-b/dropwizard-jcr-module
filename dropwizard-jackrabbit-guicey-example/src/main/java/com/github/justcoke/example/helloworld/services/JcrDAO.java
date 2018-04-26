package com.github.justcoke.example.helloworld.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;
import javax.jcr.Binary;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.ValueFactory;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.value.ValueFactoryImpl;

public class JcrDAO {

	private final Repository repository;

	@Inject
	public JcrDAO(final Repository repository) {
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
			session = getSession();
			final Binary b = session.getRootNode().getProperty(path).getBinary();
			ret = IOUtils.toByteArray(b.getStream());
			b.dispose();
		} catch (final Exception e) {
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
			session = getSession();
			final String[] parts = explode(path);
			Node node = session.getRootNode();
			for (int i = 0; i < parts.length; i++) {
				final String part = parts[i];
				if (i == parts.length - 1) {
					final ValueFactory vf = ValueFactoryImpl.getInstance();
					final Binary b = vf.createBinary(new ByteArrayInputStream(content));
					node.setProperty(part, b);
				} else {
					// TODO disallow same-name siblings.
					try {
						node = node.getNode(part);
					} catch (final PathNotFoundException e) {
						node = node.addNode(part);
					}
				}
			}
			session.save();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	private Session getSession() throws LoginException, RepositoryException {
		return repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
	}

	public void store(final String path, final byte[] content) throws LoginException, RepositoryException {
		if (repository == null) {
			throw new RepositoryException("repo failed to initialize");
		}
		set(path, content);
	}

	public String createData() throws Exception {

		final Session session = getSession();

		try {
			final Node root = session.getRootNode();

			final Node contact1 = root.addNode(UUID.randomUUID().toString());
			contact1.setProperty("name", "Lustig");
			contact1.setProperty("vorname", "Peter");

			final Node dok1 = contact1.addNode(UUID.randomUUID().toString());
			dok1.setProperty("name", "Schreiben1");
			final Node dok2 = contact1.addNode(UUID.randomUUID().toString());
			dok2.setProperty("name", "Schreiben2");

			session.save();

			// Retrieve content
			return getData();
		} finally {
			session.logout();
		}
	}

	public String getData() throws Exception {
		final Session session = getSession();
		StringBuilder output = new StringBuilder();
		try {
			final Node root = session.getRootNode();

			output = listNode(root, output, 0);

			return output.toString();
		} catch (final Exception e) {
			throw e;
		} finally {
			session.logout();
		}
	}

	private StringBuilder listNode(final Node node, final StringBuilder output, final int ebene)
			throws RepositoryException {
		for (int i = 0; i < ebene; i++) {
			output.append("-");
			System.out.print("-");
		}
		output.append(node.getName() + "\n");
		System.out.println(node.getName());
		final NodeIterator nodes = node.getNodes();
		while (nodes.hasNext()) {
			output.append(listNode(nodes.nextNode(), output, ebene + 1));
		}
		return output;
	}
}
