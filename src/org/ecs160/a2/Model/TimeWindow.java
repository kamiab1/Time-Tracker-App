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
        //System.out.print("\nend is: "+ date);
        this.end = date;
    }

    public Date getDuration () {
        return new Date(getEnd().getTime() - getStart().getTime());
    }

    public Date getStart() {
        if (this.start == null) {
            System.out.print("\nEMPTY "+ start);
            return new Date();
        } else {
            return this.start;
        }
    }
    public Date getEnd() {
        if (this.end == null) {
            return new Date();
        } else {
            return this.end;
        }
    }

}
