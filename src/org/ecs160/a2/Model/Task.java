package org.ecs160.a2.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Task {
    public String name;
    public String description = "No decription";
    public String isRunning = "false";
    public String size = "No size";
    public Date startTime;
    public Date endTime;
    private List<TimeWindow> timeWindowList = new ArrayList<TimeWindow>();
    private List<TimeWindow> sorted = null;

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

        AtomicBoolean hasStarted = new AtomicBoolean(false);
        TimeWindow timeWindow = new TimeWindow();

        allDatesList.forEach(eachTime -> {
            if (!hasStarted.get()) {
                hasStarted.set(true);
                timeWindow.start(eachTime);
            } else {
                hasStarted.set(false);
                timeWindow.end(eachTime);
                windowList.add(timeWindow);
            }
        });

        return windowList;
    }


    public Boolean getIsRunning() {
        return isRunning.equals("true");
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
        if (sort().isEmpty()) {
            return new Date();
        }
        else  {
            return sort()
                    .stream()
                    .findFirst()
                    .get()
                    .getDuration();
        }
    }

    public Date getMaxDuration() {
        if (sort().isEmpty()) {
            return new Date();
        }
        else  {
            return sort().get(sort().size() -1).getDuration();
        }
    }

    // TODO : check for when size is 0

    public Date getAvgDuration() {
        if (timeWindowList.size() == 0) {
            return new Date();
        }

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
        if (sorted == null) {
            sorted = timeWindowList;
            sorted.sort(Comparator.comparing(TimeWindow::getDuration));
        }
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
