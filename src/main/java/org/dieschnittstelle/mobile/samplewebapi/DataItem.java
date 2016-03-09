package org.dieschnittstelle.mobile.samplewebapi;

import org.dieschnittstelle.mobile.samplewebapi.crud.GenericCRUDEntity;

import java.io.Serializable;
import java.util.List;

public class DataItem implements Serializable, GenericCRUDEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 3731856366358220228L;
	private long id;
	private String name;
	private String description;

	public DataItem(String name, String description) {
		this.name = name;
		this.description = description;
	}

	// a default constructor
	public DataItem() {
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean equals(Object other) {
		return this.getId() == ((DataItem) other).getId();
	}

}
