package main.java.ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Iterator;
import main.java.ar.edu.uns.cs.ed.tdas.tdaarbol.Tree;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.IteradorPL;
import main.java.ar.edu.uns.cs.ed.tdas.tdadiccionario.TdaDiccionario;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdamapeo.HashMapeo;
public class TdaArbolBinario<E> implements BinaryTree<E>{
    private BTNodo<E> raiz;
    private int cantidad;

    public boolean isEmpty(){
        return cantidad == 0;
    }

    public int size(){
        return cantidad;
    }

    private BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
        if(p == null){throw new InvalidPositionException("La posicion es nula");}
        BTNodo<E> a;
        try {
           a =  (BTNodo<E>)p;
        } catch (ClassCastException e) { throw new InvalidPositionException("La posicion no es valida");}
        return a;
    }
    
    public Position<E> left(Position<E> p){
        BTNodo<E> padre = checkPosition(p);
        if(padre.getHijoI() == null){throw new BoundaryViolationException("La Posicion no tiene hijo izquierdo");}
        return padre.getHijoI();
    }   
    public Position<E> right(Position<E> p){
        BTNodo<E> padre = checkPosition(p);
        if(padre.getHijoD() == null){throw new BoundaryViolationException("La Posicion no tiene hijo derecho");}
        return padre.getHijoD();
    }   
    public boolean hasLeft(Position<E> p){
        BTNodo<E> padre = checkPosition(p);
        return padre.getHijoI()!= null;
    }
    public boolean hasRight(Position<E> p){
        BTNodo<E> padre = checkPosition(p);
        return padre.getHijoD() != null;
    }

    //ADDS

    public Position<E> addLeft(Position<E> p, E e){
        BTNodo<E> padre = checkPosition(p);
        if(isEmpty()){throw new InvalidPositionException("Arbol vacio!!");}
        if(padre.getHijoI() != null){throw new InvalidPositionException("La posicion ya cuenta con un hijo izquierdo");}
        BTNodo<E> añadir = new BTNodo<E>(e);
        añadir.setPadre(padre);
        padre.setHijoI(añadir);
        return añadir;
    }
    
    public Position<E> addRight(Position<E> p, E e){
        BTNodo<E> padre = checkPosition(p);
        if(isEmpty()){throw new InvalidPositionException("Arbol vacio!!");}
        if(padre.getHijoD() != null){throw new InvalidPositionException("La posicion ya cuenta con un hijo derecho");}
        BTNodo<E> añadir = new BTNodo<E>(e);
        añadir.setPadre(padre);
        padre.setHijoD(añadir);
        return añadir;
    }      
    // HECHO COMO LA MIERDA EL ATTACH
    public void attach(Position<E>p , BinaryTree<E> t1, BinaryTree<E> t2){
        if(isEmpty()){throw new InvalidPositionException("El arbol receptor esta vacio");}
        BTNodo<E> nodo = checkPosition(p);
        if(!pertenecePre(nodo)){throw new InvalidPositionException("Esta posicion no pertenece al arbol");}
        nodo.setHijoD(checkPosition(t2.root()));
        nodo.setHijoI(checkPosition(t1.root()));

    }
    private boolean pertenecePre(BTNodo<E> p){
        if(p == raiz){
            return true;
        }
        if(p.getPadre() != null){
            return pertenecePre(p.getHijoD()) || pertenecePre(p.getHijoI());
        }
        return false;
        
    }

    // extendidos de Tree
    @Override
    public Iterable<Position<E>> children(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        PositionList<Position<E>> hijos = new TdaLista<Position<E>>();
        if(hasLeft(nodo)){hijos.addLast(nodo.getHijoI());}
        if(hasRight(nodo)){hijos.addLast(nodo.getHijoD());}
        return hijos;

    }
    @Override
    public Position<E> parent(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        return nodo.getPadre();
    }
    @Override
    public Position<E> root() {
        return raiz;
    }
    @Override
    public void createRoot(E e) {
        if(raiz != null){throw new InvalidOperationException("la raiz ya cuenta con una raiz");}
        BTNodo<E> nuevaraiz = new BTNodo<E>(e);
        raiz = nuevaraiz;
    }
    @Override
    public boolean isExternal(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        return nodo.getHijoD()== null && nodo.getHijoI() == null;
    }

    public boolean isInternal(Position<E> v){
        BTNodo<E> nodo = checkPosition(v);
        return nodo.getHijoD() != null || nodo.getHijoI() != null;
    }



    
    public Iterator<E> iterator() {
        PositionList<E> lista = new TdaLista<E>();
        for (Position<E> position : positions()) {
            lista.addLast(position.element());
        }
        return lista.iterator();
    } 
    
    public Iterable<Position<E>> positions() {
        // visita
        PositionList<Position<E>> pos = new TdaLista<Position<E>>();
        if(!isEmpty()){
        guardarPreOrden(pos, raiz);
    }
        return pos;
    }
    private void guardarPreOrden(PositionList<Position<E>> l, Position<E> p){
        //visita 
        l.addLast(p);
        BTNodo<E> nodo = checkPosition(p);
        if(hasLeft(p)){guardarPreOrden(l, nodo.getHijoI());}
        if(hasRight(p)){guardarPreOrden(l,nodo.getHijoD());}
        
    }
    @Override
    public E replace(Position<E> v, E e) {
        BTNodo<E> nodo = checkPosition(v);
        E elementoeliminado = nodo.element();
        nodo.setE(e);

        return elementoeliminado;

    }

    @Override
    public Position<E> addFirstChild(Position<E> p, E e) {
        return addLeft(p, e);
    }
    
    @Override
    public Position<E> addLastChild(Position<E> p, E e) {
        return addRight(p, e);
    }

    @Override
    public void removeExternalNode(Position<E> p) {
        BTNodo<E> nodo = checkPosition(p);
        if(isInternal(nodo)){throw new InvalidPositionException("no es un nodo externo");}
        BTNodo<E> padre = nodo.getPadre();
        if(padre.getHijoD()== nodo){ padre.setHijoD(null);
            cantidad--;
        }
        if(padre.getHijoI()== nodo){ padre.setHijoI(null);
            cantidad--;
        }

    }


    //CLAUSURADOS

    
    @Override
    public void removeInternalNode(Position<E> p) {
        BTNodo<E> nodo = checkPosition(p);
        if(raiz == nodo){
            if(isExternal(nodo)){throw new InvalidPositionException("la posicion es la raiz y no cuenta con ningun hijo");}
            if(hasLeft(raiz) && hasLeft(raiz)){ throw new InvalidPositionException("La posicion es la raiz y cuenta con mas de un hijo");}
            else{
                for (Position<E> h : children(raiz)) { // lo reemplaza por su unico hijo
                    raiz = checkPosition(h);
                    break;
                };
            }
        }   
        else{
            if(isInternal(p)){
            BTNodo<E> padre = checkPosition(parent(nodo));
            Iterable<Position<E>> pos = children(nodo);
            if(padre.getHijoD() == nodo){
                for(Position<E> i : pos){ // lo remeplaza por el unico hijo q tenga o en su defecto por el izquierdo si tiene los dos
                    BTNodo<E> pi = checkPosition(i);
                    padre.setHijoD(pi);
                    pi.setPadre(padre);
                    break;
                }
            }
            if(padre.getHijoI() == nodo){
                for(Position<E> i : pos){// lo remeplaza por el unico hijo q tenga o en su defecto por el izquierdo si tiene los dos
                    BTNodo<E> pi = checkPosition(i);
                    padre.setHijoI(pi);
                    pi.setPadre(padre);
                    break;
                }
                
            }
            }
            else{throw new InvalidPositionException("no es un nodo interno");}

        }
    }
    @Override
    public boolean isRoot(Position<E> v) {
        return checkPosition(v) == raiz;
    }
    public Position<E> addAfter(Position<E> pos, Position<E> lb, E element) {
        BTNodo<E> nodo = checkPosition(pos);
        BTNodo<E> nodoHijo = checkPosition(lb);
        if (nodo.getHijoI() != nodoHijo) {
            throw new InvalidPositionException("lb no es el hijo izquierdo de p.");
        }
        return addRight(pos, element);
    }

     public Position<E> addBefore(Position<E> pos, Position<E> rb, E element) {
        if (pos == null || rb == null) {
            throw new InvalidPositionException("Posición nula.");
        }
        BTNodo<E> nodo = checkPosition(pos);
        BTNodo<E> nodoHijo = checkPosition(rb);
        if (nodo.getHijoD() != nodoHijo) {
            throw new InvalidPositionException("rb no es el hijo derecho de p.");
        }
        return addLeft(pos, element);
    }

    @Override
    public void removeNode(Position<E> p) {
        if(isExternal(p)){
            removeExternalNode(p);
        }
        else{removeInternalNode(p);}
    }

    public Dictionary<E,BTNodo<E>> Ej2(){
        Dictionary<E,BTNodo<E>> toret = new TdaDiccionario<E, BTNodo<E>>();
        if(!isEmpty()){
            preOrdenEj2(toret, raiz);
        }
        return toret;
    }

    private void preOrdenEj2(Dictionary<E,BTNodo<E>> d, BTNodo<E> n){
        if(isInternal(n)){
            d.insert(n.getPadre().element(), n);
            for(Position<E> hijo : children(n)){
                preOrdenEj2(d,checkPosition(hijo));
            }
        }
    }
    
    public static Iterable<Character> Ej3(BinaryTree<Character> A){
        PositionList<Character> toret = new TdaLista<Character>();
        if(!A.isEmpty()){
            inOrdenEj3(toret, A.root(), A);
        }
        return toret;
    }

    private static void inOrdenEj3 (PositionList<Character> l, Position<Character> n, BinaryTree<Character> A){
        if(A.isInternal(n)){
            l.addLast('(');
            Position<Character> iz = A.left(n);
            inOrdenEj3(l, iz, A);
            //visita
            l.addLast(n.element());
            Position<Character> de = A.right(n);
            inOrdenEj3(l,de,A);
            l.addLast(')');
        }
        else{
            l.addLast(n.element());
        }
    }

    public void completarDerechos(E r, BinaryTree<E> t){
        if(t.isEmpty()){throw new EmptyTreeException("arbol vacio");} // O(1)
        for (Position<E> e : t.positions()) {    // O(n)
            if(t.hasLeft(e) && !t.hasRight(e)){
                t.addRight(e, r);
            }
        }
    }
}
