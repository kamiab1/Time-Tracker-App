package org.ecs160.a2.Storage;

import org.ecs160.a2.model.Task;
import java.util.List;


/** Flexibility to add CloudStorage in the future **/

public class Storage {
    private final LocalStorage localStorage = new LocalStorage();
    //* CloudStorage => for the Future if needed
    private static Storage SingletonInstance;

    public static Storage instanse() {
        if(SingletonInstance == null) {
            SingletonInstance = new Storage();
        }
        return SingletonInstance;
    }


    /****************** public *******************/


    /******** GET ********/

    public Task getTask(String taskName) {
        return localStorage.getTask(taskName);
    }

    public List<Task> getAllTasks () {
        return localStorage.getAllTasks();
    }


    /******** SET ********/

    public void addTask(Task task) {
        localStorage.addTask(task);
    }

    public void editTask(Task task) {

    }

    public void deleteTask(Task task) {
        localStorage.deleteTask(task);
    }
}
