package com.example.jobapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobapp.LogicaNegocio.Skill;
import com.example.jobapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ActAgrSkillActivity extends AppCompatActivity {

    private FloatingActionButton ma_skill;
    private boolean editable = true;

    private EditText nombre, descripcion;
    private int id;
    private View activity;
    String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agr_skill);

        // Inicializacion de Variables
        editable = true;

        // boton para agregar o editar
        ma_skill = findViewById(R.id.ma_skill);

        //varibles del formulario
        nombre = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion);
        nombre.setText("");
        descripcion.setText("");

        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        USER_ID = prefs.getString("ID", defaultValue);

        //recibe informacion de admCarreraActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            editable = extras.getBoolean("editable");
            if (editable) {   // si su modo es de edicion
                Skill aux = (Skill) getIntent().getSerializableExtra("skill");
                // Completo los espacios con la información de cada carrera.
                id = aux.getId();
                nombre.setText(aux.getNombre());
                descripcion.setText(aux.getDescripcion());

                // Cuando se termina de editar
                ma_skill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editarSkill();
                    }
                });
            } else { // en caso de agregar una carrera nueva
                ma_skill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agregarSkill();
                    }
                });
            }
        }
    }

    public void agregarSkill() {
        if (validateForm()) {
            Skill skill = new Skill(
                    USER_ID,
                    nombre.getText().toString(),
                    descripcion.getText().toString()
            );
            Intent intent = new Intent(getBaseContext(), SkillsActivity.class);
            intent.putExtra("agregarSkill", skill);
            startActivity(intent);
            finish();
        }
    }

    public void editarSkill() {
        if (validateForm()) {
            Skill skill = new Skill(
                    id,
                    USER_ID,
                    nombre.getText().toString(),
                    descripcion.getText().toString()
            );

            Intent intent = new Intent(getBaseContext(), SkillsActivity.class);
            intent.putExtra("editarSkill", skill);
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
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Contiene algunos errores.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
