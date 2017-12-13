package downloader.ui;

import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;


//Créez un classe downloader.ui.Main qui ouvre un fenêtre principale, 
//lance en tâche de fond (dans un Thread dédié) le téléchargement des URL passées sur la ligne de commande. 
//Chaque téléchargement est représenté par une barre de progression qui donne un feedback à sa progression. 
//Pour cela, réalisez un ChangeListener qui s'abonne à downloader.fc.Downloader e
//t met à jour la valeur de la barre de progression lorsque la propriété progress change. 
//Pour gérer la liste des téléchargement, vous pouvez utiliser le conteneur VBox à l'intérieur d'un ScrollPane.

// progressbar.progressProperty().bind(downloader.progressProperty());
// ou
// progressProperty.addListener((obs, o, n) -> { 
// 	Platform.numLater(()->{
//	progressbar.setProgress(n);
// });

public class Main extends Application {
	
	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		ProgressBar progressbar = new ProgressBar();
		Download dl = new Download(getParameters());
		
		// bind download with physical display
		progressbar.progressProperty().bind(dl.progressProperty());  
		root.setCenter(progressbar);
		
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