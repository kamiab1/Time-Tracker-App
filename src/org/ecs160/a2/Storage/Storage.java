package org.ecs160.a2.Storage;

public class Storage {
    private static Storage SingletonInstance;

    public static Storage instanse() {
        if(SingletonInstance == null) {
            SingletonInstance = new Storage();
        }

        return SingletonInstance;
    }

}
