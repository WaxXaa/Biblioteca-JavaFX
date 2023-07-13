package front;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FrontendController extends Application {
    LoginUsuario loginUsuario;
    RegistroUsuario registroUsuario;
    Usuario usuario = new Usuario();
    Administrador adm = new Administrador();
    AdministradorRegistroPrestamo admRegistroPrestamo = new AdministradorRegistroPrestamo();
    AdministradorRegistroDevolucion admRegistroDevolucion = new AdministradorRegistroDevolucion();
    VBox vb1 = new VBox();
    HBox hb1 = new HBox();

    Scene escenaBienvenida;
    Scene escenaIngresoUsuario;
    Scene escenaRegistroUsuario;
    Scene escenaPrincipalUsuario;
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

        // se crean todas las escenas
        escenaBienvenida = new Scene(vb1, 1000,500);
        escenaRegistroUsuario = new Scene(registroUsuario.vb1,1000,500);
        escenaIngresoUsuario = new Scene(loginUsuario.mainLauoyt, 1000, 500);
        escenaPrincipalAdministrador = new Scene(adm.mainLayout,1000,500);
        escenaAdministradorRegistro = new Scene(admRegistroPrestamo.mainLayout,1000,500);
        escenaAdministradorDevolucion = new Scene(admRegistroDevolucion.mainLayout, 1000,500);
        escenaPrincipalUsuario = new Scene(usuario.mainLayout, 1000, 500);
        //estos son los evento al presionar los botones de la escena bienvenida
        user.setOnAction(e -> stage.setScene(escenaIngresoUsuario));
        admin.setOnAction(e -> {
            stage.setScene(escenaPrincipalAdministrador);
            try {
                adm.mostrarContenido();
            }catch (Exception y) {
                adm.mostrarErrorAlListarLibros("error al mostrar los libros \n" + y);
            }
        });


        //controladores de usuario

        loginUsuario.b3.setOnAction(e -> {

            stage.setScene(escenaRegistroUsuario);
        });
        //evento al ingresar
        loginUsuario.b2.setOnAction(e -> {

            try {
                if (loginUsuario.ingresar(loginUsuario.obtenerNombre(), loginUsuario.obtenerCorreo(), usuario)) {
                    usuario.personalizarNombre();
                    stage.setScene(escenaPrincipalUsuario);
                }
                else
                    loginUsuario.mostrarErrorAlIngresar("Credenciales Incorrectas");

            }
            catch (Exception ex) {
                loginUsuario.mostrarErrorAlIngresar(ex.toString());
            } finally {
                loginUsuario.resetearInput();
            }

        });
        loginUsuario.b4.setOnAction(e ->{
            stage.setScene(escenaBienvenida);
        } );
        registroUsuario.volver.setOnAction(e -> stage.setScene(escenaIngresoUsuario));
        usuario.volver.setOnAction(e -> {
            usuario.borrarNombre();
            stage.setScene(escenaIngresoUsuario);
        });
        usuario.imgG.setOnMouseClicked(e -> {

        });

        // controladores de administrador
        adm.volver.setOnAction(e -> {

            stage.setScene(escenaBienvenida);
        });
        //evento para dirigir a la escena de registro de prestamo
        adm.prestamo.setOnAction(e -> {
            stage.setScene(escenaAdministradorRegistro);
        });
        //evento para dirigir a la escena de registro de devoluciones
        adm.devolucion.setOnAction(e -> {
            stage.setScene(escenaAdministradorDevolucion);
        });
        // evento al cambiar si listar todos los libros o todos lo prestamos en la escena principal del administrador
        adm.opcionesListado.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                try {
                    adm.mostrarContenido(adm.opcionesListado.getSelectedToggle().getUserData().toString());
                }catch (Exception e) {
                    //en caso de que ocurra un error al obtener los libros de la BD se desplegara un mensaje de error
                    //para desplegar el mensaje se llama a el metodo mostrarErrorAlListarLibros y se le pasa el mensaje
                    adm.mostrarErrorAlListarLibros("error al mostrar los libros \n" + e);
                }
            }
        });
        // evento para volverr de la escena de prestamo
        admRegistroPrestamo.volver.setOnAction(e -> {
            stage.setScene(escenaPrincipalAdministrador);
        });

        // evento para volver de la escena de devoluciones
        admRegistroDevolucion.volver.setOnAction(e -> {
            stage.setScene(escenaPrincipalAdministrador);
        });
        // stage config
        stage.getIcons().add(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdZTGDJuuflypoqDPVO-3gaT_bwGtNoJGCdA&usqp=CAU"));
        stage.setTitle("BIBLIOTECA");
        stage.setScene(escenaBienvenida);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}