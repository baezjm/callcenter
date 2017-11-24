package com.examen.callcenter.entity;


import com.examen.callcenter.talk.Talk;
import com.examen.callcenter.talk.impl.TimeScheduleTalk;

import java.util.Objects;

public class Employee implements Comparable<Employee> {
    private final Long id;
    private final String name;
    private EmployeeType type;
    private Call call;
    private Talk talk;

    private Employee(final String name, final Long id, EmployeeType type) {
        this.name = name;
        this.id = id;
        this.type = type;
        talk = new TimeScheduleTalk(1, 5);
    }

    public static Employee newDirector(final String name, final Long id) {
        return new Employee(name, id, EmployeeType.DIRECTOR);
    }

    public static Employee newOperario(final String name, final Long id) {
        return new Employee(name, id, EmployeeType.OPERATOR);
    }

    public static Employee newSupervisor(final String name, final Long id) {
        return new Employee(name, id, EmployeeType.SUPERVISOR);
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    public Call getCall() {
        return call;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Employee assignCall(final Call call) {
        Objects.requireNonNull(call);
        this.call = call;
        return this;
    }

    public void talk() {
        talk.talk();
    }

    public void hangout() {
        Objects.requireNonNull(call);
        this.call.closeCall();
    }

    public EmployeeType getType() {
        return type;
    }

     public int getEmployeePriority() {
         return type.getPriority();
     }

    public boolean isOperator() {
        return type == EmployeeType.OPERATOR;
    }

    public boolean isSupervisor() {
        return type == EmployeeType.SUPERVISOR;
    }

    public boolean isDirector() {
        return type == EmployeeType.DIRECTOR;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Employee anotherEmployee) {
        return this.getEmployeePriority() >= anotherEmployee.getEmployeePriority() ? 1 : -1;
    }
}