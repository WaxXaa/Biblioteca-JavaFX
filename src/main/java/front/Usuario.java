package front;

import backend.modelos.Usuario_set_get;
import backend.user.OperacionesUsuario;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    Rectangle rect = new Rectangle();
    ImageInput imgV;
    Group imgG;

    public Usuario() {
        try {
            Image img = new Image(new FileInputStream("\\notification.png"));
            imgV = new ImageInput(img);
        } catch (Exception e) {

        }
        rect.setWidth(30);
        rect.setHeight(30);
        rect.setEffect(imgV);
        imgG = new Group(rect);

        choiceBox.getItems().add("Título");
        choiceBox.getItems().add("Autor");
        choiceBox.getItems().add("Género");
        mainLayout.setAlignment(Pos.BASELINE_CENTER);
        mainLayout.setSpacing(30);
        mainLayout.setStyle(
                "-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        listaLibros.setHgap(20);
        listaLibros.setVgap(5);
        scrollPane.setPrefSize(800, 400);
        scrollPane.setContent(listaLibros);
        imgG.setTranslateX(30);
        rect.setCursor(Cursor.HAND);
        scrollPane.setStyle("-fx-bacground-color: #f7f8ff");
        barraBusqueda.setAlignment(Pos.BASELINE_CENTER);
        textField.setMinSize(300, 30);
        volver.setStyle("-fx-background-color: #dad7cd");
        volver.setTranslateX(-30);
        volver.setCursor(Cursor.HAND);
        choiceBox.setStyle("-fx-background-color: transparent");
        choiceBox.setValue("ordenar por");
        barraBusqueda.getChildren().addAll(textField, choiceBox);
        nombre.setStyle("-fx-font-size: 18px;-fx-font-weight: 600;");
        saludo.setStyle("-fx-text-fill: #e5be01;-fx-font-size: 25px");
        bienvenida.setSpacing(-50);
        bienvenida.setAlignment(Pos.BASELINE_CENTER);
        bienvenida.getChildren().addAll(nombre, saludo);
        primeraFila.getChildren().addAll(volver, bienvenida, imgG);
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

    public void borrarNombre() {
        nombre.setText("");
    }
}
