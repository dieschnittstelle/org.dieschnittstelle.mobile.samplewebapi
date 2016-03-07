package org.dieschnittstelle.mobile.samplewebapi.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.dieschnittstelle.mobile.samplewebapi.Todo;
import org.dieschnittstelle.mobile.samplewebapi.ITodoCRUDOperations;
import org.dieschnittstelle.mobile.samplewebapi.crud.GenericCRUDExecutor;

public class RemoteTodoCRUDOperationsImpl implements
		ITodoCRUDOperations {

	protected static Logger logger = Logger
			.getLogger(RemoteTodoCRUDOperationsImpl.class);

	/**
	 * the executor that actually performs the CRUD operations
	 */
	private GenericCRUDExecutor<Todo> crudExecutor;
	
	/**
	 * here we will be passed the context parameters by the resteasy framework
	 * note that the request context is only declared for illustration purposes, but will not be further used here
	 * @param servletContext
	 */	
	public RemoteTodoCRUDOperationsImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		logger.info("<constructor>: " + servletContext + "/" + request);
		// read out the dataAccessor
		this.crudExecutor = (GenericCRUDExecutor<Todo>)servletContext.getAttribute("todoCRUD");
		
		logger.info("read out the todoCRUD from the servlet context: " + this.crudExecutor);		
	}

	
	@Override
	public List<Todo> readAllTodos() {
		logger.info("readAllTodos()");
		return this.crudExecutor.readAllObjects();
	}

	@Override
	public Todo createTodo(Todo item) {
		logger.info("createItem(): " + item);
		return this.crudExecutor.createObject(item);
	}

	@Override
	public boolean deleteTodo(final long itemId) {
		logger.info("deleteItem(): " + itemId);
		return this.crudExecutor.deleteObject(itemId);
	}

	@Override
	public Todo updateTodo(long id, Todo item) {
		item.setId(id);
		logger.info("updateItem(): " + item);
		return this.crudExecutor.updateObject(item);
	}

	@Override
	public Todo readTodo(final long itemId) {
		return this.crudExecutor.readObject(itemId);
	}

}
