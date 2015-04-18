package com.itant.htmltextview;

import java.io.File;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.TextView;

import com.itant.htmltextview.utils.ImageDownloader;

public class MainActivity extends Activity {

	private TextView tv_html_container;
	private String storedPath = android.os.Environment.getExternalStorageDirectory().getPath().toString() + "/images";
	private String htmlContent = "<h2>Welcome</h2><a href=\"http://blog.csdn.net/ithouse\">http://blog.csdn.net/ithouse</a><br/><IMG src=\"http://upload.techweb.com.cn/2015/0414/1428981388832.jpg\"/><br/><b>Let's go climbing.</b><br/><IMG src=\"http://upload.techweb.com.cn/2015/0414/1428981388855.jpg\"/>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv_html_container = (TextView) findViewById(R.id.tv_html_container);
		new MyThread().start();
	}

	/**
	 * update TextView UI and download images in the child thread
	 * @author ITAnt
	 * 
	 */
	public class MyThread extends Thread {
		public void run() {
			handler.sendEmptyMessage(1);
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			tv_html_container.setText(Html.fromHtml(htmlContent, imgGetter, null));
		}
	};
	
	private ImageGetter imgGetter = new Html.ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			Log.e("Image Path", source);
			String fileString = storedPath + String.valueOf(source.hashCode());
			if (new File(fileString).exists()) {
				Log.i("DEBUG", fileString + "  eixts");
				// get local Drawable
				drawable = Drawable.createFromPath(fileString);
				// set image's bounds
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

				return drawable;
			} else {
				Log.i("DEBUG", fileString + " Do not eixts");
				new ImageDownloader(handler, storedPath, source).execute();
			}
			return null;
		}
	};
}
