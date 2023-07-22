package front;
import backend.modelos.Usuario_set_get;
import backend.user.OperacionesUsuario;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LoginUsuario {
    GridPane gp1 = new GridPane();
    VBox mainLauoyt = new VBox();
    Button b2 = new Button("Ingresar");
    Button b3 = new Button("registrarse");
    Button b4 = new Button("volver");
    Label l1 = new Label("nombre:");
    TextField nombre = new TextField();
    Label l2 = new Label("E-mail:");
    TextField correo = new TextField();
    protected LoginUsuario() {

        mainLauoyt.setStyle("-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        correo.setMinSize(200, 30);
        nombre.setMinSize(200,30);
        l1.setStyle("-fx-font-size: 20px");
        l2.setStyle("-fx-font-size: 20px");
        b2.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        b2.setMinSize(100,30);
        b2.setCursor(Cursor.HAND);
        b3.setTranslateY(-30);
        b3.setStyle("-fx-background-color: transparent; -fx-text-fill: #32556E; -fx-font-size: 14px");
        b3.setCursor(Cursor.HAND);
        b4.setStyle("-fx-background-color: #dad7cd");
        b4.setCursor(Cursor.HAND);

        gp1.setAlignment(Pos.BASELINE_CENTER);
        mainLauoyt.setAlignment(Pos.CENTER);
        mainLauoyt.setSpacing(40);
        gp1.setVgap(10);
        gp1.setHgap(20);

        gp1.addRow(0,l2,correo);
        gp1.addRow(1, l1,nombre);
        mainLauoyt.getChildren().add(gp1);
        mainLauoyt.getChildren().addAll(b2,b3,b4);

    }
    public String obtenerCorreo() {
        return correo.getText();
    }
    public String obtenerNombre() {
        return nombre.getText();
    }
    public boolean ingresar(String nombre, String correo, Usuario usuario, Notificaciones notificaciones, OperacionesUsuario operacionesUsuario) throws Exception{
        Usuario_set_get u;
        try{
            u = operacionesUsuario.verificarUsuario(nombre,correo);
            if (u == null)
                return false;
            usuario.setUsuario(u);
            notificaciones.setUsuario(u);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    public void mostrarErrorAlIngresar(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("lo sentimos, Ha ocurrido un error al ingresar");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void resetearInput() {
        nombre.setText("");
        correo.setText("");
    }
}
