package backend.admin;
import backend.conexionsql.Conexion;
import backend.modelos.Libros_set_get;
import backend.operaciones.Operaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class OperacionesAdministrador extends Operaciones{
    private Statement statement;
    private ResultSet recordSet;
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
    public LinkedList<Prestamo_set_get> ListaDevolucion(int id_usuario) throws Exception {
        String consulta = "";
	    Connection conn = null;
        //Metodo que listara TODOS los libros prestados y los ordenará por fecha.
        LinkedList<Prestamo_set_get> listaPrestamos = new LinkedList<Prestamo_set_get>();
        try {
            conn = Conexion.establecerConexion();
            stmt = conn.createStatement();
	        consulta = = "SELECT l.id_libro, l.titulo, l.autor, u.nombre, u.apellido, p.fecha_prestamo, p.fecha_devolucion " +
    				    "FROM Libro l " +
    				    	"JOIN Prestamos p ON l.id_libro = p.id_libro " +
    					    "JOIN Usuario u ON u.id_usuario = p.id_usuario " +
    				    "ORDER BY " +
    					    "CASE " +
        					    "WHEN p.fecha_devolucion IS NULL THEN 0 " + 
        					    "ELSE 1 " + 
    					    "END, " +
    				    "p.fecha_devolucion DESC;";
            recordSet = stmt.executeQuery (consulta);
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
		
		
                registroPrestamo.setFecha_prestamo(recordSet.getDate("fecha_prestamo"));
                
                // Obtener la fecha_devolucion del registro
                java.sql.Date fechaDevolucion = recordSet.getDate("fecha_devolucion");

                // Verificar si la fecha_devolucion es NULL y cambiarlo por "Pendiente"
                if (fechaDevolucion == null) {
                    registroPrestamo.setFecha_devolucion("Pendiente");
                } else {
                    registroPrestamo.setFecha_devolucion(fechaDevolucion.toString()); // Convierte la fecha a una cadena legible
                }
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

public void registrarPrestamo(int idLibro) throws Exception {
                /*
                 * Este método registra un préstamo para un libro específico dado su ID.
                 * Recibe como parámetro el ID del libro que se va a prestar.
                 */

                Connection conn = null;
                try {
                    conn = Conexion.establecerConexion();
                    statement = conn.createStatement();

                    // Verificar si el libro está disponible antes de registrar el préstamo
                    recordSet = statement.executeQuery("SELECT fecha_devolucion FROM Prestamos WHERE id_libro = " + idLibro);
                    if (recordSet.next()) {
                        // Si la consulta devuelve resultados, significa que el libro ya está prestado
                        throw new Exception("El libro ya está prestado y no está disponible en este momento.");
                    } else {
                        // Si la consulta no devuelve resultados, el libro está disponible y se puede prestar
                        statement.executeUpdate("INSERT INTO Prestamos (id_libro) VALUES (" + idLibro + ")");
                    }
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

        public void registrarDevolucion(int idLibro) throws Exception {
            /*
             * Este método registra la devolución de un libro específico dado su ID.
             * Recibe como parámetro el ID del libro que se va a devolver.
             */

            Connection conn = null;
            try {
                conn = Conexion.establecerConexion();
                statement = conn.createStatement();

                // Verificar si el libro está prestado antes de registrar la devolución
                recordSet = statement.executeQuery("SELECT fecha_devolucion FROM Prestamos WHERE id_libro = " + idLibro);
                if (recordSet.next()) {
                    // Si la consulta devuelve resultados, significa que el libro está prestado y se puede devolver
                    statement.executeUpdate("UPDATE Prestamos SET fecha_devolucion = NOW() WHERE id_libro = " + idLibro);
                } else {
                    // Si la consulta no devuelve resultados, significa que el libro no está prestado
                    throw new Exception("El libro no está prestado actualmente y no se puede registrar la devolución.");
                }
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
