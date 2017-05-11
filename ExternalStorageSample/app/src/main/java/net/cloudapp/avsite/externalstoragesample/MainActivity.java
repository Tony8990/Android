package net.cloudapp.avsite.externalstoragesample;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private final String fileName = "mj.jpg";
    private String TAG = "ExternalFileUsage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if external storage is available
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {

            // Try to find the file in a specific predefined dir
            File outFile = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    fileName);

            // If file doesn't exist, copy it there
            if (!outFile.exists())
                copyImageToExtStorage(outFile);
            else
                Toast.makeText(getApplicationContext(),"Image already present",Toast.LENGTH_LONG).show();

            // Show the image
            ImageView imageview = (ImageView) findViewById(R.id.image);
            String imgURI="file://" + outFile.getAbsolutePath();
            imageview.setImageURI(Uri.parse(imgURI));
        }
    }

    private void copyImageToExtStorage(File outFile) {
        try {
            BufferedOutputStream os = new BufferedOutputStream(
                    new FileOutputStream(outFile));

            BufferedInputStream is = new BufferedInputStream(getResources()
                    .openRawResource(R.raw.mj));

            copy(is, os);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException");
        }
    }

    private void copy(InputStream is, OutputStream os) {
        final byte[] buf = new byte[1024];
        int numBytes;
        try {
            while (-1 != (numBytes = is.read(buf))) {
                os.write(buf, 0, numBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            }
        }
    }

    public void DeleteFile(View v)
    {
        File outFile = new File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                fileName);
        outFile.delete();

        ImageView imageview = (ImageView) findViewById(R.id.image);
        imageview.setImageURI(null);
    }

}
