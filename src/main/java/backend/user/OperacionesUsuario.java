package backend.user;
import backend.modelos.Usuario_set_get;

public class OperacionesUsuario {

  private Statement stmt; // objeto que permite la manipulaciòn de sentencias SQL

  Usuario_set_get datosUsuario = new Usuario_set_get()
  
  public Usuario_set_get verificarUsuario (String nom, String correo, Conexion obj1) throws Exception {
        String cadSql;
        boolean registro;
        Connection cnn = null;
        try {
            cnn = obj1.establecer_conexion();
            stmt = cnn.createStatement();
            cadSql = "SELECT * FROM Biblioteca WHERE correo = '"+correo"'" ;
            recordset = stmt.executeQuery(cadSql);
            registro = recordset.next();          
            If(registro == False)
              throw new Exception("Correo Ingresado Incorrecto.");
            else {  
              if(nom =! recordset.getString("nombre"){
              registro = false;
              throw new Exception("Nombre Ingresado Incorrecto.");
            }
            datosUsuario.setNombre(recordset.getString("nombre"));
            datosUsuario.setApellido(recordset.getString("apellido"));
            datosUsuario.setDireccion(recordset.getString("direccion"));
            datosUsuario.setTelefono(recordset.getString("telefono"));
            datosUsuario.setCorreo(recordset.getString("correo"));
            datosUsuario.setId_usuario(recordset.getInt("Id_usuario"));
              
            return datosUsuario;
        } catch (SQLException e) {
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (cnn != null)
              cnn.close();
        }
       
}


