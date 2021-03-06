package org.dieschnittstelle.mobile.samplewebapi.impl;

import org.dieschnittstelle.mobile.samplewebapi.Todo;

import java.util.*;

public class TodoPopulator {

    private static final long GRANULARITY = 15*60*1000;

    public static List<Todo> createDefaultTodos() {
        // take the current time
        long currenttime = (System.currentTimeMillis() / GRANULARITY) * GRANULARITY;

        // create a calendar
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date(currenttime));

        List<Todo> todos = new ArrayList<>();

        for (String name : Arrays.asList("dorem","lipsum","olor","sed","adispiscing","consectetur","elit")) {

            Todo todo = new Todo();
            todo.setName(name);
            todo.setDescription("");
            // we add one day
            if (todos.size() == 0) {
                todo.setExpiry(currenttime - (10 * GRANULARITY));
            }
            else {
                cal.add(Calendar.DAY_OF_MONTH,1);
                todo.setExpiry(cal.getTimeInMillis());
            }

            todos.add(todo);
        }

        return todos;
    }

}
