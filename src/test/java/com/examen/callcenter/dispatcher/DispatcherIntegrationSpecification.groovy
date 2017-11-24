package com.examen.callcenter.dispatcher

import com.examen.callcenter.entity.Call
import com.examen.callcenter.entity.Employee
import com.examen.callcenter.queue.UniquePriorityQueue
import com.examen.callcenter.selection.SelectionStrategy
import com.examen.callcenter.selection.impl.EmployeeSelectionStrategy
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

import static com.examen.callcenter.entity.Employee.newDirector
import static com.examen.callcenter.entity.Employee.newOperario
import static com.examen.callcenter.entity.Employee.newSupervisor

/**
 * Created by jbaez on 24/11/17.
 */
public class DispatcherIntegrationSpecification extends Specification {

    private Dispatcher dispatcher
    private SelectionStrategy<Employee> selectEmployeeStrategy
    private ExecutorService executorService
    private ScheduledExecutorService timeoutService

    def "employees are scheduled by priority and connection rejected if there are no more employees"() {

        def conditions = new PollingConditions(timeout: 50)

        final List<Optional<Future>> futureList = new ArrayList<>();

        given: "a queue with 1 operario, 1 supervisor and 1 director"
        Queue<Employee> queue = getAvailableEmployees()

        and: "a by priority employee selection strategy"
        selectEmployeeStrategy = new EmployeeSelectionStrategy(queue)
        executorService = Executors.newFixedThreadPool(10)
        timeoutService = Executors.newSingleThreadScheduledExecutor()

        when: "dispatching 10 connections"
        dispatcher = new Dispatcher(selectEmployeeStrategy, executorService, timeoutService)

        for (int i = 0; i < 10; i++) {
            Optional<Future<Employee>> future = dispatcher.dispatchCall(new Call(123123L))
            futureList.add(future);
        }

        then: "the 3 are handled and 7 rejected"
        conditions.eventually {
            futureList.get(0).get().isDone();
            ((Employee) futureList.get(0).get().get()).isOperator()
            futureList.get(1).get().isDone();
            ((Employee) futureList.get(1).get().get()).isSupervisor()
            futureList.get(2).get().isDone();
            ((Employee) futureList.get(2).get().get()).isDirector()
            !futureList.get(3).isPresent();
            !futureList.get(4).isPresent();
            !futureList.get(5).isPresent();
            !futureList.get(6).isPresent();
            !futureList.get(7).isPresent();
            !futureList.get(8).isPresent();
            !futureList.get(9).isPresent();
        }

        executorService.shutdown();
    }


    def "talk is terminated if it last more than 10 seconds"() {

        def conditions = new PollingConditions(timeout: 50)

        given: "a queue with 1 operario, 1 supervisor and 1 director"
        Queue<Employee> queue = getAvailableEmployees()

        and: "a by priority employee selection strategy"
        selectEmployeeStrategy = new EmployeeSelectionStrategy(queue)
        executorService = Executors.newFixedThreadPool(10)
        timeoutService = Executors.newSingleThreadScheduledExecutor()

        when: "dispatching 1 connections"
        dispatcher = new Dispatcher(selectEmployeeStrategy, executorService, timeoutService)

        Optional<Future<Employee>> future = dispatcher.dispatchCall(new Call(12313L))

        then: "the conection is canceled"

        conditions.eventually {
            future.get()
        }

        executorService.shutdown();
    }
    private static Queue<Employee> getAvailableEmployees() {
        final Queue<Employee> availableEmployees = new UniquePriorityQueue<>();
        availableEmployees.add(newOperario("Jorge", 1L));
        availableEmployees.add(newSupervisor("Fausto", 2L));
        availableEmployees.add(newDirector("Ailen", 3L));
        return availableEmployees;
    }
}
