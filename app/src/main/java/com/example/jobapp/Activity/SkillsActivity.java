package com.example.jobapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jobapp.Adapter.SkillsAdapter;
import com.example.jobapp.Helper.RecyclerItemTouchHelper;
import com.example.jobapp.LogicaNegocio.Skill;
import com.example.jobapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SkillsActivity extends AppCompatActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, SkillsAdapter.SkillsAdapterListener{

    private RecyclerView mRecyclerView;
    private SkillsAdapter mAdapter;
    private List<Skill> skillList;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton agregar;
    //private ModeloDatos modelo;

    String apiUrl = "http://192.168.1.12:8080/JobApp_Web/SkillServlet?";
    String apiUrlTemporal = "";

    String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        mRecyclerView = findViewById(R.id.recycler_skill);
        skillList = new ArrayList<>();
        mAdapter = new SkillsAdapter(skillList, this);
        coordinatorLayout = findViewById(R.id.coordinator_layout_skill);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        USER_ID = prefs.getString("ID", defaultValue);

        apiUrlTemporal = apiUrl+"opc=1&usuario="+USER_ID;
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();


        // Boton
        agregar = findViewById(R.id.agregar_skill);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarSkill();
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
            if (viewHolder instanceof SkillsAdapter.MyViewHolder) {
                // get the removed item name to display it in snack bar
                String id = String.valueOf(skillList.get(viewHolder.getAdapterPosition()).getId());
                String name = skillList.get(viewHolder.getAdapterPosition()).getNombre();

                ///CONECTA LA URL AL SERVLET PARA ELIMINAR PROFESOR
                apiUrlTemporal = apiUrl + "opc=3&id="+id;
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
                // save the index deleted
                final int deletedIndex = viewHolder.getAdapterPosition();
                // remove the item from recyclerView
                mAdapter.removeItem(viewHolder.getAdapterPosition());

                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar.make(coordinatorLayout, name + " removido!", Snackbar.LENGTH_LONG);
                snackbar.setAction("Deshacer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // undo is selected, restore the deleted item from adapter
                        mAdapter.restoreItem(deletedIndex);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        } else {
            //If is editing a row object
            Skill aux = mAdapter.getSwipedItem(viewHolder.getAdapterPosition());
            //send data to Edit Activity
            Intent intent = new Intent(this, ActAgrSkillActivity.class);
            intent.putExtra("editable", true);
            intent.putExtra("skill", aux);
            mAdapter.notifyDataSetChanged(); //restart left swipe view
            startActivity(intent);
        }
    }

    @Override
    public void onItemMove(int source, int target) {
        mAdapter.onItemMove(source, target);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds alumnoList to the action bar if it is present.
       /* getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView   !IMPORTANT
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change, every type on input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_search) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
      /*  if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }*/
        Intent a = new Intent(this, NavDrawerActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        super.onBackPressed();
    }

    @Override
    public void onContactSelected(Skill skill) { //TODO get the select item of recycleView
        Toast.makeText(getApplicationContext(), "Selected: " + skill.getUsuario(), Toast.LENGTH_LONG).show();
    }

    private void checkIntentInformation() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Skill aux;
            aux = (Skill) getIntent().getSerializableExtra("agregarSkill");
            if (aux == null) {
                aux = (Skill) getIntent().getSerializableExtra("editarSkill");
                if (aux != null) {
                    apiUrlTemporal = apiUrl + "opc=4&id="+aux.getId()+"usuario="+aux.getUsuario()+"&nombre="+aux.getNombre()+"&descripcion="+aux.getDescripcion();
                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                    Toast.makeText(getApplicationContext(),  "Editado correctamente!", Toast.LENGTH_LONG).show();
                }
            } else {
                apiUrlTemporal = apiUrl + "opc=2&usuario="+aux.getUsuario()+"&nombre="+aux.getNombre()+"&descripcion="+aux.getDescripcion();
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
                Toast.makeText(getApplicationContext(),  "Agregado correctamente!", Toast.LENGTH_LONG).show();
            }
        }


    }


    private void agregarSkill() {
        Intent intent = new Intent(this, ActAgrSkillActivity.class);
        intent.putExtra("editable", false);
        startActivity(intent);
    }


    //-------------------------------------

    /// Otra clase
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
                ArrayList<Skill> skillArrayList = (ArrayList<Skill>) gson.fromJson(s,
                        new TypeToken<ArrayList<Skill>>() {
                        }.getType());

                skillList = skillArrayList;
                mAdapter = new SkillsAdapter(skillList, SkillsActivity.this);
                coordinatorLayout = findViewById(R.id.coordinator_layout_skill);

                mRecyclerView = findViewById(R.id.recycler_skill);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(SkillsActivity.this, DividerItemDecoration.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);


                Log.w("ArrayList",skillArrayList.toString());

            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
