package noah.NoMoreUnicorns;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import noah.bluetoothapplication.R;

public class Tut2 extends AppCompatActivity {

    OutputStream ostream;
    InputStream istream;
    private BluetoothSocket socket;
    Timer timer;

    TextView TV_count;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tut2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        socket = ((MyApplication)this.getApplication()).getSocket();

        setTitle("Part 2: While loops");

        setTimer();

        TV_count = (TextView)findViewById(R.id.count);

        TV_count.setText(String.valueOf(count));



    }

    public void setTimer(View view) {
        count = 0;
        setTimer();
    }

    private void setTimer() {
        timer = new Timer();

        runOnUiThread(new TimerTask() {
            @Override
            public void run() {

            }
        });

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (count < 5) {
                    ++count;
                    TV_count.setText(String.valueOf(count));
                } else {
                    timer.cancel();
                }
            }
        }, 3000, 3000);
    }

    public void goNext (View view) {
        /*Intent intent = new Intent(this, Tut3.class);
        startActivity(intent);*/
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "************");
        if (istream != null) {
            try {
                istream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ostream != null) {
            try {
                ostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}
