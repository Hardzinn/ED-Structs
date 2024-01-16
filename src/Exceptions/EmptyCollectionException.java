package Exceptions;

public class EmptyCollectionException extends Exception{

    public EmptyCollectionException(String message){
        super();
        System.out.println(message);
    }
}
