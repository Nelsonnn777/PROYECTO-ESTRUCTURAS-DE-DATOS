package ar.edu.uns.cs.ed.tdas.excepciones;

public class FullQueueException extends RuntimeException{
    public FullQueueException(String msg){
        super(msg);
    }
}