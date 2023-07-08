package front;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Administrador {
    HBox mainLayout = new HBox();
    VBox opcionesMenu = new VBox();
    Button prestamo = new Button("registrar prestamo");
    Button devolucion = new Button("registrar devolucion");
    Button informes = new Button("realizar informes");
    Button volver = new Button("volver");
    VBox listado = new VBox();
    ToggleGroup opcionesListado = new ToggleGroup();
    RadioButton radioLibros = new RadioButton("todos los libros");
    RadioButton radioPrestamos = new RadioButton("todos los prestamos");
    HBox opcionesListadoLayout = new HBox();
    GridPane listaLibros = new GridPane();
    public Administrador() {
        radioLibros.setUserData("libros");
        radioPrestamos.setUserData("prestamos");
        opcionesMenu.getChildren().addAll(prestamo,devolucion,informes,volver);
        radioLibros.setToggleGroup(opcionesListado);
        radioPrestamos.setToggleGroup(opcionesListado);
        opcionesListado.selectToggle(radioLibros);
        opcionesListadoLayout.getChildren().addAll(radioLibros,radioPrestamos);
        listado.getChildren().addAll(opcionesListadoLayout,listaLibros);
        listaLibros.addRow(0,new Label("Título"), new Label("Autor"), new Label("Género"), new Label("Editorial"), new Label("ISBN"), new Label("Fecha de Publicación"));
        mainLayout.getChildren().addAll(opcionesMenu, listado);
        mainLayout.setStyle("-fx-padding: 20px;-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        mainLayout.setSpacing(40);
        opcionesMenu.setSpacing(40);
        listado.setSpacing(20);
        listaLibros.setBorder(Border.stroke(Paint.valueOf("#000")));
        listaLibros.setHgap(10);
        listaLibros.setVgap(5);

        prestamo.setMinSize(200,30);
        devolucion.setMinSize(200,30);
        informes.setMinSize(200,30);
        volver.setMinSize(200,30);
        prestamo.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        devolucion.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        informes.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        volver.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
    }
    public void listarLibros() {

    }
}
