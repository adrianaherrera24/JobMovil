package com.example.jobapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapp.Adapter.PublicacionesAdapter;
import com.example.jobapp.Adapter.PuestoAdapter;
import com.example.jobapp.LogicaNegocio.Publicaciones;
import com.example.jobapp.LogicaNegocio.Puesto;
import com.example.jobapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PuestoAdapter.PuestoAdapterListener {

    private RecyclerView mRecyclerView;
    private PuestoAdapter mAdapter;
    private List<Puesto> puestoList;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton agregar;
    private Button editar, borrar;

    String apiUrl = "http://10.20.106.126:8080/JobApp_Web/PuestoUsuarioServlet?";
    String apiUrlTemporal = "";
    String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.privilegios();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recycler_pub);
        puestoList = new ArrayList<>();
        mAdapter = new PuestoAdapter(puestoList, this);
        coordinatorLayout = findViewById(R.id.coordinator_layout_pub);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        //LISTA LOS DATOS DEL USUARIO
        apiUrlTemporal = apiUrl + "opc=1&usuario=" + USER_ID;
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();

        // Receive the Carrera sent by AddUpdCarreraActivity
        checkIntentInformation();

        //refresh view
        mAdapter.notifyDataSetChanged();
    }

    private void privilegios() {
        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        String privilegio = prefs.getString("Privilegio", defaultValue);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem holder;
        //using privileges to lock data
        switch (privilegio) {
            case "user":
                holder = menu.findItem(R.id.nav_descripcion);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_skills);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_educacion);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_experiencia);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_referencia);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_puestos);
                holder.setVisible(false);
                break;
            case "empresa":
                holder = menu.findItem(R.id.nav_descripcion);
                holder.setVisible(false);
                holder = menu.findItem(R.id.nav_skills);
                holder.setVisible(false);
                holder = menu.findItem(R.id.nav_educacion);
                holder.setVisible(false);
                holder = menu.findItem(R.id.nav_experiencia);
                holder.setVisible(false);
                holder = menu.findItem(R.id.nav_referencia);
                holder.setVisible(false);
                holder = menu.findItem(R.id.nav_puestos);
                holder.setEnabled(true);
                break;
            default:    //if is none
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, LoginActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_descripcion) {
            abrirDescripcion();
        } else if (id == R.id.nav_skills) {
            abrirSkill();
        } else if (id == R.id.nav_educacion) {
            abrirEducacion();
        } else if (id == R.id.nav_experiencia) {
            abrirTrabajo();
        } else if (id == R.id.nav_referencia) {
            abrirReferencia();
        } else if (id == R.id.nav_puestos) {

        } else if (id == R.id.nav_logout) {
            abrirLogin();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onContactSelected(Puesto pub) { //TODO get the select item of recycleView
        Toast.makeText(getApplicationContext(), "Selected: " + pub.getNombre(), Toast.LENGTH_LONG).show();
    }

    public void abrirLogin() {
        finish(); /// Termina el proceso del layout
        Intent a = new Intent(NavDrawerActivity.this, LoginActivity.class);
        startActivity(a);
    }

    public void abrirEducacion() {
        finish(); /// Termina el proceso del layout
        Intent b = new Intent(NavDrawerActivity.this, EducacionActivity.class);
        startActivity(b);
    }

    public void abrirSkill() {
        finish(); /// Termina el proceso del layout
        Intent b = new Intent(NavDrawerActivity.this, SkillsActivity.class);
        startActivity(b);
    }

    public void abrirTrabajo() {
        finish(); /// Termina el proceso del layout
        Intent b = new Intent(NavDrawerActivity.this, TrabajoActivity.class);
        startActivity(b);
    }

    public void abrirReferencia() {
        finish(); /// Termina el proceso del layout
        Intent b = new Intent(NavDrawerActivity.this, ReferenciaActivity.class);
        startActivity(b);
    }

    public void abrirDescripcion() {
        finish(); /// Termina el proceso del layout
        Intent a = new Intent(NavDrawerActivity.this, PerfilActivity.class);
        startActivity(a);
    }

    private void checkIntentInformation() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Puesto aux;
            aux = (Puesto) getIntent().getSerializableExtra("agregarPuesto");
            if (aux == null) {
                aux = (Puesto) getIntent().getSerializableExtra("editarPuesto");
                if (aux != null) {
                    //found an item that can be updated
                   /* boolean founded = false;
                    for (Publicaciones pub : publicacionesList) {
                        if (pub.getUsuario().equals(aux.getUsuario())) {
                            pub.setTitulo_publicacion(aux.getTitulo_publicacion());
                            pub.setDescripcion_publicacion(aux.getDescripcion_publicacion());
                            founded = true;
                            break;
                        }
                    }
                    //check if exist
                    if (founded) {
                        Toast.makeText(getApplicationContext(), "Editado correctamente!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No encontrado!", Toast.LENGTH_LONG).show();
                    }*/
                }
            } else {
                //found a new Alumno Object
                //publicacionesList.add(aux);
                //Toast.makeText(getApplicationContext(), "Agregado correctamente!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void agregarPublicacion() {
        Intent intent = new Intent(this, ActAgrPublicacionActivity.class);
        intent.putExtra("editable", false);
        startActivity(intent);
    }

    private void editarPublicacion() {
        Intent intent = new Intent(this, ActAgrPublicacionActivity.class);
        intent.putExtra("editable", true);
        startActivity(intent);
    }


    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
        }

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
                ArrayList<Puesto> puestolArrayList = (ArrayList<Puesto>) gson.fromJson(s,
                        new TypeToken<ArrayList<Puesto>>() {
                        }.getType());

                puestoList = puestolArrayList;
                mAdapter = new PuestoAdapter(puestoList, NavDrawerActivity.this);
                coordinatorLayout = findViewById(R.id.coordinator_layout_pub);

                mRecyclerView = findViewById(R.id.recycler_pub);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(NavDrawerActivity.this, DividerItemDecoration.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);

//                Log.w("ArrayList", puestolArrayList.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}