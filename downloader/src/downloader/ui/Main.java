package downloader.ui;


import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(1);
	    hbox.setStyle("-fx-background-color: #336699;");

	    Button buttonAdd = new Button("Add");
	    buttonAdd.setPrefSize(100, 20);

		TextField text = new TextField();

	    hbox.getChildren().addAll(text, buttonAdd);
	    
		
		ProgressBar progressbar = new ProgressBar();
		Download dl = new Download(getParameters());
		
		// bind download with physical display
		progressbar.progressProperty().bind(dl.progressProperty()); 
		
		root.setCenter(progressbar);
		root.setBottom(hbox);
		root.setRight(null);
		root.setLeft(null);
		
		stage.setTitle("Downloader");
		stage.setScene(new Scene(root));
		stage.show();
		
		// launch the thread in background
		new Thread(dl).start();
			
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}