package org.dieschnittstelle.mobile.samplewebapi.impl;

import org.apache.log4j.Logger;
import org.dieschnittstelle.mobile.samplewebapi.DataItem;
import org.dieschnittstelle.mobile.samplewebapi.crud.GenericCRUDExecutor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.crypto.Data;
import java.io.File;

@WebListener
public class RemoteDataItemCRUDServletContextListener implements ServletContextListener {

	protected static Logger logger = Logger
			.getLogger(RemoteDataItemCRUDServletContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent evt) {
		logger.info("contextDestroyed()");

		// we read out the TouchpointCRUDExecutor and let it store its content
		GenericCRUDExecutor<DataItem> exec = (GenericCRUDExecutor<DataItem>) evt.getServletContext()
				.getAttribute("dataitemCRUD");

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
		GenericCRUDExecutor<DataItem> exec = new GenericCRUDExecutor<DataItem>(new File(rootPath,
				"dataitems.data"));

		// we call load() on the executor to load any exsisting data (if there
		// are any)
		exec.load();

		exec.createObject(new DataItem("Item " + String.valueOf(System.currentTimeMillis()),
				"direm lopsum consectetur"));
		exec.createObject(new DataItem("Item " + String.valueOf(System.currentTimeMillis()),
				"lorem dipsum at simet"));

		// then we put the executor into the context to make it available to the
		// other components
		evt.getServletContext().setAttribute("dataitemCRUD", exec);

	}

}
