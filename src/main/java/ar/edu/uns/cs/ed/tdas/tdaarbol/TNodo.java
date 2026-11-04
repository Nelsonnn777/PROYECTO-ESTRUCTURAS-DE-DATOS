package ar.edu.uns.cs.ed.tdas.tdaarbol;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;
public class TNodo<E> implements Position<E> {
    private E element;
    private TNodo<E> Padre;
    private PositionList<Position<E>> Hijos;

    public TNodo(E e, TNodo<E> p){
        Padre = p;
        element = e;
        Hijos = new TdaLista<Position<E>>();
    }

    public E element(){
        return element;
    }

    public PositionList<Position<E>> hijos(){
        return Hijos;
    }
    
    public Position<E> getPadre(){
        return Padre;
    }
    
    public E setElement(E e){
        element = e;
        return element;
    }
    public void setPadre(TNodo<E> padre) {
        Padre = padre;
    }
    public void setHijos(PositionList<Position<E>> hijos) {
        Hijos = hijos;
    }

    


}
