package com.github.justcoke.dropwizard.jcr;

import javax.jcr.Repository;

import io.dropwizard.lifecycle.Managed;

public interface ManagedRepository extends Repository, Managed {

}
