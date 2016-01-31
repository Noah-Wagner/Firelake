package noah.NoMoreUnicorns;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import noah.bluetoothapplication.R;

public class Introduction extends AppCompatActivity {

    OutputStream ostream;
    InputStream istream;
    private BluetoothAdapter bluetoothAdapter;

    final static int REQUEST_ENABLE_BT = 2;


    BluetoothSocket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable();

        doConnect();


    }

    public void doNext(View view) {
        Intent intent = new Intent(this, Tut1.class);
        startActivity(intent);
    }


    private void doConnect() {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
//            socket = bluetoothAdapter.getRemoteDevice("00:06:66:6E:31:77").createRfcommSocketToServiceRecord(uuid);
            socket = bluetoothAdapter.getRemoteDevice("00:06:66:6E:31:55").createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            Log.e("we are here", "***********************");
            ((MyApplication) this.getApplication()).setSocket(socket);
            ostream = socket.getOutputStream();
            istream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
