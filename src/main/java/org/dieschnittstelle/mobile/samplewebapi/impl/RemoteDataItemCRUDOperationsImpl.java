package org.dieschnittstelle.mobile.samplewebapi.impl;

import org.dieschnittstelle.mobile.samplewebapi.DataItem;
import org.dieschnittstelle.mobile.samplewebapi.IDataItemCRUDOperations;
import org.dieschnittstelle.mobile.samplewebapi.Todo;
import org.dieschnittstelle.mobile.samplewebapi.crud.GenericCRUDExecutor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by master on 08.03.16.
 */
public class RemoteDataItemCRUDOperationsImpl implements IDataItemCRUDOperations {

    protected static Logger logger = Logger.getLogger(RemoteDataItemCRUDOperationsImpl.class);

    /**
     * the executor that actually performs the CRUD operations
     */
    private GenericCRUDExecutor<DataItem> crudExecutor;

    public RemoteDataItemCRUDOperationsImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
        logger.info("<constructor>: " + servletContext + "/" + request);
        // read out the dataAccessor
        this.crudExecutor = (GenericCRUDExecutor<DataItem>)servletContext.getAttribute("dataitemCRUD");

        logger.info("read out the dataItemCRUD from the servlet context: " + this.crudExecutor);
    }


    @Override
    public DataItem createDataItem(DataItem item) {
        return this.crudExecutor.createObject(item);
    }

    @Override
    public List<DataItem> readAllDataItems() {
        return this.crudExecutor.readAllObjects();
    }

    @Override
    public DataItem readDataItem(long dataItemId) {
        return this.crudExecutor.readObject(dataItemId);
    }

    @Override
    public DataItem updateDataItem(DataItem item) {
        return this.crudExecutor.updateObject(item);
    }

    @Override
    public boolean deleteDataItem(long dataItemId) {
        return this.crudExecutor.deleteObject(dataItemId);
    }
}
