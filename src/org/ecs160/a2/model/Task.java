package org.ecs160.a2.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Task {
    public String name;
    public String description = "No decription";
    public String isRunning = "false";
    public String size = "No size";
    public Date startTime;
    public Date endTime;
    private List<TimeWindow> timeWindowList = new ArrayList<TimeWindow>();
    private List<Date> durationList = new ArrayList<Date>();

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
    private Date parseTime(String time) throws ParseException {
        DateFormat format =
                new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        return format.parse(time);
    }
}
