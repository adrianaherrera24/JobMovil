package com.example.jobapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jobapp.Adapter.PerfilAdapter;
import com.example.jobapp.Helper.RecyclerItemTouchHelper;
import com.example.jobapp.LogicaNegocio.Perfil;
import com.example.jobapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity
        implements PerfilAdapter.PerfilAdapterListener{

    private RecyclerView mRecyclerView;
    private PerfilAdapter mAdapter;
    private List<Perfil> perfilList;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton editar;

    String apiUrl = "http://10.20.106.126:8080/JobApp_Web/PerfilServlet?";
    String apiUrlTemporal = "";
    String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recycler_per);
        perfilList = new ArrayList<>();
        mAdapter = new PerfilAdapter(perfilList, this);
        coordinatorLayout = findViewById(R.id.coordinator_layout_per);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        //OBTIENE LOS DATOS DESDE EL MOMENTO DEL LOGIN
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        USER_ID = prefs.getString("ID", defaultValue);

        //LISTA LOS DATOS DEL USUARIO
        apiUrlTemporal = apiUrl+"opc=1&id="+USER_ID;
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();

        // BOTON PARA AGREGAR SKILLS
        editar = findViewById(R.id.editar_per);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPerfil();
            }
        });

        //SWIPE PARA BORRAR O EDITAR
        //ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
       // new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        //RECIBE LOS DATOS DE SKILL ENVIADOS DESDE DONDE SE MODIFICA O AGREGA
        checkIntentInformation();

        //SE REFRESCA LA VISTA
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { return true; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, NavDrawerActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        super.onBackPressed();
    }

    @Override
    public void onContactSelected(Perfil perfil) { //TODO get the select item of recycleView
        Toast.makeText(getApplicationContext(), "Perfil Personal", Toast.LENGTH_LONG).show();
    }

    private void checkIntentInformation() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Perfil aux;
            aux = (Perfil) getIntent().getSerializableExtra("agregarPerfil");
            if (aux == null) {
                aux = (Perfil) getIntent().getSerializableExtra("editarPerfil");
                if (aux != null) {
                    apiUrlTemporal = apiUrl + "opc=4&id="+aux.getId()+"&nombre="+aux.getNombre()+"&lugar_habitacion="+aux.getLugar_habitacion()+"&edad="+aux.getEdad()+"&correo="+aux.getCorreo()+"&telefono="+aux.getTelefono()+"&descripcion="+aux.getDescripcion();
                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                    Toast.makeText(getApplicationContext(),  "Perfil Actualizado!", Toast.LENGTH_LONG).show();
                }
            } /*else {
                apiUrlTemporal = apiUrl + "opc=2&usuario="+aux.getUsuario()+"&institucion="+aux.getInstitucion()+"&carrera="+aux.getCarrera()+"&titulo="+aux.getCarrera()+"&anno="+aux.getAnno();
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
                Toast.makeText(getApplicationContext(),  "Agregado correctamente!", Toast.LENGTH_LONG).show();
            }*/
        }
    }

    private void editarPerfil() {
        /*Intent intent = new Intent(this, ActAgrEducacionActivity.class);
        intent.putExtra("editable", true);
        intent.putExtra("educacion", aux);
        startActivity(intent);*/
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() { }

        @Override
        protected String doInBackground(String... params) {

            // implement API in background and store the response in current variable
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrlTemporal);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();

                    }
                    // return the data to onPostExecute method
                    Log.w("JSON", current);
                    return current;


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }


        @Override
        protected void onPostExecute(String s) {
            Log.w("miJSON", s);

            try {
                Gson gson = new Gson();
                ArrayList<Perfil> perfilArrayList = (ArrayList<Perfil>) gson.fromJson(s,
                        new TypeToken<ArrayList<Perfil>>() {
                        }.getType());

                perfilList = perfilArrayList;
                mAdapter = new PerfilAdapter(perfilList, PerfilActivity.this);
                coordinatorLayout = findViewById(R.id.coordinator_layout_per);

                mRecyclerView = findViewById(R.id.recycler_per);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(PerfilActivity.this, DividerItemDecoration.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);

                Log.w("ArrayList",perfilArrayList.toString());

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
