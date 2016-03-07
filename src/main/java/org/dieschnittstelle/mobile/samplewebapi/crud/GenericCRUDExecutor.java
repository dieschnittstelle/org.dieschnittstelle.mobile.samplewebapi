package org.dieschnittstelle.mobile.samplewebapi.crud;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * partial generic CRUD operations for objects which are read in /
 * written to a file
 * 
 * @author kreutel
 * 
 */
public class GenericCRUDExecutor<T extends GenericCRUDEntity> {

	/**
	 * the id counter
	 */
	private int currentObjectId;

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
		System.out.println("<constructor>: " + databaseFile);
		this.objectsDatabaseFile = databaseFile;
	}

	/**
	 * create an object
	 */
	public T createObject(T obj) {
		System.out.println("createObject(): " + obj);

		// assign an id and add it to the list
		obj.setId(currentObjectId++);
		this.objects.add(obj);

		return obj;
	}

	/**
	 * read all object
	 */
	public List<T> readAllObjects() {
		System.out.println("readAllObjects(): " + this.objects);

		return this.objects;
	}

	/**
	 * delete an object given its id
	 */
	public boolean deleteObject(final long toDeleteId) {
		System.out.println("deleteObject(): " + toDeleteId);

		try {
			return this.objects.remove(readObject(toDeleteId));
		} catch (Exception e) {
			System.err.println("got an exception trying to delete object for id "
					+ toDeleteId + ". Supposedly, this object does not exist.");
			return false;
		}
	}

	/**
	 * update an existing object
	 */
	public T updateObject(final T obj) {
		System.out.println("updateObject(): " + obj);

		// we try to read the object given the id of the object that is being
		// updated
		T readObj = readObject(obj.getId());

		if (readObj == null) {
			System.out.println("could not find object to be updated with id "
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
		System.out.println("load()...");

		try {
			if (!this.objectsDatabaseFile.exists()) {
				System.out.println("the file " + this.objectsDatabaseFile
						+ " does not exist yet. Will not try to read anything.");
			} else {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(this.objectsDatabaseFile));

				// first we try to read the currentObjectId
				this.currentObjectId = ois.readInt();
				System.out.println("load(): read objectId: " + currentObjectId);

				// then try to read the objects
				T obj = null;
				do {
					obj = (T) ois.readObject();
					System.out.println("load(): read object: " + obj);

					if (obj != null) {
						this.objects.add(obj);
					}
				} while (true);
			}
		} catch (EOFException eof) {
			System.out.println("we have reached the end of the data file");
		} catch (Exception e) {
			String err = "got exception: " + e;
			System.err.println(err);
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		System.out.println("load(): objects are: " + objects);
	}

	/**
	 * store the data to the file
	 */
	public void store() {
		System.out.println("store()...");

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(this.objectsDatabaseFile));

			// write the currentObjectId
			oos.writeInt(this.currentObjectId);
			// then write the objects
			for (T tp : this.objects) {
				oos.writeObject(tp);
			}
		} catch (Exception e) {
			String err = "got exception: " + e;
			System.err.println(err);
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		System.out.println("store(): done.");
	}

	/*
	 * read an object given its id
	 */
	public T readObject(long i) {
		System.out.println("readObject(): " + i);
		
		for (T obj : this.objects) {
			if (obj.getId() == i) {
				return obj;
			}
		}

		return null;
	}

}
