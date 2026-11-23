package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.Position;

public class Vertice<V,E> implements Vertex<V> {
    private V elem;
    private PositionList<Edge<E>> listaArcos;
    private Position<Vertice<V,E>> pos;

    public Vertice(V e){
        elem = e;
        listaArcos = new TdaLista<>();
    }

    public V element() { return elem;}
    public PositionList<Edge<E>> getListaArcos() { return listaArcos;}
    public Position<Vertice<V,E>> position() { return pos;}

    public void setPos(Position<Vertice<V, E>> p) { pos = p;}
    public void setElem(V elem) { this.elem = elem;}
}