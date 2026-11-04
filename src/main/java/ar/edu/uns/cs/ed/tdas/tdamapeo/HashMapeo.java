package main.java.ar.edu.uns.cs.ed.tdas.tdamapeo;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;

import main.java.ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import java.util.Iterator;

import main.java.ar.edu.uns.cs.ed.tdas.Entrada;
import main.java.ar.edu.uns.cs.ed.tdas.Entry;
public class HashMapeo<K,V> implements Map<K,V> {
    private int N;
    private PositionList<Entrada<K,V>> bucket[];
    private int cant;

    @SuppressWarnings("unchecked")
    public HashMapeo(){
        N = 10;
        bucket = (PositionList<Entrada<K, V>>[]) new PositionList[N];
        cant = 0;

        for(int i = 0; i < N; i++){
            bucket[i] = new TdaLista<>();
        }
    }
    public int size(){
        return cant;
    }
    public boolean isEmpty(){
        return cant == 0;
    }
    private int Hash(K key){
        return key.hashCode()%N;
    }
    public V get(K key) {
        
        if(key== null){ throw new InvalidKeyException("llave invalida");}
        int hashkey = Hash(key);
        Iterator<Entrada<K,V>> it = bucket[hashkey].iterator();
        V valor = null;
        boolean enc = false;
        while(it.hasNext() ||(it.hasNext() && !enc)){
            Entry<K,V> en = it.next();
            enc = en.getKey().equals(key);
            if(enc){
                valor = en.getValue();
            }
        }
        return valor;

    }
    public V put(K key, V valor) throws InvalidKeyException{
        if(key== null){ throw new InvalidKeyException("llave invalida");}
        int hashkey = Hash(key);
        V val = null;
        boolean enc = false;
        for (Position<Entrada<K,V>> en : bucket[hashkey].positions()) {
            if(en.element().getKey().equals(key)){
                val = en.element().getValue();
                en.element().setValue(valor);
                enc = true;
                break;
            }
        }
        if(!enc){
        Entrada<K,V> nuevo = new Entrada<K,V>(key,valor);
            bucket[hashkey].addLast(nuevo);
            cant++;
        }
        return val;
    }
    
    public Iterable<V> values(){
        PositionList<V> valores= new TdaLista<V>();
        for (PositionList<Entrada<K,V>> positionList : bucket) {
            for (Entrada<K,V> entrada : positionList) {
                valores.addLast(entrada.getValue());
            }
        }
        return valores;
    }
    
    public Iterable<K> keys(){
        PositionList<K> llaves= new TdaLista<K>();
        for (PositionList<Entrada<K,V>> positionList : bucket) {
            for (Entrada<K,V> entrada : positionList) {
                llaves.addLast(entrada.getKey());
            }
        }
        return llaves;
    }
    
    public Iterable<Entry<K,V>> entries(){
        PositionList<Entry<K,V>> entradas= new TdaLista<Entry<K,V>>();
        for (PositionList<Entrada<K,V>> positionList : bucket) {
            for (Entrada<K,V> entrada : positionList) {
                entradas.addLast(entrada);
            }
        }
        return entradas;
    }
    public V remove(K key){
        if(key== null){ throw new InvalidKeyException("llave invalida");}
        int hashkey = Hash(key);
        V valor = null;
        for (Position<Entrada<K,V>> entrada : bucket[hashkey].positions()) {
            if(entrada.element().getKey().equals(key)){
                valor = entrada.element().getValue();
                bucket[hashkey].remove(entrada);
                cant--;
                break;
            }
        }
        return valor;
    }
}
