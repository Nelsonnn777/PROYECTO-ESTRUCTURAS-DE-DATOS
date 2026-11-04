package main.java.ar.edu.uns.cs.ed.tdas;

public class Entrada<K,V> implements Entry<K,V> {
private K clave;
private V valor;
// Constructor
public Entrada(K c, V v) { this.clave = c; this.valor = v; }
public K getKey() {
    return clave;
} // Getters
public V getValue() {
    return valor;
}
public void setValue(V v) {
    valor = v;
}
public String toString( ) { 
return "(" + getKey() + "," + getValue() + ")" ; }
}