package com.example.jobapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobapp.LogicaNegocio.Trabajo;
import com.example.jobapp.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TrabajoAdapter extends RecyclerView.Adapter<TrabajoAdapter.MyViewHolder> {

    private List<Trabajo> trabajoList;
    private List<Trabajo> listaFiltrarTrabajo;
    private TrabajoAdapterListener listener;
    private Trabajo borrarItem;

    /// Clase de ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtEmpresa, txtPuesto, txtDescripcion, txtInicio, txtFinal;
        //two layers
        public RelativeLayout viewForeground, viewBackgroundDelete, viewBackgroundEdit;

        public MyViewHolder(View view) {
            super(view);
            txtEmpresa = view.findViewById(R.id.txt_empresa);
            txtPuesto = view.findViewById(R.id.txt_puesto);
            txtDescripcion = view.findViewById(R.id.txt_descripcion_);
            txtInicio = view.findViewById(R.id.txt_inicio);
            txtFinal = view.findViewById(R.id.txt_final);

            viewBackgroundDelete = view.findViewById(R.id.view_background_delete);
            viewBackgroundEdit = view.findViewById(R.id.view_background_edit);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listaFiltrarTrabajo.get(getAdapterPosition()));
                }
            });
        }
    } /// Termina clase ViewHolder

    public TrabajoAdapter(List<Trabajo> trabajoList, TrabajoAdapterListener listener) {
        this.trabajoList = trabajoList;
        this.listener = listener;
        //init filter
        this.listaFiltrarTrabajo = trabajoList;
    }

    @Override
    public TrabajoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_trab, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrabajoAdapter.MyViewHolder holder, int position) {
        // rendering view
        final Trabajo trab = listaFiltrarTrabajo.get(position);
        holder.txtEmpresa.setText(trab.getEmpresa());
        holder.txtPuesto.setText(trab.getPuesto());
        holder.txtDescripcion.setText(trab.getDescripcion());
        holder.txtInicio.setText("Año inicio: " + String.valueOf(trab.getAnno_inicio()));
        holder.txtFinal.setText("Año final: " + String.valueOf(trab.getAnno_final()));
    }

    @Override
    public int getItemCount() {
        return listaFiltrarTrabajo.size();
    }

    public void removeItem(int position) {
        borrarItem = listaFiltrarTrabajo.remove(position);
        Iterator<Trabajo> iter = trabajoList.iterator();
        while (iter.hasNext()) {
            Trabajo aux = iter.next();
            if (borrarItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (listaFiltrarTrabajo.size() == trabajoList.size()) {
            listaFiltrarTrabajo.add(position, borrarItem);
        } else {
            listaFiltrarTrabajo.add(position, borrarItem);
            trabajoList.add(borrarItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public Trabajo getSwipedItem(int index) {
        if (this.trabajoList.size() == this.listaFiltrarTrabajo.size()) { //not filtered yet
            return trabajoList.get(index);
        } else {
            return listaFiltrarTrabajo.get(index);
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (trabajoList.size() == listaFiltrarTrabajo.size()) { // without filter
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(trabajoList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(trabajoList, i, i - 1);
                }
            }
        } else {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(listaFiltrarTrabajo, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(listaFiltrarTrabajo, i, i - 1);
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface TrabajoAdapterListener {
        void onContactSelected(Trabajo trabajo);
    }
}
