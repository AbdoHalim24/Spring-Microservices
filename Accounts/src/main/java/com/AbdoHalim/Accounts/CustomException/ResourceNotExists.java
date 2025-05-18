package com.AbdoHalim.Accounts.CustomException;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotExists  extends RuntimeException{

    public ResourceNotExists(String Resource,String filedName,String filedValue){
        super(String.format(("%s not exists with %s: %s"),Resource,filedName,filedValue));
    }


}
