package com.example.menus;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class FrontendController extends Application {
    LoginUsuarioController luc;
    RegistroUsuarioController ruc;
    VBox vb1 = new VBox();
    HBox hb1 = new HBox();

    Scene scene1;
    Scene scene2;
    Scene scene3;
    @Override
    public void start(Stage stage){
        luc = new LoginUsuarioController();
        ruc = new RegistroUsuarioController();
        Button user = new Button("User");
        Button admin = new Button("Admin");
        Label lb1 = new Label("Bienvenido");
        Label lb2 = new Label("seleccione un modo de uso");
        user.setMinSize(100,30);
        user.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        admin.setMinSize(100,30);
        admin.setStyle("-fx-background-color: #023047; -fx-text-fill: #ffff; -fx-font-size: 16px");
        lb1.setStyle("-fx-font-size: 30; -fx-font-weight: bold");
        lb2.setStyle("-fx-font-size: 15");
        vb1.setStyle("-fx-background-color: linear-gradient(to right top, #ffffff, #eeefff, #d7e0ff, #b9d3ff, #93c7ff)");
        vb1.setAlignment(Pos.CENTER);
        hb1.setAlignment(Pos.BASELINE_CENTER);
        user.setCursor(Cursor.HAND);
        admin.setCursor(Cursor.HAND);
        lb1.setTranslateY(-90);
        hb1.setSpacing(30);
        vb1.setSpacing(30);
        hb1.getChildren().addAll(admin,user);
        vb1.getChildren().addAll(lb1,lb2,hb1);


        scene1 = new Scene(vb1, 1000,500);
        scene3 = new Scene(ruc.vb1,1000,500);
        scene2 = new Scene(luc.vb2, 1000, 500);
        user.setOnAction(e -> stage.setScene(scene2));
        luc.b3.setOnAction(e -> {

            stage.setScene(scene3);
        });
        luc.b4.setOnAction(e -> stage.setScene(scene1));
        ruc.volver.setOnAction(e -> stage.setScene(scene2));
        stage.setTitle("BIBLIOTECA");
        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}