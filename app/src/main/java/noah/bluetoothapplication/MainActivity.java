package noah.bluetoothapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    final static int  REQUEST_ENABLE_BT = 2;

    ArrayAdapter<String> arrayAdapter;

    private BluetoothAdapter bluetoothAdapter;

    BluetoothDevice bluetoothDevice;

    OutputStream ostream;
    InputStream istream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.layout_available_list, R.id.text_row);
        ((ListView)findViewById(R.id.list_view_available)).setAdapter(arrayAdapter);

        ((ListView)findViewById(R.id.list_view_available)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doConnect();
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled()) {
                ((Button)findViewById(R.id.butt_toggle)).setText("Disable");
            } else {
                ((Button)findViewById(R.id.butt_toggle)).setText("Enable");
            }
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                ((Button)findViewById(R.id.butt_toggle)).setText("Disable");
            }
        }
    }

    public void doToggle (View view) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Toast.makeText(getApplicationContext(), "This device does not support bluetooth", Toast.LENGTH_LONG).show();
            return;
        }
        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        } else {
            //Todo: we need to ask for permission for this
            adapter.disable();
            ((Button)findViewById(R.id.butt_toggle)).setText("Enable");
        }
    }


    public void doFind (View view) {

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "This device does not support bluetooth", Toast.LENGTH_LONG).show();
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is disabled", Toast.LENGTH_LONG).show();
            return;
        }
        Set<BluetoothDevice> bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();
        if (bluetoothDeviceSet.size() > 0) {
            for (BluetoothDevice device : bluetoothDeviceSet) {
                list.add(device.getName());

            }
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            ((ListView)findViewById(R.id.list_view_paired)).setAdapter(adapter1);

        }
        arrayAdapter.clear();

        boolean flag = bluetoothAdapter.startDiscovery();

        Log.e("FLAG", String.valueOf(flag));

        registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }


    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(Objects.equals(device.getName(), "TinyBT-3155")) {
                    Log.e("It happened!", "*********");
                    bluetoothDevice = device;
                }
                arrayAdapter.add(device.getName() + "\n" + device.getAddress());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private void doConnect() {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
            BluetoothSocket socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            ostream = socket.getOutputStream();
            istream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doSend(View view) {

        String text = ((EditText)findViewById(R.id.send_text)).getText().toString();
        text += "\n";
        try {
            ostream.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Handler handler = new Handler();
        final Thread workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    // stuff here

                    try {
                        int bytes = istream.available();
                        if (bytes > 0) {
                            byte[] readBytes = new byte[bytes];
                            istream.read(readBytes);
                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        workerThread.start();



    }



}
