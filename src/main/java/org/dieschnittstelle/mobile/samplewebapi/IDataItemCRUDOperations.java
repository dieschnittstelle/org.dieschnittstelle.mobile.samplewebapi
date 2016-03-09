package org.dieschnittstelle.mobile.samplewebapi;

import javax.ws.rs.*;
import java.util.List;

@Path("/dataitems")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface IDataItemCRUDOperations {

	/*
	 * the operations
	 */
	@POST
	public DataItem createDataItem(DataItem item);

	@GET
	public List<DataItem> readAllDataItems();

	@GET
	@Path("/{itemId}")
	public DataItem readDataItem(@PathParam("itemId")long dateItemId);

	@PUT
	public DataItem updateDataItem(DataItem item);

	@DELETE
	@Path("/{itemId}")
	public boolean deleteDataItem(@PathParam("itemId")long dataItemId);

}