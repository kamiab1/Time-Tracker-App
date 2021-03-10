package org.ecs160.a2.Model;

import java.util.Date;

// keeps track of duration of any task
public class TimeWindow {
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
