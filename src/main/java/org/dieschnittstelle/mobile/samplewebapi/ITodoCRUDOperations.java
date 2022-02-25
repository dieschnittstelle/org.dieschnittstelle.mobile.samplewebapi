package org.dieschnittstelle.mobile.samplewebapi;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/todos")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ITodoCRUDOperations {

	/*
	 * the operations
	 */
	@POST
	public Todo createTodo(Todo item);

	@GET
	public List<Todo> readAllTodos();

	@GET
	@Path("/{itemId}")
	public Todo readTodo(@PathParam("itemId") long id);

	@PUT
	@Path("/{itemId}")
	public Todo updateTodo(@PathParam("itemId") long id, Todo item);

	@DELETE
	public boolean deleteAllTodos();

	@DELETE
	@Path("/{itemId}")
	public boolean deleteTodo(@PathParam("itemId") long dataItemId);

	@PUT
	@Path("/reset")
	public boolean reset();

}
