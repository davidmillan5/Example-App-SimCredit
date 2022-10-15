package com.example.appsimcredito;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Crear el arreglo con las opciones que tendrá el spinner

    String[] arrTypeCredit ={"Vivienda","Educacion","Libre Inversión"};
    String selTypeCredit;

    //Instanciar el/los elementos con id definidos en el archivo XML (activity_mail.xml)
    /*EditText fullname, mail, amount;
    Spinner typeCredit;
    RadioButton install12,install24,install36;
    Switch fees;
    TextView totalDebt, installValue;*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fullname = findViewById(R.id.fullname);
        //Instanciar y referenciar los ID's del archivo xml
        EditText fullname = findViewById(R.id.fullname);
        EditText email = findViewById(R.id.email);
        EditText amount = findViewById(R.id.amount);
        Spinner typeCredit = findViewById(R.id.typeCredit);
        RadioButton  install12 = findViewById(R.id.install12);
        RadioButton  install24 = findViewById(R.id.install24);
        RadioButton  install36 = findViewById(R.id.install36);
        Switch fees = findViewById(R.id.fees);
        TextView totalDebt = findViewById(R.id.totalDebt);
        TextView installValue = findViewById(R.id.installValue);
        Button calculate = findViewById(R.id.calculate);
        Button clean = findViewById(R.id.clean);

        //Generar el adapterArray para que tome la información del array arrTypeCredit

        ArrayAdapter adptypeCredit = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,arrTypeCredit);

        //Llenar el spinner con el arrayadapter (adptypeCredit)
        typeCredit.setAdapter(adptypeCredit);


        //Generar el evento para el tipo de credito al seleccionar un item
        typeCredit.setOnItemSelectedListener(this);



        //Generar el evento click del boton calcular

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar que el fullname, email y amount se hayan digitado
                if(!fullname.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()
                        && !amount.getText().toString().isEmpty()){
                    double xamount = parseDouble(amount.getText().toString());
                    if(xamount >= 1000000 && xamount <=100000000){
                        //Chequear el tipo de credito
                        double rate = 0;
                        switch(selTypeCredit){
                            case "Vivienda":
                                rate = 0.01;
                                break;
                            case "Educacion":
                                rate = 0.005;
                                break;
                            case "Libre Inversión":
                                rate= 0.02;
                                break;

                        }

                        //Chequear el numero de cuotas

                        int xinstall = 12;

                        if(install24.isChecked()){
                            xinstall = 24;
                        }
                        if(install36.isChecked()){
                            xinstall = 36;
                        }

                        //Chequear cuota de manejo
                        double xfees = 0;

                        if(fees.isChecked()){
                            xfees = 10000;
                        }

                        // Calcular el total de la deuda

                        double xtotaldebt = xamount + (xamount * rate * xinstall);
                        double xinstallValue = (xtotaldebt/xinstall)+xfees;

                        //Dar formatos a los numeros con la clase DecimalFormat

                        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###.##");

                        //Asignar el contenido de las variables anteriores a los Id's respectivos....

                        totalDebt.setText(numberFormat.format(xtotaldebt));
                        installValue.setText(numberFormat.format(xinstallValue));


                    }else{
                        Toast.makeText(getApplicationContext(),"El monto debe estar entre 1 millon y 100 millones",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Ingresar nombre, correo y monto del prestamo",Toast.LENGTH_SHORT).show();
                }
            }
        });


        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname.setText("");
                email.setText("");
                amount.setText("");
                install12.setChecked(true);
                fees.setChecked(false);
                totalDebt.setText("");
                installValue.setText("");
                fullname.requestFocus();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Asignar a la variable seltypeCredit el contenido de la opcion seleccionada
        selTypeCredit = arrTypeCredit[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}