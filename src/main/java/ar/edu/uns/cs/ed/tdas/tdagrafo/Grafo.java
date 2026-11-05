package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;
import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import main.java.ar.edu.uns.cs.ed.tdas.tdamapeo.*;

public class Grafo<V,E> implements Graph<V,E> {
    
    private PositionList<Vertice<V,E>> lisvertices;
    private PositionList<Arco<V,E>> lisarcos;
    

    public Grafo(){
        
        lisvertices = new TdaLista<Vertice<V,E>>();
        lisarcos = new TdaLista<Arco<V,E>>();
    }

    public Vertex<V> insertVertex(V x){

        Vertice<V,E> ver = new Vertice<V,E>(x);
        lisvertices.addLast(ver);
        ver.setPosicionenlista(lisvertices.last());
        return ver;
    }

    public Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E e){
        Arco<V,E> arco = new Arco<V,E>(v1, v2, e);
        lisarcos.addLast(arco);
        arco.setPosicionenlistaarco(lisarcos.last());
        return arco;
    }

    
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> toret = new TdaLista<Edge<E>>();
        for(Edge<E> e : lisarcos){
            toret.addLast(e);
        }
        return toret;
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


    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> toret = new TdaLista<Vertex<V>>();
        for(Vertex<V> v : lisvertices) {
            toret.addLast(v);
        }
        return toret;
    }

    @Override
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) {
        PositionList<Edge<E>> toret = new TdaLista<Edge<E>>();
        for(Arco<V,E> ar : lisarcos){
            if (ar.getV1() == v || ar.getV2() == v ){
                toret.addLast(ar); 
            }
        }
        return toret;
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

    public E replace(Edge<E> e, E x) {
        Arco<V,E> ar = checkEdge(e);
        E ant = ar.element();
        ar.setRotulo(x);
        return ant;
    }
    public V replace(Vertex<V> v, V x){
        Vertice<V,E> ver = checkVertex(v);
        V ant = ver.element();
        ver.setRotulo(x);
        return ant;
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
        for(Position<Vertice<V,E>> w : lisvertices.positions()){
            if(w.element()== ver){lisvertices.remove(w); break;}
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

    public boolean esConexo(){
        if(lisvertices.size() == 0){return true;}// o throw new InvalidOperationException("es una grafo vacio");}
        boolean ret = false;
        Map<V,Boolean> map = new HashMapeo<>();
        for(Vertice<V,E> v: lisvertices){
            map.put(v.element(), false);
        }
        DFS(map, lisvertices.first().element());
        for(Vertice<V,E> v: lisvertices){
            ret = map.get(v.element());
            if(ret == false){break;}
        }
        return ret;
        

    }

    public void BFS(HashMapeo<V,Boolean> map, Vertice<V,E> v){

    }

    public PositionList<Vertex<V>> caminomascorto(Vertice<V,E> o ,Vertice<V,E> d){
        if(o == d){return new TdaLista<Vertex<V>>();}
        HashMapeo<V, Boolean> recorrido = new HashMapeo<V,Boolean>();
        for(Vertice<V,E> w :lisvertices){
            recorrido.put(w.element(), false);
        }
        return camcortoDFS(o, d, recorrido);   }

    private PositionList<Vertex<V>> camcortoDFS(Vertice<V,E> v, Vertice<V,E> d, HashMapeo<V, Boolean> map){
        map.put(v.element(), true);
        PositionList<Vertex<V>> toret = null;
        if(v == d){
            toret = new TdaLista<Vertex<V>>();
            toret.addFirst(d);
            return toret;
        }
        Integer menor = null;
        for(Arco<V,E> w : v.getListaAdyacencia()){
            Vertice<V,E> vv = checkVertex(opposite(v, w));
            if(!map.get(vv.element())){
                PositionList<Vertex<V>> wlis = camcortoDFS(vv,d,map);
                if( wlis != null &&(menor == null||wlis.size() < menor)){
                    toret = wlis;
                    toret.addFirst(v);
                    menor = toret.size();
                }
            }
        }
        map.put(v.element(), false);
        return toret;
    }

    public PositionList<Vertex<V>> camcortoBFS(Vertice<V,E> v, Vertice<V,E> d, Hashmapeo<V,Boolean> map){
        
    }

    public void DFS(HashMapeo<V,Boolean> map, Vertice<V,E> v){
        //visita
        map.put(v.element(), true);

        //recorrido
        for(Edge<E> e : incidentEdges(v)){
            Arco<V,E> arco = checkEdge(e);
            Vertice<V,E> v1 = checkVertex(arco.getV1());
            Vertice<V,E> v2 =checkVertex(arco.getV2());
            if(v1 != v && !map.get(v1.element())){
                BFS(map, v1);
            }
            if(v2 != v && !map.get(v2.element())){
                BFS(map, v2);
            };
        }

    

    }
    




}
