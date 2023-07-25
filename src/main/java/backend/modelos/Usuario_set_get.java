package backend.modelos;
/*
 * esta es la clase modelo de la tabla Usuario para almacenar en los objeos que se instancian de ella los datos de un Usuario
 *
 * */
public final class Usuario_set_get {
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;
    private int id_usuario;
    private int num_prestamos;
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getNum_prestamos() {
        return num_prestamos;
    }

    public void setNum_prestamos(int num_prestamos) {
        this.num_prestamos = num_prestamos;
    }
}
