package noah.NoMoreUnicorns;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import noah.bluetoothapplication.R;

public class Tut1 extends AppCompatActivity {

    int stepIndex;
    int input;

    Code exampleCode;
    Code testCode;


    TextView textTestCode;

    OutputStream ostream;
    InputStream istream;
    private BluetoothSocket bluetoothSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tut1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Part 1: If statements");




        exampleCode = new Code("Example");
        exampleCode.putLine("if (x == 2) {");
        exampleCode.putLine("print('A');");
        exampleCode.putLine("} else {");
        exampleCode.putLine("print('B');");
        exampleCode.putLine("}");

        try {
            testCode = (Code) exampleCode.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        testCode.setTitle("Test");

//        testCode.editLine(testCode.getLine("if (x == 2) {"), "if (x > 2) {");
        testCode.editLine(testCode.getLine("if (x == 2) {"), "if (x > 2) {");

        textTestCode = (TextView)findViewById(R.id.tut1_test_code);


        textTestCode.setText(testCode.toString());

        bluetoothSocket = ((MyApplication)this.getApplication()).getSocket();

        try {
            if (bluetoothSocket != null) {
                ostream = bluetoothSocket.getOutputStream();
                istream = bluetoothSocket.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doNextTest(View view) {
        Log.e("index", String.valueOf(stepIndex));
        switch(stepIndex ++) {

            case 0: {
                testCode.setCurrentLine(-1);
                textTestCode.setText(testCode.toString());
                ((EditText) findViewById(R.id.et_tut1)).setText("");
                ((Button)findViewById(R.id.butt_tut1_test)).setText("Submit");
                break;
            }
            case 1: {
                ((Button)findViewById(R.id.butt_tut1_test)).setText("Step Forward");
                try {
                    input = Integer.valueOf(((EditText) findViewById(R.id.et_tut1)).getText().toString());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    ((Button)findViewById(R.id.butt_tut1_test)).setText("Submit");
                    stepIndex --;
                }
                break;
            }

            case 2: {
                testCode.setCurrentLine(testCode.getCurrentLine());
                textTestCode.setText(testCode.toString());
                break;
            }
            case 3: {
                if (input > 2) {
                    testCode.setCurrentLine(testCode.getCurrentLine() + 1);
                    textTestCode.setText(testCode.toString());
                    ((TextView)findViewById(R.id.out_tut1)).setText("Output: A");
                } else {
                    testCode.setCurrentLine(testCode.getCurrentLine() + 2);
                    textTestCode.setText(testCode.toString());
                }
                break;
            }
            case 4: {
                if (input > 2) {
                    testCode.setCurrentLine(testCode.getCurrentLine() + 1);
                    textTestCode.setText(testCode.toString());
                } else {
                    testCode.setCurrentLine(testCode.getCurrentLine() + 1);
                    textTestCode.setText(testCode.toString());
                    ((TextView)findViewById(R.id.out_tut1)).setText("Output: B");
                }
                break;
            }
            case 5: {
                if (input > 2) {
                    testCode.setCurrentLine(testCode.getCurrentLine() + 2);
                    textTestCode.setText(testCode.toString());
                } else {
                    testCode.setCurrentLine(testCode.getCurrentLine() + 1);
                    textTestCode.setText(testCode.toString());
                }

                ((Button)findViewById(R.id.butt_tut1_test)).setText("Restart");
                stepIndex = 0;
                break;
            }

        }
    }

    public void goNext (View view) {
        Intent intent = new Intent(this, Tut2.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("stop","************");
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
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void doSend(View view) {
        Log.e("Do send", "************");

        String text = ((EditText) findViewById(R.id.bt_field1)).getText().toString();
        text += "\n";
        try {
            ostream.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
