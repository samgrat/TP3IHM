package downloader.ui;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//sujet tp: http://iihm.imag.fr/blanch/RICM4/IHM/tps/3-notification/

// TODO 
// - Ameliorer interface pour que la barre de dl fasse toute le fenetre
// - Ajouter un eventlistener sur buttonAdd pour creer une barre de dl:
//		- A la fois dans l'interface (champ Arraylist de ProgressBar dans Main?)
//		- Et aussi dans Download (modifier les champs urls et drls)
//		- lancer le thread => le telechargement
// honnetement je sais pas trop comment faire

public class Main extends Application {

	public void start(Stage stage) {
		BorderPane root = new BorderPane();

		/////////// this Hbox is the bottom container
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(1);
		hbox.setStyle("-fx-background-color: #336699;");

		// creation of the button Add and textfield
		Button buttonAdd = new Button("Add");
		buttonAdd.setPrefSize(100, 20);
		TextField text = new TextField();
		hbox.getChildren().addAll(text, buttonAdd);

		/////////// this Vbox is the center container
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vbox.setSpacing(1);
		vbox.setStyle("-fx-background-color: #336699;");

		// creation of the first progress bar with download object depending on
		// parameters
		ProgressBar progressbar = new ProgressBar();
		Download dl = new Download(getParameters());
		// bind download with physical display
		progressbar.progressProperty().bind(dl.progressProperty());
		vbox.getChildren().addAll(progressbar);

		/////////// this Vbox is the right container
		VBox vbox2 = new VBox();
		vbox2.setPadding(new Insets(15, 12, 15, 12));
		vbox2.setSpacing(1);
		vbox2.setStyle("-fx-background-color: #336699;");

		// creation of the cancel download button
		Button buttonCancel = new Button("Cancel");
		buttonCancel.setPrefSize(100, 20);
		vbox2.getChildren().addAll(buttonCancel);

		// Let's add the boxes
		root.setCenter(vbox);
		root.setBottom(hbox);
		root.setRight(vbox2);
		root.setLeft(null);

		stage.setTitle("Downloader");
		stage.setScene(new Scene(root));
		stage.show();

		// launch the thread in background
		Thread t1 = new Thread(dl);
		t1.start();

		// Add button listener for a new link to be downloaded
		buttonAdd.setOnAction((event) -> {
			ProgressBar pb2 = new ProgressBar();
			Download dl2 = new Download(text.getText());
			pb2.progressProperty().bind(dl2.progressProperty());
			vbox.getChildren().addAll(pb2);
			Button bC = new Button("Cancel");
			bC.setPrefSize(100, 20);
			vbox2.getChildren().addAll(bC);

			Thread t2 = new Thread(dl2);
			t2.start();
			bC.setOnAction((event2) -> {
				vbox.getChildren().remove(pb2);
				vbox2.getChildren().remove(bC);
				t2.interrupt();
			});
		});

		// Cancel button listener for the first link to be cancelled
		buttonCancel.setOnAction((event) -> {
			vbox.getChildren().remove(progressbar);
			vbox2.getChildren().remove(buttonCancel);
			t1.interrupt();
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}