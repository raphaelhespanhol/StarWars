package com.raphahes.starwars.model.commons.exceptions;

/**
 * @author Raphael Hespanhol  @since 2019-29-09
 * HTTP has a few important verbs.
 *     POST - Create a new resource
 *     GET - Read a resource
 *     PUT - Update an existing resource
 *     DELETE - Delete a resource
 * 
 * HTTP also defines standard response codes.
 *     200 - SUCESS
 *     404 - RESOURCE NOT FOUND
 *     400 - BAD REQUEST
 *     201 - CREATED
 *     401 - UNAUTHORIZED
 *     415 - UNSUPPORTED TYPE - Representation not supported for the resource
 *     500 - SERVER ERROR
 *
 */

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String exception) {
		super(exception);
	}

}
