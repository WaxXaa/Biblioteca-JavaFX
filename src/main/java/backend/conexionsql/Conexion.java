package backend.conexionsql;
import backend.secret.VariablesSecretas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexion {

    // Método de conexion
    public static Connection establecerConexion() throws Exception {
        Connection cnn;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cnn = DriverManager.getConnection("jdbc:sqlserver://0.0.0.0:1433;databaseName=Biblioteca;user="+VariablesSecretas.USERNAME+";password="+VariablesSecretas.PASSWORD+";encrypt=false");
            return cnn;
        }
        catch (ClassNotFoundException e){
            throw new Exception ("\nPara el programador: "+e+
                    "\n\nPara el usuario: Error...No se pudo cargar el driver puente Jdbc_Odbc");
        }
        catch (SQLException e){
            throw new Exception ("\nPara el programador: "+e+
                    "\n\nPara el usuario: Error... No se pudo establecer la conexion");
        }
    }
}
