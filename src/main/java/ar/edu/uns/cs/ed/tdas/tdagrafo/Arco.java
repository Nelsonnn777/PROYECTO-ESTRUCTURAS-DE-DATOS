package main.java.ar.edu.uns.cs.ed.tdas.tdagrafo;

public class Arco<V,E> implements Edge<E> {
    private Vertice<V,E> vert1;
    private Vertice<V,E> vert2;
    private E elem;

    public Arco(Vertice<V,E> v1, Vertice<V,E> v2, E e){
        elem  = e;
        vert1 = v1;
        vert2 = v2;
    }

    public E element() {return elem;}
    public Vertice<V,E> getVert1() { return vert1;}
    public Vertice<V,E> getVert2() { return vert2;}

    public void setElem(E elem) { this.elem = elem;}
    public void setVert1(Vertice<V,E> vert1) { this.vert1 = vert1;}
    public void setVert2(Vertice<V,E> vert2) { this.vert2 = vert2;}
}
