package main.java.ar.edu.uns.cs.ed.tdas.tdalista;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

import org.w3c.dom.traversal.NodeIterator;
import java.util.Iterator;

import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.Dnodo;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;

public class TdaLista<E> implements PositionList<E> {
    protected int cantel;
    protected Dnodo<E> cabecera;
    protected Dnodo<E> cola;

    public TdaLista(){
        cantel = 0;
        cabecera = new Dnodo<E>(null);
        cola = new Dnodo<E>(null);
        
        cabecera.setSiguiente(cola);
        cola.setAnterior(cabecera);
    }
    @Override 
    public boolean isEmpty(){
        return cantel == 0;
    }
    @Override 
    public int size(){
        return cantel;
    }
    @SuppressWarnings("unchecked")
    public Position<E> first(){
        if(!isEmpty()){ 
            return cabecera.getSiguiente();

        }
        else throw new EmptyListException("Lista Vacia!!!");
    }

    @SuppressWarnings("unchecked") 
    public Position<E> last(){
        if (!isEmpty()){
            return cola.getAnterior();
        }
        else throw new EmptyListException("Lista Vacia!1!");
    }

    protected Dnodo<E> checkPosition(Position<E> p){
        if(isEmpty()){
            throw new InvalidPositionException("Lista vacia!!");
        }
        if (p==null){
            throw new InvalidPositionException("Posicion no existente");
        }
        Dnodo<E> nodo;
        try {nodo =(Dnodo<E>) p;}
        catch (ClassCastException e){
            throw new InvalidPositionException("no es un nodo");
        }
        if(nodo== cola || nodo == cabecera){
            throw new BoundaryViolationException("la posicion es la cabezera o la cola");
        }
        return nodo;

    }

    @SuppressWarnings("unchecked")
    public Position<E> prev(Position<E> p) throws InvalidPositionException, EmptyListException{
        Dnodo<E> nodoc = checkPosition(p);
        if(nodoc.getAnterior() == cabecera) throw new BoundaryViolationException("la anterior es la cabecera");
        return nodoc.getAnterior();
    }

    @SuppressWarnings("unchecked")
    public Position<E> next(Position<E> p) throws InvalidPositionException, EmptyListException, BoundaryViolationException{
        Dnodo<E> nodoc = checkPosition(p);
        if(nodoc.getSiguiente() == cola) throw new BoundaryViolationException("el siguiente es la cola");
        return nodoc.getSiguiente();
    }

    @SuppressWarnings("unchecked")
    public void addFirst(E p) throws InvalidPositionException{
        Dnodo<E> nodoc = new Dnodo<E>(p);
        Dnodo<E> sigactual = cabecera.getSiguiente();
        cabecera.setSiguiente(nodoc);
        nodoc.setSiguiente(sigactual);
        nodoc.setAnterior(cabecera);
        sigactual.setAnterior(nodoc);
        cantel++;
    }

    @SuppressWarnings("unchecked")
    public void addLast(E p) throws InvalidPositionException{
        Dnodo<E> nodoc = new Dnodo<E>(p);
        Dnodo<E> antactual = cola.getAnterior();
        cola.setAnterior(nodoc);
        nodoc.setAnterior(antactual);
        nodoc.setSiguiente(cola);
        antactual.setSiguiente(nodoc);
        cantel++;
    }
    
    @SuppressWarnings("unchecked")
    public void addBefore(Position<E> p, E elemento){
        Dnodo<E> nodoC = checkPosition(p);
        Dnodo<E> nodoA = new Dnodo<E>(elemento);
        
        Dnodo<E> nodoanterior = nodoC.getAnterior();
        nodoanterior.setSiguiente(nodoA);
        nodoC.setAnterior(nodoA);
        nodoA.setSiguiente(nodoC);
        nodoA.setAnterior(nodoanterior);

        cantel++;

    }

    @SuppressWarnings("unchecked")
    public void addAfter(Position<E> p, E elemento) throws InvalidPositionException, EmptyListException{
        Dnodo<E> nodoC = checkPosition(p);
        Dnodo<E> nodoA = new Dnodo<E>(elemento);
        Dnodo<E> sigactual = nodoC.getSiguiente();

        sigactual.setAnterior(nodoA);
        nodoC.setSiguiente(nodoA);
        nodoA.setAnterior(nodoC);
        nodoA.setSiguiente(sigactual);
        cantel++;
    }

    @SuppressWarnings("unchecked")
    public E remove(Position<E> p) throws InvalidPositionException, EmptyListException{
        Dnodo<E> nodo = checkPosition(p);
        Dnodo<E> nodoanterior = nodo.getAnterior();
        Dnodo<E> nodosiguiente = nodo.getSiguiente();
        nodoanterior.setSiguiente(nodosiguiente);
        nodosiguiente.setAnterior(nodoanterior);
        cantel--;
        return nodo.element();
    }

    public E set(Position<E> p,E el){
        Dnodo<E> nodoeliminado = checkPosition(p);
        E eliminado = nodoeliminado.element();
        nodoeliminado.setElem(el);
        return eliminado;
    }
    public Iterator<E> iterator(){
        return new IteradorPL<E>(this);
    }
    @SuppressWarnings("unchecked")
    public Iterable<Position<E>> positions(){
        PositionList<Position<E>> pl = new TdaLista<Position<E>>();
        if(cantel!=0){
            Dnodo<E> n = cabecera.getSiguiente();
            while(n!= cola){
                pl.addLast(n);
                n=n.getSiguiente();
            }
        }
        return pl;
    }

    
    // EJERCICIO 2
    @SuppressWarnings("unchecked")
    public void ej2(E e1, E e2){
        if(size() > 0){
            Dnodo<E> primer = cabecera.getSiguiente();
            addAfter(primer, e1);
            
            Dnodo<E> ultimo = cola.getAnterior();
            addBefore(ultimo, e2);

        }
        else throw new EmptyListException("cantidad de elementos insuficientes");

    }

    public TdaLista<E> dividirlista(TdaLista<E> l, Position<E> p){
        Position<E> paux = p;
        Position<E> eliminar;
        TdaLista<E> toret = new TdaLista<>();
        while(l.next(paux) != cola){
            eliminar = paux;
            toret.addLast(paux.element());
            paux = l.next(paux);
            l.remove(eliminar);

        }
        return toret;
    }

    public int superEliminar(E elem) throws EmptyListException{
        int cont = 0;
        Position<E> cursor = cabecera.getSiguiente();
        if(cursor == cola) throw new EmptyListException("lista vacia");
        while (cursor != cola){
            if (cursor.element().equals(elem)){
                Position<E> borrar = cursor;
                cursor = this.next(cursor);
                this.remove(borrar);
                cont++;
            }
            else cursor = this.next(cursor);

        }
        return cont;
    }
    }



    



