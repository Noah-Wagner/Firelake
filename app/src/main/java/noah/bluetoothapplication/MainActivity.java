package noah.bluetoothapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    final static int  REQUEST_ENABLE_BT = 2;

    ArrayAdapter<String> arrayAdapter;

    private BluetoothAdapter bluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item);
        ((ListView)findViewById(R.id.list_view_paired)).setAdapter(arrayAdapter);

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

        } else {
            arrayAdapter.clear();

            ibluetoothAdapter.startDiscovery()
            registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));


        }

    }


    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("*****", "*****1");
            String action = getIntent().getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = getIntent().getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrayAdapter.add(device.getName() + "\n" + device.getAddress());
                arrayAdapter.notifyDataSetChanged();

            }
        }
    };


}
