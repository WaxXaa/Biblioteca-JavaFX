package backend.conexionsql;
import backend.secret.VariablesSecretas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
* la clase conexion esta hecha para conectarse con la base de datos "Biblioteca" en localhost:puerto 1433
* es final para que no pueda ser superclase
* el metodo establecerConexion es el metodo que usaremos para conectarnos con la base de datos
* es static para que al momento de usarlo se llame directamente de la clase(ver ejemplo de la clase OperacionesAdministrador linea 22)
* este metodo retorna un objeto de tipo Connection
* deben instalar el driver mssql-jdbc-12.2.0.jre8 en external libraries
* deben crear en el paquete de backend otro paquete llamado secret y dentro de ese paquete crear una clase llamaba VariablesSecretas
* esa clase VariablesSecretas debe tener dos atributos con la siguiente declaracion:
*
*
    public final static String USERNAME = "";
    public final static String PASSWORD = "";

* en USERNAME ponen su usuario de sqlserver
* y en PASSWORD ponen su contraseña de sqlserver
*
* sigan esos pasos para que esta clase no se vea afectada ni por algun cambio en las variables USERNAME y PASSWORD en el metodo getConnection
* y tampoco se vea afectada en el import de la clase variablesSecrtas
*
* en teoria esa carpeta secret ya esta puesta en el .gitignore asi que cuando hagan push  y luego pull request no se subira
* */
public final class Conexion {
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
