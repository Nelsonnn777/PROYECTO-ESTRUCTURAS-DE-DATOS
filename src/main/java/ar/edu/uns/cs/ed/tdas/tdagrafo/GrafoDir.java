package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdaarbol.TdaArbol;
public class GrafoDir<V,E> implements GraphD<V,E> {

    private PositionList<VertDir<V,E>> listaVertices;
    private PositionList<ArcoDir<V,E>> listaArcos;
    
    public GrafoDir(){
        listaArcos = new TdaLista<>();
        listaVertices = new TdaLista<>();
    }

    private VertDir<V,E> checkValidVert(Vertex<V> vert){
        if (vert == null){
            throw new InvalidVertexException("El vértice es nulo");
        }
        try{
            VertDir<V,E> toRet = (VertDir<V,E>) vert;
            return toRet;
        } catch (ClassCastException e) {
            throw new ClassCastException("El tipo de vértice no es válido");
        }
    }
    private ArcoDir<V, E> checkValidArco(Edge<E> edg){
        if (edg == null)
            throw new InvalidEdgeException("El arco es nulo");
        try {
            ArcoDir<V,E> arco = (ArcoDir<V,E>) edg;

            if (arco.getVert1() == null || arco.getVert2() == null)
                throw new InvalidEdgeException("El arco no pertenece a el grafo");
            return arco;
        } catch (ClassCastException e) {
            throw new ClassCastException("El tipo del arco no es compatible");

        }
    }

    public Iterable<Vertex<V>> vertices(){
        TdaLista <Vertex<V>> toRet = new TdaLista<>();
        for (Vertex<V> vert : listaVertices) {
            toRet.addLast(vert);
        }
        return toRet;
    }

    public Iterable<Edge<E>> edges(){
        TdaLista <Edge<E>> toRet = new TdaLista<>();
        for (Edge<E> arc : listaArcos) {
            toRet.addLast(arc);
        }
        return toRet;
    }

    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) {
    VertDir<V, E> vert = checkValidVert(v);
    return vert.getIncidentes(); // O(1)
    }

    public Iterable<Edge<E>> succesorEdges(Vertex<V> v) {
        VertDir<V, E> vert = checkValidVert(v);
        return vert.getEmergentes(); // O(1)
    }

    public Vertex<V> opposite(Vertex<V> v, Edge<E> e){
        VertDir<V,E> vertice = checkValidVert(v);
        ArcoDir<V,E> arco = checkValidArco(e);
        if (arco.getVert1() == vertice)
            return arco.getVert2();
        if(arco.getVert2() == vertice)
            return arco.getVert1();
        throw new InvalidOperationException("El arco no está relacionado con el vértice pasado por parámetro");
    }

    public Vertex<V> [] endvertices(Edge<E> e){
        ArcoDir<V,E> arco = checkValidArco(e);
        Vertex<V> [] toRet =(Vertex<V> []) new Vertex[2];
        toRet[0] = arco.getVert1();
        toRet[1] = arco.getVert2();
        return  toRet;
    }

    public boolean areAdjacent(Vertex<V> v1, Vertex<V> v2){
        VertDir<V,E> vert = checkValidVert(v1);
        checkValidVert(v2);
        for (Edge<E> arc : vert.getEmergentes()) {
            ArcoDir<V,E> arco = checkValidArco(arc);
            if (arco.getVert2() == v2) {
                return true;
            } 
        }
        for (Edge<E> arc : vert.getIncidentes()) {
            ArcoDir<V,E> arco = checkValidArco(arc);
            if (arco.getVert1() == v2) {
                return true;
            } 
        }
        return false;
    }

    public V replace(Vertex<V> v, V x){
        VertDir<V,E> vertice = checkValidVert(v);
        V toRet = vertice.element();
        vertice.setElement(x);
        return toRet;
    }

    public E replace(Edge<E> e, E x){
        ArcoDir<V,E> arco = checkValidArco(e);
        E toRet = arco.element();
        arco.setElemento(x);
        return toRet;
    }

	public Vertex<V> insertVertex(V x){
    listaVertices.addLast(new VertDir<V,E>(x));
    VertDir<V,E> vertice = listaVertices.last().element();
    vertice.setPosition(listaVertices.last());
    return vertice;
    }

    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e){
        VertDir<V, E> vertV = checkValidVert(v);
        VertDir<V, E> vertW = checkValidVert(w);
        ArcoDir<V, E> arco = new ArcoDir<V,E>(vertV, vertW, e);
        vertV.getEmergentes().addLast(arco);
        vertW.getIncidentes().addLast(arco);
        listaArcos.addLast(arco);
        return arco;
    }

    public V removeVertex(Vertex<V> v){
        VertDir<V,E> vertice = checkValidVert(v);
        while (!vertice.getEmergentes().isEmpty()) {
        Edge<E> arco = vertice.getEmergentes().first().element();
        removeEdge(arco);
        }
        while (!vertice.getIncidentes().isEmpty()) {
        Edge<E> arco = vertice.getIncidentes().first().element();
        removeEdge(arco);
        }
        if (vertice.position() != null) {
        listaVertices.remove(vertice.position());
        vertice.setPosition(null);
        }
        
        return  vertice.element();
    }

    public E removeEdge(Edge<E> e){
        ArcoDir<V,E> arco = checkValidArco(e);
        VertDir<V,E> vert1 = checkValidVert(arco.getVert1()); 
        VertDir<V,E> vert2 = checkValidVert(arco.getVert2());

        for (Position<Edge<E>> a : vert1.getEmergentes().positions()) {
            if (a.element() == e) {
                vert1.getEmergentes().remove(a);
                break;
            }
        }
        for (Position<Edge<E>> a : vert2.getIncidentes().positions()) {
            if (a.element() == e) {
                vert2.getIncidentes().remove(a);
                break;
            }
        }

        if (arco.getPosicion() != null) {
        listaArcos.remove(arco.getPosicion());
        arco.setPosicion(null);
        }
        return e.element();
    }
}
