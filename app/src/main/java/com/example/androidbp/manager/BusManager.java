package com.example.androidbp.manager;

import com.squareup.otto.Bus;

/**
 * Created by sharp on 12/12/14 AD.
 */
public class BusManager {
    private static Bus bus = new Bus();

    public static void register(Object object) {
        bus.register(object);
    }

    public static void unregister(Object object) {
        bus.unregister(object);
    }

    public static void post(Object event) {
        bus.post(event);
    }
}
