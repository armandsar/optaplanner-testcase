package shift.domain;

public class Shift {

    private Integer id;
    private String name;
    private Integer fromTime;
    private Integer toTime;

    public Shift(Integer id, String name, Integer fromTime, Integer toTime) {
        this.id = id;
        this.name = name;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getFromTime() {
        return fromTime;
    }

    public Integer getToTime() {
        return toTime;
    }

    public Integer getTotalHours() {
        return toTime - fromTime;
    }
}
