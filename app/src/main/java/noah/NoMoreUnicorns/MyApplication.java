package noah.NoMoreUnicorns;


import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class MyApplication extends Application {

    BluetoothDevice device;

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    BluetoothSocket socket;



    public synchronized BluetoothDevice getBTDevice() {
        if (device == null) {
            return null;
        } else {
            return device;
        }

    }

}
