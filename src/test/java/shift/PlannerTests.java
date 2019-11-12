package shift;


import org.junit.Assert;
import org.junit.Test;
import shift.domain.Employee;
import shift.domain.Shift;
import shift.domain.WorkloadRequirement;

import java.util.ArrayList;
import java.util.List;

public class PlannerTests {

    @Test
    public void basic() {
        Planner planner = new Planner();

        List<Employee> employeeList = new ArrayList<>();

        for (int i = 1; i < 2; i++) {
            employeeList.add(new Employee(i, "E#" + i, 8250));
        }

        planner.getShiftList().add(new Shift(1, "Regular day", 9000, 17250));

        // from 9 to 17 we need 1 worker
        int[][] dayRequiredWorkers = {{9, 17, 1}};

        for(int i = 1; i < 2; i++) {
            planner.getWorkloadRequirementList().addAll(generateWorkloadRequirement(i, dayRequiredWorkers));
        }

        planner.setEmployeeList(employeeList);

        planner.populateShiftAssignmentList();

        Planner solved = planner.solve();

        solved.contractHoursDebugInfo();

        Assert.assertEquals(0, solved.getScore().getHardScore());
    }

    @Test
    public void complex() {
        Planner planner = new Planner();

        List<Employee> employeeList = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            employeeList.add(new Employee(i, "E#" + i, 8250));
        }

        planner.getShiftList().add(new Shift(1, "Regular day", 9000, 17250));

        // from 9 to 17 we need 1 worker
        int[][] dayRequiredWorkers = {{9, 17, 1}};

        for(int i = 1; i <= 10; i++) {
            planner.getWorkloadRequirementList().addAll(generateWorkloadRequirement(i, dayRequiredWorkers));
        }

        planner.setEmployeeList(employeeList);

        planner.populateShiftAssignmentList();

        Planner solved = planner.solve();

        solved.contractHoursDebugInfo();

        Assert.assertEquals(0, solved.getScore().getHardScore());
    }

    private List<WorkloadRequirement> generateWorkloadRequirement(Integer date, int[][] dayConfig) {
        List<WorkloadRequirement> list = new ArrayList<>();
        int[] hours = new int[24];

        for (int i = 0; i <= 23; i++) {
            hours[i] = 0;
        }

        for (int[] part : dayConfig) {
            int from = part[0];
            int to = part[1];
            int load = part[2];

            for (int h = from; h < to; h++) {
                hours[h] += load;
            }
        }
        for (int h = 0; h <= 23; h++) {
            for (int part = 0; part < 4; part++) {
                Integer load = hours[h] * 1000 / 4;
                list.add(new WorkloadRequirement(date, h * 1000, part, load));
            }
        }

        return list;
    }

}

