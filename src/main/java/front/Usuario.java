package front;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Usuario {
    VBox mainLayout = new VBox();
    GridPane listaLibros = new GridPane();
    ScrollPane scrollPane = new ScrollPane();
    ChoiceBox choiceBox = new ChoiceBox();
    Button volver = new Button("volver");
    TextField textField = new TextField();
    HBox barraBusqueda = new HBox();
    public Usuario() {
        choiceBox.getItems().add("Título");
        choiceBox.getItems().add("Autor");
        choiceBox.getItems().add("Género");
        mainLayout.setAlignment(Pos.BASELINE_CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        listaLibros.setHgap(20);
        listaLibros.setVgap(5);
        scrollPane.setPrefSize(900,400);
        scrollPane.setContent(listaLibros);
        scrollPane.setStyle("-fx-bacground-color: #f7f8ff");
    }
}
