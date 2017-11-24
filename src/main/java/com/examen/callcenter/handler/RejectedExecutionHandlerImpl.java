package com.examen.callcenter.handler;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jbaez on 23/11/17.
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    private final static Logger logger = Logger.getLogger(RejectedExecutionHandlerImpl.class.getName());

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.log(Level.WARNING,r.toString() + " is rejected");
        throw new RejectedExecutionException();
    }

}
