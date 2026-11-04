package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.Position;
public class Arco<V,E> implements Edge<E> {
    private Position<Arco<V,E>> posicionenlistaarco;
    private Vertex<V> v1;
    private Vertex<V> v2;
    private E rotulo;

    public Arco(Vertex<V> v, Vertex<V> vv, E r){
        v1 = v;
        v2 = vv;
        rotulo = r;
        posicionenlistaarco = null;
    }
    public void setPosicionenlistaarco(Position<Arco<V,E>> pos) {
        this.posicionenlistaarco = pos;
    }
    public void setRotulo(E rotulo) {
        this.rotulo = rotulo;
    }
    public void setV1(Vertice<V,E> v1) {
        this.v1 = v1;
    }
    public void setV2(Vertice<V,E> v2) {
        this.v2 = v2;
    }
    public E element(){
        return rotulo;
    }
    public Vertex<V> getV1() {
        return v1;
    }
    public Vertex<V> getV2() {
        return v2;
    }
    public Position<Arco<V, E>> getPosicionenlistaarco() {
        return posicionenlistaarco;
    }
    public E getRotulo() {
        return rotulo;
    }


}
