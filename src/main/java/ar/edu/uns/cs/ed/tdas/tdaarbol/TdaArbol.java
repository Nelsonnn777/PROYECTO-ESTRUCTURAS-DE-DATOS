package ar.edu.uns.cs.ed.tdas.tdaarbol;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.tdamapeo.HashMapeo;
public class TdaArbol<E> implements Tree<E> {
    private TNodo<E> raiz;
    private int cantidad;

    public TdaArbol(){
        raiz = null;
        cantidad = 0;
    }
    
    public int size(){
        return cantidad;
    }

    public boolean isEmpty(){
        return cantidad == 0;
    }

    public void createRoot(E e){
        if(!isEmpty()){throw new InvalidOperationException("ya hay una raiz");}
        raiz = new TNodo<E>(e, null);
        cantidad++;
    }

    public Position<E> root(){
        if(raiz == null){
            throw new EmptyTreeException("Abrol vacio");
        }
        return raiz;
    }
    protected TNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
        
        if(isEmpty() || p== null){throw new InvalidPositionException("posicion nula");}
        TNodo<E> nodo;
        try{nodo =(TNodo<E>) p;}
            catch(ClassCastException e){
                throw new InvalidPositionException("NO es un nodo");
            }
        
        return nodo;
    }
    
    public Position<E> parent(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
        TNodo<E> nodo = checkPosition(p);
        if(nodo == raiz){throw new BoundaryViolationException("es la raiz");}
        return nodo.getPadre();
    }

    public Iterable<Position<E>> children(Position<E> p) throws InvalidPositionException{
        TNodo<E> nodo = checkPosition(p);
        Iterable<Position<E>> hijos = nodo.hijos();
        return hijos; 
    }

    public E replace(Position<E> p, E e) throws InvalidPositionException{
        TNodo<E> nodo = checkPosition(p);
        nodo.setElement(e);
        return e;
    }

    public boolean isInternal(Position<E> p) throws InvalidPositionException{
        TNodo<E> nodo = checkPosition(p);
        Iterator<Position<E>> hh = nodo.hijos().iterator();
        return hh.hasNext();
    }

    public boolean isExternal(Position<E> p) throws InvalidPositionException{
        TNodo<E> nodo = checkPosition(p);
        Iterator<Position<E>> hh = nodo.hijos().iterator();
        return !hh.hasNext();
    }

    public boolean isRoot(Position<E> p) throws InvalidPositionException{
        TNodo<E> nodo = checkPosition(p);
        return nodo == raiz;
    } 

    public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException{
        TNodo<E> nodo = checkPosition(p);
        TNodo<E> nuevo = new TNodo<E>(e, nodo);
        PositionList<Position<E>> hijos = nodo.hijos();
        hijos.addFirst(nuevo);
        cantidad++;
        return nuevo;
    }

    public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException{
        TNodo<E> nodo = checkPosition(p);
        TNodo<E> nuevo = new TNodo<E>(e, nodo);
        PositionList<Position<E>> hijos = nodo.hijos();
        hijos.addLast(nuevo);
        cantidad++;
        return nuevo;
    }
    

    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) {
        TNodo<E> nodo = checkPosition(p);
        Position<Position<E>> pos = null;
        if(!parent(rb).equals(p)){throw new InvalidPositionException("las posiciones no son padre e hijo respectivamente");}
        for (Position<Position<E>> hijo : nodo.hijos().positions()) {
            if(checkPosition(hijo.element()) == checkPosition(rb)){
                pos = hijo;
                break;
            }
        }
        TNodo<E> nuevo = new TNodo<E>(e, nodo);
        nodo.hijos().addBefore(pos, nuevo);
        cantidad++;
        return nuevo;
    }

    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) {
          TNodo<E> nodo = checkPosition(p);
        Position<Position<E>> pos = null;
        if(!parent(lb).equals(p) ){throw new InvalidPositionException("Las posiciones no son padre e hijo respectivamente");}
        for (Position<Position<E>> hijo : nodo.hijos().positions()) {
            if(checkPosition(hijo.element()) == checkPosition(lb)){
                pos = hijo;
                break;
            }
        }
        TNodo<E> nuevo = new TNodo<E>(e, nodo);
        nodo.hijos().addAfter(pos, nuevo);
        cantidad++;
        return nuevo;
    }

    
    public void removeExternalNode(Position<E> p) {
        if(isEmpty()){throw new InvalidPositionException("Arbol vacio");}
        TNodo<E> nodo = checkPosition(p);
        if(!nodo.hijos().isEmpty()){ throw new InvalidPositionException("No es un nodo externo");}
        if(!isRoot(p)){TNodo<E> padre= checkPosition(nodo.getPadre());
        for(Position<Position<E>> hijos : padre.hijos().positions()){
            if((hijos.element() == p)){
                padre.hijos().remove(hijos);
                break;
            }
        }
        }else{raiz = null;}
        cantidad--;
    }   

    @Override
    public void removeInternalNode(Position<E> p) {
        if(cantidad== 0){throw new InvalidPositionException("Arbol vacio");}
        TNodo<E> nodo = checkPosition(p);
        PositionList<Position<E>> hijos= nodo.hijos();
        if(nodo == raiz){ // Si hay q eliminar la raiz
            if(hijos.size() != 1){throw new InvalidPositionException("Es raiz con mas de un hijo");}
            raiz = checkPosition(hijos.first().element());
            raiz.setPadre(null);
            cantidad--;
        }
        else if(!hijos.isEmpty()){
            TNodo<E> padre = checkPosition(parent(p));
            PositionList<Position<E>> hermanos = padre.hijos();
            Position<Position<E>> posicion = null;
            for (Position<Position<E>> pos :hermanos.positions()){
                if (pos.element() == p){
                    posicion = pos;
                    break;
                }
            }
            Position<Position<E>> paraeliminar = posicion;
            for(Position<E> h : hijos){
                checkPosition(h).setPadre(padre);
                hermanos.addAfter(posicion, h);
                posicion = hermanos.next(posicion);
            }
            hermanos.remove(paraeliminar);
            cantidad--;
        }else{throw new InvalidPositionException("es un nodo externo");}
        

    }

    public void removeNode(Position<E> p){
        TNodo<E> nodo = checkPosition(p);
        if(nodo.hijos().isEmpty()){
            removeExternalNode(p);
        }
        else{
            removeInternalNode(p);
        }
    }

    @Override
    public Iterator<E> iterator() {
        PositionList<E> lista = new TdaLista<E>();
        for (Position<E> position : positions()) {
            lista.addLast(position.element());
        }
        return lista.iterator();
    }
    @Override
    public Iterable<Position<E>> positions() {
        // visita
        PositionList<Position<E>> pos = new TdaLista<Position<E>>();
        if(!isEmpty()){
        pos.addLast(raiz);
        for(Position<E> p : raiz.hijos()){
            guardarPreOrden(pos, p);
        }
    }
        return pos;
    }

    private void guardarPreOrden(PositionList<Position<E>> l, Position<E> p){
        //visita 
        PositionList<Position<E>> toret = l;
        TNodo<E> pc = checkPosition(p);
        toret.addLast(p);
        if(isInternal(p)){
            for(Position<E> h : pc.hijos()){
                guardarPreOrden(toret, h);
            }
        }
        
    }

    // EJERCICIO 2 Tp arboles
    public void eliminarUltimoHijo(Position<E> p){
        if(isRoot(p)){ throw new InvalidOperationException("la posicion es la raiz");}
        TNodo<E> nodo = checkPosition(p);
        TNodo<E> padre = checkPosition(nodo.getPadre());
        TNodo<E> ultimohijodepadre = checkPosition(padre.hijos().last().element());
        if(nodo != ultimohijodepadre){
            throw new InvalidPositionException("no es el ultimo");
        }
        else{ removeNode(nodo);}
    }

    public static HashMapeo<Character, Integer> cantidadRepeticiones(Tree<Character> t){
        HashMapeo<Character, Integer> toret = new HashMapeo<>();
        if(!t.isEmpty()){
            contarPreOrden(toret, t.root(), t);
        }
        
        return toret;
    }

    private static void contarPreOrden(HashMapeo<Character,Integer> h, Position<Character> p, Tree<Character> t){
        // visita
        if(h.get(p.element()) == null){ h.put(p.element(), 1);}
        else {
            int a = h.get(p.element()) +1;
            h.put(p.element(), a);
        }
        // no se
        if(t.isInternal(p)){   
            for(Position<Character> hijos : t.children(p)){
                contarPreOrden(h, hijos, t);
            }
        }
    }

    public static Iterable<Position<String>> ej4(Tree<String> t, String s){
        PositionList<Position<String>> toret = new TdaLista<Position<String>>();
        postOrden(toret,t.root(), t);
        return toret;

    }

    private static void postOrden(PositionList<Position<String>> l, Position<String> p, Tree<String> t){
        if(t.isInternal(p)){
            for(Position<String> pos : t.children(p)){
                postOrden(l, pos, t);
            }
        }
        l.addLast(p);
    }

    public static <E> void ej5(Tree<E>a  , E e){
        for(Position<E> pos : a.positions()){
            if(pos.element()== e){ a.removeNode(pos);}
        }
    }


}





    





