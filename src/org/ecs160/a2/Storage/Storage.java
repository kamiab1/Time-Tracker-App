package org.ecs160.a2.Storage;

import org.ecs160.a2.Model.Task;
import java.util.List;


/** This gives flexibility to add CloudStorage in the future **/

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

    public void startTask(Task task) {
        localStorage.startTask(task);
    }

    public void stopTask(Task task) {
        localStorage.stopTask(task);
    }

    public boolean addTask(Task task) {
        return localStorage.addTask(task);
    }

    public void editTask(Task newTask, Task oldTask) {
        localStorage.editTask(newTask, oldTask);
    }

    public void deleteTask(Task task) {
        localStorage.deleteTask(task);
    }
}
