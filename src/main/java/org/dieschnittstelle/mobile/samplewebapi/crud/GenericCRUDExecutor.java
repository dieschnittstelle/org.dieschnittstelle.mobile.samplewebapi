package org.dieschnittstelle.mobile.samplewebapi.crud;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * partial generic CRUD operations for objects which are read in /
 * written to a file
 *
 * @author kreutel
 *
 */
public class GenericCRUDExecutor<T extends GenericCRUDEntity> {

	protected static Logger logger = Logger.getLogger(GenericCRUDExecutor.class);

	/**
	 * the id counter
	 */
	private long currentObjectId;

	/*
	 * whether we accept external ids
	 */
	private boolean acceptExternalIds;


	/**
	 * the file that contains the "database"
	 */
	private File objectsDatabaseFile;

	/**
	 * the list of objects that is managed by this class
	 */
	private List<T> objects = new ArrayList<T>();

	/**
	 * create the executor passing a file
	 */
	public GenericCRUDExecutor(File databaseFile) {
		logger.info("<constructor>: " + databaseFile);
		this.objectsDatabaseFile = databaseFile;
	}

	public GenericCRUDExecutor(File databaseFile,boolean acceptExternalIds) {
		this(databaseFile);
		this.acceptExternalIds = acceptExternalIds;
	}

	/**
	 * create an object
	 */
	public T createObject(T obj) {
		logger.info("createObject(): " + obj);

		// consider the case that ids are set from outside
		if (acceptExternalIds) {
			// check whether the element has an id set - it might be problematic if the id 0 is given - this needs to
			if (obj.getId() > 0) {
				// check whether we already have an an object with the given id
				T existingobj = this.readObject(obj.getId());
				if (existingobj != null) {
					logger.warn("createObject(): object with id " + obj.getId() + " already exists. Will replace it.");
					deleteObject(obj.getId());
				} else {
					logger.info("createObject(): will use id set on object " + obj.getId());
				}
			}
			else {
				// this is kindof over-complicated and inefficient, but just in case that ids may be both set from outside and locally, we need to prevent duplications
				long nextid;
				T existingobj;
				do {
					nextid = currentObjectId++;
					existingobj = readObject(nextid);
				}
				while (existingobj != null);
				logger.info("createObject(): will assign local id: " + nextid);
				obj.setId(nextid);
			}
			this.objects.add(obj);
		}
		else {
			// assign an id and add it to the list
			obj.setId(currentObjectId++);
			this.objects.add(obj);
		}

		return obj;
	}

	/**
	 * read all object
	 */
	public List<T> readAllObjects() {
		logger.info("readAllObjects(): " + this.objects);

		return this.objects;
	}

	/**
	 * delete an object given its id
	 */
	public boolean deleteObject(final long toDeleteId) {
		logger.info("deleteObject(): " + toDeleteId);

		try {
			return this.objects.remove(readObject(toDeleteId));
		} catch (Exception e) {
			logger.error("got an exception trying to delete object for id "
					+ toDeleteId + ". Supposedly, this object does not exist.");
			return false;
		}
	}

	/**
	 * update an existing object
	 */
	public T updateObject(final T obj) {
		logger.info("updateObject(): " + obj);

		// we try to read the object given the id of the object that is being
		// updated
		T readObj = readObject(obj.getId());

		if (readObj == null) {
			logger.info("could not find object to be updated with id "
					+ obj.getId() + ". Will create a new one.");
			return createObject(obj);
		}

		// we just replace the existing object with the new one
		int index = this.objects.indexOf(readObj);
		this.objects.remove(index);
		this.objects.add(index, obj);

		return obj;
	}

	/**
	 * load the data from the file
	 */
	public void load() {
		logger.info("load()...");

		try {
			if (!this.objectsDatabaseFile.exists()) {
				logger.info("the file " + this.objectsDatabaseFile
						+ " does not exist yet. Will not try to read anything.");
			} else {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(this.objectsDatabaseFile));

				// first we try to read the currentObjectId
				this.currentObjectId = ois.readLong();
				logger.info("load(): read objectId: " + currentObjectId);

				// then try to read the objects
				T obj = null;
				do {
					obj = (T) ois.readObject();
					logger.info("load(): read object: " + obj);

					if (obj != null) {
						this.objects.add(obj);
					}
				} while (true);
			}
		} catch (EOFException eof) {
			logger.info("we have reached the end of the data file");
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err);
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		logger.info("load(): objects are: " + objects);
	}

	/**
	 * store the data to the file
	 */
	public void store() {
		logger.info("store()...");

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(this.objectsDatabaseFile));

			// write the currentObjectId
			oos.writeLong(this.currentObjectId);
			// then write the objects
			for (T tp : this.objects) {
				oos.writeObject(tp);
			}
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err);
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		logger.info("store(): done.");
	}

	/*
	 * read an object given its id
	 */
	public T readObject(long i) {
		logger.info("readObject(): " + i);

		for (T obj : this.objects) {
			if (obj.getId() == i) {
				return obj;
			}
		}

		return null;
	}

}
