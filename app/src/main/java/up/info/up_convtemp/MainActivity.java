package up.info.up_convtemp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "UPConvTemp"; // Identifiant pour les messages de log
    private EditText editInputTemp; // Boite de saisie de la température
    private RadioGroup radioGroup; // Le groupe de boutons radio
    private RadioButton rbCelsius; // Bouton radio indiquant si la saisie est en Celsius
    private RadioButton rbFahrenheit; // Bouton radio indiquant si la saisie est en Fahrenheit
    private TextView dispResult; // Le TextView qui affichera le résultat
    private Button buttonConvert; // Le bouton pour lancer la conversion
    private Spinner spinnerInput;
    private Spinner spinnerOutput;
    private Switch switchAutoConv;

    boolean currentAutoMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editInputTemp = findViewById(R.id.editInputTemp);
        dispResult = findViewById(R.id.dispResult);
        buttonConvert = findViewById(R.id.buttonConvert);
        spinnerInput = findViewById(R.id.spinnerInput);
        spinnerOutput = findViewById(R.id.spinnerOutput);
        switchAutoConv = findViewById(R.id.switchAutoConv);

        switchAutoConv.setOnCheckedChangeListener((compoundButton, b) -> currentAutoMode = b);
        editInputTemp.setOnKeyListener((view, i, keyEvent) -> {convertIfNeed(view); return false;});

        spinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convertIfNeed(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerOutput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convertIfNeed(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void convertIfNeed(android.view.View view) {
        if (currentAutoMode) action_convert(view);
    }


    public void vibrate(long duration_ms) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(duration_ms < 1)
            duration_ms = 1;
        if (v != null && v.hasVibrator()) {
            // attention changement comportement avec API >= 26 (cf doc)
            if (Build.VERSION.SDK_INT >= 26) {
                v.vibrate(VibrationEffect.createOneShot(duration_ms, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(duration_ms);
            }
        }
        // sinon il nèy a pas de mecanisme de vibration
    }

    public void action_convert(android.view.View v) {


        try {
            String userInputString = editInputTemp.getText().toString();
            double userInput = Double.parseDouble(userInputString);
            String result = "";

            int posInput = spinnerInput.getSelectedItemPosition();
            int posOutput = spinnerOutput.getSelectedItemPosition();


            switch (posInput) {
                case 0: // celsius
                    switch (posOutput) {
                        // kelvin
                        case 0: result = userInput  + "°C"; break;

                        // fahrenheit
                        case 1: result = celsiusToFahrenheit(userInput) + "°F"; break;

                        // kelvin
                        case 2: result = celsiusToKelvin(userInput)  + "°K"; break;

                        default :
                            break;
                    }
                    break;
                case 1: // fahrenheit
                    switch (posOutput) {
                        // celsius
                        case 0: result = fahrenheitToCelsius(userInput)  + "°C"; break;

                        // fahrenheit
                        case 1: result = userInput  + "°F"; break;

                        // kelvin
                        case 2: result = fahrenheitToKelvin(userInput)  + "°K"; break;

                        default :
                            break;
                    }
                    break;

                case 2: // kelvin
                    Log.i(TAG, "********************************");
                    switch (posOutput) {
                        // celsius
                        case 0: result = kelvinToCelsius(userInput)  + "°C"; break;

                        // fahrenheit
                        case 1: result = kelvinToFahrenheit(userInput)  + "°F"; break;

                        // kelvin
                        case 2: result = userInput  + "°K"; break;

                        default :
                            break;
                    }
                    break;
            }


/*
            if (rbCelsius.isChecked()) {
                result = celsiusToFahrenheit(userInput) + "°F";
            } else {
                result = fahrenheitToCelsius(userInput) + "°C";
            }
*/

            dispResult.setText(result);
            //toast(result);

        } catch (Exception e) {
            vibrate(100);
            String error_msg = getString(R.string.ERROR_NAN_INPUT);
            toast(error_msg);
        }

    }

    public void toast(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }







    protected double celsiusToFahrenheit(double celsius){
        return (celsius * 9/5) + 32;
    }

    protected double fahrenheitToCelsius(double fahrenheit){
        return (fahrenheit - 32) * 5/9;
    }

    protected double celsiusToKelvin(double celsius){
        return (celsius + 273.15);
    }

    protected double kelvinToCelsius(double kelvin){
        return (kelvin - 273.15);
    }

    protected double fahrenheitToKelvin(double fahrenheit){
        return (fahrenheit + 459.67)/1.8;
    }

    protected double kelvinToFahrenheit(double kelvin){
        return (kelvin * 1.8 - 459.67);
    }

}