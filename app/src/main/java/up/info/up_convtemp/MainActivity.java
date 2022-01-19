package up.info.up_convtemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "UPConvTemp"; // Identifiant pour les messages de log
    private EditText editInputTemp; // Boite de saisie de la température
    private RadioGroup radioGroup; // Le groupe de boutons radio
    private RadioButton rbCelsius; // Bouton radio indiquant si la saisie est en Celsius
    private RadioButton rbFahrenheit; // Bouton radio indiquant si la saisie est en Fahrenheit
    private TextView dispResult; // Le TextView qui affichera le résultat
    private Button buttonConvert; // Le bouton pour lancer la conversion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editInputTemp = findViewById(R.id.editInputTemp);
        rbCelsius = findViewById(R.id.rbCelsius);
        rbFahrenheit = findViewById(R.id.rbFahrenheit);
        dispResult = findViewById(R.id.dispResult);
        buttonConvert = findViewById(R.id.buttonConvert);



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
            if (rbCelsius.isChecked()) {
                result = celsiusToFahrenheit(userInput) + "°F";
            } else {
                result = fahrenheitToCelsius(userInput) + "°C";
            }
            dispResult.setText(result);
            toast(result);

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
}