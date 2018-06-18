package org.dieschnittstelle.mobile.samplewebapi;

import org.dieschnittstelle.mobile.samplewebapi.crud.GenericCRUDEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Todo implements Serializable, GenericCRUDEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -6410064189686738560L;

	/**
	 *
	 */

	// the id
	private long id;

	// name and decription
	private String name;
	private String description;

	// expirydate as long value
	private long expiry;

	// whether the todo is done
	private boolean done;

	// whether it is a favourite
	private boolean favourite;

	// a list of contacts with whom the item is associated - for allowing various solutions how contacts may be represented, we foresee an array of strings
	private List<String> contacts;

	// a geolocation
	private Location location;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<String> getContacts() {
		return contacts;
	}

	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}

	public Todo(String name, String description) {
		this.name = name;
		this.description = description;
	}

	// a default constructor
	public Todo() {
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
		return this.getId() == ((Todo) other).getId();
	}

	public String toString() {
		return "{Todo " + this.id + " " + this.name + ", " + this.description + this.expiry
				+ "}";
	}

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	/*
	 * an inner class for representing a geolocation
	 */
	public static class LatLng implements Serializable {

		private double lat;
		private double lng;

		public LatLng() {

		}

		public LatLng(double lat,double lng) {
			this.lat = lat;
			this.lng = lng;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}

	}

	public static class Location implements Serializable {

		private String name;

		private LatLng latlng;

		public Location() {

		}

		public Location(String name,LatLng latlng) {
			this.name = name;
			this.latlng = latlng;
		}

		public LatLng getLatlng() {
			return latlng;
		}

		public void setLatlng(LatLng latlng) {
			this.latlng = latlng;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


	}

}
