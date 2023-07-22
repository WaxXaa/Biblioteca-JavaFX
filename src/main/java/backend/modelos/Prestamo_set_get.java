package backend.modelos;

import java.sql.Date;

public final class Prestamo_set_get {

    private Date fecha_prestamo;
    private Date fecha_devolucion;
    private Usuario_set_get usuario;
    private Libros_set_get libro;
    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_devolucion() {
        return fecha_prestamo;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public Usuario_set_get getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_set_get usuario) {
        this.usuario = usuario;
    }

    public Libros_set_get getLibro() {
        return libro;
    }

    public void setLibro(Libros_set_get libro) {
        this.libro = libro;
    }
}
