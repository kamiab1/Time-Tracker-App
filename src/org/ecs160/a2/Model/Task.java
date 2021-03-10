package org.ecs160.a2.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Task {
    public String name;
    public String description = "No decription";
    public String isRunning = "false";
    public String size = "No size";
    public Date startTime;
    public Date endTime;
    private List<TimeWindow> timeWindowList = new ArrayList<TimeWindow>();


    public Task(String name, Map<String, String> map, List<String> timeList)  {
        this.name = name;
        this.description = map.get("description");
        this.isRunning = map.get("isRunning");
        this.size = map.get("size");
        this.timeWindowList = parseTimeWindow(timeList);
    }

    private List<TimeWindow> parseTimeWindow(List<String> timeList) {
        List<TimeWindow> windowList = new ArrayList<TimeWindow>();

        List<Date> allDatesList = timeList
                .stream()
                .map(this::parseTimeString)
                .collect(Collectors.toList());

        System.out.print(allDatesList);

        return windowList;
    }


    public Boolean getIsRunning() {
         if (isRunning.equals("true")) {
             return true;
         } else
             return false;
    }

    /*************** Public ****************/


    public List<TimeWindow> getTimeWindowList() {
        return timeWindowList;
    }

    public Date getTotalDuration() {
        long totalTime = 0;
        for (TimeWindow timeWindow : timeWindowList) {
            totalTime = totalTime + timeWindow.getDuration().getTime();
        }
        return new Date(totalTime);
    }

    public Date getMinDuration() {
        List<TimeWindow> sorted = sort();
        return sorted.get(0).getDuration();
    }

    public Date getMaxDuration() {
        List<TimeWindow> sorted = sort();
        return sorted.get(sorted.size() -1).getDuration();
    }

    public Date getAvgDuration() {
        long totalTime = 0, avgTime = 0;
        for (TimeWindow timeWindow : timeWindowList) {
            totalTime = totalTime + timeWindow.getDuration().getTime();
        }
        avgTime = totalTime / timeWindowList.size();
        return new Date(avgTime);
    }


    /*************** Private ****************/


    /******* private helpers *******/
    private Date parseTimeString(String time)  {
        DateFormat format =
                new SimpleDateFormat("dd-M-yyy hh:mm:ss");
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private List<TimeWindow> sort() {
        List<TimeWindow> sorted = timeWindowList;
        sorted.sort(Comparator.comparing(TimeWindow::getDuration));
        return sorted;
    }
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
