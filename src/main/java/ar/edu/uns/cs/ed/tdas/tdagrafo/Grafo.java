package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import main.java.ar.edu.uns.cs.ed.tdas.Position;

public class Grafo<V,E> implements Graph<V,E> {
    private PositionList<Vertice<V,E>> listaVertices;
    private PositionList<Arco<V,E>> listaArcos;

    public Grafo(){
        listaArcos = new TdaLista<>();
        listaVertices = new TdaLista<>();
    }

    private Vertice<V,E> checkValidVertice(Vertex<V> v){
        if (v == null) throw new InvalidVertexException("El vector es nulo");
        try{
            Vertice<V,E> vert = (Vertice<V,E>) v;
            return vert;
        } catch (ClassCastException e) {
            throw new ClassCastException("Tipo de vértice incorrecto");
        }
    }

    private Arco<V,E> checkValidArco(Edge<E> e){
        if (e == null) throw new InvalidEdgeException("El arco es nulo");
        try {
            Arco<V,E> arco = (Arco<V,E>) e;
            if (arco.getVert1() == null || arco.getVert2() == null)
                throw new InvalidEdgeException("El arco no pertenece al grafo");
            return arco;
        } catch (ClassCastException c) {
            throw new ClassCastException("Tipo de arco incorrecto");
        }
    }

    public Iterable<Vertex<V>> vertices(){
        PositionList<Vertex<V>> toRet = new TdaLista<>();
        for (Vertex<V> vertex : listaVertices) {
            toRet.addLast(vertex);
        }
        return toRet;
    }

    public Iterable<Edge<E>> edges(){
        PositionList<Edge<E>> toRet = new TdaLista<>();
        for (Edge<E> edge : listaArcos){
            toRet.addLast(edge);
        }
        return toRet;
    }

    public Iterable<Edge<E>> incidentEdges(Vertex<V> v){
        Vertice <V,E> vert = checkValidVertice(v);
        return vert.getListaArcos();
    }

    public Vertex<V> opposite(Vertex<V> v, Edge<E> e){
        Vertice<V,E> vert = checkValidVertice(v);
        Arco<V,E> arco = checkValidArco(e);
        for (Edge<E> edge : vert.getListaArcos()) {
            if (edge == e) {
                if (arco.getVert1() == vert){
                    return arco.getVert2();
                }
                else 
                    return arco.getVert1();
            }
        }
        throw new InvalidEdgeException("El arco no pertenece al grafo");
    } 

    public Vertex<V> [] endvertices(Edge<E> e) {
        Arco<V,E> arco = checkValidArco(e);
        Vertice<V,E> [] toRet = (Vertice<V,E> []) new Vertex [2]; 
        toRet[0] = arco.getVert1();
        toRet[1] = arco.getVert2();
        return toRet;
    }

    public boolean areAdjacent(Vertex<V> v,Vertex<V> w){
        Vertice<V,E> vertV = checkValidVertice(v);
        Vertice<V,E> vertW = checkValidVertice(w);
        for (Edge<E> edge : vertV.getListaArcos()) {
            Arco<V,E> arco = checkValidArco(edge);
            if (arco.getVert1() == vertW || arco.getVert2() == vertW) {
                return true;
            }
        }        
        return false;
    }

	public V replace(Vertex<V> v, V x){
        Vertice <V,E> vertice = checkValidVertice(v);
        V toRet = x;
        vertice.setElem(x);
        return toRet;
    }

	public E replace(Edge<E> e, E x){
        Arco<V,E> arco = checkValidArco(e);
        E toRet = arco.element();
        arco.setElem(x);
        return toRet;
    }

	public Vertex<V> insertVertex(V x){
        Vertice<V,E> vertice = new Vertice<V,E>(x);
        listaVertices.addLast(vertice);
        vertice.setPos(listaVertices.last());
        return vertice;
    }

    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E x){
        Vertice<V,E> vertV = checkValidVertice(v);
        Vertice<V,E> vertW = checkValidVertice(w);
        Arco<V,E> arco = new Arco<V,E>(vertV, vertW, x);
        listaArcos.addLast(arco);
        vertV.getListaArcos().addLast(arco);
        vertW.getListaArcos().addLast(arco);
        return arco;
    }

	public V removeVertex(Vertex<V> v){
        Vertice<V,E> vertice = checkValidVertice(v);
        for (Edge<E> edge : vertice.getListaArcos()) {
            removeEdge(edge); 
        } 
        V toRet = vertice.element();      
        removeVertex(vertice);
        return toRet;
    }

	public E removeEdge(Edge<E> e){
        Arco<V,E> arco = checkValidArco(e);
        Vertice<V,E> vert1 = arco.getVert1();
        Vertice<V,E> vert2 = arco.getVert2();
        for (Position<Edge<E>> pos : vert1.getListaArcos().positions()) {
            if (pos.element() == e) {
                vert1.getListaArcos().remove(pos);
                break;
            }   
        }
        for (Position<Edge<E>> pos : vert2.getListaArcos().positions()) {
            if (pos.element() == e) {
                vert1.getListaArcos().remove(pos);
                break;
            }   
        }

        for (Position<Arco<V,E>> pos : listaArcos.positions()) {
            if (pos.element() == e){listaArcos.remove(pos); break;}
        }

        E toRet = e.element();
        return toRet;
    }
}
