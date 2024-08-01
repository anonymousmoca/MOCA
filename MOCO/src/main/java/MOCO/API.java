package MOCO;

import java.util.Objects;

public class API {
    private String name;
    private Boolean preemptive;
    private Boolean humanIntervention;

    public API(String name, Boolean preemptive, Boolean humanIntervention) {
        this.name = name;
        this.preemptive = preemptive;
        this.humanIntervention = humanIntervention;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPreemptive() {
        return preemptive;
    }

    public void setPreemptive(Boolean preemptive) {
        this.preemptive = preemptive;
    }

    public Boolean getHumanIntervention() {
        return humanIntervention;
    }

    public void setHumanIntervention(Boolean humanIntervention) {
        this.humanIntervention = humanIntervention;
    }

    @Override
    public String toString() {
        return name + "|" + preemptive + "|" + humanIntervention;
    }
}
