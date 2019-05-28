package com.example.jobapp.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapp.Activity.ActAgrPublicacionActivity;
import com.example.jobapp.LogicaNegocio.Publicaciones;
import com.example.jobapp.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PublicacionesAdapter extends RecyclerView.Adapter<PublicacionesAdapter.MyViewHolder> {

    private List<Publicaciones> publicacionesList;
    private List<Publicaciones> listaFiltrarPublicaciones;
    private PublicacionesAdapterListener listener;
    private Publicaciones borrarItem;

    /// Clase de ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitulo_Publicacion, txtDescripcion_Publicacion;
        //two layers
        public RelativeLayout viewForeground;
        public Button editar, borrar;


        public MyViewHolder(View view) {
            super(view);
            txtTitulo_Publicacion = view.findViewById(R.id.txt_titulo_pub);
            txtDescripcion_Publicacion = view.findViewById(R.id.txt_descripcion_pub);

            viewForeground = view.findViewById(R.id.view_foreground);

            editar = view.findViewById(R.id.edit_pubb);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //editarPublicacion();
                    Toast.makeText(v.getContext(), "Editar! ", Toast.LENGTH_LONG).show();
                }
            });

            borrar = view.findViewById(R.id.delete_pubb);
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //editarPublicacion();
                    Toast.makeText(v.getContext(), "Borrar! ", Toast.LENGTH_LONG).show();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listaFiltrarPublicaciones.get(getAdapterPosition()));
                }
            });
        }
    } /// Termina clase ViewHolder

    public PublicacionesAdapter(List<Publicaciones> publicacionesList, PublicacionesAdapterListener listener) {
        this.publicacionesList = publicacionesList
        ;
        this.listener = listener;
        //init filter
        this.listaFiltrarPublicaciones = publicacionesList;
    }

    @Override
    public PublicacionesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_pub, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PublicacionesAdapter.MyViewHolder holder, int position) {
        // rendering view
        final Publicaciones publicaciones = listaFiltrarPublicaciones.get(position);
        holder.txtTitulo_Publicacion.setText(publicaciones.getTitulo_publicacion());
        holder.txtDescripcion_Publicacion.setText(publicaciones.getDescripcion_publicacion());
    }

    @Override
    public int getItemCount() {
        return listaFiltrarPublicaciones.size();
    }

    public void removeItem(int position) {
        borrarItem = listaFiltrarPublicaciones.remove(position);
        Iterator<Publicaciones> iter = publicacionesList.iterator();
        while (iter.hasNext()) {
            Publicaciones aux = iter.next();
            if (borrarItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (listaFiltrarPublicaciones.size() == publicacionesList.size()) {
            listaFiltrarPublicaciones.add(position, borrarItem);
        } else {
            listaFiltrarPublicaciones.add(position, borrarItem);
            publicacionesList.add(borrarItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public interface PublicacionesAdapterListener {
        void onContactSelected(Publicaciones publicaciones);
    }
}
