package main.java.ar.edu.uns.cs.ed.tdas.tdamapeo;
import main.java.ar.edu.uns.cs.ed.tdas.Entry;
import main.java.ar.edu.uns.cs.ed.tdas.Position;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdadiccionario.Entrada;
public class HashMapeo<K,V> implements Map<K,V>{  
    protected static final double limite = 0.7;
    private static final int CAPACIDAD_POR_DEFECTO = 11;

    protected int cant;
    protected PositionList<Entrada<K, V>>[] lista;
    protected int buckets;

    @SuppressWarnings("unchecked")
    public HashMapeo(int capacidad) {
        cant = 0;
        buckets = capacidad;
        // 1. Creamos el arreglo como Object[] para evitar el ClassCastException global
        lista = (TdaLista<Entrada<K, V>>[]) new TdaLista[buckets];
        
        // 2. IMPORTANTE: Inicializamos cada posición con una instancia de tu lista (ej. TdaDinamica o la que usen)
        for (int i = 0; i < buckets; i++) {
            lista[i] = new TdaLista<>(); // O la implementación de lista que utilicen en la materia
        }
    }

    public HashMapeo() {
        this(CAPACIDAD_POR_DEFECTO);
    }

    private int hash(K key){
        return Math.abs(key.hashCode()%buckets);
    }

    private void reHash(){
       buckets = nuevoPrimo(buckets);
       PositionList<Entrada<K,V>>[] listaAux = (TdaLista<Entrada<K, V>>[]) new TdaLista[buckets];

       for (int i = 0; i < buckets; i++) {
        listaAux[i] = new TdaLista<>();
    } 

        for (PositionList<Entrada<K,V>> l : lista) {
            for (Entrada<K,V> entr : l) {
                listaAux[hash(entr.getKey())].addLast(entr);
            }
        }
        lista = listaAux;
    }

    private int nuevoPrimo(int n) {
    int candidato = 2 * n + 1;
    while (true) {
        boolean esPrimo = true;
        if (candidato % 2 == 0) {
            esPrimo = false;
        } else {
            for (int i = 3; i * i <= candidato; i += 2) {
                if (candidato % i == 0) {
                    esPrimo = false;
                    break;
                }
            }
        }
        if (esPrimo) {
            return candidato;
        }
        candidato++;
    }
}


    public int size(){
        return cant;
    }

    public boolean isEmpty(){
        return cant == 0;
    }

    public V put(K key, V value){
    if (key == null) throw new IllegalArgumentException("La clave no puede ser nula");

    int h = hash(key);
    for (Entrada<K,V> entr : lista[h]) {
        if (entr.getKey().equals(key)) { 
            V toRet = entr.getValue();
            entr.setValue(value);
            return toRet;
        }
    }
    
    if ((double) (cant + 1) / buckets > limite) {
        reHash();
        h = hash(key);
    }
    
    lista[h].addLast(new Entrada<K,V>(key, value));
    cant++;
    return null;
}

    public V get(K key){
        V toRet = null;
        for (Entrada<K, V> entr : lista[hash(key)]) {
            if (entr.getKey().equals(key)) {
                toRet = entr.getValue();
                break;
            }
        }
        return toRet;
    }

    public V remove(K key){
        V toRet = null;
        for (Position<Entrada<K, V>> entr : lista[hash(key)].positions()) {
            if (entr.element().getKey().equals(key)) {
                toRet = entr.element().getValue();
                lista[hash(key)].remove(entr);
                cant --;
                break;
            }
        }
        return toRet;
    }

    public Iterable<K> keys(){
        PositionList<K> listaRet = new TdaLista<>();
        for (int i = 0; i < lista.length; i++) {
            for (Entrada<K, V> entr : lista[i]) {
                listaRet.addLast(entr.getKey());
            }
        }
        return listaRet;
    }

    public  Iterable<V> values(){
        PositionList<V> listaRet = new TdaLista<>();
        for (int i = 0; i < lista.length; i++) {
            for (Entrada<K, V> entr : lista[i]) {
                listaRet.addLast(entr.getValue());
            }
        }
        return listaRet;
    }

    public Iterable<Entry<K, V>> entries(){
        PositionList<Entry<K, V>> listRet = new TdaLista<>();
        for (int i = 0; i < lista.length; i++) {
            for (Entry<K,V> entry : lista[i]) {
                listRet.addLast(entry);
            }
        }
        return listRet;
    }

}
