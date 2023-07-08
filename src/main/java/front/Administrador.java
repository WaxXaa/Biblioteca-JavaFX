package front;

import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import backend.admin.OperacionesAdministrador;
import backend.modelos.Libros_set_get;
import java.util.LinkedList;
import java.sql.Date;
import java.text.SimpleDateFormat;
public class Administrador {
    OperacionesAdministrador operaciones = new OperacionesAdministrador();
    Libros_set_get libro = new Libros_set_get();
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
    ScrollPane scrollPane = new ScrollPane();
    public Administrador() {
        radioLibros.setUserData("libros");
        radioPrestamos.setUserData("prestamos");
        opcionesMenu.getChildren().addAll(prestamo,devolucion,informes,volver);
        radioLibros.setToggleGroup(opcionesListado);
        radioPrestamos.setToggleGroup(opcionesListado);
        opcionesListado.selectToggle(radioLibros);
        opcionesListadoLayout.getChildren().addAll(radioLibros,radioPrestamos);
        scrollPane.setPrefSize(700,400);
        scrollPane.setContent(listaLibros);
        listado.getChildren().addAll(opcionesListadoLayout,scrollPane);
        mainLayout.getChildren().addAll(opcionesMenu, listado);
        mainLayout.setStyle("-fx-padding: 20px;-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        mainLayout.setSpacing(40);
        opcionesMenu.setSpacing(40);
        listado.setSpacing(20);
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
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            LinkedList<Libros_set_get> libros = operaciones.listarTodosLosLibros();
            listaLibros.addRow(0,new Label("Identificador"),new Label("Título"), new Label("Autor"), new Label("Género"), new Label("Editorial"), new Label("ISBN"), new Label("Fecha de Publicación"));
            for (int i = 0; i < libros.size(); i++) {
                int id = libros.get(i).getId_libro();
                String titulo = libros.get(i).getTitulo();
                String autor = libros.get(i).getAutor();
                String genero = libros.get(i).getGenero();
                String editorial = libros.get(i).getEditorial();
                String isbn = libros.get(i).getIsbn();
                Date fecha = libros.get(i).getFecha_publicacion();
                String fechaFromateada = formato.format(fecha);
                listaLibros.addRow(i+1, new Label(String.valueOf(id)), new Label(titulo),new Label(autor), new Label(genero), new Label(editorial), new Label(isbn), new Label(fechaFromateada));
            }
        }catch (Exception e) {
            mostrarErrorAlListarLibros("error al mostrar los libros \n" + e);
        }
    }
    public void mostrarErrorAlListarLibros(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("lo sentimos, Ha ocurrido un error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void mostrarContenido(String opcion) {
        borrarContenido();
        if (opcion.equals(radioLibros.getUserData())) {
            listarLibros();
        }
    }
    public void mostrarContenido() {
        listarLibros();
    }
    public void borrarContenido() {
        listaLibros.getChildren().clear();
    }

}
