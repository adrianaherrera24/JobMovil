package com.example.jobapp.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobapp.Activity.ActAgrEducacionActivity;
import com.example.jobapp.Activity.EducacionActivity;
import com.example.jobapp.LogicaNegocio.Educacion;
import com.example.jobapp.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EducacionAdapter extends RecyclerView.Adapter<EducacionAdapter.MyViewHolder> {

    private List<Educacion> listaEducacion;
    private List<Educacion> listaFiltrarEducacion;
    private EducacionAdapterListener listener;
    private Educacion borrarItem;

    /// Clase de ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInstitucion, txtCarrera, txtTitulo, txtAnno;
        //two layers
        public RelativeLayout viewForeground, viewBackgroundDelete, viewBackgroundEdit;


        public MyViewHolder(View view) {
            super(view);
            txtInstitucion = view.findViewById(R.id.txt_institucion);
            txtCarrera = view.findViewById(R.id.txt_carrera);
            txtTitulo = view.findViewById(R.id.txt_titulo);
            txtAnno = view.findViewById(R.id.txt_anno);

            viewBackgroundDelete = view.findViewById(R.id.view_background_delete);
            viewBackgroundEdit = view.findViewById(R.id.view_background_edit);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listaFiltrarEducacion.get(getAdapterPosition()));
                }
            });
        }
    } /// Termina clase ViewHolder


    public EducacionAdapter(List<Educacion> listaEducacion, EducacionAdapterListener listener) {
        this.listaEducacion = listaEducacion;
        this.listener = listener;
        //init filter
        this.listaFiltrarEducacion = listaEducacion;
    }

    @Override
    public EducacionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_educ, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EducacionAdapter.MyViewHolder holder, int position) {
        // rendering view
        final Educacion educacion = listaFiltrarEducacion.get(position);
        holder.txtInstitucion.setText(educacion.getInstitucion());
        holder.txtCarrera.setText(educacion.getCarrera());
        holder.txtTitulo.setText("Título: " + educacion.getTitulo());
        holder.txtAnno.setText("Año de finalización: " + educacion.getAnno());
    }

    @Override
    public int getItemCount() {
        return listaFiltrarEducacion.size();
    }

    public void removeItem(int position) {
        borrarItem = listaFiltrarEducacion.remove(position);
        Iterator<Educacion> iter = listaEducacion.iterator();
        while (iter.hasNext()) {
            Educacion aux = iter.next();
            if (borrarItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (listaFiltrarEducacion.size() == listaEducacion.size()) {
            listaFiltrarEducacion.add(position, borrarItem);
        } else {
            listaFiltrarEducacion.add(position, borrarItem);
            listaEducacion.add(borrarItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public Educacion getSwipedItem(int index) {
        if (this.listaEducacion.size() == this.listaFiltrarEducacion.size()) { //not filtered yet
            return listaEducacion.get(index);
        } else {
            return listaFiltrarEducacion.get(index);
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (listaEducacion.size() == listaFiltrarEducacion.size()) { // without filter
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(listaEducacion, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(listaEducacion, i, i - 1);
                }
            }
        } else {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(listaFiltrarEducacion, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(listaFiltrarEducacion, i, i - 1);
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface EducacionAdapterListener {
        void onContactSelected(Educacion educacion);
    }
}
