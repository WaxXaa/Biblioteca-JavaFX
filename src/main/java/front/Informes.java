package front;

import backend.admin.OperacionesAdministrador;
import backend.modelos.Libros_set_get;
import backend.modelos.Prestamo_set_get;
import backend.modelos.Usuario_set_get;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class Informes {
    OperacionesAdministrador operacionesAdministrador = new OperacionesAdministrador();
    VBox mainLayout = new VBox();
    Button volver = new Button("volver");
    VBox listado = new VBox();
    ToggleGroup opcionesListado = new ToggleGroup();
    RadioButton radioLibrosMasPrestados = new RadioButton("Libros más prestados");
    RadioButton radioUsuariosConMasPrestamos = new RadioButton("Usuarios con más Prestamos");
    Label cantidadPrestamos = new Label();
    Label informesBiblioteca_txt = new Label("Informes de la Biblioteca");
    HBox navLayout = new HBox();
    GridPane lista = new GridPane();
    public Informes() {
        informesBiblioteca_txt.setStyle("-fx-font-size: 24;-fx-font-weight: 600;");
        cantidadPrestamos.setStyle("-fx-font-size: 14;-fx-font-weight: 700;");
        radioLibrosMasPrestados.setUserData("libros");
        radioUsuariosConMasPrestamos.setUserData("prestamos");
        radioLibrosMasPrestados.setToggleGroup(opcionesListado);
        radioUsuariosConMasPrestamos.setToggleGroup(opcionesListado);
        radioLibrosMasPrestados.setStyle("-fx-font-size:14px");
        radioUsuariosConMasPrestamos.setStyle("-fx-font-size:14px");
        opcionesListado.selectToggle(radioLibrosMasPrestados);
        navLayout.getChildren().addAll(volver,radioLibrosMasPrestados,radioUsuariosConMasPrestamos,cantidadPrestamos);
        navLayout.setSpacing(20);
        lista.setStyle("-fx-background-color: #f7f8ff");
        listado.getChildren().addAll(navLayout);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        navLayout.setAlignment(Pos.TOP_CENTER);
        lista.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(informesBiblioteca_txt,navLayout, lista);
        mainLayout.setStyle("-fx-padding: 20px;-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        mainLayout.setSpacing(40);
        listado.setSpacing(20);
        lista.setHgap(20);
        lista.setVgap(5);


        volver.setMinSize(200,30);
        volver.setAlignment(Pos.CENTER);
        volver.setStyle("-fx-background-color: #dad7cd");
        volver.setCursor(Cursor.HAND);
    }
    public void listarLibrosMasPrestados() throws Exception {
        LinkedList<Libros_set_get> listaLibrosMasPrestados;
        try {
            listaLibrosMasPrestados = operacionesAdministrador.obtenerLibrosMasPrestados();
            lista.addRow(0, new Label("Identificador"), new Label("Título"), new Label("Autor"), new Label("Género"), new Label("Cantidad Prestamos"));
            for (int i = 0; i < listaLibrosMasPrestados.size(); i++) {
                int id_libro = listaLibrosMasPrestados.get(i).getId_libro();
                String titulo = listaLibrosMasPrestados.get(i).getTitulo();
                String autor = listaLibrosMasPrestados.get(i).getAutor();
                String genero = listaLibrosMasPrestados.get(i).getGenero();
                int cantidad = listaLibrosMasPrestados.get(i).getCantidad_prestamos();

                lista.addRow(i+1, new Label(String.valueOf(id_libro)), new Label(titulo), new Label(autor), new Label(genero), new Label(String.valueOf(cantidad)));
            }
        }catch (Exception err) {
            throw err;
        }
    }
    public void listarUsuariosConMasPrestamos() throws Exception {
        LinkedList<Usuario_set_get> listaUsuariosConMasPrestamos;
        try {
            listaUsuariosConMasPrestamos = operacionesAdministrador.obtenerUsuariosConMasPrestamos();
            lista.addRow(0, new Label("Nombre"), new Label("Apellido"), new Label("Correo"), new Label("Cantidad Prestamos"));
            for (int i = 0; i < listaUsuariosConMasPrestamos.size(); i++) {
                String nombre = listaUsuariosConMasPrestamos.get(i).getNombre();
                String apellido = listaUsuariosConMasPrestamos.get(i).getApellido();
                String correo = listaUsuariosConMasPrestamos.get(i).getCorreo();
                int cantidad = listaUsuariosConMasPrestamos.get(i).getNum_prestamos();

                lista.addRow(i+1, new Label(nombre), new Label(apellido), new Label(correo), new Label(String.valueOf(cantidad)));
            }
        }catch (Exception err) {
            throw err;
        }
    }
    public void mostrarMensajeAlRealizarInformes(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("lo sentimos, Ha ocurrido un error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //los metodos mostrar contenido estan sobrecargados ya que hacen lo mismo pero en contextos diferentes
    public void mostrarContenido(String opcion)throws Exception {
        //este metodo se llama cada vez que se detecta un cambio en los radioButton
        //es decir cuando se interactua con las opciones de listar los libros mas prestados o listar usuarios con mas prestamos
        borrarContenido();
        if (opcion.equals(radioLibrosMasPrestados.getUserData())) {
            try {
                mostrarCantidadPrestamos();
                listarLibrosMasPrestados();
            }catch (Exception e) {
                throw e;
            }
        } else {
            try {
                mostrarCantidadPrestamos();
                listarUsuariosConMasPrestamos();
            }catch (Exception e) {
                throw e;
            }
        }
    }
    public void mostrarCantidadPrestamos() throws Exception {
        try {
            int cantPrestamos = operacionesAdministrador.obtenerCantidadPrestamos();
            cantidadPrestamos.setText("Cantidad de Prestamos realizados: "+ String.valueOf(cantPrestamos));
        }catch (Exception e) {
            throw e;
        }
    }
    public void mostrarContenido() throws Exception {
        //y este metodo lista es para cada vez que la escena del stage cambia a la escena principal del administrador
        borrarContenido();
        opcionesListado.selectToggle(radioLibrosMasPrestados);
        try {
            mostrarCantidadPrestamos();
            listarLibrosMasPrestados();
        }catch (Exception e) {
            throw e;
        }
    }
    public void borrarContenido() {
        lista.getChildren().clear();
    }
}
