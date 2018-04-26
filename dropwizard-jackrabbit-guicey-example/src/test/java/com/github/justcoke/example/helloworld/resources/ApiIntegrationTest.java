package com.github.justcoke.example.helloworld.resources;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.github.justcoke.example.helloworld.HelloWorldApplication;
import com.github.justcoke.example.helloworld.HelloWorldConfiguration;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class ApiIntegrationTest {
	@Rule
	public final DropwizardAppRule<HelloWorldConfiguration> RULE = new DropwizardAppRule<>(HelloWorldApplication.class,
			ResourceHelpers.resourceFilePath("integration-test.yml"));

	@Test
	@Ignore
	// FIXME
	public void setAndGetNode() {
		final String bodyText = "Hello world";

		Client client = ClientBuilder.newClient();

		Response saveResponse = client
				.target(String.format("http://localhost:%d/api/jcr/var?name=Test", RULE.getLocalPort())).request()
				.put(Entity.text(bodyText));

		Response getResponse = client
				.target(String.format("http://localhost:%d/api/jcr/var?name=Test", RULE.getLocalPort())).request()
				.get();

		String getBody = getResponse.readEntity(String.class);

		assertThat(saveResponse.getStatus()).isEqualTo(200);
		assertThat(getResponse.getStatus()).isEqualTo(200);
		assertThat(getBody).isEqualToIgnoringCase(bodyText);
	}
}
