package org.ecs160.a2.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Task implements Cloneable {
    public String name;
    public String description = "No decription";
    public String isRunning = "false";
    public String size = "No size";
    private long totalLongDuration = 0;
    private List<TimeWindow> timeWindowList = new ArrayList<TimeWindow>();
    private List<TimeWindow> sorted = null;

    public Task(String name, Map<String, String> map, List<String> timeList)  {
        this.name = name;
        this.description = map.get("description");
        this.isRunning = map.get("isRunning");
        this.size = map.get("size");
        this.timeWindowList = parseTimeWindow(timeList);
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    private List<TimeWindow> parseTimeWindow(List<String> timeList) {
        List<TimeWindow> windowList = new ArrayList<TimeWindow>();

        List<Date> allDatesList = timeList
                .stream()
                .map(this::parseTimeString)
                .collect(Collectors.toList());

        AtomicBoolean hasStarted = new AtomicBoolean(false);

        AtomicReference<Date> first = new AtomicReference<>(new Date());
        allDatesList.forEach(eachTime -> {
            if (!hasStarted.get()) {
                hasStarted.set(true);
                first.set(eachTime);
            } else {
                hasStarted.set(false);
                TimeWindow timeWindow = new TimeWindow();
                timeWindow.start(first.get());
                timeWindow.end(eachTime);
                totalLongDuration += timeWindow.getDuration().getTime();
                windowList.add(timeWindow);
            }
        });

        return windowList;
    }



    public Boolean getIsRunning() {
        if (isRunning == null)
            return false;
        else
            return isRunning.equals("true");
    }

    /*************** Public ****************/


    public Date getMinDuration() {
        if (sort().isEmpty()) {
            return new Date(0);
        }
        else  {
            return  sort()
                    .stream()
                    .findFirst()
                    .get()
                    .getDuration();
        }
    }

    public Date getMaxDuration() {
        if (sort().isEmpty()) {
            return new Date(0);
        }
        else  {
            return sort().get(sort().size() -1).getDuration();
        }
    }

    public Date getTotalDuration() {
       return new Date(totalLongDuration);
    }


    public Date getAvgDuration() {
        if (timeWindowList.size() == 0) {
            return new Date(0);
        }
        else {
            long avgTime = totalLongDuration / timeWindowList.size();
            return new Date(avgTime);
        }
    }


    /*************** Private ****************/


    /******* private helpers *******/
    private Date parseTimeString(String time)  {
        DateFormat format =
                new SimpleDateFormat("hh:mm:ss");
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

    private String durationToTimePassed(Date time){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(time.getTime());
    }
}
