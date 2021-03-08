package org.ecs160.a2.Storage;
import com.codename1.io.Storage;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.ecs160.a2.model.Task;

public class LocalStorage {

    /****************** public *******************/


    /******** GET ********/

    public Task getTask(String taskName) {
        Vector val = (Vector)Storage.getInstance().readObject(taskName);
        Map<String, String> map = (Map<String, String>) val.get(0);
        return new Task(taskName,map);
    }

    public List<Task> getAllTasks () {
        final List<Task> taskList = new ArrayList<Task>();
        final List<String> taskNamesList = getTaskNameList();
        taskNamesList.forEach( (taskName) -> {
            Task task = getTask(taskName);
            taskList.add(task);
        });
        return taskList;
    }


    /******** SET ********/

    public void addTask(Task task) {
        String taskName = task.name;
        if (doesTaskExist(taskName)) {
            System.out.print("this task already exists \n");
            return;
        }

        Vector vector = new Vector();
        Map<String, String> map    = new HashMap<>();
        map.put("size", task.size);
        map.put("description",  task.description);
        map.put("isRunning",  "false");
        map.put("startTime",  task.startTime.toString());
        map.put("endTime", task.endTime.toString());
        vector.addElement(map);
        Storage.getInstance().writeObject(taskName, vector);
        // add to the list
        //deleteTaskNameList();
        addToTaskNameList(taskName);
    }


    public void editTask(Task task) {

    }

    public void deleteTask(Task task) {
        deleteSingularTaskObject(task.name);
        removeFromTaskNameList(task.name);
    }



    /**************** Private ****************/


    private List<String> getTaskNameList() {
        Vector allTasks = (Vector)Storage.getInstance().readObject("allTasks");
        if (allTasks == null) {
            return new ArrayList<String>();
        } else {
            List<String> nameList = (List<String>) allTasks.get(0);
            return nameList;
        }
    }

    private void addToTaskNameList(String taskName) {
        Vector vector = new Vector();
        final List<String> nameList = getTaskNameList();
        nameList.add(taskName);
        vector.addElement(nameList);
        Storage.getInstance().writeObject("allTasks", vector);
    }

    /**************** Helper ****************/

    private boolean doesTaskExist(String taskName) {
        final List<String> taskNamesList = getTaskNameList();
        AtomicBoolean exist = new AtomicBoolean(false);
        taskNamesList.forEach((eachTaskName) -> {
            if (eachTaskName.equals(taskName)) {
                exist.set(true);
                return;
            }
        });
        return exist.get();
    }

    private void removeFromTaskNameList(String name) {
        Vector vector = new Vector();
        final List<String> nameList = getTaskNameList();
        nameList.remove(name);
        vector.addElement(nameList);
        Storage.getInstance().writeObject("allTasks", vector);
    }

    private void deleteSingularTaskObject(String name) {
        Storage.getInstance().deleteStorageFile(name);
    }

}