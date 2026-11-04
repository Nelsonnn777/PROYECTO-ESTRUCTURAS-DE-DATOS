package main.java.ar.edu.uns.cs.ed.tdas.tdalista;

import java.security.DomainLoadStoreParameter;
import java.util.Iterator;
import java.util.NoSuchElementException;

import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.*;

public class IteradorPL<E> implements Iterator<E> {
        
    protected PositionList<E> list;
    protected Position<E> cursor;

    public IteradorPL(PositionList<E> l){
        list = l;
        if(list.isEmpty()){
            cursor = null;
        }
        else{
            try{
                cursor = list.first();
            } catch (EmptyListException e){
                e.printStackTrace();
            } 
        }

    }

    public boolean hasNext(){
        return cursor !=null;
    }

    public E next() throws NoSuchElementException{
        if (cursor == null){
            throw new NoSuchElementException("No existe un siguiente elemento.");
        }
         E toReturn = cursor.element();
         try{
             cursor =(cursor== list.last()) ? null : list.next(cursor);
         } catch (InvalidPositionException | BoundaryViolationException| EmptyListException e){
            e.printStackTrace();
        }
        return toReturn;
    }
    public boolean ej3a(E e1){
        boolean esta = false;
            while(this.hasNext() && !esta){
                esta = this.next().equals(e1);
            }
            return esta;
    }
    
    public int ej3b(E e1){
        int cant = 0;
            while(this.hasNext()){
                if(this.next().equals(e1)){
                    cant++;
                }
            }
            return cant;
    }
    public boolean ej3c(E e1, int n){
        int cant = 0;
            while(this.hasNext()){
                if(this.next().equals(e1) && n >= cant){
                    cant++;
                }
            }
            return cant == n;
    }
    public PositionList<E> ej4(PositionList<E> pl){
        PositionList<E> listaret = new TdaLista<E>();
        for(E elemento : pl){
            listaret.addLast(elemento);
            listaret.addLast(elemento);
        }
        return listaret;
    }
    
}




