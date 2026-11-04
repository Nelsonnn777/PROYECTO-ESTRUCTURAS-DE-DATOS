package main.java.ar.edu.uns.cs.ed.tdas.tdaarbolbinario;


import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
public class BTNodo<E> implements Position<E> {
    private E e;
    private BTNodo<E> Padre;
    private BTNodo<E> HijoI;
    private BTNodo<E> HijoD;


    public BTNodo(E el){
        e= el;
        Padre = null;
        HijoI = null;
    }
    public E element(){
        return e;
    }
    public void setE(E e) {
        this.e = e;
    }
    public BTNodo<E> getPadre(){
        return Padre;
    }
    
    public BTNodo<E> getHijoD() {
        return HijoD;
    }

    public BTNodo<E> getHijoI() {
        return HijoI;
    }
    public void setHijoD(BTNodo<E> hijoD) {
        HijoD = hijoD;
    }
    public void setHijoI(BTNodo<E> hijoI) {
        HijoI = hijoI;
    }
    public void setPadre(BTNodo<E> padre) {
        Padre = padre;
    }
}
