package com.itant.htmltextview.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * @author ITAnt
 * 
 */
public class ImageDownloader extends AsyncTask<String, integer, String> {

	private Handler handler;
	private String storedPath;
	private String imageUrl;

	/**
	 * 
	 * @param handler message handler(when downloading finished, send a message to update our TextView)
	 * @param storedPath the local path to save images that will be downloaded
	 * @param imageUrl the image's net url
	 */
	public ImageDownloader(Handler handler, String storedPath, String imageUrl) {
		this.handler = handler;
		this.storedPath = storedPath;
		this.imageUrl = imageUrl;
	}

	@Override
	protected String doInBackground(String... params) {
		Bitmap bitmap = null;
		File file = null;
		try {
			file = new File(storedPath + String.valueOf(imageUrl.hashCode()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (file.exists()) {
			bitmap = BitmapFactory.decodeFile(file.getPath());

			if (bitmap == null) {
				return null;
			}

		} else {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			try {
				file.createNewFile();
				URL url = new URL(imageUrl);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setConnectTimeout(500);

				InputStream is = httpURLConnection.getInputStream();

				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
				int index = -1;
				byte data[] = new byte[1024];

				while ((index = is.read(data)) != -1) {

					randomAccessFile.write(data, 0, index);

				}

				is.close();
				randomAccessFile.close();
				return file.getPath().toString();

			} catch (MalformedURLException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(integer... values) {

		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String dd) {
		if (dd == null) {
			this.cancel(true);

		} else {
			// finish downloading
			handler.sendEmptyMessage(100);
			this.cancel(true);
		}
		super.onPostExecute(dd);
	}
}