package shift.domain;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@XStreamAlias("ShiftAssignment")
@PlanningEntity
public class ShiftAssignment {

    private Employee employee;
    private Shift shift;
    private Integer date;

    public ShiftAssignment() {

    }

    public ShiftAssignment(Integer date, Employee employee) {
        this.date = date;
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @PlanningVariable(nullable = true, valueRangeProviderRefs = {"availableShifts"})
    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Integer getDate() {
        return date;
    }

    public boolean hasShift() {
        return shift != null;
    }

    public Boolean isUsefulLoad(Integer hour, Integer part) {
        if (shift == null) {
            return false;
        }

        Integer hourPart = 1000/4*part;
        Integer comparableHour = hour + hourPart;

        return comparableHour >= shift.getFromTime() && comparableHour < shift.getToTime();
    }

    public Integer getUsefulLoad() {
        return 1000 / 4;
    }
}

