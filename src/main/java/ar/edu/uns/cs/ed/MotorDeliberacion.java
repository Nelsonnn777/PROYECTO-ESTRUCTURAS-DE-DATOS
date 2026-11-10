package main.java.ar.edu.uns.cs.ed;
import main.java.ar.edu.uns.cs.ed.tdas.Entry;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdaarbol.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdamapeo.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdapila.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdadiccionario.*;

import java.util.Stack;


import main.java.ar.edu.uns.cs.ed.tdas.Position;


/**
 * Incluir los import necesarios para las clases utilizadas en el código.
 */

/**
 * Clase que representa el motor de deliveración.
 * 
 */
public class MotorDeliberacion {

    /**
     * Incluir los atributos necesarios para el desarrollo.
     */
        private Tree<Opinion> arbol;
        private Map<String, PositionList<Opinion>> opinionesUsuario;
        private Stack<Operaciones> historial;
        // Guarda el nodo del arbol 
        private Map<String, Position<Opinion>> opiniones; 

        // POSIBLE SOLUCIÓN PARA EL MÉTODO ELIMINARPOSTEO: SE VA A AGREGAR UN MAP<STRING,POSITION<OPINION>> EL POSITION<OPINION> ES LA POSICIÓN DE LA OPINIÓN DENTRO DE LA LISTA DE OPINIONES DEL USUARIO
        private Map<String, Position<Opinion>> posOpUsuario;
    /**
     * Constructor de la clase MotorDeliveracion.
     * Inicializa el árbol de debate, y las estructuras correspondientes.  
     * 
     */
    public MotorDeliberacion() {
    //TODO: Inicializar el árbol de debate y las estructuras correspondientes. 
        arbol = new TdaArbol<>();
        opinionesUsuario = new HashMapeo<>();
        historial = new Stack<> ();
        opiniones = new HashMapeo<>();
        // POSIBLE SOLUCIÓN PARA ELIMINARPOSTEO
        posOpUsuario = new HashMapeo<>();
    }

    /**
     * Registra una operación en la estructura que almacena las operaciones.
     * Las siguientes operaciones se pueden deshacer: "CREAR_DEBATE", "POSTEAR". El resto de las operaciones no se pueden deshacer.
     * 
     * La operación eliminar posteo no se puede deshacer, y si se intenta deshacer una operación cuando no hay operaciones registradas, se lanza una excepción.
     * 
     * 
     * @param operacion : La operación a registrar. 1: CREAR_DEBATE, 2: POSTEAR
     * @param detalle : Detalle de la operación, como el id del argumento o el id del usuario. El texto de la operación se almacena en mayúsculas para mantener consistencia. Por ejemplo: "CREAR_DEBATE:" + idRaiz.trim()
     * @param idArgumento : Identificador del argumento o de root en caso de ser el tema raíz.
     * @throws IllegalArgumentException si la operación es nula o vacía.
     */
    public void registrarOperacion(int operacion, String detalle, String idArgumento) {
        //TODO: Implementar el registro de la operación en la estructura correspondiente.
            if (operacion < 1 && operacion > 2){throw new IllegalArgumentException("la operacion es nula");}
            Operaciones op = new Operaciones(operacion, detalle, idArgumento);
            historial.push(op);
    }

/**
 * Deshace la última operación registrada en el motor de deliveración.
 * La operación eliminar posteo no se puede deshacer, y si se intenta deshacer una operación cuando no hay operaciones registradas, se lanza una excepción.
 * @return 
 * @throws EmptyStackException
 */
//RETURN INDEFINIDO. HAY QUE PREGUNTAR QUÉ RETORNA EL MÉTODO
    public String deshacerUltimaOperacion() throws EmptyStackException {
        //TODO: Implementar la funcionalidad de deshacer la última operación registrada.
        if (historial.isEmpty()) {throw new EmptyStackException("No se realizaron operaciones previamente.");}
        Operaciones ultOperacion;
        Stack<Operaciones>  pilaAux = new Stack<>();
        ultOperacion = historial.pop();
        if (ultOperacion.getOperacion() != 1 || ultOperacion.getOperacion() != 2)
            while (!historial.isEmpty()){
                ultOperacion = historial.pop();
                if (ultOperacion.getOperacion() == 1 || ultOperacion.getOperacion() == 2)
                    break;
                pilaAux.push(ultOperacion);
            }
        if (ultOperacion.getOperacion() == 1){
            while (!historial.isEmpty()) {
                historial.pop();
            }
            opinionesUsuario.remove(arbol.root().element().getUsuario().getId());              
            opiniones.remove(arbol.root().element().getIdArgumento());
            posOpUsuario.remove(arbol.root().element().getIdArgumento());
            arbol.removeNode(arbol.root());
        }  else {
            Position<Opinion> opinion = opiniones.get(ultOperacion.getDetalle());
            opiniones.remove(opinion.element().getIdArgumento());
            opinionesUsuario.get(opinion.element().getUsuario().getId()).remove(posOpUsuario.get(opinion.element().getIdArgumento()));
            arbol.removeExternalNode(opinion);
        }
    }
/**
 * Crea un nuevo debate con un argumento raíz.
 * @param idRaiz   : Identificador único del tema raíz.
 * @param texto:    Texto del tema raíz.
 * @param idUsuario: Identificador del usuario.
 * @param nombreUsuario: Nombre del usuario.
 * 
 * El método valida que los parámetros no sean nulos o vacíos, y que el identificador de la raíz sea numérico.
 * Si el árbol ya tiene un debate, lanza una excepción.
 * El método registra la operación en la pila de operaciones para permitir deshacerla posteriormente.
 * @throws IllegalArgumentException si algún parámetro es inválido.
 * @throws IllegalStateException si ya existe un debate en curso.
 * Crea un usuario y un tema raíz, y los agrega al árbol y a las estructuras correspondientes.
 */
    public void crearDebate(String idRaiz, String texto, String idUsuario, String nombreUsuario) {
    //TODO: Implementar la creación del debate con el argumento raíz y el usuario correspondiente.
        if(arbol.root() != null){throw new IllegalStateException("Ya hay un debate en curso");}

        Usuario user = new Usuario(idUsuario, nombreUsuario);
        Opinion root = new Opinion( idRaiz, texto, "ROOT", user );
        arbol.createRoot(root);
        // guardo el Position<Opinion> para guardarlo en opinionesUsuarios y en Opiniones
        Position<Opinion> r = arbol.root();
        opinionesUsuario.put(idUsuario, new TdaLista<>());
        opinionesUsuario.get(idUsuario).addLast(root);
        opiniones.put(idRaiz, r);
        //POSIBLE SOLUCION PAR ELIMINARPOSTEO
        posOpUsuario.put(idRaiz, opinionesUsuario.get(idUsuario).last());
        //guardo en el historial la Operacion 
        registrarOperacion(1, "CREAR_DEBTATE" + idRaiz.trim(), idRaiz);
    
    }

    public void Postear(Usuario usr, String idNuevo, String idPadre, String postura, String texto) {
        //TODO: Implementar la funcionalidad de postear un nuevo argumento en el debate.
            Opinion post = new Opinion(idNuevo, texto, postura, usr);
            Position<Opinion> padre = opiniones.get(idPadre);
            Position<Opinion> pos = arbol.addLastChild(padre, post);
            opinionesUsuario.get(usr.getId()).addLast(post);
            opiniones.put(idNuevo, pos);
            // POSIBLE OPERACIÓN PARA ELIMINARPOSTEO
            posOpUsuario.put(idNuevo, opinionesUsuario.get(usr.getId()).last());
            registrarOperacion(2, postura + idNuevo.trim(), idNuevo);
    }
    

    // Elimina un posteo y todo el subárbol.
    public void eliminarPosteo(String idUsuario, String idPosteo) {
    //TODO: Implementar la funcionalidad de eliminar un posteo y todo su subárbol en el debate.
        Position<Opinion> nodo = opiniones.get(idPosteo);
        eliminarArbolInterno(nodo);
        
    }
    private void eliminarArbolInterno(Position<Opinion> pos){
        for (Position<Opinion> posHijo : arbol.children(pos)){
            eliminarArbolInterno(posHijo);
        }
       //CONSULTA: COMO REMOVER DE LA LISTA DE NODOS EN TIEMPO O(1)
        opinionesUsuario.get(pos.element().getUsuario().getId()).remove(posOpUsuario.get(pos.element().getIdArgumento()));
        posOpUsuario.remove(pos.element().getIdArgumento());
        
        arbol.removeExternalNode(pos);
        opiniones.remove(pos.element().getIdArgumento());
    }

    //obtiene las posiciones en postorden.
    private void obtenerIdsYPosicionesSubarbol(Position<Opinion> nodo, PositionList<String> ids, PositionList<Position<Opinion>> posiciones) {
    //TODO: Implementar la funcionalidad de obtener los ids y posiciones de un subárbol en postorden.
    for (Position<Opinion> pos : arbol.children(nodo)) {
        obtenerIdsYPosicionesSubarbol(pos, ids, posiciones);
    }
    ids.addLast(nodo.element().getIdArgumento());
    posiciones.addLast(nodo);
    }

    //limpia todo el debate, dejando la estructura vacía.   
    public void clearDebate() {
    //TODO: Implementar la funcionalidad de limpiar todo el debate, dejando la estructura vacía.
    eliminarArbolInterno(arbol.root());
    while (!historial.isEmpty())
        historial.pop();

    }

    //consultar por usuario, devuelve una lista de argumentos que pertenecen a ese usuario.
    public PositionList<Opinion> consultarArgumentosPorUsuario(String idUsuario) {
        PositionList<Opinion> toret= opinionesUsuario.get(idUsuario);
        return toret;
    //TODO: Implementar la funcionalidad de consultar los argumentos de un usuario específico en el debate.
    }

        /**
     * Calcula la polarización de todos los nodos del debate.
     * La polarización se define como la cantidad de argumentos a favor y en contra que existen en el subárbol que tiene como raíz cada nodo.
     * @throws IllegalStateException si no hay un debate activo.
     * @return Un diccionario cuya clave es el ID del nodo y el valor es un diccionario con la polarización: 
     * "AFAVOR" y "ENCONTRA" y sus respectivos conteos.
     */
    public Map<String,Map<String,Integer>> calcularPolarizacionDebate(){
        //TODO
        HashMapeo<String,Map<String,Integer>> toret = new HashMapeo<String, Map<String, Integer>>();
        calPolDerec(arbol.root(), toret);
        return toret;
    }

    private void calPolDerec(Position<Opinion> nodo, Map<String,Map<String,Integer>> map){
        String idar = nodo.element().getIdArgumento();
        map.put(idar, calcularPolarizacionNodo(idar));
        if(arbol.isInternal(nodo)){            
            for(Position<Opinion> h : arbol.children(nodo)){
                calPolDerec(h, map);
            }
        }
     }

     /**
    * Calcula la polarización de un nodo específico en el árbol de debate.
    * La polarización se define como la cantidad de argumentos a favor y en contra que existen en el subárbol que tiene como raíz el nodo especificado por idNodo.
    * @throws IllegalStateException si no hay un debate activo. 
    * @param idNodo : Identificador del nodo para el cual se desea calcular la polarización.
    * @return Un DiccionarioHashAbierto<String,Integer> que contiene la polarización del nodo, con las claves "AFAVOR" y "ENCONTRA" y sus respectivos conteos.
    */
    public Map<String,Integer> calcularPolarizacionNodo(String idNodo){
        //TODO
        if(arbol == null){throw new IllegalStateException("no hay debate en curso");}
        Opinion op = opiniones.get(idNodo).element();
        HashMapeo<String,Integer> toret = new HashMapeo<String, Integer>();
        toret.put("A FAVOR", 0);
        toret.put("EN CONTRA", 0);
        recorridoCalcPolNodo(idNodo, op.getPostura(), toret);
        // recorridoCalcPolNodo(idNodo, toret); SI ES Q NO RESPETA EL CONTEXTO
        return toret;
    }
    private void recorridoCalcPolNodo(String idNodo, String postura ,HashMapeo<String,Integer> map){
        Position<Opinion> posop = opiniones.get(idNodo);
        Opinion op = posop.element();
        int c = map.get(op.getPostura()) +1;
        map.put(op.getPostura(),  c);
        if(arbol.isInternal(posop)){
            for(Position<Opinion> p : arbol.children(posop)){
                recorridoCalcPolNodo(p.element().getIdArgumento(), postura, map);
            }
        }
         
    }
    // SI ES Q NO RE SPETA EL CONTEXTO DE CADA NODO
    // private void recorridoCalcPolNodo(String idNodo, HashMapeo<String,Integer> map){
    //     Position<Opinion> posop = opiniones.get(idNodo);
    //     Opinion op = posop.element();
    //     int c = map.get(op.getPostura()) +1;
    //     map.put(op.getPostura(),  c);
    //     if(arbol.isInternal(posop)){
    //         for(Position<Opinion> p : arbol.children(posop)){
    //             recorridoCalcPolNodo(p.element().getIdArgumento(), map);
    //         }
    //     }
         
    // }

 /*** Mostrar árbol por consola ***/
 
    /**
     * Imprime por consola, en modo texto, el contenido del árbol de debate,
     * con indentación jerárquica que muestra la estructura padre-hijo.
     * Cada nodo se muestra con su id, postura, usuario y texto del argumento.
     */
    public void imprimirDebate() {
        if (arbol == null || arbol.isEmpty()) {
            System.out.println("(El árbol de debate está vacío.)");
            return;
        }

        try {
            Position<Opinion> raiz = arbol.root();
            System.out.println(formatearNodo(raiz.element()));
            imprimirHijos(raiz, "");
        } catch (Exception e) {
            System.out.println("No se pudo imprimir el árbol: " + e.getMessage());
        }
    }

    /**
     * Imprime recursivamente los hijos de 'nodo', usando 'prefijo' para
     * mantener la indentación acumulada de los niveles superiores y
     * conectores tipo "├──" / "└──" para el nivel actual.
     */
    private void imprimirHijos(Position<Opinion> nodo, String prefijo) throws InvalidPositionException {
        PositionList<Position<Opinion>> hijos = new TdaLista<Position<Opinion>>();
        for (Position<Opinion> hijo : arbol.children(nodo)) {
            hijos.addLast(hijo);
        }


        for (Position<Opinion> hijo : arbol.children(nodo)) {
            boolean esUltimo = (hijo == hijos.last().element());
            
            String conector = esUltimo ? "└── " : "├── ";
            System.out.println(prefijo + conector + formatearNodo(hijo.element()));

            String prefijoHijo = prefijo + (esUltimo ? "    " : "│   ");
            imprimirHijos(hijo, prefijoHijo);
            
        }
    }

/** Arma la línea de texto que representa a un Argumento en la impresión. */
    private String formatearNodo(Opinion a) {
        if (a == null) {
            return "(argumento nulo)";
        }
        return "[" + a.getIdArgumento() + "] (" + a.getPostura() + ") "
                + "usuario=" + a.getUsuario().getId() + " -> " + a.getTexto();
    }
}


