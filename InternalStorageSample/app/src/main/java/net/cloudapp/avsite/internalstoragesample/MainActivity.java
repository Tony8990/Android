package net.cloudapp.avsite.internalstoragesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final static String fileName = "LogFile1.txt";
    private String TAG = "InternalFileUse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);

        // Check whether fileName already exists in directory used
        // by the openFileOutput() method.
        // If the text file doesn't exist, then create it now
        if (!getFileStreamPath(fileName).exists()) {
            try {
                writeFile();
            } catch (FileNotFoundException e) {
                Log.i(TAG, "FileNotFoundException");
            }
        }
        // Read the data from the text file and display it
        try {
            readFile(ll);
        } catch (IOException e) {
            Log.i(TAG, "IOException");
        }
    }

    private void writeFile() throws FileNotFoundException {
        FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);

        PrintWriter pw = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(fos)));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        pw.println("The user opened this app for the first time on:");
        pw.println(currentDateandTime);

        pw.close();
    }

    private void readFile(LinearLayout ll) throws IOException {

        FileInputStream fis = openFileInput(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = "";

        while (null != (line = br.readLine())) {
            TextView tv = new TextView(this);
            tv.setTextSize(24);
            tv.setText(line);
            ll.addView(tv);
        }
        br.close();

    }

}
