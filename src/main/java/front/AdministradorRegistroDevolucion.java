package front;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AdministradorRegistroDevolucion {
    Label id_lb = new Label("Indetificador del Libro");
    Label nombre_lb = new Label("Nombre del Usuario");
    Label correo_lb = new Label("E-mail del Usuario");
    Label titulo = new Label("Registro de Devoluciones");
    Label mensaje = new Label("por favor llene los siguientes campos");
    TextField id_txt = new TextField();
    TextField nombre_txt = new TextField();
    TextField correo_txt = new TextField();
    GridPane campos = new GridPane();
    Button registrar = new Button("registrar devolucion");
    Button volver = new Button("volver");
    VBox mainLayout = new VBox();
    public AdministradorRegistroDevolucion() {
        id_lb.setStyle("-fx-font-size: 18px");
        nombre_lb.setStyle("-fx-font-size: 18px");
        correo_lb.setStyle("-fx-font-size: 18px");
        id_txt.setMinSize(200, 30);
        nombre_txt.setMinSize(200, 30);
        correo_txt.setMinSize(200, 30);
        campos.addRow(0,id_lb,id_txt);
        campos.addRow(1,nombre_lb,nombre_txt);
        campos.addRow(2,correo_lb,correo_txt);
        campos.setAlignment(Pos.BASELINE_CENTER);
        mainLayout.getChildren().addAll(titulo,mensaje,campos,registrar,volver);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(30);
        campos.setVgap(20);
        campos.setHgap(20);
        titulo.setTranslateY(-50);
        registrar.setCursor(Cursor.HAND);
        registrar.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        titulo.setStyle("-fx-font-size: 24;-fx-font-weight: 600;");
        volver.setStyle("-fx-background-color: #dad7cd");
        volver.setCursor(Cursor.HAND);
        mainLayout.setStyle("-fx-padding: 20px;-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
    }
}
