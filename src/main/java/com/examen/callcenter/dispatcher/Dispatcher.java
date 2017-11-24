package com.examen.callcenter.dispatcher;


import com.examen.callcenter.entity.Call;
import com.examen.callcenter.entity.Employee;
import com.examen.callcenter.selection.SelectionStrategy;
import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ThreadSafe
public class Dispatcher {

    private static final long TEN_SECOND_TIMEOUT = 10L;

    private final SelectionStrategy<Employee> selectEmployeeStrategy;

    private final ExecutorService executorService;
    private final ScheduledExecutorService timeoutService;

    private final static Logger logger = Logger.getLogger(Dispatcher.class.getName());

    public Dispatcher(final SelectionStrategy<Employee> selectEmployeeStrategy, final ExecutorService executorService) {
        this(selectEmployeeStrategy, executorService, Executors.newSingleThreadScheduledExecutor());
    }

    public Dispatcher(final SelectionStrategy<Employee> selectEmployeeStrategy, final ExecutorService executorService,
                      final ScheduledExecutorService timeoutService) {
        this.selectEmployeeStrategy = selectEmployeeStrategy;
        this.executorService = executorService;
        this.timeoutService = timeoutService;
    }

    public Optional<Future<Employee>> dispatchCall(final Call call) {
        final Optional<Employee> employee = selectEmployeeStrategy.getNextSelectable();

        if (employee.isPresent()) {
            return Optional.of(handleCallToEmployee(employee.get().assignCall(call)));
        } else {
            handleNoEmployeesAvailable();
            return Optional.empty();
        }
    }

    private Future<Employee> handleCallToEmployee(final Employee employee) {
        final Future<Employee> future = this.executorService.submit(() -> {
            dispatchCallToEmployee(employee);
            return employee;
        });


        this.timeoutService.schedule(() -> {
            restoreEmployeeOnTimeout(employee, future);
            return employee;
        }, TEN_SECOND_TIMEOUT, TimeUnit.SECONDS);


        return future;
    }

    private void restoreEmployeeOnTimeout(Employee employee, Future future) {
        future.cancel(true);
        logger.log(Level.INFO, "Se cancela por timeout");
        selectEmployeeStrategy.addSelectable(employee);
    }


    private void dispatchCallToEmployee(final Employee employee) {

        logger.log(Level.INFO, Thread.currentThread().getName() + " Se asigna " + employee.toString());

        employee.talk();

        logger.log(Level.INFO, Thread.currentThread().getName() + " Se libera " + employee.toString());
        selectEmployeeStrategy.addSelectable(employee);
    }

    private void handleNoEmployeesAvailable() {
        logger.log(Level.WARNING, "No hay empleados disponibles");
    }
}