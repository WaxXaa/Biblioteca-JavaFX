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
}
