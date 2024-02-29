package org.example.numbeer.server;

public class WrongFormatException extends Exception{
    private final String exceptionMessage;
    public WrongFormatException(String message){
        this.exceptionMessage = message;
    }
    public String getExceptionMessage(){
        return this.exceptionMessage;
    }
}
