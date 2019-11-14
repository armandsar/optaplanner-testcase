package shift.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Employee")
public class Employee {
    private Integer id;
    private String name;
    private Integer contractHours;

    public Employee(Integer id, String name, Integer contractHours) {
        this.id = id;
        this.name = name;
        this.contractHours = contractHours;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getContractHours() {
        return contractHours;
    }
}
