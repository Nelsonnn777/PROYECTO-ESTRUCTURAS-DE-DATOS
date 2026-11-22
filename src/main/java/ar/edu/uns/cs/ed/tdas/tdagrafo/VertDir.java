package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;


public class VertDir<V,E> implements Vertex<V>{
    private V elemento;
    private Position<VertDir<V, E>> pos;
    
    // Listas internas del vértice
    private PositionList<Edge<E>> emergentes; // Aristas salientes (succesor)
    private PositionList<Edge<E>> incidentes; // Aristas entrantes (incident)

    public VertDir(V e) {
        elemento = e;
        pos = null;
        emergentes = new TdaLista<>();
        incidentes = new TdaLista<>();
    }

    public PositionList<Edge<E>> getEmergentes() { return emergentes; }
    public PositionList<Edge<E>> getIncidentes() { return incidentes; }
// GETTERS
    public Position<VertDir<V,E>> position(){return pos;}
    public V element() {return elemento;}
// SETTERS
    public void setPosition(Position<VertDir<V,E>> posicion){pos = posicion;}
    public void setElement(V elem){elemento = elem;}
}
