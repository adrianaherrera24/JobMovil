package com.example.jobapp.Activity;

import android.content.Intent;
import android.graphics.Color;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jobapp.Adapter.TrabajoAdapter;
import com.example.jobapp.Helper.RecyclerItemTouchHelper;
import com.example.jobapp.LogicaNegocio.Trabajo;
import com.example.jobapp.R;

import java.util.ArrayList;
import java.util.List;

public class TrabajoActivity extends AppCompatActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, TrabajoAdapter.TrabajoAdapterListener{

    private RecyclerView mRecyclerView;
    private TrabajoAdapter mAdapter;
    private List<Trabajo> trabajoList;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton agregar;
    //private ModeloDatos modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo);

        mRecyclerView = findViewById(R.id.recycler_trab);
        trabajoList = new ArrayList<>();
        //modelo = new ModeloDatos();
        //trabajoList = modelo.getTrabajoList();
        mAdapter = new TrabajoAdapter(trabajoList, this);
        coordinatorLayout = findViewById(R.id.coordinator_layout_trab);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

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
                // get the removed item name to display it in snack bar
                String name = trabajoList.get(viewHolder.getAdapterPosition()).getEmpresa();

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
                    //found an item that can be updated
                    boolean founded = false;
                    for (Trabajo trabajo : trabajoList) {
                        if (trabajo.getUsuario().equals(aux.getUsuario())) {
                            trabajo.setEmpresa(aux.getEmpresa());
                            trabajo.setPuesto(aux.getPuesto());
                            trabajo.setDescripcion(aux.getDescripcion());
                            trabajo.setAnno_inicio(aux.getAnno_inicio());
                            trabajo.setAnno_final(aux.getAnno_final());
                            founded = true;
                            break;
                        }
                    }
                    //check if exist
                    if (founded) {
                        Toast.makeText(getApplicationContext(), "Editado correctamente!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No encontrado!", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                //found a new Alumno Object
                trabajoList.add(aux);
                Toast.makeText(getApplicationContext(),  "Agregado correctamente!", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void agregarTrabajo() {
        Intent intent = new Intent(this, ActAgrTrabajoActivity.class);
        intent.putExtra("editable", false);
        startActivity(intent);
    }

}
