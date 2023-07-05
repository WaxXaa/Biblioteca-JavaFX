package front;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LoginUsuarioController {
    GridPane gp1 = new GridPane();
    VBox vb2 = new VBox();
    Button b2 = new Button("Ingresar");
    Button b3 = new Button("registrarse");
    Button b4 = new Button("volver");
    Label l1 = new Label("nombre:");
    TextField tx1 = new TextField();
    Label l2 = new Label("E-mail:");
    TextField tx2 = new TextField();
    protected LoginUsuarioController() {

        vb2.setStyle("-fx-background-color: #ffff");
        tx2.setMinSize(200, 30);
        tx1.setMinSize(200,30);
        l1.setStyle("-fx-font-size: 20px");
        l2.setStyle("-fx-font-size: 20px");
        b2.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        b2.setMinSize(100,30);
        b2.setCursor(Cursor.HAND);
        b3.setTranslateY(-30);
        b3.setStyle("-fx-background-color: #ffff; -fx-text-fill: #219ebc");
        b3.setCursor(Cursor.HAND);
        b4.setStyle("-fx-background-color: #dad7cd");
        b4.setCursor(Cursor.HAND);


        gp1.setAlignment(Pos.BASELINE_CENTER);
        vb2.setAlignment(Pos.CENTER);
        vb2.setSpacing(40);
        gp1.setVgap(10);
        gp1.setHgap(20);

        gp1.addRow(0,l2,tx2);
        gp1.addRow(1, l1,tx1);
        vb2.getChildren().add(gp1);
        vb2.getChildren().addAll(b2,b3,b4);
    }
}
