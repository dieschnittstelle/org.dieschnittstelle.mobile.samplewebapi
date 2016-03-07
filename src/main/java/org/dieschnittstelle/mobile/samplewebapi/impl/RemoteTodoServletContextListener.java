package org.dieschnittstelle.mobile.samplewebapi.impl;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.dieschnittstelle.mobile.samplewebapi.Todo;
import org.dieschnittstelle.mobile.samplewebapi.crud.GenericCRUDExecutor;

@WebListener
public class RemoteTodoServletContextListener implements ServletContextListener {

	protected static Logger logger = Logger
			.getLogger(RemoteTodoServletContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent evt) {
		logger.info("contextDestroyed()");

		// we read out the TouchpointCRUDExecutor and let it store its content
		GenericCRUDExecutor<Todo> exec = (GenericCRUDExecutor<Todo>) evt.getServletContext()
				.getAttribute("todoCRUD");

		logger.info("contextDestroyed(): loaded executor from context: " + exec);

		if (exec == null) {
			logger.warn("contextDestroyed(): no executor found in context. Ignore.");
		} else {
			exec.store();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		logger.info("contextInitialised()");

		// we create a new executor for a file to be stored in the context root
		String rootPath = evt.getServletContext().getRealPath("/");
		GenericCRUDExecutor<Todo> exec = new GenericCRUDExecutor<Todo>(new File(rootPath,
				"todo.data"));

		// we call load() on the executor to load any exsisting data (if there
		// are any)
		exec.load();

		exec.createObject(new Todo(0, String.valueOf(System.currentTimeMillis()),
				"lorem"));
		exec.createObject(new Todo(1, String.valueOf(System.currentTimeMillis()),
				"ipsum"));

		// then we put the executor into the context to make it available to the
		// other components
		evt.getServletContext().setAttribute("todoCRUD", exec);

	}

}
