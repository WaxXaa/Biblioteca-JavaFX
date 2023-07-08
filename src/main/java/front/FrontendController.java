package front;

import backend.user.Prueba;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FrontendController extends Application {
    Prueba pru = new Prueba();
    LoginUsuario loginUsuario;
    RegistroUsuario registroUsuario;
    Administrador adm = new Administrador();
    VBox vb1 = new VBox();
    HBox hb1 = new HBox();

    Scene escenaBienvenida;
    Scene escenaIngresoUsuario;
    Scene escenaRegistroUsuario;
    Scene escenaMainUsuario;
    Scene escenaPrincipalAdministrador;
    Scene escenaAdministradorRegistro;
    Scene escenaAdministradorDevolucion;


    @Override
    public void start(Stage stage){
        loginUsuario = new LoginUsuario();
        registroUsuario = new RegistroUsuario();
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


        escenaBienvenida = new Scene(vb1, 1000,500);
        escenaRegistroUsuario = new Scene(registroUsuario.vb1,1000,500);
        escenaIngresoUsuario = new Scene(loginUsuario.vb2, 1000, 500);
        escenaPrincipalAdministrador = new Scene(adm.mainLayout,1000,500);
        user.setOnAction(e -> stage.setScene(escenaIngresoUsuario));
        admin.setOnAction(e -> {
            adm.borrarContenido();
            adm.mostrarContenido();
            stage.setScene(escenaPrincipalAdministrador);
        });
        //controladores de usuario
        loginUsuario.b3.setOnAction(e -> {

            stage.setScene(escenaRegistroUsuario);
        });
        loginUsuario.b2.setOnAction(e -> {
            boolean existeUsuario = false;
        // boolean existeUsuario = objeto de una clase del backend para el user.metodo para consultar si existe el usuario (luc.cedula.getValue());
        if(existeUsuario) {
            stage.setScene(escenaMainUsuario);
        }
        else {
            loginUsuario.mostrarErrorAlIngresar();
        }
        });
        loginUsuario.b4.setOnAction(e ->{
            stage.setScene(escenaBienvenida);
        } );
        registroUsuario.volver.setOnAction(e -> stage.setScene(escenaIngresoUsuario));
        // controladores de administrador
        adm.volver.setOnAction(e -> {

            stage.setScene(escenaBienvenida);
        });
        adm.opcionesListado.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                adm.mostrarContenido(adm.opcionesListado.getSelectedToggle().getUserData().toString());
            }
        });


        // stage config
        stage.setTitle("BIBLIOTECA");
        stage.setScene(escenaBienvenida);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}