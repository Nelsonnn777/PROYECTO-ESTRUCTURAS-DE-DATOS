package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdamapeo.HashMapeo;
import main.java.ar.edu.uns.cs.ed.tdas.tdamapeo.Map;


public class Prueba2 {
    public static void main(String[] args) {
    }

    public static <V,E> PositionList<PositionList<Vertex<V>>> CaminosPosibles(Graph<V,E> grafo, Vertex<V> inicio, Vertex<V> fin ){
            if (grafo == null || inicio == null || fin == null) {
            throw new InvalidVertexException("El grafo o los vértices no pueden ser nulos.");
        }
        PositionList<Vertex<V>> caminos = new TdaLista<>();
        PositionList<PositionList<Vertex<V>>> caminosPosibles = new TdaLista<>();
        if (inicio == fin){
            throw new InvalidOperationException("El vértice de salida y de entrada son el mismo BOLUDO");
        }
        Map<Vertex<V>, V> mapa = new HashMapeo<>();
        metodoRec(grafo, mapa, fin, inicio, caminos, caminosPosibles);

        return caminosPosibles;
    }

    private static <V,E> void metodoRec(Graph<V,E> grafo, Map<Vertex<V>,V> mapa,Vertex<V> fin, Vertex<V> vertice, PositionList<Vertex<V>> camino, PositionList<PositionList<Vertex<V>>> caminosPosibles){
        camino.addLast(vertice);
        if (vertice == fin){
            PositionList<Vertex<V>> caminoCorrecto = new TdaLista<>();
            for (Vertex<V> v : camino) {
                caminoCorrecto.addLast(v);
            }
            caminosPosibles.addLast(caminoCorrecto);
        }
        else {
            mapa.put(vertice, vertice.element());

            for (Edge<E> e : grafo.incidentEdges(vertice)) {
                V elemento = mapa.get(grafo.opposite(vertice, e));
                if (elemento == null) {
                    metodoRec(grafo, mapa, fin, grafo.opposite(vertice, e), camino, caminosPosibles);
                }
            }
            mapa.remove(vertice);
        }
        camino.remove(camino.last());
    }
}
