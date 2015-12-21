# JCR support 4 dropwizard
A set of modules for connecting a Dropwizard app to a **J**ava **C**ontent **R**epository

The set contains:
* dropwizard-jcr - some basic jcr interfaces and classes
* dropwizard-jackrabbit - support for jackrabbit (the reference implementation of jcr)
* dropwizard-jackrabbit-example - a basic example dropwizard app that uses the dropwizard


:heavy_exclamation_mark: Note: dropwizard-jcr is not listed in maven central yet. I`ll try my very best, but this all is new to me...

# Usage
This module assumes you are including it in a Dropwizard 0.9.0 application with dropwizard-guice wiring.

Include as a maven dependency:

```xml
<dependency>
    <groupId>com.github.justcoke</groupId>
    <artifactId>dropwizard-jackrabbit</artifactId>
    <version>${version.dropwizard-jackrabbit}</version>
</dependency>
```

Add configuration to your application's configuration class, like:

```java
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import com.github.justcoke.dropwizard.jackrabbit.JackrabbitRepositoryFactory;

public class HelloWorldConfiguration extends Configuration {
	@Valid
	@NotNull
	@JsonProperty
	private JackrabbitRepositoryFactory repository = new JackrabbitRepositoryFactory();

	public JackrabbitRepositoryFactory getRepository() {
		return repository;
	}
}

```

Add reasonable configuration options to your application YML file:

```yaml
...
repository:
    homeDir: <mandatory; path where to store the te repository>
    configFile: <xml-configuration of the jcr; if not set built in defaultRepository.xml will be used>
...
```

Make sure your Guice wiring "provides" the Repository as a bean so you are able to inject it in your business code:

```java
import java.io.IOException;
import javax.inject.Named;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class HelloWorldModule extends AbstractModule {
	...
	@Provides
	@Named("jcr")
	public Repository provideRepository(HelloWorldConfiguration configuration) {
		try {
			return configuration.getRepository().build(null);
		} catch (RepositoryException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
```

Finally in your application's main initialize() method, add your new HelloWorldModule to your guice bundle, for example:

```java
import com.hubspot.dropwizard.guice.GuiceBundle;

	private GuiceBundle<HelloWorldConfiguration> guiceBundle = null;

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		// Guice
		guiceBundle = GuiceBundle.<HelloWorldConfiguration> newBuilder().addModule(new HelloWorldModule())
				.enableAutoConfig(getClass().getPackage().getName()).setConfigClass(HelloWorldConfiguration.class)
				.build();

		bootstrap.addBundle(guiceBundle);
	}
```

Also have look at the dropwizard-jackrabbit-example.