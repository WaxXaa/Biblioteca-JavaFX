package front;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RegistroUsuario {
    VBox vb1 = new VBox();
    GridPane gp1 = new GridPane();
    TextField nombre = new TextField();
    TextField apellido = new TextField();
    TextField email = new TextField();
    TextField direccion = new TextField();
    TextField telefono = new TextField();
    Label l1 = new Label("Nombre");
    Label l2 = new Label("Apellido");
    Label l3 = new Label("Telefono");
    Label l4 = new Label("Direccion");
    Label l5 = new Label("Correo");
    Button registrar = new Button("Registrarme");
    Button volver = new Button("volver");
    Label titulo = new Label("Registrate!");
    protected RegistroUsuario() {
        gp1.addRow(0,l1,nombre,l2,apellido);
        gp1.addRow(1,l3,telefono,l4,direccion);
        gp1.addRow(2,l5,email);
        vb1.getChildren().add(titulo);
        vb1.getChildren().add(gp1);
        vb1.getChildren().add(registrar);
        vb1.getChildren().add(volver);
        vb1.setAlignment(Pos.CENTER);
        gp1.setAlignment(Pos.BASELINE_CENTER);
        gp1.setHgap(20);
        gp1.setVgap(20);
        vb1.setSpacing(40);

        //estilos
        vb1.setStyle("-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        l1.setStyle("-fx-font-size: 18px");
        l2.setStyle("-fx-font-size: 18px");
        l3.setStyle("-fx-font-size: 18px");
        l4.setStyle("-fx-font-size: 18px");
        l5.setStyle("-fx-font-size: 18px");
        titulo.setStyle("-fx-font-size: 24px;-fx-font-weight: 600;");
        titulo.setTranslateY(-50);
        nombre.setMinSize(200, 30);
        apellido.setMinSize(200, 30);
        telefono.setMinSize(200, 30);
        direccion.setMinSize(200, 30);
        email.setMinSize(200, 30);
        registrar.setMinSize(100,30);
        registrar.setCursor(Cursor.HAND);
        registrar.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        volver.setStyle("-fx-background-color: #dad7cd");
        volver.setCursor(Cursor.HAND);

    }
}