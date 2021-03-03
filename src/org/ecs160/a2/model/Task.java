package org.ecs160.a2.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Task {
    String name;
    String description = "No decription";
    String isRunning = "false";
    String size = "No size";
    Date startTime;
    Date endTime;

    Task(String name, Map<String, String> map) throws ParseException {
        this.name = name;
        this.description = map.get("description");
        this.isRunning = map.get("isRunning");
        this.size = map.get("size");
        this.startTime = parseTime(map.get("startTime"));
        this.endTime = parseTime(map.get("endTime"));
    }


    /******* private helpers *******/
    private Date parseTime(String time) throws ParseException {
        DateFormat format =
                new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        return format.parse(time);
    }
}
