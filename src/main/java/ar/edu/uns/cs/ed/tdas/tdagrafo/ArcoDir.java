package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.Position;

public class ArcoDir<V,E> implements Edge<E> {
    private E elemento;
    private Position<ArcoDir<V,E>> posicion;
    private Vertex<V> vert1;
    private Vertex<V> vert2;

    public ArcoDir (Vertex<V> v1, Vertex<V> v2, E elem){
        elemento = elem;
        posicion = null;
        vert1 = v1;
        vert2 = v2;
    }

    public E element() {
        return elemento;
    }
    public Position<ArcoDir<V, E>> getPosicion() {
        return posicion;
    }
    public Vertex<V> getVert1() {
        return vert1;
    }
    public Vertex<V> getVert2() {
        return vert2;
    }

    public void setElemento(E elemento) {
        this.elemento = elemento;
    }
    public void setPosicion(Position<ArcoDir<V, E>> posicion) {
        this.posicion = posicion;
    }
    public void setVert1(Vertex<V> vert1) {
        this.vert1 = vert1;
    }
    public void setVert2(Vertex<V> vert2) {
        this.vert2 = vert2;
    }
}
