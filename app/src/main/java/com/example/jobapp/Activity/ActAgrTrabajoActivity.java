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

import com.example.jobapp.LogicaNegocio.Trabajo;
import com.example.jobapp.R;

public class ActAgrTrabajoActivity extends AppCompatActivity {

    private FloatingActionButton ma_trabajo;
    private boolean editable = true; // Para  saber si es modo de edicion

    /* Datos requeridos para Carreras. */
    private EditText empresa, puesto, descripcion, anno_inicio, anno_final;
    private View activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agr_trabajo);

        // Inicializacion de Variables
        editable = true;

        // boton para agregar o editar
        ma_trabajo = findViewById(R.id.ma_trabajo);

        //varibles del formulario
        empresa = findViewById(R.id.empresa);
        puesto = findViewById(R.id.puesto);
        descripcion = findViewById(R.id.descripcion_);
        anno_inicio = findViewById(R.id.anno_inicio);
        anno_final = findViewById(R.id.anno_final);
        empresa.setText("");
        puesto.setText("");
        descripcion.setText("");
        anno_inicio.setText("");
        anno_final.setText("");

        //recibe informacion de admCarreraActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            editable = extras.getBoolean("editable");
            if (editable) {   // si su modo es de edicion
                Trabajo aux = (Trabajo) getIntent().getSerializableExtra("trabajo");
                // Completo los espacios con la información de cada carrera.
                empresa.setText(aux.getEmpresa());
                puesto.setText(aux.getPuesto());
                descripcion.setText(aux.getDescripcion());
                anno_inicio.setText(String.valueOf(aux.getAnno_inicio()));
                anno_final.setText(String.valueOf(aux.getAnno_final()));

                // Cuando se termina de editar
                ma_trabajo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editarTrabajo();
                    }
                });
            } else { // en caso de agregar una carrera nueva
                ma_trabajo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agregarTrabajo();
                    }
                });
            }
        }
    }

    public void agregarTrabajo() {
        if (validateForm()) {
            Trabajo trab = new Trabajo(
                    "222",
                    empresa.getText().toString(),
                    puesto.getText().toString(),
                    descripcion.getText().toString(),
                    Integer.parseInt(anno_inicio.getText().toString()),
                    Integer.parseInt(anno_final.getText().toString())
            );

            Intent intent = new Intent(getBaseContext(), TrabajoActivity.class);
            intent.putExtra("agregarTrabajo", trab);
            startActivity(intent);
            finish();
        }
    }

    public void editarTrabajo() {
        if (validateForm()) {
            Trabajo trab = new Trabajo(
                    "222",
                    empresa.getText().toString(),
                    puesto.getText().toString(),
                    descripcion.getText().toString(),
                    Integer.parseInt(anno_inicio.getText().toString()),
                    Integer.parseInt(anno_final.getText().toString())
            );

            Intent intent = new Intent(getBaseContext(), TrabajoActivity.class);
            intent.putExtra("editarTrabajo", trab);
            startActivity(intent);
            finish();
        }
    }

    public boolean validateForm() {
        int error = 0;
        if (TextUtils.isEmpty(this.empresa.getText())) {
            empresa.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.puesto.getText())) {
            puesto.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.descripcion.getText())) {
            descripcion.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.anno_inicio.getText())) {
            anno_inicio.setError("Campo requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.anno_final.getText())) {
            anno_final.setError("Campo requerido");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Contiene algunos errores.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
