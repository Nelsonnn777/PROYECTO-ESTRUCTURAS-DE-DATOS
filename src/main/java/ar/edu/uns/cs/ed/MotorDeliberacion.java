package main.java.ar.edu.uns.cs.ed;
import main.java.ar.edu.uns.cs.ed.tdas.Entry;
import main.java.ar.edu.uns.cs.ed.tdas.excepciones.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdaarbol.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdalista.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdamapeo.*;
import main.java.ar.edu.uns.cs.ed.tdas.tdapila.*;
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
    public String deshacerUltimaOperacion() throws EmptyStackException {
        //TODO: Implementar la funcionalidad de deshacer la última operación registrada.
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
    }

    //limpia todo el debate, dejando la estructura vacía.   
    public void clearDebate() {
    //TODO: Implementar la funcionalidad de limpiar todo el debate, dejando la estructura vacía.
    }

    //consultar por usuario, devuelve una lista de argumentos que pertenecen a ese usuario.
    public PositionList<Opinion> consultarArgumentosPorUsuario(String idUsuario) {
        PositionList<Opinion> toret = new TdaLista<Opinion>();
        for(Entry<String,Position<Opinion>> e : opinionesUsuario.findAll(idUsuario)){
            toret.addLast(e.getValue().element());
        }
        return toret;
    //TODO: Implementar la funcionalidad de consultar los argumentos de un usuario específico en el debate.
    }

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


