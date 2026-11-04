package main.java.ar.edu.uns.cs.ed.tdas.tdalista;

import main.java.ar.edu.uns.cs.ed.tdas.Position;

public class Dnodo<E> implements Position<E> {
    protected E el;
    protected Dnodo<E> sig;
    protected Dnodo<E> ant;

    public Dnodo(E ele){
        el = ele;
        sig = null;
        ant = null;
    }
    public E element(){
        return el;
    }
    public void setElem(E ele){
        el = ele;
    }
    public Dnodo getSiguiente(){
        return sig;
    }
    public Dnodo getAnterior(){
        return ant;
    }
    public void setSiguiente(Dnodo siguiente){
        sig = siguiente;
    }
    public void setAnterior(Dnodo anterior){
        ant = anterior;
    }

}