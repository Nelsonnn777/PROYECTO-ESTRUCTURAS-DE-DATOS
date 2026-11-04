package ar.edu.uns.cs.ed.tdas.tdacola;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
import ar.edu.uns.cs.ed.tdas.excepciones.FullQueueException;

public class ColaConArreglo<E> implements Queue <E>{
    private E[] elementos;
    private int cantidad;
    private static int capacidad = 1000;
     @SuppressWarnings("unchecked") //
    public ColaConArreglo(){   
        elementos = (E[])new Object[capacidad];
        cantidad = 0;

    }
    public int size(){
        return cantidad;
    }
    private void reorganizar(){
        if (cantidad > 0){
            boolean enc = false;
            for(int i = 1;!enc && i < capacidad ;i++ ){
                if(elementos[i] != null){
                    elementos[i-1] = elementos [i];
                    elementos[i] = null;
                }
                else enc = true;
            }
        }
    }
    public E dequeue(){
        if(size()> 0){
            E el = elementos[0];
            elementos[0]= null;
            cantidad--;
            reorganizar();
            return el;
        }
        else throw new EmptyQueueException("Cola Vacia");
    }
    public void enqueue(E el){
        boolean enc = false;
        if(cantidad != capacidad){
        for(int i = 0; !enc ; i++){
            if(elementos[i] == null){
                cantidad++;
                enc = true;
                elementos[i]= el;
                System.err.println(cantidad);
            }

        }
        }
        else throw new FullQueueException("Cola llena");
    }
    public boolean isEmpty(){
        return size()==0;
    }
    public E front(){
        if(size()> 0){
            return elementos[0];
        }
        else throw new EmptyQueueException("Cola Vacia");
    }
    
}
