package front;

import backend.modelos.Libros_set_get;
import backend.modelos.Usuario_set_get;
import backend.user.OperacionesUsuario;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class Usuario {
    OperacionesUsuario operacionesUsuario = new OperacionesUsuario();
    Usuario_set_get usuario = new Usuario_set_get();
    Label nombre = new Label();
    Label saludo = new Label("👋");
    VBox mainLayout = new VBox();
    GridPane listaLibros = new GridPane();
    ScrollPane scrollPane = new ScrollPane();
    ChoiceBox choiceBox = new ChoiceBox();
    Button volver = new Button("salir");
    TextField textField = new TextField();
    HBox barraBusqueda = new HBox();
    HBox bienvenida = new HBox();
    HBox primeraFila = new HBox();
    Rectangle rectN = new Rectangle();
    ImageInput imgVN;
    Group imgGN;
    Button buscar = new Button("buscar");
    Button catalogoEntero = new Button("todos los libros");
    public Usuario() {
        try {
            Image imgN = new Image(new FileInputStream("C:\\Users\\Mosquera\\Documents\\Alejandro17\\Code_java\\menuS\\src\\main\\resources\\notification.png"));
            imgVN = new ImageInput(imgN);
        } catch (Exception e) {

        }
        rectN.setWidth(30);
        rectN.setHeight(30);
        rectN.setEffect(imgVN);
        imgGN = new Group(rectN);

        choiceBox.getItems().add("Titulo");
        choiceBox.getItems().add("Autor");
        choiceBox.getItems().add("Genero");
        mainLayout.setAlignment(Pos.BASELINE_CENTER);
        mainLayout.setSpacing(30);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        listaLibros.setHgap(20);
        listaLibros.setVgap(5);
        listaLibros.setMinWidth(800);
        scrollPane.setPrefSize(800, 400);
        scrollPane.setContent(listaLibros);
        listaLibros.setStyle("-fx-background-color: #f7f8ff");
        imgGN.setTranslateX(30);
        rectN.setCursor(Cursor.HAND);
        scrollPane.setStyle("-fx-background-color: #f7f8ff; -fx-padding: 20px");
        listaLibros.setStyle("-fx-background-color: #f7f8ff");
        barraBusqueda.setAlignment(Pos.BASELINE_CENTER);
        textField.setMinSize(300, 30);
        volver.setStyle("-fx-background-color: #dad7cd");
        volver.setTranslateX(-30);
        volver.setCursor(Cursor.HAND);
        choiceBox.setStyle("-fx-background-color: transparent");
        choiceBox.setValue("buscar por");
        catalogoEntero.setStyle("-fx-background-color: #ffff");
        catalogoEntero.setCursor(Cursor.HAND);
        catalogoEntero.setTranslateX(-150);
        catalogoEntero.setBorder(Border.stroke(Paint.valueOf("#AEAEAE")));
        buscar.setStyle("-fx-background-color: #ffff");
        buscar.setCursor(Cursor.HAND);
        buscar.setBorder(Border.stroke(Paint.valueOf("#AEAEAE")));
        barraBusqueda.getChildren().addAll(catalogoEntero,textField, choiceBox, buscar);
        nombre.setStyle("-fx-font-size: 18px;-fx-font-weight: 600;");
        saludo.setStyle("-fx-text-fill: #e5be01;-fx-font-size: 25px");
        bienvenida.setSpacing(-50);
        bienvenida.setAlignment(Pos.BASELINE_CENTER);
        bienvenida.getChildren().addAll(nombre, saludo);
        primeraFila.getChildren().addAll(volver, bienvenida, imgGN);
        primeraFila.setSpacing(300);
        primeraFila.setAlignment(Pos.BASELINE_CENTER);
        mainLayout.getChildren().addAll(primeraFila, barraBusqueda, scrollPane);
    }

    public void setUsuario(Usuario_set_get usuario) {
        this.usuario = usuario;
    }

    public void personalizarNombre() {
        nombre.setText("Bienvenido " + usuario.getNombre());
    }

    public void resetearEscenaUsuarios() {
        nombre.setText("");
        resetChoise();
    }
    public void mostrarLibros() throws Exception{
        //este metodo es para desplegar todos los libros
        //se usa el objeto operacionesAdministrador de la clase OperacionesAdministrador para obtener todos los libros de la base de datos
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        LinkedList<Libros_set_get> libros = null;

        try {
            if(!(choiceBox.getValue().toString().equals("buscar por"))) {
                if (textField.getText().length() != 0)
                    libros = operacionesUsuario.listarLibros(choiceBox.getValue().toString().toLowerCase(), textField.getText());
            }
            else
                libros = operacionesUsuario.listarLibros();
            if (libros == null || libros.size() == 0) {
                listaLibros.addRow(0,new Label("no se encontraron resultados"));
            }
            else {
                listaLibros.addRow(0, new Label("Identificador"), new Label("Dispinibilidad"), new Label("Título"), new Label("Autor"), new Label("Género"), new Label("Editorial"), new Label("ISBN"), new Label("Fecha de Publicación"));
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
                    if (disponible.equals("Disponible")) {
                        disponibilidad.setText("✔ " + disponible);
                        disponibilidad.setStyle("-fx-font-size: 14px; -fx-text-fill: #50AE5D");
                    } else {
                        disponibilidad.setText("❌ " + disponible);
                        disponibilidad.setStyle("-fx-font-size: 14px; -fx-text-fill: #D16677");
                    }

                    listaLibros.addRow(i + 1, new Label(String.valueOf(id)), disponibilidad, new Label(titulo), new Label(autor), new Label(genero), new Label(editorial), new Label(isbn), new Label(fechaFromateada));
                }
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
    public void buscarLibros() throws Exception{
        borrarContenido();
        try {
            mostrarLibros();
        } catch (Exception e) {
            throw e;
        }
    }
    public void borrarContenido() {
        listaLibros.getChildren().clear();
    }
    public void resetChoise() {
        choiceBox.setValue("buscar por");
    }
}
