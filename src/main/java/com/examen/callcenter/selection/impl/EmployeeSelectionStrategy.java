package com.examen.callcenter.selection.impl;

import com.examen.callcenter.entity.Employee;
import com.examen.callcenter.selection.SelectionStrategy;
import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

@ThreadSafe
public final class EmployeeSelectionStrategy implements SelectionStrategy<Employee> {
    private final Queue<Employee> availableEmployees;

    public EmployeeSelectionStrategy(final Queue<Employee> employees) {
        this.availableEmployees = new PriorityBlockingQueue<>(employees);
    }

    @Override
    public Optional<Employee> getNextSelectable() {
            return Optional.ofNullable(this.availableEmployees.poll());
        }

    @Override
    public void addSelectable(final Employee employee) {
        availableEmployees.add(employee);
    }
}
