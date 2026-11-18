package main.java.ar.edu.uns.cs.ed.tdas.tdadiccionario;

import main.java.ar.edu.uns.cs.ed.tdas.Entry;
import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.TdaLista;

public class TdaDiccionario<K, V> implements Dictionary<K, V> {
    private int cant;
    private PositionList<Entrada<K, V>>[] lista;

    @SuppressWarnings("unchecked")
    public TdaDiccionario() {
        cant = 0;
        lista = (PositionList<Entrada<K, V>>[]) new PositionList[10];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = new TdaLista<>();
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % lista.length);
    }

    public int size() {
        return cant;
    }

    public boolean isEmpty() {
        return cant == 0;
    }

    public Entry<K, V> find(K key) {
        Entrada<K, V> entRet = null;
        for (Entrada<K, V> entr : lista[hash(key)]) {
            if (entr.getKey().equals(key)) {
                entRet = entr;
                break;
            }
        }
        return entRet;
    }

    public Iterable<Entry<K, V>> findAll(K key) {
        PositionList<Entry<K, V>> listaRet = new TdaLista<>();
        for (Entrada<K, V> entr : lista[hash(key)]) {
            if (entr.getKey().equals(key)) {
                listaRet.addLast(entr);
            }
        }
        return listaRet;
    }

    public Entry<K, V> insert(K key, V value) {
        Entrada<K, V> entr = new Entrada<>(key, value);
        lista[hash(key)].addLast(entr);
        cant++;
        return entr;
    }

    public Entry<K, V> remove(Entry<K, V> e) {
        if (e == null) return null;
        Entrada<K, V> entr = (Entrada<K, V>) e;
        Entrada<K, V> toRet = null;
        
        int h = hash(entr.getKey());
        for (Position<Entrada<K, V>> pos : lista[h].positions()) {
            if (pos.element().equals(entr)) {
                toRet = pos.element();
                lista[h].remove(pos);
                cant--;
                break;
            }
        }
        return toRet;
    }

    public Iterable<Entry<K, V>> entries() {
        PositionList<Entry<K, V>> toRet = new TdaLista<>();
        for (int i = 0; i < lista.length; i++) {
            for (Entry<K, V> entr : lista[i]) {
                toRet.addLast(entr);
            }
        }
        return toRet;
    }
}