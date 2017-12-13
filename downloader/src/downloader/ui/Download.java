package downloader.ui;

import java.nio.file.Paths;
import java.util.ArrayList;

import downloader.fc.Downloader;
import javafx.application.Application.Parameters;
import javafx.beans.value.ObservableValue;

class Download implements Runnable {
	// urls to be downloded
	public ArrayList<String> urls = new ArrayList<String>();
	public ArrayList<Downloader> dlrs = new ArrayList<Downloader>();

	public Download(Parameters param) {
		// for debug
		urls.add("http://iihm.imag.fr/index.html");
		dlrs.add(new Downloader("http://iihm.imag.fr/index.html"));

//		for (String url : param.getRaw()) {
//			urls.add(url);
//			try {
//				dlrs.add(new Downloader(url));
//			} catch (RuntimeException e) {
//				System.err.format("skipping %s %s\n", url, e);
//				continue;
//			}
//		}
	}

	public ObservableValue<? extends Number> progressProperty() {
		return dlrs.get(0).progressProperty();
	}

	@Override
	public void run() {
		for (int i = 0; i < urls.size(); i++) {
			String url = urls.get(i);
			Downloader downloader = dlrs.get(i);

			System.out.format("Downloading %s:\n", downloader);

			downloader.progressProperty().addListener((obs, o, n) -> {
				System.out.print(".");
				System.out.flush();
			});

			String filename;
			try {
				filename = downloader.download();
			} catch (Exception e) {
				System.err.println("failed!");
				continue;
			}
			System.out.format(" file %s downloaded to %s\n", filename, Paths.get(".").toAbsolutePath().normalize().toString());
		}
	}
}
