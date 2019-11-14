package shift.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("WorkloadRequirement")

public class WorkloadRequirement {

    private Integer hour;
    private Integer date;
    private Integer part;
    private Integer load;

    public WorkloadRequirement(Integer date, Integer hour, Integer part, Integer load) {
        this.date = date;
        this.hour = hour;
        this.part = part;
        this.load = load;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

}

