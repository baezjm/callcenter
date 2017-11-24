package com.examen.callcenter.dispatcher.config;

import com.examen.callcenter.dispatcher.Dispatcher;
import com.examen.callcenter.entity.Employee;
import com.examen.callcenter.handler.RejectedExecutionHandlerImpl;
import com.examen.callcenter.monitor.CallMonitorThread;
import com.examen.callcenter.queue.UniquePriorityQueue;
import com.examen.callcenter.selection.SelectionStrategy;
import com.examen.callcenter.selection.impl.EmployeeSelectionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.examen.callcenter.entity.Employee.newDirector;
import static com.examen.callcenter.entity.Employee.newOperario;
import static com.examen.callcenter.entity.Employee.newSupervisor;


@Configuration
public class DispatcherConfiguration {

    private static final Integer POOL_SIZE = 10;
    private static final Integer QUEUE_SIZE = 2;

    @Bean
    public Dispatcher getDispatcher() {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_SIZE), threadFactory, new RejectedExecutionHandlerImpl());

        final SelectionStrategy selectEmployeeStrategy =
                new EmployeeSelectionStrategy(getAvailableEmployees());
        final Dispatcher dispatcher = new Dispatcher(selectEmployeeStrategy, executorPool);

        CallMonitorThread monitor = new CallMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        return dispatcher;
    }

    private static Queue<Employee> getAvailableEmployees() {
        final Queue<Employee> availableEmployees = new UniquePriorityQueue<>();
        availableEmployees.add(newOperario("Pedro", 1L));
        availableEmployees.add(newOperario("Pablo", 2L));
        availableEmployees.add(newOperario("Pablo", 3L));
        availableEmployees.add(newOperario("Pablo", 4L));
        availableEmployees.add(newOperario("Pablo", 5L));
        availableEmployees.add(newOperario("Pablo", 6L));
        availableEmployees.add(newOperario("Pablo", 7L));
        availableEmployees.add(newOperario("Pablo", 8L));
        availableEmployees.add(newSupervisor("Vilma", 9L));
        availableEmployees.add(newDirector("Betty", 10L));
        availableEmployees.add(newOperario("Pablo", 11L));
        availableEmployees.add(newOperario("Pedro", 12L));
        availableEmployees.add(newOperario("Pablo", 13L));
        availableEmployees.add(newOperario("Pablo", 14L));
        availableEmployees.add(newOperario("Pablo", 15L));
        availableEmployees.add(newOperario("Pablo", 16L));
        availableEmployees.add(newOperario("Pablo", 17L));
        availableEmployees.add(newOperario("Pablo", 18L));
        availableEmployees.add(newOperario("Pablo", 19L));
        availableEmployees.add(newSupervisor("Vilma", 20L));
        availableEmployees.add(newDirector("Betty", 21L));
        availableEmployees.add(newOperario("Pablo", 22L));
        return availableEmployees;
    }

}
