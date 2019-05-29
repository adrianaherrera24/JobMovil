package com.example.jobapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobapp.LogicaNegocio.Educacion;
import com.example.jobapp.R;

public class ActAgrEducacionActivity extends AppCompatActivity {

    private FloatingActionButton ma_educacion;
    private boolean editable = true;

    private EditText institucion, carrera, titulo, anno;
    private View activity;
    String USER_ID;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agr);

        // Inicializacion de Variables
        editable = true;

        // boton para agregar o editar
        ma_educacion = findViewById(R.id.ma_educacion);

        //varibles del formulario
        institucion = findViewById(R.id.institucion);
        carrera = findViewById(R.id.carrera);
        titulo = findViewById(R.id.titulo);
        anno = findViewById(R.id.anno);
        institucion.setText("");
        carrera.setText("");
        titulo.setText("");
        anno.setText("");

        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        USER_ID = prefs.getString("ID", defaultValue);

        //recibe informacion de admCarreraActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            editable = extras.getBoolean("editable");
            if (editable) {   // si su modo es de edicion
                Educacion aux = (Educacion) getIntent().getSerializableExtra("educacion");
                // Completo los espacios con la información de cada carrera.
                id = aux.getId();
                institucion.setText(aux.getInstitucion());
                carrera.setText(aux.getCarrera());
                titulo.setText(aux.getTitulo());
                anno.setText(aux.getAnno());

                // Cuando se termina de editar
                ma_educacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editarEducacion();
                    }
                });
            } else { // en caso de agregar una carrera nueva
                ma_educacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agregarEducacion();
                    }
                });
            }
        }
    }

    public void agregarEducacion() {
        if (validateForm()) {
            Educacion edu = new Educacion(
                    USER_ID,
                    institucion.getText().toString(),
                    carrera.getText().toString(),
                    titulo.getText().toString(),
                    anno.getText().toString()
            );

            Intent intent = new Intent(getBaseContext(), EducacionActivity.class);
            intent.putExtra("agregarEducacion", edu);
            startActivity(intent);
            finish();
        }
    }

    public void editarEducacion() {
        if (validateForm()) {
            Educacion edu = new Educacion(
                    id,
                    USER_ID,
                    institucion.getText().toString(),
                    carrera.getText().toString(),
                    titulo.getText().toString(),
                    anno.getText().toString()
            );

            Intent intent = new Intent(getBaseContext(), EducacionActivity.class);
            intent.putExtra("editarEducacion", edu);
            startActivity(intent);
            finish();
        }
    }

    public boolean validateForm() {
        int error = 0;
        if (TextUtils.isEmpty(this.institucion.getText())) {
            institucion.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.carrera.getText())) {
            carrera.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.titulo.getText())) {
            titulo.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.anno.getText())) {
            anno.setError("Campo requerido");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Contiene algunos errores.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
