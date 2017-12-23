package downloader.ui;

import javafx.scene.control.TextField;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//sujet tp: http://iihm.imag.fr/blanch/RICM4/IHM/tps/3-notification/

public class Main extends Application {

	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		final Lock lock = new ReentrantLock();

		/////////// this Hbox is the bottom container
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(1);
		hbox.setStyle("-fx-background-color: #336699;");

		// creation of the button Add and textfield
		Button buttonAdd = new Button("Add");
		buttonAdd.setPrefSize(50, 20);
		TextField text = new TextField();
		text.setPrefSize(250, 25);
		hbox.getChildren().addAll(text, buttonAdd);

		// creation of the first progress bar with download object depending on
		// parameters
		ProgressBar progressbar = new ProgressBar();
		progressbar.setPrefSize(200, 25);
		Download dl = new Download(getParameters());
		// bind download with physical display
		progressbar.progressProperty().bind(dl.progressProperty());

		/////////// this Vbox is the right container's container
		VBox vbox2 = new VBox();
		vbox2.setPadding(new Insets(0, 0, 0, 0));
		vbox2.setSpacing(1);
		vbox2.setStyle("-fx-background-color: #336699;");
		

		/////////// this Hbox is the right container of cancel, play and pause
		/////////// buttons
		HBox hbox2 = new HBox();
		hbox2.setPadding(new Insets(15, 12, 15, 12));
		hbox2.setSpacing(1);
		hbox2.setStyle("-fx-background-color: #336699;");

		// creation of the cancel, pause and play buttons
		Button buttonCancel = new Button("C");
		buttonCancel.setPrefSize(50, 20);
		Button buttonPausePlay = new Button("P");
		buttonPausePlay.setPrefSize(50, 20);
		
		
		// text
		Text t = new Text(10, 50, "\t http://iihm.imag.fr/index.html");
		t.setFont(new Font(15));

		hbox2.getChildren().addAll(progressbar , buttonCancel, buttonPausePlay);
		vbox2.getChildren().addAll(t,hbox2);

		// Let's add the boxes
		root.setCenter(vbox2);
		root.setBottom(hbox);
		root.setRight(null);
		root.setLeft(null);

		stage.setTitle("Downloader");
		stage.setScene(new Scene(root));
		stage.setHeight(400);
		stage.show();

		// launch the thread in background
		Thread t1 = new Thread(dl);
		t1.start();

		// Add button listener for a new link to be downloaded
		buttonAdd.setOnAction((event) -> {
			// recreating every displayer needed
			ProgressBar pb2 = new ProgressBar();
			pb2.setPrefSize(200, 25);
			Download dl2 = new Download(text.getText());
			pb2.progressProperty().bind(dl2.progressProperty());
			Button bC = new Button("C");
			bC.setPrefSize(50, 20);
			Button bPP = new Button("P");
			bPP.setPrefSize(50, 20);
			Text text2 = new Text(10, 50, "\t " + text.getText());
			text2.setFont(new Font(15));
			HBox hbox3 = new HBox();
			hbox3.setPadding(new Insets(15, 12, 15, 12));
			hbox3.setSpacing(1);
			hbox3.setStyle("-fx-background-color: #336699;");
			hbox3.getChildren().addAll(pb2, bC, bPP);
			vbox2.getChildren().addAll(text2, hbox3);

			Thread t2 = new Thread(dl2);
			t2.start();
			
			bC.setOnAction((event2) -> {
				vbox2.getChildren().remove(text2);
				vbox2.getChildren().remove(hbox3);
				t2.interrupt();
			});
			bPP.setOnAction((event2) -> {
				// not safe but works
				if(dl2.isPaused()){
					dl2.play();
					t2.resume();
				}else{
					dl2.pause();
					t2.suspend();
				}
			});
		});

		// Cancel button listener for the first link to be cancelled
		buttonCancel.setOnAction((event) -> {
			vbox2.getChildren().remove(t);
			vbox2.getChildren().remove(hbox2);
			t1.interrupt();
		});

		
		buttonPausePlay.setOnAction((event) -> {

			// TODO lock mechanism TODEBUG
//			synchronized (t1) {
//
//				if (dl.isPaused()) {
//					lock.lock();
//					try {
//						dl.play();
//						this.notify();
//					} finally {
//
//						lock.unlock();
//					}
//				} else {
//					lock.lock();
//					try {
//						dl.pause();
//						while (dl.isPaused()) {
//							t1.wait();
//						}
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					} finally {
//
//						lock.unlock();
//					}
//				}
//			}
			
			// not safe but works
			if(dl.isPaused()){
				dl.play();
				t1.resume();
			}else{
				dl.pause();
				t1.suspend();
			}
			
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}