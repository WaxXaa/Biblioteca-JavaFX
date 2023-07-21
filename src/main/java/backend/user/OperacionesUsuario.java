package backend.user;

import backend.modelos.Usuario_set_get;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backend.conexionsql.Conexion;

public class OperacionesUsuario {

    private Statement stmt; // objeto que permite la manipulaciòn de sentencias
    private ResultSet recordset;

    Usuario_set_get datosUsuario = new Usuario_set_get();

    public void setDatosUsuario(Usuario_set_get datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    public Usuario_set_get verificarUsuario(String nom, String correo) throws Exception {
        // el metodo verificarUsuario comprueba la existencia del usuario, si existe
        // retorna un objeto tipo Usiario_set_get con los datos del usuario
        String cadSql;
        Connection cnn = null;
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            cadSql = "SELECT * FROM Usuario WHERE correo = '" + correo + "' and nombre = '" + nom + "'";
            recordset = stmt.executeQuery(cadSql);
            if (recordset.next()) {
                datosUsuario.setNombre(recordset.getString("nombre"));
                datosUsuario.setApellido(recordset.getString("apellido"));
                datosUsuario.setDireccion(recordset.getString("direccion"));
                datosUsuario.setTelefono(recordset.getString("telefono"));
                datosUsuario.setCorreo(recordset.getString("correo"));
                datosUsuario.setId_usuario(recordset.getInt("Id_usuario"));
                return datosUsuario;
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
            recordset = stmt.executeQuery(cadSqlCorreo);
            existeRegistro = recordset.next();
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
    public LinkedList<Libros_set_get> ListaLibrosUser () throws Exception {
        Connection cnn = null;
        //Metodo que listara los libros para el usuario por nombre, autor y genero 
        LinkedList<Libros_set_get> ListaLibrosUser = new LinkedList<Libros_set_get>();
        try {
            cnn = Conexion.establecerConexion();
            stmt = cnn.createStatement();
            recordSet = statement.executeQuery (
                "SELECT titulo , autor , genero FROM Libro;" )
            while (recordSet.next()) {
                Libros_set_get libro = new Libros_set_get();

                libro.setTitulo(recordSet.getString("titulo"));
                libro.setAutor(recordSet.getString("autor"));
                libro.setGenero(recordSet.getString("genero")); 
            }
            return ListaLibrosUser; 
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

