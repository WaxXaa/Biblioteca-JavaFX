package backend.admin;
import backend.conexionsql.Conexion;
import backend.modelos.Libros_set_get;
import backend.modelos.Prestamo_set_get;
import backend.modelos.Usuario_set_get;
import backend.operaciones.Operaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class OperacionesAdministrador extends Operaciones{
    private Statement statement;
    private ResultSet recordSet;
    private Usuario_set_get usuario = new Usuario_set_get();

    public Usuario_set_get getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_set_get usuario) {
        this.usuario = usuario;
    }

    public LinkedList<Libros_set_get> listarLibros() throws Exception{
        /*
        * este metodo es para listar todos los libros por orden alfabetico del titulo
        * retorna una lista enlazada que almacena objetos de tipo Libros_set_get
        * */
        Connection conn = null;
        LinkedList<Libros_set_get> listaLibros = new LinkedList<Libros_set_get>();
        try {
            conn = Conexion.establecerConexion();
            statement = conn.createStatement();

            recordSet = statement.executeQuery("WITH PrestamosActuales AS (\n" +
                    "  SELECT DISTINCT id_libro\n" +
                    "  FROM Prestamos\n" +
                    "  WHERE fecha_devolucion IS NULL\n" +
                    ")\n" +
                    "SELECT L.*,\n" +
                    "  CASE\n" +
                    "    WHEN PA.id_libro IS NOT NULL THEN 'No disponible'\n" +
                    "    ELSE 'Disponible'\n" +
                    "  END AS disponibilidad\n" +
                    "FROM Libro L\n" +
                    "LEFT JOIN PrestamosActuales PA ON L.id_libro = PA.id_libro;");
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
        } catch (Exception e) {
            throw e;
        } finally {
            if(conn != null){
                conn.close();
            }
        }
    }
    public LinkedList<Prestamo_set_get> obternerPrestamos() throws Exception {
        String consulta = "";
	    Connection conn = null;
        //Metodo que listara TODOS los libros prestados y los ordenará por fecha.
        LinkedList<Prestamo_set_get> listaPrestamos = new LinkedList<Prestamo_set_get>();
        try {
            conn = Conexion.establecerConexion();
            statement = conn.createStatement();
	        consulta = "SELECT \n" +
                    "    p.id_prestamo,\n"+
                    "    l.id_libro, \n" +
                    "    l.titulo, \n" +
                    "    l.autor, \n" +
                    "    u.nombre, \n" +
                    "    u.apellido, \n" +
                    "    p.fecha_prestamo,\n" +
                    "    p.fecha_devolucion\n" +
                    "FROM Libro l\n" +
                    "JOIN Prestamos p ON l.id_libro = p.id_libro\n" +
                    "JOIN Usuario u ON u.id_usuario = p.id_usuario\n" +
                    "ORDER BY 1 DESC;";
            recordSet = statement.executeQuery (consulta);
            while (recordSet.next()) {
                Prestamo_set_get registroPrestamo = new Prestamo_set_get();
                Libros_set_get libro = new Libros_set_get();
                Usuario_set_get usuario = new Usuario_set_get();
		
		        //Asignar los datos del libro prestadp
                libro.setId_libro(recordSet.getInt("id_libro"));
                libro.setTitulo(recordSet.getString("titulo"));
                libro.setAutor(recordSet.getString("autor"));

        		//Asignar los datos del Usuario que solicitó el Préstamo
        		usuario.setNombre(recordSet.getString("nombre"));
        		usuario.setApellido(recordSet.getString("apellido"));
		
		        //Agregar Registros
                registroPrestamo.setLibro(libro);
		        registroPrestamo.setUsuario(usuario);
		
		        registroPrestamo.setId_prestamo(recordSet.getInt("id_prestamo"));
                registroPrestamo.setFecha_prestamo(recordSet.getDate("fecha_prestamo"));
                
                // Obtener la fecha_devolucion del registro


                // Verificar si la fecha_devolucion es NULL y cambiarlo por "Pendiente"
                if(recordSet.getDate("fecha_devolucion") != null)
                    registroPrestamo.setFecha_devolucion(recordSet.getDate("fecha_devolucion"));
                listaPrestamos.add(registroPrestamo);
                }
            return listaPrestamos;
            } catch (SQLException e) {
                throw e;
            }catch (Exception e) {
            throw e;
            } finally {
                if (conn != null)
                conn.close();
                }
            }

    public boolean registrarPrestamo(int id_libro) throws Exception {
        /*
         * Este método registra un préstamo para un libro específico dado su ID.
         * Recibe como parámetro el ID del libro que se va a prestar.
         */

        Connection conn = null;
        try {
            conn = Conexion.establecerConexion();
            statement = conn.createStatement();

            // Verificar si el libro está disponible antes de registrar el préstamo
            recordSet = statement.executeQuery("SELECT fecha_devolucion FROM Prestamos WHERE id_libro = " + id_libro + " and fecha_devolucion is null");
            if (recordSet.next())
                return false;
            else
                statement.executeUpdate("INSERT INTO Prestamos (id_libro, id_usuario, fecha_prestamo) VALUES (" + id_libro + ", " + usuario.getId_usuario() + ", CONVERT(DATE, GETDATE()))");
            return true;
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

        public boolean registrarDevolucion(int id_libro) throws Exception {
            /*
             * Este método registra la devolución de un libro específico dado su ID.
             * Recibe como parámetro el ID del libro que se va a devolver.
             */

            Connection conn = null;
            try {
                conn = Conexion.establecerConexion();
                statement = conn.createStatement();

                // Verificar si el libro está prestado antes de registrar la devolución
                recordSet = statement.executeQuery("SELECT id_prestamo, fecha_devolucion FROM Prestamos WHERE id_libro = " + id_libro + " and id_usuario = " + usuario.getId_usuario() +" and fecha_devolucion is null");
                if (recordSet.next()) {
                    // Si la consulta devuelve resultados, significa que el libro está prestado y se puede devolver
                    statement.executeUpdate("UPDATE Prestamos SET fecha_devolucion = CONVERT(DATE, GETDATE()) WHERE id_prestamo = " + recordSet.getInt("id_prestamo"));
                    return true;
                } else
                    return false;
            } catch (SQLException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        }
}
