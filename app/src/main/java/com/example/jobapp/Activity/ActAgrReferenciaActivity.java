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
import com.example.jobapp.LogicaNegocio.Referencia;
import com.example.jobapp.R;

public class ActAgrReferenciaActivity extends AppCompatActivity {

    private FloatingActionButton ma_ref;
    private boolean editable = true;

    private EditText nombre, descripcion, telefono, email;
    private View activity;
    String USER_ID;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agr_referencia);

        // Inicializacion de Variables
        editable = true;

        // boton para agregar o editar
        ma_ref = findViewById(R.id.ma_ref);

        //varibles del formulario
        nombre = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion_ref);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email_ref);
        nombre.setText("");
        descripcion.setText("");
        telefono.setText("");
        email.setText("");

        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        USER_ID = prefs.getString("ID", defaultValue);

        //recibe informacion de admCarreraActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            editable = extras.getBoolean("editable");
            if (editable) {   // si su modo es de edicion
                Referencia aux = (Referencia) getIntent().getSerializableExtra("referencia");
                // Completo los espacios con la información de cada carrera.
                id = aux.getId();
                nombre.setText(aux.getNombre());
                descripcion.setText(aux.getDescripcion());
                telefono.setText(aux.getTelefono());
                email.setText(aux.getEmail());

                // Cuando se termina de editar
                ma_ref.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editarReferencia();
                    }
                });
            } else { // en caso de agregar una carrera nueva
                ma_ref.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agregarReferencia();
                    }
                });
            }
        }
    }

    public void agregarReferencia() {
        if (validateForm()) {
            Referencia ref = new Referencia(
                    USER_ID,
                    nombre.getText().toString(),
                    descripcion.getText().toString(),
                    telefono.getText().toString(),
                    email.getText().toString()
            );

            Intent intent = new Intent(getBaseContext(), ReferenciaActivity.class);
            intent.putExtra("agregarReferencia", ref);
            startActivity(intent);
            finish();
        }
    }

    public void editarReferencia() {
        if (validateForm()) {
            Referencia ref = new Referencia(
                    id,
                    USER_ID,
                    nombre.getText().toString(),
                    descripcion.getText().toString(),
                    telefono.getText().toString(),
                    email.getText().toString()
            );

            Intent intent = new Intent(getBaseContext(), ReferenciaActivity.class);
            intent.putExtra("editarReferencia", ref);
            startActivity(intent);
            finish();
        }
    }

    public boolean validateForm() {
        int error = 0;
        if (TextUtils.isEmpty(this.nombre.getText())) {
            nombre.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.descripcion.getText())) {
            descripcion.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.telefono.getText())) {
            telefono.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.email.getText())) {
            email.setError("Campo requerido");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Contiene algunos errores.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
