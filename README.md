# HtmlTextView(Deprecated)
<font color=red size=5>Works bad on Android 7.0, not recommend.</font><br/>
Show html codes in the TextView on Android 3.0 or higher version.<br/>
As we can't do some downloading on Android 3.0 or higher version in the main thread, so we should download the images in the child thread, and then update the UI through handler.<br/>
# How to use:
1.Add related permission in your AndroidManifest.xml:<br/>
android.permission.INTERNET<br/>
android.permission.WRITE_EXTERNAL_STORAGE<br/>
2.Import ImageDownloader.java<br/>
3.Replace the TextView, local path that saves the image, and your html codes in MainActivity.java<br/>
![image](https://github.com/ITAnt/HtmlTextView/raw/master/screenshots/2.png)

# Run your app, it looks like this:
![image](https://github.com/ITAnt/HtmlTextView/raw/master/screenshots/1.png)
