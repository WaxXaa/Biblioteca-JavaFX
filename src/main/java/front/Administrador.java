package front;

import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import backend.admin.OperacionesAdministrador;
import backend.modelos.Libros_set_get;
import java.util.LinkedList;
import java.sql.Date;
import java.text.SimpleDateFormat;
public class Administrador {
    OperacionesAdministrador operacionesAdministrador = new OperacionesAdministrador();
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
        radioLibros.setStyle("-fx-font-size:14px");
        radioPrestamos.setStyle("-fx-font-size:14px");
        opcionesListado.selectToggle(radioLibros);
        opcionesListadoLayout.getChildren().addAll(radioLibros,radioPrestamos);
        opcionesListadoLayout.setSpacing(20);
        scrollPane.setPrefSize(700,400);
        scrollPane.setContent(listaLibros);
        scrollPane.setStyle("-fx-background-color: #f7f8ff");
        listaLibros.setStyle("-fx-background-color: #f7f8ff");
        listado.getChildren().addAll(opcionesListadoLayout,scrollPane);
        mainLayout.getChildren().addAll(opcionesMenu, listado);
        mainLayout.setStyle("-fx-padding: 20px;-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        mainLayout.setSpacing(40);
        opcionesMenu.setSpacing(40);
        listado.setSpacing(20);
        listaLibros.setHgap(20);
        listaLibros.setVgap(5);

        prestamo.setMinSize(200,30);
        devolucion.setMinSize(200,30);
        informes.setMinSize(200,30);
        volver.setMinSize(200,30);
        prestamo.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        devolucion.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        informes.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        volver.setStyle("-fx-background-color: #dad7cd");
        prestamo.setCursor(Cursor.HAND);
        devolucion.setCursor(Cursor.HAND);
        informes.setCursor(Cursor.HAND);
        volver.setCursor(Cursor.HAND);
    }
    public void listarLibros() throws Exception{
        //este metodo es para desplegar todos los libros
        //se usa el objeto operacionesAdministrador de la clase OperacionesAdministrador para obtener todos los libros de la base de datos
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        LinkedList<Libros_set_get> libros;

        try {
            libros = operacionesAdministrador.listarLibros();
            listaLibros.addRow(0,new Label("Identificador"),new Label("Dispinibilidad"),new Label("Título"), new Label("Autor"), new Label("Género"), new Label("Editorial"), new Label("ISBN"), new Label("Fecha de Publicación"));
            for (int i = 0; i < libros.size(); i++) {
                int id = libros.get(i).getId_libro();
                String titulo = libros.get(i).getTitulo();
                String autor = libros.get(i).getAutor();
                String genero = libros.get(i).getGenero();
                String editorial = libros.get(i).getEditorial();
                String isbn = libros.get(i).getIsbn();
                Date fecha = libros.get(i).getFecha_publicacion();
                String fechaFromateada = formato.format(fecha);
                String disponible = libros.get(i).getDispinibilidad();
                Label disponibilidad = new Label();
                if(disponible.equals("Disponible")) {
                    disponibilidad.setText("✔ " + disponible);
                    disponibilidad.setStyle("-fx-font-size: 14px; -fx-text-fill: #50AE5D");
                }
                else {
                    disponibilidad.setText("❌ " + disponible);
                    disponibilidad.setStyle("-fx-font-size: 14px; -fx-text-fill: #D16677");
                }

                listaLibros.addRow(i+1, new Label(String.valueOf(id)), disponibilidad, new Label(titulo),new Label(autor), new Label(genero), new Label(editorial), new Label(isbn), new Label(fechaFromateada));
            }
        }catch (Exception e) {
            throw e;
        }
    }
    public void mostrarErrorAlListarLibros(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("lo sentimos, Ha ocurrido un error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //los metodos mostrar contenido estan sobrecargados ya que hacen lo mismo pero en contextos diferentes
    public void mostrarContenido(String opcion)throws Exception {
        //este metodo se llama cada vez que se detecta un cambio en los radioButton
        //es decir cuando se interactua con las opciones de listar todos los libros o listar prestamos
        borrarContenido();
        if (opcion.equals(radioLibros.getUserData())) {
            try {
                listarLibros();
            }catch (Exception e) {
                throw e;
            }
        }
    }
    public void mostrarContenido() throws Exception {
        //y este metodo lista es para cada vez que la escena del stage cambia a la escena principal del administrador
        borrarContenido();
        try {
            listarLibros();
        }catch (Exception e) {
            throw e;
        }
    }
    public void borrarContenido() {
        listaLibros.getChildren().clear();
    }

}
