package noah.NoMoreUnicorns;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import noah.bluetoothapplication.R;

public class Tut1 extends AppCompatActivity {

    int[] stepIndex;
    int input;

    Code exampleCode;
    Code testCode;

    TextView textExampleCode;
    TextView textTestCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tut1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stepIndex = new int[2];
        //Todo research array init
        stepIndex[0] = 1;
        stepIndex[1] = 1;

        exampleCode = new Code("Example");
        exampleCode.putLine("if (x == 2)");
        exampleCode.putLine("{");
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

        textExampleCode = (TextView)findViewById(R.id.tut1_example_code);
        textTestCode = (TextView)findViewById(R.id.tut1_test_code);

        textExampleCode.setText(exampleCode.toString());
        textTestCode.setText(testCode.toString());

    }

    public void doStep(View view) {



        switch(stepIndex[0]++) {
            case 0: {
                ((Button)findViewById(R.id.butt_tut1_step)).setText("Step Forward");
                break;
            }
            case 1: {
                exampleCode.setCurrentLine(exampleCode.getCurrentLine()+ 1);
                textExampleCode.setText(exampleCode.toString());
//                ((TextView)findViewById(R.id.tut1_example_code)).setText("Test code:\n\nif(x == 2) { <- \n\tprint(\'A\');\n } else { \n \tprint('B');\n}");
                break;
            }
            case 2: {
                exampleCode.setCurrentLine(exampleCode.getCurrentLine()+ 1);
                textExampleCode.setText(exampleCode.toString());
//                ((TextView)findViewById(R.id.tut1_example_code)).setText("Test code:\n\nif(x == 2) { \n\tprint(\'A\'); <- \n } else { \n \tprint('B');\n}");

                break;
            }
            case 3: {
                exampleCode.setCurrentLine(exampleCode.getCurrentLine() + 1);
                textExampleCode.setText(exampleCode.toString());
//                ((TextView)findViewById(R.id.tut1_example_code)).setText("Test code:\n\nif(x == 2) { \n\tprint(\'A\');\n } else { <- \n \tprint('B');\n}");
                break;
            }
            case 4: {
                exampleCode.setCurrentLine(exampleCode.getCurrentLine()+ 1);
                textExampleCode.setText(exampleCode.toString());
//                ((TextView)findViewById(R.id.tut1_example_code)).setText("Test code:\n\nif(x == 2) { \n\tprint(\'A\');\n } else { \n \tprint('B');\n} <-");
                ((Button)findViewById(R.id.butt_tut1_step)).setText("Restart");
                stepIndex[0] = 0;
                break;
            }

        }


    }

    public void doNextTest(View view) {

        switch(stepIndex[1]++) {
            case 0: {
                ((EditText)findViewById(R.id.et_tut1)).setText("");
                ((Button)findViewById(R.id.butt_tut1_test)).setText("Submit");
            }


            case 1: {
                input = Integer.valueOf(((EditText)findViewById(R.id.et_tut1)).getText().toString());
                ((Button)findViewById(R.id.butt_tut1_test)).setText("Step Forward");
                break;
            }
            case 2: {
                testCode.setCurrentLine(1);
                textTestCode.setText(testCode.toString());
//                ((TextView)findViewById(R.id.tut1_test_code)).setText("Test code:\n\nif(x > 2) { <- \n\tprint(\'A\');\n } else { \n \tprint('B');\n}");
                break;
            }
            case 3: {
                if (input > 2) {
                    testCode.setCurrentLine(testCode.getCurrentLine() + 1);
                    textTestCode.setText(testCode.toString());
//                    ((TextView) findViewById(R.id.tut1_test_code)).setText("Test code:\n\nif(x > 2) { \n\tprint(\'A\'); <- \n } else { \n \tprint('B');\n}");
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
                stepIndex[1] = 0;
                break;
            }

        }
    }

}
