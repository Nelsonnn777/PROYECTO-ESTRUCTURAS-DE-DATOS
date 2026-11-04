package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.excepciones.*;

import main.java.ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import main.java.ar.edu.uns.cs.ed.tdas.Position;


public class TdaGrafoDirigido<V,E> implements GraphD<V,E> {
    private PositionList<Vertice<V,E>> lisver;
    private PositionList<Arco<V,E>> lisarcos;

    public TdaGrafoDirigido(){
        lisver = new TdaLista<Vertice<V,E>>();
        lisarcos = new TdaLista<Arco<V,E>>();
    }
    private Vertice<V,E> checkVertex(Vertex<V> v){
        if (v== null){throw new InvalidVertexException("vertice nulo");}
        Vertice<V,E> ver;
        try {
            ver = (Vertice<V,E>) v;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("no es un vertice");
        }
        return ver;
    }

    private Arco<V,E> checkEdge(Edge<E> ed ){
        if(ed == null){throw new InvalidEdgeException("arco nulo");}
        Arco<V,E> arco;
        try{ arco = (Arco<V,E>) ed;}
        catch(ClassCastException e){ throw new InvalidEdgeException("no es un arco");}
        return arco;
    }

    public E replace(Edge<E> e, E x){
        Arco<V,E> arco = checkEdge(e);
        E ant = arco.element();
        arco.setRotulo(x);
        return ant;
    }
    public V replace(Vertex<V> v, V x){
        Vertice<V,E> ver = checkVertex(v);
        V ant = ver.element();
        ver.setRotulo(ant);
        return ant;
    }

    public Vertex<V> insertVertex(V x){
        Vertice<V,E> ver = new Vertice<V,E>(x);
        lisver.addLast(ver);
        return ver;
    }

    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e){
        Vertice<V,E> v1 = checkVertex(v);
        Vertice<V,E> v2 = checkVertex(w);
        Arco<V,E> arco = new Arco<V,E>(v1, v2, e);
        return arco;
    }

    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> toret = new TdaLista<Vertex<V>>();
        for(Vertex<V> v : lisver) {
            toret.addLast(v);
        }
        return toret;
    }
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> toret = new TdaLista<Edge<E>>();
        for(Edge<E> e : lisarcos){
            toret.addLast(e);
        }
        return toret;
    }
    public V removeVertex(Vertex<V> v){
        Vertice<V,E> ver = checkVertex(v);
        V toret = ver.element();
        PositionList<Position<Arco<V,E>>> eliminararcos = new TdaLista<Position<Arco<V,E>>>();
        for(Position<Arco<V,E>> ar : lisarcos.positions()){
            if(ar.element().getV1() == ver || ar.element().getV2() == ver ){
                eliminararcos.addLast(ar);
            }
        }
        for(Position<Arco<V,E>> ar : eliminararcos){
            lisarcos.remove(ar);
        }
        for(Position<Vertice<V,E>> w : lisver.positions()){
            if(w.element()== ver){lisver.remove(w); break;}
        }
        return toret;
        
    }
    public E removeEdge(Edge<E> e){
        Arco<V,E> arco = checkEdge(e);
        E toret = arco.getRotulo();
        for(Position<Arco<V,E>> p : lisarcos.positions()){
            if(p.element() == arco){lisarcos.remove(p); break;}
        }
        return toret;
    }
    @SuppressWarnings("unchecked")
    public Vertex<V> [] endvertices(Edge<E> e){
        Arco<V,E> ar = checkEdge(e);
        Vertex<V>[] toret = new Vertex[]{ar.getV1(), ar.getV2()};
        return toret;
    }

    public boolean areAdjacent(Vertex<V>v, Vertex<V> w){
        boolean enc = false;
        for(Arco<V,E> ar : lisarcos){
            if((ar.getV1() == v || ar.getV2() == v) && (ar.getV1() == w || ar.getV2() == w) ){
                enc = true;
                break;
            }
        }
        return enc;
    }

    public Vertex<V> opposite(Vertex<V> v, Edge<E> e){
        Arco<V,E> ar = checkEdge(e);
        Vertex<V> toret;
        boolean v1b = ar.getV1() == v;
        boolean v2b = ar.getV2() == v;
        if(!v1b&& !v2b){
            throw new InvalidOperationException("El vertice y el arco no estan relacionados");
        }
        if(v1b){toret = ar.getV2();}
        else{toret = ar.getV1();}
        return toret;
    }

    public Iterable<Edge<E>> succesorEdges(Vertex<V> v){
        PositionList<Edge<E>> toret = new TdaLista<Edge<E>>();
        for(Arco<V,E> ar :lisarcos){
            if(ar.getV1() == checkVertex(v)){
                toret.addLast(ar);
            }
        }
        return toret;
    }

    public Iterable<Edge<E>> incidentEdges(Vertex<V> v){
        Vertice<V,E> ver = checkVertex(v);
        PositionList<Edge<E>> toret = new TdaLista<Edge<E>>();
        for(Arco<V,E> ar: lisarcos){
            if(ar.getV2() == ver){
                toret.addLast(ar);
            }
        }
        return toret;
    }

    
}
