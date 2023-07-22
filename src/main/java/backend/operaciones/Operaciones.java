package backend.operaciones;

import backend.modelos.Libros_set_get;

import java.util.LinkedList;

public abstract class Operaciones {
    abstract public LinkedList<Libros_set_get> listarLibros() throws Exception;
}
