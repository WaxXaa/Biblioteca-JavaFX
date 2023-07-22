package front;

import backend.modelos.Libros_set_get;
import backend.modelos.Prestamo_set_get;
import backend.modelos.Usuario_set_get;
import backend.user.OperacionesUsuario;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class Notificaciones {
    OperacionesUsuario operacionesUsuario = new OperacionesUsuario();
    private Usuario_set_get usuario = new Usuario_set_get();
    static VBox mainLayout = new VBox();
    GridPane devolucionesGrid = new GridPane();
    ScrollPane scrollPane = new ScrollPane();
    Label mensajeSinDevoluciones = new Label("no tiene libros pendientes por entregar, Gracias");
    Label mensajeConDevoluciones = new Label("ATENCION!, usted tiene libros pendientes por entregar!");
    public Notificaciones() {
        mensajeConDevoluciones.setStyle("-fx-text-fill: #D16677");
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #f7f8ff");
        scrollPane.setContent(devolucionesGrid);
        scrollPane.setStyle("-fx-background-color: #f7f8ff; -fx-padding: 2px");
        devolucionesGrid.setStyle("-fx-background-color: #f7f8ff");
        scrollPane.setPrefSize(400,250);
        devolucionesGrid.setMinWidth(400);
        devolucionesGrid.setMinHeight(250);

    }
    public static void mostrarNotificaciones() {

        Stage pupup = new Stage();
        pupup.initModality(Modality.WINDOW_MODAL);
        pupup.setTitle("Notificaciones");
        HBox root = new HBox();
        root.getChildren().add(mainLayout);
        Scene escenaNotificaciones = new Scene(root,400,250);
        pupup.setScene(escenaNotificaciones);
        pupup.setMaxHeight(220);
        pupup.setMaxWidth(400);
        pupup.show();
    }

    public void listarProximasDevoluciones() throws Exception{
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        LinkedList<Prestamo_set_get> listaDevoluciones;
        try {
            listaDevoluciones = operacionesUsuario.ListaDevolucion(usuario.getId_usuario());
            if(listaDevoluciones.size() == 0) {
                devolucionesGrid.addRow(0,mensajeSinDevoluciones);
            }
            else {

                mainLayout.getChildren().addAll(mensajeConDevoluciones,scrollPane);
                devolucionesGrid.addRow(1,new Label("ID_libro"), new Label("Titulo"), new Label("Autor"), new Label("Fecha Prestamo"));
                for(int i = 0; i < listaDevoluciones.size(); i++) {
                    Label id_libro = new Label(listaDevoluciones.get(i).getLibro().getId_libro() + "");
                    Label titulo = new Label(listaDevoluciones.get(i).getLibro().getTitulo());
                    Label autor = new Label(listaDevoluciones.get(i).getLibro().getAutor());
                    Date fecha_prestamo = listaDevoluciones.get(i).getFecha_prestamo();
                    String fecha_prestamo_formateada = formato.format(fecha_prestamo);
                    Label fecha_prestamo_label = new Label(fecha_prestamo_formateada);
                    devolucionesGrid.addRow(i+2, id_libro, titulo,autor,fecha_prestamo_label);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
    public void resetearDevoluciones() {
        devolucionesGrid.getChildren().clear();
    }

    public Usuario_set_get getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario_set_get usuario) {
        this.usuario = usuario;
    }

}
