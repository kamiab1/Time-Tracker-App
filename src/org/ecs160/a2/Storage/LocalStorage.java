package org.ecs160.a2.Storage;
import com.codename1.io.Storage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.ecs160.a2.Model.Task;


public class LocalStorage {
    private final SimpleDateFormat formatter =
            new SimpleDateFormat("hh:mm:ss");
    /****************** public *******************/


    /******** GET ********/

    public Task getTask(String taskName) {
        List<String> timeList;

        if (taskName.equals("")) {
            timeList = new ArrayList<String>();
            Map<String, String> taskMap  = new HashMap<>();
            return new Task(taskName,taskMap, timeList);
        }

        Vector taskVector = (Vector)Storage.getInstance().readObject(taskName);
        Map<String, String> taskMap = new HashMap<>();


        if (taskVector == null || taskVector.isEmpty()) {
            timeList = new ArrayList<String>();
            return new Task(taskName, taskMap,timeList);
        } else {
            taskMap = (Map<String, String>) taskVector.get(0);
            timeList = getTaskTimeList(taskName);
            return new Task(taskName, taskMap,timeList);
        }
    }

    public List<Task> getAllTasks () {
        final List<String> taskNamesList = getTaskNameList();

        return taskNamesList
                .stream()
                .map(this::getTask)
                .collect(Collectors.toList());
    }


    /******** SET ********/

    public void addTask(Task task) {

        if (doesTaskExist(task.name) || task.name.equals("") ) {
            System.out.print("this task already exists \n");
            return;
        }

        saveTaskOnDisk(task);
        addToTaskNameList(task.name);
    }

    public void editTask(Task newTask, Task oldTask) {
        deleteTask(oldTask);
        saveTaskOnDisk(newTask);
        addToTaskNameList(newTask.name);
    }

    public void deleteTask(Task task) {
        deleteSingularTaskObject(task.name);
        deleteTaskTimeList(task.name);
        removeFromTaskNameList(task.name);
    }

    public void startTask(Task task) {
        if (getTask(task.name).getIsRunning()) {
            return; // cant start if its already running
        }
        task.isRunning = "true";
        saveTaskOnDisk(task);
        addToTaskTimeList(task.name);
    }


    public void stopTask(Task task) {
        if (!getTask(task.name).getIsRunning()) {
            return; // cant STOP if its already STOPED
        }
        task.isRunning = "false";
        saveTaskOnDisk(task);
        addToTaskTimeList(task.name);
    }




    /**************** Private ****************/

    private void addToTaskTimeList(String taskName) {
        Vector TimeListVector = new Vector();
        final List<String> timeList = getTaskTimeList(taskName);

        final String time = formatter.format(new Date());
        //new Date().toString();

        timeList.add(time);
        TimeListVector.addElement(timeList);

        String path = taskName + "time";
        Storage.getInstance().writeObject(path, TimeListVector);
    }

    private List<String> getTaskTimeList(String taskName) {
        String path = taskName + "time";
        Vector TimeListVector = (Vector)Storage.getInstance().readObject(path);
        if (TimeListVector == null || TimeListVector.isEmpty()) {
            return new ArrayList<String>();
        } else {
            return (List<String>) TimeListVector.get(0);
        }
    }

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

    private void saveTaskOnDisk(Task task) {
        Vector vector = new Vector();
        Map<String, String> taskMap  = new HashMap<>();
        taskMap.put("size", task.size);
        taskMap.put("description",  task.description);
        taskMap.put("isRunning",  task.isRunning);
        vector.addElement(taskMap);
        Storage.getInstance().writeObject(task.name, vector);
    }

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
    private void deleteTaskTimeList(String name) {
        String path = name + "time";
        Storage.getInstance().deleteStorageFile(path);
    }

    public void deleteAllNames() {
        Storage.getInstance().deleteStorageFile("allTasks");
    }
}
