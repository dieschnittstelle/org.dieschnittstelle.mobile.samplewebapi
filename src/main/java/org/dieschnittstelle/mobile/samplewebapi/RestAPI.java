package org.dieschnittstelle.mobile.samplewebapi;

import org.dieschnittstelle.mobile.samplewebapi.impl.RemoteTodoCRUDOperationsImpl;
import org.dieschnittstelle.mobile.samplewebapi.impl.RemoteUserOperationsImpl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by master on 07.03.16.
 */
@ApplicationPath("/api")
public class RestAPI extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet(Arrays.asList(new Class[]{RemoteTodoCRUDOperationsImpl.class, RemoteUserOperationsImpl.class}));
    }
}
