package front;

import backend.user.OperacionesUsuario;
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
    OperacionesUsuario operacionesUsuario = new OperacionesUsuario();
    LoginUsuario loginUsuario;
    RegistroUsuario registroUsuario;
    Usuario usuario = new Usuario();
    Administrador adm = new Administrador();
    AdministradorRegistroPrestamo admRegistroPrestamo = new AdministradorRegistroPrestamo();
    AdministradorRegistroDevolucion admRegistroDevolucion = new AdministradorRegistroDevolucion();
    Notificaciones notificaciones = new Notificaciones();
    Informes informes = new Informes();
    VBox vb1 = new VBox();
    HBox hb1 = new HBox();

    Scene escenaBienvenida;
    Scene escenaIngresoUsuario;
    Scene escenaRegistroUsuario;
    Scene escenaPrincipalUsuario;
    Scene escenaPrincipalAdministrador;
    Scene escenaAdministradorRegistro;
    Scene escenaAdministradorDevolucion;
    Scene escenaInformes;


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
        escenaInformes = new Scene(informes.mainLayout, 1000,500);
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
                if (loginUsuario.ingresar(loginUsuario.obtenerNombre(), loginUsuario.obtenerCorreo(), usuario,notificaciones, operacionesUsuario)) {
                    usuario.personalizarNombre();
                    stage.setScene(escenaPrincipalUsuario);
                    try {
                        usuario.buscarLibros();
                    } catch (Exception err) {
                        usuario.mostrarErrorAlListar(err.toString());
                    }
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
        //volver a la escena de bienvenida
        loginUsuario.b4.setOnAction(e ->{
            stage.setScene(escenaBienvenida);
        } );
        //volverde la escena de registro de usuario a la escena de ingreso
        registroUsuario.volver.setOnAction(e -> stage.setScene(escenaIngresoUsuario));
        //evento que controla el registro de usuarios
        registroUsuario.registrar.setOnAction(e -> {
            try {
                boolean registro = registroUsuario.registrarUsuario(registroUsuario.comprobarSiHayDatosIntroducidos(), operacionesUsuario);
                if(registro) {
                    registroUsuario.mostrarExitoAlRegistrar("su registro se ha realizado exitosamente");
                    stage.setScene(escenaIngresoUsuario);
                }
                else
                    registroUsuario.mostrarErrorAlRegistrar("por favor, ingrese nuevamente los datos");
            }catch (Exception err) {
                registroUsuario.mostrarErrorAlRegistrar(err.toString());
            }
        });

        //controlador de usuario
        //evento para volver de la escena principal del usuario a la escena de ingreso
        usuario.volver.setOnAction(e -> {
            usuario.resetearEscenaUsuarios();
            stage.setScene(escenaIngresoUsuario);
        });
        //evento que controla el mostrar las devoluciones pendientes
        usuario.imgGN.setOnMouseClicked(e -> {
            notificaciones.resetearDevoluciones();
            try {
                notificaciones.listarProximasDevoluciones();
                Notificaciones.mostrarNotificaciones();
            }catch (Exception err) {
             usuario.mostrarErrorAlListar(err.toString());
            }
            notificaciones.setUsuario(usuario.getUsuario());
        });
        //evento que controla la busqueda de un libro especifico
        usuario.buscar.setOnAction(e -> {
            try {
                usuario.buscarLibros();

            } catch (Exception err) {
                usuario.mostrarErrorAlListar(err.toString());
            }
        });
        //evento que controla el mostrar todo el catalogo de libros al usuario
        usuario.catalogoEntero.setOnAction(e -> {
            try {
                usuario.resetChoise();
                usuario.buscarLibros();

            } catch (Exception err) {
                usuario.mostrarErrorAlListar(err.toString());
            }
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
        //evento al registrar prestamo
        admRegistroPrestamo.registrar.setOnAction(e -> {
            try{

                if(admRegistroPrestamo.registrarPrestamo()){
                    admRegistroPrestamo.mostrarMensaje("El prestamo se ha registrado  exitosamente", "REGISTRADO!", "Prestamo Registrado");
                    try {
                        adm.mostrarContenido();
                    }catch (Exception y) {
                        adm.mostrarErrorAlListarLibros("error al mostrar los libros \n" + y);
                    }
                    admRegistroPrestamo.resetearCampos();
                    stage.setScene(escenaPrincipalAdministrador);
                } else {
                    admRegistroPrestamo.mostrarMensaje("No se ha podido registrar el Prestamo", "ERROR", "Prestamo no Registrado");
                    admRegistroPrestamo.resetearCampos();
                }
            }catch (Exception err) {
                admRegistroPrestamo.mostrarMensaje(err.getMessage(), "ERROR", "Lo sentimos, ha ocurrido");
            }
        });

        admRegistroDevolucion.registrar.setOnAction(e -> {
            try{

                if(admRegistroDevolucion.registrarDevolucion()){
                    admRegistroDevolucion.mostrarMensaje("La devolucion se ha registrado exitosamente", "REGISTRADO!", "Devolucion Registrada");
                    try {
                        adm.mostrarContenido();
                    }catch (Exception y) {
                        adm.mostrarErrorAlListarLibros("error al mostrar los libros \n" + y);
                    }
                    admRegistroDevolucion.resetearCampos();
                    stage.setScene(escenaPrincipalAdministrador);
                } else {
                    admRegistroDevolucion.mostrarMensaje("No se ha podido registrar la devolucion", "ERROR", "devolucion no Registrada");
                    admRegistroDevolucion.resetearCampos();
                }
            }catch (Exception err) {
                admRegistroDevolucion.mostrarMensaje(err.getMessage(), "ERROR", "Lo sentimos, ha ocurrido");
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

        adm.informes.setOnAction(e -> {
            stage.setScene(escenaInformes);
            try {
                informes.mostrarContenido();
            }catch (Exception ex) {
                informes.mostrarMensajeAlRealizarInformes("Lo sentimos, ha ocurrido un error al realizar los informes" + ex);
            }
        });
        informes.opcionesListado.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                try {
                    informes.mostrarContenido(informes.opcionesListado.getSelectedToggle().getUserData().toString());
                }catch (Exception e) {
                    //en caso de que ocurra un error al obtener los libros de la BD se desplegara un mensaje de error
                    //para desplegar el mensaje se llama a el metodo  y se le pasa el mensaje
                    informes.mostrarMensajeAlRealizarInformes("Lo sentimos, ha ocurrido un error al realizar los informes" + e);
                }
            }
        });

        informes.volver.setOnAction(e -> {
            stage.setScene(escenaPrincipalAdministrador);
        });
        // stage config
        stage.getIcons().add(new Image("C:\\Users\\Mosquera\\Documents\\Alejandro17\\Code_java\\menuS\\src\\main\\resources\\icon.png"));
        stage.setTitle("BIBLIOTECA");
        stage.setScene(escenaBienvenida);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}