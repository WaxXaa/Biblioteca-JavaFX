package front;

import backend.modelos.Libros_set_get;
import backend.modelos.Prestamo_set_get;
import backend.user.OperacionesUsuario;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Notificaciones {
    OperacionesUsuario operacionesUsuario = new OperacionesUsuario();
    VBox mainLayout = new VBox();
    GridPane devolucionesGrid = new GridPane();
    ScrollPane scrollPane = new ScrollPane();
    Scene escenaNotificaciones;
    Label mensajeSinDevoluciones = new Label("no tiene libros pendientes por entregar, Gracias");
    public void mostrarNotificaciones() {
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to right top, #ffffff, #f7f8ff, #edf1ff, #e1ebff, #d2e6ff);");
        scrollPane.setContent(devolucionesGrid);

        Stage pupup = new Stage();
        pupup.initModality(Modality.APPLICATION_MODAL);
        pupup.setTitle("Notificaciones");

        escenaNotificaciones = new Scene(mainLayout,400,250);
        pupup.showAndWait();
    }

    public void listarProximasDevoluciones() {
        LinkedList<Prestamo_set_get> listaDevoluciones;
        try {
            listaDevoluciones =
            if(listaDevoluciones.size() == 0) {
                mainLayout.getChildren().add(mensajeSinDevoluciones);
            }
            else {
                for(int i = 0; i < listaDevoluciones.size(); i++) {
                    devolucionesGrid.addRow();
                }
            }
        } catch (Exception e) {

        }

    }
    public void resetearDevoluciones() {
        devolucionesGrid.getChildren().clear();
    }
}
