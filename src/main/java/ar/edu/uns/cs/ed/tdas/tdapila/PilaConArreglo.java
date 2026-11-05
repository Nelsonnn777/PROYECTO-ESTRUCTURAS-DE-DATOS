package main.java.ar.edu.uns.cs.ed.tdas.tdapila;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException;
public class PilaConArreglo<E> implements Stack<E> {
    private int cantidad;
    private  static int capacidad = 1000;
    private E elementos[];

    public PilaConArreglo(){
        cantidad = 0;
        elementos = (E[]) new Object[capacidad];
    }

    public int size(){
        return cantidad;
    }
    public boolean isEmpty(){
        return cantidad == 0 ;
    }
    public E top(){
        if (cantidad != 0){
            return elementos[cantidad-1];
        }
        else throw new EmptyStackException("Pila vacia");

    }
    public E pop(){
        E el = null;
        if(cantidad!=0){
            el = elementos[cantidad-1];
            elementos[cantidad-1] = null;
            cantidad--;
            return el;
        }
        else throw new EmptyStackException("pila vacia");
    }

     public void push(E element){
        E[] arrAux;
        if(elementos.length == cantidad){
            arrAux = (E[]) new Object[elementos.length*2];
        } else {
            arrAux = (E[]) new Object[elementos.length];
        }
        for (int i = 0; i < cantidad; i++) {
            arrAux[i+1] = elementos[i];
        }
        arrAux[0] = element;
        cantidad++;
        elementos = arrAux;
    }
    
}
