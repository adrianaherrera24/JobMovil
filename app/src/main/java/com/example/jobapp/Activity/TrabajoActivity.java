package com.example.jobapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.example.jobapp.Adapter.TrabajoAdapter;
import com.example.jobapp.Helper.RecyclerItemTouchHelper;
import com.example.jobapp.LogicaNegocio.Trabajo;
import com.example.jobapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrabajoActivity extends AppCompatActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, TrabajoAdapter.TrabajoAdapterListener{

    private RecyclerView mRecyclerView;
    private TrabajoAdapter mAdapter;
    private List<Trabajo> trabajoList;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton agregar;

    String apiUrl = "http://192.168.1.3:8080/JobApp_Web/TrabajoServlet?";
    String apiUrlTemporal = "";

    String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo);

        mRecyclerView = findViewById(R.id.recycler_trab);
        trabajoList = new ArrayList<>();
        mAdapter = new TrabajoAdapter(trabajoList, this);
        coordinatorLayout = findViewById(R.id.coordinator_layout_trab);

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
        apiUrlTemporal = apiUrl+"opc=1&usuario="+USER_ID;
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();

        // Boton
        agregar = findViewById(R.id.agregar_trabajo);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarTrabajo();
            }
        });

        //delete swiping left and right
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        // Receive the Carrera sent by AddUpdCarreraActivity
        checkIntentInformation();

        //refresh view
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (direction == ItemTouchHelper.START) {
            if (viewHolder instanceof TrabajoAdapter.MyViewHolder) {
                //SE OBTIENE EL ID DEL ELEMENTO PARA PODER ELIMINARLO
                String id = String.valueOf(trabajoList.get(viewHolder.getAdapterPosition()).getId());

                //CONECTA LA URL AL SERVLET PARA ELIMINAR PROFESOR
                apiUrlTemporal = apiUrl + "opc=3&id="+id;
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();

                // save the index deleted
                final int deletedIndex = viewHolder.getAdapterPosition();
                // remove the item from recyclerView
                mAdapter.removeItem(viewHolder.getAdapterPosition());
            }
        } else {
            //If is editing a row object
            Trabajo aux = mAdapter.getSwipedItem(viewHolder.getAdapterPosition());
            //send data to Edit Activity
            Intent intent = new Intent(this, ActAgrTrabajoActivity.class);
            intent.putExtra("editable", true);
            intent.putExtra("trabajo", aux);
            mAdapter.notifyDataSetChanged(); //restart left swipe view
            startActivity(intent);
        }
    }

    @Override
    public void onItemMove(int source, int target) {
        mAdapter.onItemMove(source, target);
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
    public void onContactSelected(Trabajo trabajo) { //TODO get the select item of recycleView
        Toast.makeText(getApplicationContext(), "Selected: " + trabajo.getUsuario(), Toast.LENGTH_LONG).show();
    }

    private void checkIntentInformation() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Trabajo aux;
            aux = (Trabajo) getIntent().getSerializableExtra("agregarTrabajo");
            if (aux == null) {
                aux = (Trabajo) getIntent().getSerializableExtra("editarTrabajo");
                if (aux != null) {
                    apiUrlTemporal = apiUrl + "opc=4&id="+aux.getId()+"&usuario="+aux.getUsuario()+"&empresa="+aux.getEmpresa()+"&puesto="+aux.getPuesto()+"&descripcion="+aux.getDescripcion()+"&ainicio="+aux.getAnno_inicio()+"&afinal="+aux.getAnno_final();
                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                    Toast.makeText(getApplicationContext(),  "Editado correctamente!", Toast.LENGTH_LONG).show();
                }
            } else {
                apiUrlTemporal = apiUrl + "opc=2&id="+aux.getId()+"&usuario="+aux.getUsuario()+"&empresa="+aux.getEmpresa()+"&puesto="+aux.getPuesto()+"&descripcion="+aux.getDescripcion()+"&ainicio="+aux.getAnno_inicio()+"&afinal="+aux.getAnno_final();
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
                Toast.makeText(getApplicationContext(),  "Agregado correctamente!", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void agregarTrabajo() {
        Intent intent = new Intent(this, ActAgrTrabajoActivity.class);
        intent.putExtra("editable", false);
        startActivity(intent);
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
                ArrayList<Trabajo> trabajoArrayList = (ArrayList<Trabajo>) gson.fromJson(s,
                        new TypeToken<ArrayList<Trabajo>>() {
                        }.getType());

                trabajoList = trabajoArrayList;
                mAdapter = new TrabajoAdapter(trabajoList, TrabajoActivity.this);
                coordinatorLayout = findViewById(R.id.coordinator_layout_trab);

                mRecyclerView = findViewById(R.id.recycler_trab);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(TrabajoActivity.this, DividerItemDecoration.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);

                Log.w("ArrayList",trabajoArrayList.toString());

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
