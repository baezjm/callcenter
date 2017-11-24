package com.examen.callcenter.talk.impl;

import com.examen.callcenter.talk.Talk;
import net.jcip.annotations.ThreadSafe;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@ThreadSafe
public class TimeScheduleTalk implements Talk {
    private final static Logger logger = Logger.getLogger(TimeScheduleTalk.class.getName());
    private final int min;
    private final int max;

    public TimeScheduleTalk(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void talk() {
        randomDelay(min, max);
    }

    private void randomDelay(int min, int max) {
        Random numAleatorio = new Random();

        // Numero entero entre 5 y 10
        int n = numAleatorio.nextInt(max-min+1) + min;
        try {
            Thread.sleep(n * 1000);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Interrupted exception", e);
        }
    }
}
