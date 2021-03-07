package org.ecs160.a2.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Task {
    public String name;
    public String description = "No decription";
    public String isRunning = "false";
    public String size = "No size";
    public Date startTime;
    public Date endTime;

    public Task(String name, Map<String, String> map)  {
        this.name = name;
        this.description = map.get("description");
        this.isRunning = map.get("isRunning");
        this.size = map.get("size");
        this.startTime = new Date();// parseTime(map.get("startTime"));
        this.endTime = new Date();//parseTime(map.get("endTime"));
    }

    // FACTORY
//    public Task(String name,String description, String size)  {
//        this.name = name;
//        this.description = description;
//        this.size = size;
//        this.isRunning = "false";
//        this.startTime = new Date();// parseTime(map.get("startTime"));
//        this.endTime = new Date();//parseTime(map.get("endTime"));
//    }
//
//    public static Task initTaskFromMap(String name, Map<String, String> map) {
//        String description = map.get("description");
//        String size = map.get("size");
//        return new Task(name,description,size);
//    }

    /******* private helpers *******/
    private Date parseTime(String time) throws ParseException {
        DateFormat format =
                new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        return format.parse(time);
    }
}
