package com.example.jobapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobapp.LogicaNegocio.Publicaciones;
import com.example.jobapp.R;

public class ActAgrPublicacionActivity extends AppCompatActivity {

    private FloatingActionButton ma_pub;
    private boolean editable = true;
    private EditText titulo, contenido;
    private View activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agr_publicacion);

        // Inicializacion de Variables
        editable = true;

        // boton para agregar o editar
        ma_pub = findViewById(R.id.ma_publicacion);

        //varibles del formulario
        titulo = findViewById(R.id.titulo_pub);
        contenido = findViewById(R.id.descripcion_pub);
        titulo.setText("");
        contenido.setText("");

        //recibe informacion de admCarreraActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            editable = extras.getBoolean("editable");
            if (editable) {   // si su modo es de edicion
                Publicaciones aux = (Publicaciones) getIntent().getSerializableExtra("publicacion");
                // Completo los espacios con la información de cada carrera.
                titulo.setText(aux.getTitulo_publicacion());
                contenido.setText(aux.getDescripcion_publicacion());

                // Cuando se termina de editar
                ma_pub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editarPublicacion();
                    }
                });
            } else { // en caso de agregar una carrera nueva
                ma_pub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agregarPublicacion();
                    }
                });
            }
        }
    }

    public void agregarPublicacion() {
        if (validateForm()) {
            Publicaciones pub = new Publicaciones(
                    titulo.getText().toString(),
                    contenido.getText().toString(),
                    "222"
            );

            Intent intent = new Intent(getBaseContext(), NavDrawerActivity.class);
            intent.putExtra("agregarPublicacion", pub);
            startActivity(intent);
            finish();
        }
    }

    public void editarPublicacion() {
        if (validateForm()) {
            Publicaciones pub = new Publicaciones(
                    titulo.getText().toString(),
                    contenido.getText().toString(),
                    "222"
            );

            Intent intent = new Intent(getBaseContext(), NavDrawerActivity.class);
            intent.putExtra("editarPublicacion", pub);
            startActivity(intent);
            finish();
        }
    }

    public boolean validateForm() {
        int error = 0;
        if (TextUtils.isEmpty(this.titulo.getText())) {
            titulo.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.contenido.getText())) {
            contenido.setError("Campo requerido");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Contiene algunos errores.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
