package org.ecs160.a2.Storage;
import com.codename1.io.Storage;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.ecs160.a2.model.Task;

public class LocalStorage {

    /****************** public *******************/


    /******** GET ********/

    public Task getTask(String taskName) {
        Vector taskVector = (Vector)Storage.getInstance().readObject(taskName);
        Map<String, String> taskMap  = new HashMap<>();
        if (taskVector == null || taskVector.isEmpty()) {
            return  new Task(taskName,taskMap);
        } else {
            taskMap = (Map<String, String>) taskVector.get(0);
            return new Task(taskName,taskMap);
        }
    }

    public List<Task> getAllTasks () {
        final List<Task> taskList = new ArrayList<Task>();
        final List<String> taskNamesList = getTaskNameList();
        taskNamesList.forEach((taskName) -> {
            Task task = getTask(taskName);
            taskList.add(task);
        });
        return taskList;
    }


    /******** SET ********/

    public void addTask(Task task) {
        String taskName = task.name;
        if (doesTaskExist(taskName) || taskName.equals("") ) {
            System.out.print("this task already exists \n");
            return;
        }

        Vector vector = new Vector();
        Map<String, Object> taskMap  = new HashMap<>();

        taskMap.put("size", task.size);
        taskMap.put("description",  task.description);
        taskMap.put("isRunning",  "false");

        vector.addElement(taskMap);
        Storage.getInstance().writeObject(taskName, vector);

        addToTaskNameList(taskName);
    }



    public void editTask(Task newTask, Task oldTask) {

        deleteTask(oldTask);

        String taskName = newTask.name;

        Vector vector = new Vector();
        Map<String, String> taskMap  = new HashMap<>();
        taskMap.put("size", newTask.size);
        taskMap.put("description",  newTask.description);
        taskMap.put("isRunning",  "false");
        taskMap.put("startTime",  newTask.startTime.toString());
        taskMap.put("endTime", newTask.endTime.toString());
        vector.addElement(taskMap);
        Storage.getInstance().writeObject(taskName, vector);

        addToTaskNameList(taskName);
    }

    public void deleteTask(Task task) {
        deleteSingularTaskObject(task.name);
        removeFromTaskNameList(task.name);
    }

    public void startTask(Task task) {
        String taskName = task.name;

        Vector vector = new Vector();
        Map<String, String> taskMap  = new HashMap<>();
        taskMap.put("size", task.size);
        taskMap.put("description",  task.description);
        taskMap.put("isRunning",  task.isRunning);
        taskMap.put("startTime",  task.startTime.toString());
        taskMap.put("endTime", task.endTime.toString());
        vector.addElement(taskMap);
        Storage.getInstance().writeObject(taskName, vector);

        addToTaskTimeList(task.name);
    }

    public void stopTask(Task task) {
        String taskName = task.name;

        Vector vector = new Vector();
        Map<String, String> taskMap  = new HashMap<>();
        taskMap.put("size", task.size);
        taskMap.put("description",  task.description);
        taskMap.put("isRunning",  task.isRunning);
        taskMap.put("startTime",  task.startTime.toString());
        taskMap.put("endTime", task.endTime.toString());
        vector.addElement(taskMap);
        Storage.getInstance().writeObject(taskName, vector);

        addToTaskTimeList(task.name);
    }

    private void addToTaskTimeList(String taskName) {
        Vector TimeListVector = new Vector();
        final List<String> timeList = getTaskTimeList(taskName);
        final String time = new Date().toString();

        timeList.add(time);
        TimeListVector.addElement(timeList);
        Storage.getInstance().writeObject(taskName, TimeListVector);
    }

    private List<String> getTaskTimeList(String taskName) {
        Vector TimeListVector = (Vector)Storage.getInstance().readObject(taskName);
        if (TimeListVector == null) {
            return new ArrayList<String>();
        } else {
            return (List<String>) TimeListVector.get(0);
        }
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
    public void deleteAllNames() {
        Storage.getInstance().deleteStorageFile("allTasks");
    }
}
