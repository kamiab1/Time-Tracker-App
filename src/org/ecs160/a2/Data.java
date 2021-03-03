package org.ecs160.a2;


import com.codename1.io.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.codename1.io.JSONParser;

public class Data {
    DataBase db = new DataBase();
    TaskManager taskManager = new TaskManager();
    public void addTask(String taskName){


        Vector vector = new Vector();
        Map<String, String> map = new HashMap<>();
        map.put("name", "jon doe");
        map.put("age", "22");
        map.put("city", "chicago");
        vector.addElement(map);

        Storage.getInstance().writeObject(taskName, vector);
        Vector val = (Vector)Storage.getInstance().readObject(taskName);
        System.out.print(val.get(0));
        Map<String, String> map2 = (Map<String, String>) val.get(0);
        //Map<String, Object> data = json.parseJSON();
        System.out.print(map2.get("city"));

    }
    private void startProgram(String[] args) {
        getInput(args);
    }
    // input from the user, its type it can change later on
    private void getInput(String[] args) {
        final String type = args[0];
        switch (type) {
            case "start":
                start(args);
                break;
            case "stop":
                stop(args);
                break;
            case "describe":
                describe(args);
                break;
            case "summary":
                summary(args);
                break;
            case "size":
                size(args);
                break;
            case "rename":
                rename(args);
                break;
            case "delete":
                delete(args);
                break;
            default:
                wrongInput();

        }
    }

    private void start(String[] args) {
        String taskName;
        try {
            taskName = args[1];
            this.db.saveStartTask(taskName);
        } catch (Exception e) {
            System.out.println("please provide a task name");
        }

    }

    private void stop(String[] args) {
        String taskName;
        try {
            taskName = args[1];
            this.db.saveStopTask(taskName);
        } catch (Exception e) {
            System.out.println("please provide a task name");
        }
    }

    private void describe(String[] args) {
        final String taskName = args[1];
        final String description = args[2];
        boolean isDescriptionAndSize =false;
        try {
            if (args[3] != null) {
                String taskSize = args[3];
                taskSize = taskSize.toUpperCase();
                this.db.saveDescriptionAndSizeTask(taskName,
                        description, taskSize);
                isDescriptionAndSize = true;
            }
        } catch (Exception e) {

        }
        if (!isDescriptionAndSize)
            this.db.saveDescriptionTask(taskName, description);
    }

    private void summary(String[] args) {
        String taskType = null;
        try {
            taskType = args[1];
        } catch (Exception e) {

        }

        if (taskType == null)
            this.taskManager.summaryTotal();
        else if (isInputOfTypeSize(taskType))
            this.taskManager.summaryBySize(taskType);
        else
            this.taskManager.summaryByTaskName(taskType);
    }

    private void size(String[] args) {
        final String taskName = args[1];
        String taskSize = args[2];
        taskSize = taskSize.toUpperCase();
        this.db.saveSizeTask(taskName, taskSize);
    }

    private void rename(String[] args) {
        final String oldName = args[1];
        final String newName = args[2];
        this.db.saveRenameTask(oldName, newName);
    }

    private void delete(String[] args) {
        final String taskName = args[1];
        this.db.saveDeletionTask(taskName);
    }

    private void wrongInput() {
        System.out.println("Wrong input try again");
    }

    /******* private helpers *******/
    private boolean isInputOfTypeSize(String taskType) {
        taskType = taskType.toUpperCase();
        if (taskType.equals("S") || taskType.equals("M")
                || taskType.equals("L") || taskType.equals("XL"))
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        Data tm = new Data();

       // tm.startProgram(args);
       // tm.testing();
    }

}

// thi class will manage eveything related to tasks
// adding , removing , summary ...
class TaskManager {

    final List<Task> taskList = new ArrayList<Task>();
    private final DataBase db = new DataBase();
    private List<TaskData> TaskDataList = new ArrayList<TaskData>();

    TaskManager(){
        this.TaskDataList = db.queryTaskData();
        buildEachTask(this.TaskDataList);
    }

    /********************** PUBLIC **********************/

    public void summaryTotal() {
        for (final Task task: taskList) {
            this.output(task);
        }
    }

    public void summaryByTaskName(String taskName) {
        final Task task = getTaskFromName(taskName);
        if (task == null) {
            System.out.println("No such task exist");
        }
        else  {
            this.output(task);
        }
    }

    public void summaryBySize(String size) {
        final List<Task> taskSizeList = getTaskBySize(size);
        List<Date>       maxTimeList  = new ArrayList<Date>();
        List<Date>       minTimeList  = new ArrayList<Date>();
        long totalTime = 0;
        for (final Task task: taskSizeList) {
            this.output(task);
            maxTimeList.add(task.getMaxDuration());
            minTimeList.add(task.getMinDuration());
            totalTime = totalTime + task.getTotalDuration().getTime();
        }
        final long avgTime = totalTime / taskSizeList.size();
        this.outputBySize(maxTimeList, minTimeList, avgTime);
    }



    /********************** PRIVATE **********************/

    private void buildEachTask(List<TaskData> TaskDataList) {

        for (final TaskData taskData: TaskDataList) {
            final Task task = getTaskFromName(taskData.name);
            if (task == null) {
                createNewTask(taskData);
            } else {
                populateExictingTask(task, taskData);
            }
        }
    }

    // if no task with this name was ever made
    private void createNewTask(TaskData taskData) {
        final Task newTask = new Task(taskData.name);
        this.startTask(newTask, taskData);
        taskList.add(newTask);
    }

    // just add more data to an existing task
    private void populateExictingTask(Task task, TaskData taskData) {
        if (taskData.type.isStart) {
            this.startTask(task, taskData);
        }
        else if (taskData.type.isEnd) {
            this.endTask(task, taskData);
        }
        else if (taskData.type.isDescription) {
            this.addTaskDescription(task, taskData);
        }
        else if (taskData.type.isSize) {
            this.addTaskSize(task, taskData);
        }
        else if (taskData.type.nameChanged) {
            this.renameTask(task, taskData);
        }
        else if (taskData.type.isDeleted) {
            this.deleteTask(task);
        }
    }


    private void startTask(Task task, TaskData taskData) {
        task.startOrStopTask("start", taskData.savedTime);
    }

    private void endTask(Task task, TaskData taskData) {
        task.startOrStopTask("stop", taskData.savedTime);
    }

    private void addTaskDescription(Task task, TaskData taskData) {
        task.setDescription(taskData.description);
    }

    private void addTaskSize(Task task, TaskData taskData) {
        task.setSize(taskData.size);
    }

    private void renameTask(Task task, TaskData taskData) {
        task.changeName(taskData.newName);
    }

    private void deleteTask(Task task) {
        taskList.remove(task);
    }

    // this give a detailed description of of each task, its
    // TOTAL, MIN, MAX and average time spend on each task
    private void output(Task task) {
        System.out.print(
                task.name + ":\n" +
                        "Total time = " + durationToTimePassed(task.getTotalDuration()) + "\n" +
                        "Min time = " + durationToTimePassed(task.getMinDuration()) + "\n" +
                        "Max time = " + durationToTimePassed(task.getMaxDuration()) + "\n" +
                        "Avg time = " + durationToTimePassed(task.getAvgDuration()) + "\n" +
                        "task size = " + task.size + "\n" +
                        "task description = " + task.description + "\n" +
                        "\n");
    }
    private void outputBySize(List<Date> maxTimeList, List<Date> minTimeList, long avgMilisecondsTime) {
        Date avgTime = new Date(avgMilisecondsTime);
        Date maxTime = getMaxTime(maxTimeList);
        Date minTime = getMinTime(minTimeList);

        System.out.print(
                "TOTAL for this size" + ":\n" +
                        "Min time = " + durationToTimePassed(minTime) + "\n" +
                        "Max time = " + durationToTimePassed(maxTime) + "\n" +
                        "Avg time = " + durationToTimePassed(avgTime) + "\n" +
                        "\n");
    }

    /******* helpers *******/
    public Task getTaskFromName(String taskName) {
        for(Task task : taskList) {
            if(task.name.equals(taskName)) return task;
        }
        return null;
    }

    public List<Task> getTaskBySize(String size) {
        List<Task> tempList = new ArrayList<Task>();
        for(Task task : taskList) {
            if(task.size.equals(size)) tempList.add(task);
        }
        return tempList;
    }

    private String durationToTimePassed(Date time){
        DateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(time);
    }

    private Date getMinTime(List<Date> minTimeList) {
        List<Date> sorted = minTimeList;
        Collections.sort(sorted, (a, b) -> a.compareTo(b));
        return sorted.get(0);
    }

    private Date getMaxTime(List<Date> maxTimeList) {
        List<Date> sorted = maxTimeList;
        Collections.sort(sorted, (a, b) -> a.compareTo(b));
        return sorted.get(sorted.size() -1);
    }

}

// A generic DB so in the future we can use a cloud DB
// even use local and cloud at the same time
class DataBase {
    LocalStorage localStorage;

    DataBase () {
        localStorage = new LocalStorage();
    }

    public List<TaskData> queryTaskData() {
        return localStorage.queryTaskData();
    }

    public void saveStartTask(String taskName) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.getTaskFromName(taskName);
        if (task == null)
            localStorage.saveStartTask(taskName);
        else if (!task.isRunning)
            localStorage.saveStartTask(taskName);
        else System.out.println("task already runing");
    }

    public void saveStopTask(String taskName) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.getTaskFromName(taskName);
        if (task.isRunning)
            localStorage.saveStopTask(taskName);
        else System.out.println("task already stoped");
    }

    public void saveDescriptionTask(String taskName, String description) {
        localStorage.saveDescriptionTask(taskName, description);
    }

    public void saveSizeTask(String taskName, String size) {
        localStorage.saveSizeTask(taskName, size);
    }

    public void saveDescriptionAndSizeTask(String taskName,
                                           String description, String size) {
        localStorage.saveDescriptionAndSizeTask(taskName, description, size);
    }

    public void saveRenameTask(String oldName, String newName) {
        localStorage.saveRenameTask(oldName, newName);
    }

    public void saveDeletionTask(String taskName) {
        localStorage.saveDeletionTask(taskName);
    }
}

// This class saves the data localy
class LocalStorage {

    final String fileName = "db.txt";
    Date date = new Date();
    SimpleDateFormat formatter =
            new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    private String strDate = formatter.format(date);
    private FileWriter myWriter;
    private File file;
    private Scanner scanner;

    LocalStorage() {
        try {
            file = new File(fileName);
            myWriter = new FileWriter(fileName, true);
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public List<TaskData> queryTaskData() {
        List<TaskData> taskDataList = new ArrayList<TaskData>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            TaskData data = new TaskData(line);
            taskDataList.add(data);
        }
        scanner.close();
        return taskDataList;
    }

    public void saveStartTask(String taskName) {
        try {
            myWriter.write(taskName + ", started at ," + strDate +"\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveStopTask(String taskName) {
        try {
            myWriter.write(taskName + ", stoped at ," + strDate +"\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveDescriptionTask(String taskName, String description) {
        try {
            myWriter.write(
                    taskName + ", description ,"
                            + description + ", at ,"
                            + strDate + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void saveDescriptionAndSizeTask(String taskName,
                                           String description, String size) {
        try {
            myWriter.write(
                    taskName + ", description ,"
                            + description + ", at ,"
                            + strDate + "\n");

            myWriter.write(
                    taskName + "," + " size ,"
                            + size + ", at ," + strDate + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveSizeTask(String taskName, String size) {
        try {
            myWriter.write(
                    taskName + "," + " size ,"
                            + size + ", at ," + strDate + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveRenameTask(String oldName, String newName) {
        try {
            myWriter.write(
                    oldName + ", renamed to ,"
                            + newName + ", at ,"
                            + strDate + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveDeletionTask(String taskName) {
        try {
            myWriter.write(
                    taskName + ", deleted at ,"
                            + ", at ," + strDate + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

// each line of data that is saved in the DB that represents a some
// task data
class TaskData {
    String name;
    String newName;
    String description;
    String size;
    Date startTime;
    Date endTime;
    Date savedTime;
    Type type = new Type();

    class Type {
        boolean isStart = false;
        boolean isEnd = false;
        boolean isDeleted = false;
        boolean nameChanged = false;
        boolean isDescription = false;
        boolean isSize = false;
    }

    TaskData(String data) {
        try {
            this.parseData(data);
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    private void parseData(String data) throws ParseException {
        String[] splitStr = data.split(",");
        String taskName = splitStr[0];
        String stringType = splitStr[1];
        name = taskName;

        if (stringType.contains("started")) {
            String time = splitStr[2];
            startTime = parseTime(time);
            savedTime = startTime;
            this.type.isStart = true;
        }
        else if (stringType.contains("stoped")) {
            String time = splitStr[2];
            endTime = parseTime(time);
            savedTime = endTime;
            this.type.isEnd = true;
        }
        else if (stringType.contains("description")) {
            this.type.isDescription = true;
            String descripString = splitStr[2];
            try {
                String sizeString = splitStr[3];
                if (sizeString.contains("size")) {
                    size = sizeString;
                    this.type.isSize = true;
                }
            } catch (Exception e) {

            }
            description = descripString;
        }

        else if (stringType.contains("size")) {
            String sizeString = splitStr[2];
            size = sizeString;
            this.type.isSize = true;
        }

        else if (stringType.contains("deleted")) {
            this.type.isDeleted = true;
        }

        else if (stringType.contains("rename")) {
            this.type.nameChanged = true;
            String reName = splitStr[2];
            newName = reName;
        }
    }

    /******* private helpers *******/
    private Date parseTime(String time) throws ParseException {
        DateFormat format =
                new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        return format.parse(time);
    }

}


// the data that each task can have
class Task {

    String name;
    String description = "No decription";
    boolean isRunning = false;
    String size = "No size";
    Date startTime;
    Date endTime;
    private List<TimeWindow> timeWindowList = new ArrayList<TimeWindow>(); private List<Date> durationList = new ArrayList<Date>();

    Task(String name) {
        this.name = name;
    }

    /******* general *******/
    public void startOrStopTask(String startOrStop , Date time) {
        if (startOrStop.equals("start")){
            this.startTask(this.name, time);
        } else {
            this.endTask(time);
        }
    }

    public void changeName(String name) {
        this.name = name;
    }

    /******* setters *******/
    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(String size) {
        this.size = size;
    }

    /******* getters *******/
    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public List<TimeWindow> getTimeWindowList() {
        return timeWindowList;
    }

    public List<Date> getDurationList() {
        return durationList;
    }

    public Date getTotalDuration() {
        long totalTime = 0;
        for (Date time : durationList) {
            totalTime = totalTime + time.getTime();
        }
        return new Date(totalTime);
    }

    public Date getMinDuration() {
        List<Date> sorted = durationList;
        Collections.sort(sorted, (a, b) -> a.compareTo(b));
        return sorted.get(0);
    }

    public Date getMaxDuration() {
        List<Date> sorted = durationList;
        Collections.sort(sorted, (a, b) -> a.compareTo(b));
        return sorted.get(sorted.size() -1);
    }

    public Date getAvgDuration() {
        long totalTime = 0, avgTime = 0;
        for (Date time : durationList) {
            totalTime = totalTime + time.getTime();
        }
        avgTime = totalTime / durationList.size();
        return new Date(avgTime);
    }


    /******* private helpers *******/

    private void startTask(String name, Date date) {
        initiateOnlyFirstCreation(name, date);
        initiateTaskDuration(date);
        isRunning = true;
    }

    private void endTask(Date date) {
        endCurrentDuration(date);
        this.endTime = date;
        isRunning = false;
    }

    private void initiateTaskDuration(Date date) {
        TimeWindow timeWindow = new TimeWindow();
        timeWindow.start(date);
        timeWindowList.add(timeWindow);
    }

    private void initiateOnlyFirstCreation(String name, Date date) {
        if (this.name == null) this.name = name;
        if (this.startTime == null) this.startTime = date;
    }

    private void endCurrentDuration(Date date) {
        TimeWindow timeWindow = getLastItemInTheList(timeWindowList);
        timeWindow.end(date);
        durationList.add(timeWindow.getDuration());
    }

    private TimeWindow getLastItemInTheList(List<TimeWindow> list) {
        return list.get(list.size() -1);
    }


}

// keeps track of duration of any task
class TimeWindow {
    private Date start;
    private Date end;

    public void start(Date date) {
        this.start = date;
    }
    public void end(Date date) {
        this.end = date;
    }

    public Date getDuration () {
        return new Date(this.end.getTime()
                - this.start.getTime());
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

}
