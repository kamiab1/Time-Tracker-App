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


    public String getTotalDuration() {

        if (timeWindowList.size() > 1) {
           TimeWindow first = timeWindowList.get(0);
           TimeWindow last = timeWindowList.get(timeWindowList.size() -1);

           TimeWindow timeWindow = new TimeWindow();
           System.out.print("\nFIRST "+ first.getStart());
           System.out.print("\nEND "+ last.getEnd());
           timeWindow.start(first.getStart());
           timeWindow.end(last.getEnd());

           return durationToTimePassed(timeWindow.getDuration());
        }

        else return "0";
    }

    public Date getMinDuration() {
        if (sort().isEmpty()) {
            return new Date();
        }
        else  {
            final Date d = sort()
                    .stream()
                    .findFirst()
                    .get()
                    .getDuration();
            System.out.print("min time is " + d);
            return d;
        }
    }

    public Date getMaxDuration() {
        if (sort().isEmpty()) {
            return new Date();
        }
        else  {
            Date d = sort().get(sort().size() -1).getDuration();
            sort().forEach( timeWindow -> {
                System.out.print("\n each time is " + timeWindow.getStart());
            });

            return d ;
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
           // sorted.sort((a, b) -> a.getDuration().compareTo(b.getDuration()));
            sorted.sort(Comparator.comparing(TimeWindow::getDuration));
        }
        return sorted;
    }

    private String durationToTimePassed(Date time){
        DateFormat format = new SimpleDateFormat("hh:mm:ss");
        return format.format(time);
    }
}
