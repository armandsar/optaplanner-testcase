package shift;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import shift.domain.Employee;
import shift.domain.Shift;
import shift.domain.ShiftAssignment;
import shift.domain.WorkloadRequirement;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Planner")
@PlanningSolution
public class Planner {
    private HardSoftLongScore score;

    private List<Employee> employeeList;
    private List<Shift> shiftList;
    private List<ShiftAssignment> shiftAssignmentList;
    private List<WorkloadRequirement> workloadRequirementList;

    public Planner() {
        employeeList = new ArrayList<>();
        shiftList = new ArrayList<>();
        shiftAssignmentList = new ArrayList<>();
        workloadRequirementList = new ArrayList<>();
    }

    @ProblemFactCollectionProperty
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @ValueRangeProvider(id = "availableShifts")
    @ProblemFactCollectionProperty
    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    @PlanningEntityCollectionProperty
    public List<ShiftAssignment> getShiftAssignmentList() {
        return shiftAssignmentList;
    }

    public void setShiftAssignmentList(List<ShiftAssignment> shiftAssignmentList) {
        this.shiftAssignmentList = shiftAssignmentList;
    }

    @ProblemFactCollectionProperty
    public List<WorkloadRequirement> getWorkloadRequirementList() {
        return workloadRequirementList;
    }

    public void setWorkloadRequirementList(List<WorkloadRequirement> workloadRequirementList) {
        this.workloadRequirementList = workloadRequirementList;
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    public void populateShiftAssignmentList() {
        Integer[] uniqDays = workloadRequirementList.stream()
                .map(WorkloadRequirement::getDate)
                .distinct().toArray(Integer[]::new);

        for (Integer date : uniqDays) {
            for (Employee emp : employeeList) {
                ShiftAssignment assignment = new ShiftAssignment(date, emp);
                shiftAssignmentList.add(assignment);
            }
        }
    }

    public Planner solve() {
        SolverFactory<Planner> solverFactory = SolverFactory.createFromXmlResource("shift/droolsConfig.xml");
        Solver<Planner> solver = solverFactory.buildSolver();
        return solver.solve(this);
    }

    public void contractHoursDebugInfo() {
        System.out.println("Employee; contract hours; real hours; difference;");

        Integer[] uniqEmployeeIds = shiftAssignmentList.stream()
                .map(s -> s.getEmployee().getId())
                .distinct().toArray(Integer[]::new);

        for (Integer employeeId : uniqEmployeeIds) {
            Employee employee = shiftAssignmentList
                    .stream()
                    .filter(shiftAssignment -> shiftAssignment.getEmployee().getId().equals(employeeId))
                    .findFirst().get().getEmployee();

            Integer worked = shiftAssignmentList
                    .stream()
                    .filter(shiftAssignment -> shiftAssignment.getEmployee().getId().equals(employeeId))
                    .filter(ShiftAssignment::hasShift)
                    .map(shiftAssignment -> shiftAssignment.getShift().getTotalHours())
                    .reduce(0, Integer::sum);

            String[] row = new String[]{
                    employee.getName(),
                    employee.getContractHours() != null ? employee.getContractHours().toString() : "-",
                    worked.toString(),
                    employee.getContractHours() != null ? String.valueOf(worked - employee.getContractHours()) : "-",
            };
            System.out.println(String.join(", ", row));
        }

    }

}

