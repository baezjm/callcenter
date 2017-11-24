package com.examen.callcenter.controller;

import com.examen.callcenter.entity.Call;
import com.examen.callcenter.dispatcher.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.RejectedExecutionException;

/**
 * Created by jbaez on 23/11/17.
 */
@RestController
@RequestMapping("/call")
public class CallcenterController {

    @Autowired
    private Dispatcher dispatcher;

    @RequestMapping(value = "/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAvailablePictures(@PathVariable Long number) {
        String result = "ok";
        try {
            dispatcher.dispatchCall(new Call(number));
        } catch (final RejectedExecutionException e) {
            result = "rejected";
        }

        return new ResponseEntity<>(result,result == "ok" ? HttpStatus.OK : HttpStatus.TOO_MANY_REQUESTS);
    }

}
