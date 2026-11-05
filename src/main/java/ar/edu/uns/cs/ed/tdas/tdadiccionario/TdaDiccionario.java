package main.java.ar.edu.uns.cs.ed.tdas.tdadiccionario;

import main.java.ar.edu.uns.cs.ed.tdas.Entry;
import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;

public class TdaDiccionario<K,V> implements Dictionary<K,V> {
    private int cant;
    private TdaLista<Entrada<K,V>>[] lista;

    public TdaDiccionario(){
        cant = 0;
        lista = (TdaLista<Entrada<K, V>>[])new Object[10];
    }

    private int hash(K key){
        return key.hashCode()%10;
    }

    public int size(){
        return cant;
    }

    public boolean isEmpty(){
        return cant == 0;
    }

    public Entry<K,V> find(K key){
        Entrada<K,V> entRet = null;
        for (Entrada<K,V> entr : lista[hash(key)]) {
            if (entr.getKey().equals(key)) {
                entRet = entr;
                break;
            }
        }
        return entRet;
    }

    public Iterable<Entry<K, V>> findAll(K key){
        TdaLista<Entry<K, V>> listaRet = new TdaLista<>();
        for (Entry<K,V> entr : lista[hash(key)]) {
            if (entr.getKey().equals(key)) {
                listaRet.addLast(entr);
            }
        }
        return listaRet;
    }

    public Entry<K,V> insert(K key, V value){
        Entrada<K,V> entr = new Entrada<>(key, value);
        lista[hash(key)].addLast(entr);
        cant ++;
        return entr;
    }

    public Entry<K,V> remove(Entry<K,V> e){
        Entrada<K,V> entr =(Entrada<K,V>) e;
        Entrada<K,V> toRet = null;
        for (Position<Entrada<K,V>> pos : lista[hash(entr.getKey())].positions()) {
            if (pos.element().getKey().equals(entr.getKey())) {
                toRet = entr;
                lista[hash(entr.getKey())].remove(pos);
                cant --;
                break;
            }
        }
        return toRet;
    }

    public Iterable<Entry<K, V>> entries(){
        TdaLista<Entry<K,V>> toRet = new TdaLista<>();
        for (int i = 0; i < lista.length; i++) {
            for (Entry<K,V> entr : lista[i]) {
                toRet.addLast(entr);
            }
        }
        return toRet;
    }
}
