package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;

public class Vertice<V,E> implements Vertex<V> {
    private Position<Vertice<V,E>> posicionenlista;
    private V rotulo;
    private  PositionList<Arco<V,E>> ListaAdyacencia;

    public Vertice(V x){
        rotulo = x;
        posicionenlista = null;
        ListaAdyacencia = new TdaLista<Arco<V,E>>();
    }

    public V element(){
        return rotulo;
    }
    public void setRotulo(V rotulo) {
        rotulo = rotulo;
    }

    public void setPosicionenlista(Position<Vertice<V,E>> pos) {
        posicionenlista = pos;
    }
    public Position<Vertice<V,E>> getPosicionenlista(){
        return posicionenlista;
    }
    public PositionList<Arco<V,E>> getListaAdyacencia() {
        return ListaAdyacencia;
    }
    


}
