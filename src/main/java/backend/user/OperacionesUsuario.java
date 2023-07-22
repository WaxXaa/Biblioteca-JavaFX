package backend.user;

import backend.modelos.Libros_set_get;
import backend.modelos.Usuario_set_get;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import backend.conexionsql.Conexion;
import backend.operaciones.Operaciones;

public class OperacionesUsuario extends Operaciones{

    private Statement stmt; // objeto que permite la manipulaciòn de sentencias
    private ResultSet recordSet;

    Usuario_set_get datosUsuario = new Usuario_set_get();

    public void setDatosUsuario(Usuario_set_get datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    public Usuario_set_get verificarUsuario(String nom, String correo) throws Exception {
        // el metodo verificarUsuario comprueba la existencia del usuario, si existe
        // retorna un objeto tipo Usiario_set_get con los datos del usuario
        String cadSql;
        Connection cnn = null;
        Usuario_set_get usuario = new Usuario_set_get();
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            cadSql = "SELECT * FROM Usuario WHERE correo = '" + correo + "' and nombre = '" + nom + "'";
            recordSet = stmt.executeQuery(cadSql);
            if (recordSet.next()) {
                usuario.setNombre(recordSet.getString("nombre"));
                usuario.setApellido(recordSet.getString("apellido"));
                usuario.setDireccion(recordSet.getString("direccion"));
                usuario.setTelefono(recordSet.getString("telefono"));
                usuario.setCorreo(recordSet.getString("correo"));
                usuario.setId_usuario(recordSet.getInt("Id_usuario"));
                return usuario;
            }
            return null;
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (cnn != null)
                cnn.close();
        }

    }

    public boolean verificarRegistro(String correo) throws Exception {
        String cadSqlCorreo;
        Connection cnn = null;
        boolean existeRegistro;
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            cadSqlCorreo = "SELECT * FROM Usuario WHERE correo = '" + correo + "'";
            recordSet = stmt.executeQuery(cadSqlCorreo);
            existeRegistro = recordSet.next();
            return existeRegistro;
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (cnn != null)
                cnn.close();
        }
    }

    public void insertarUsuario() throws Exception {
        String cadSqlInsertar;
        Connection cnn = null;
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            cadSqlInsertar = "insert into Usuario (nombre, apellido, direccion, telefono, correo ) Values ('" + datosUsuario.getNombre() + "','" + datosUsuario.getApellido() + "','" + datosUsuario.getDireccion() + "','" + datosUsuario.getTelefono() + "','" + datosUsuario.getCorreo() + "')";
            stmt.executeUpdate(cadSqlInsertar);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (cnn != null)
                cnn.close();
        }
    }

    //Listado de libros 
    public LinkedList<Libros_set_get> listarLibros(String opcion, String texto) throws Exception {
        Connection cnn = null;
        //Metodo que listara los libros para el usuario por nombre, autor y genero
        // este metodo lista los libros donde la opcion de busqueda se igual al texto de busqueda
        LinkedList<Libros_set_get> listaLibros = new LinkedList<Libros_set_get>();
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            recordSet = stmt.executeQuery (
                    "SELECT L.*,\n" +
                            "    CASE\n" +
                            "        WHEN P.id_prestamo IS NOT NULL AND P.fecha_devolucion IS NULL THEN 'No disponible'\n" +
                            "        ELSE 'Disponible'\n" +
                            "    END AS disponibilidad\n" +
                            "FROM Libro L\n" +
                            "LEFT JOIN Prestamos P ON L.id_libro = P.id_libro WHERE L." + opcion + " like('%" + texto + "%')");
            while (recordSet.next()) {
                Libros_set_get libro = new Libros_set_get();

                libro.setTitulo(recordSet.getString("titulo"));
                libro.setAutor(recordSet.getString("autor"));
                libro.setGenero(recordSet.getString("genero"));
                libro.setEditorial(recordSet.getString("editorial"));
                libro.setIsbn(recordSet.getString("isbn"));
                libro.setId_libro(recordSet.getInt("id_libro"));
                libro.setFecha_publicacion(recordSet.getDate("fecha_publicacion"));
                libro.setDispinibilidad(recordSet.getString("disponibilidad"));
                listaLibros.add(libro);
            }
            return listaLibros;
            } catch (SQLException e) {
                throw e;
            }catch (Exception e) {
            throw e;
            } finally {
                if (cnn != null)
                cnn.close();
            }
    }
    public LinkedList<Libros_set_get> listarLibros() throws Exception {
        Connection cnn = null;
        //Metodo que listara los libros para el usuario por nombre, autor y genero
        LinkedList<Libros_set_get> listaLibros = new LinkedList<Libros_set_get>();
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            recordSet = stmt.executeQuery (
                    "SELECT L.*,\n" +
                            "    CASE\n" +
                            "        WHEN P.id_prestamo IS NOT NULL AND P.fecha_devolucion IS NULL THEN 'No disponible'\n" +
                            "        ELSE 'Disponible'\n" +
                            "    END AS disponibilidad\n" +
                            "FROM Libro L\n" +
                            "LEFT JOIN Prestamos P ON L.id_libro = P.id_libro;");
            while (recordSet.next()) {
                Libros_set_get libro = new Libros_set_get();

                libro.setTitulo(recordSet.getString("titulo"));
                libro.setAutor(recordSet.getString("autor"));
                libro.setGenero(recordSet.getString("genero"));
                libro.setEditorial(recordSet.getString("editorial"));
                libro.setIsbn(recordSet.getString("isbn"));
                libro.setId_libro(recordSet.getInt("id_libro"));
                libro.setFecha_publicacion(recordSet.getDate("fecha_publicacion"));
                libro.setDispinibilidad(recordSet.getString("disponibilidad"));
                listaLibros.add(libro);
            }
            return listaLibros;
        } catch (SQLException e) {
            throw e;
        }catch (Exception e) {
            throw e;
        } finally {
            if (cnn != null)
                cnn.close();
        }
    }

    //Listado de libros por devolver
    public LinkedList<Libros_set_get> ListaDevolucion() throws Exception {
        Connection cnn = null;
        //Metodo que listara los libros por devolver para el usuario con titulo, autor, Fprestamo y Fdevolucion de la mas antigua a la mas reciente
        LinkedList<Prestamo_set_get> ListaDevolucion = new LinkedList<Prestamo_set_get>();
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            recordSet = statement.executeQuery (
                "SELECT Libro.titulo, Libro.autor, Prestamos.fecha_prestamo, Prestamos.fecha_devolucion
                FROM Libro
                JOIN Prestamos ON Libro.id_libro = Prestamos.id_libro
                ORDER BY Prestamos.fecha_prestamo ASC, Prestamos.fecha_devolucion ASC;" )
            while (recordSet.next()) {
                Prestamo_set_get devolucion = new Prestamo_set_get();

                libro.setTitulo(recordSet.getString("titulo"));
                libro.setAutor(recordSet.getString("autor"));
                prestamos.setFecha_prestamo(recordSet.getDate("fecha_prestamo"));
                prestamos.setFecha_devolucion(recordSet.getDate("fecha_devolucion"));
            }
            return ListaDevolucion;
            } catch (SQLException e) {
                throw e;
            }catch (Exception e) {
            throw e;
            } finally {
                if (cnn != null)
                cnn.close();
            }
    }

}

