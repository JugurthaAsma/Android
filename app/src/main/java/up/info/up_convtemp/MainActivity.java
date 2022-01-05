package up.info.up_convtemp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "UPConvTemp"; // Identifiant pour les messages de log
    private EditText editInputTemp; // Boite de saisie de la température
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


    protected double celsiusToFahrenheit(double celsius){

        return (celsius * 9/5) + 32;
    }

    protected double fahrenheitToCelsius(double fahrenheit){

        return (fahrenheit - 32) * 5/9;
    }
}