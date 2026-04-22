package com.springai.resumax.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> builder(
            Object data, Map<String,String> error, HttpStatus httpStatus, LocalDateTime time){


        Map<String,Object> result = new HashMap<>();

        result.put("data",data);
        result.put("error",error);
        result.put("status",httpStatus);
        result.put("timestamp",time);


        return new ResponseEntity<>(result,httpStatus);

    }
}
