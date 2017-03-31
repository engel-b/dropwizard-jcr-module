package com.github.justcoke.example.helloworld.services;

import java.io.IOException;

import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.github.justcoke.example.helloworld.services.JcrDAO;

public class WarehouseTest {
	@Mock
	Repository repository;

	JcrDAO warehouse;

	@Test(expected = RepositoryException.class)
	public void fetchRepositoryNull() throws LoginException, RepositoryException, IOException {
		warehouse = new JcrDAO(null);
		warehouse.fetch("");
	}

	@Before
	public void setup() {
		warehouse = new JcrDAO(repository);
	}
}
