package com.examen.callcenter.queue;

import java.util.PriorityQueue;

/**
 * Created by jbaez on 23/11/17.
 */
public class UniquePriorityQueue<T> extends PriorityQueue<T> {

    @Override
    public boolean offer(T e) {
        boolean isAdded = false;
        if (!super.contains(e)) {
            isAdded = super.offer(e);
        }
        return isAdded;
    }
}
