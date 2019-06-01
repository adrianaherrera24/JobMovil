package com.example.jobapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapp.LogicaNegocio.Puesto;
import com.example.jobapp.R;

import java.util.Iterator;
import java.util.List;

public class PuestoAdapter extends RecyclerView.Adapter<PuestoAdapter.MyViewHolder> {

    private List<Puesto> puestoList;
    private List<Puesto> listaFiltrarPuesto;
    private PuestoAdapterListener listener;
    private Puesto borrarItem;

    /// Clase de ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitulo_Publicacion, txtDescripcion_Publicacion;
        //two layers
        public RelativeLayout viewForeground;


        public MyViewHolder(View view) {
            super(view);
            txtTitulo_Publicacion = view.findViewById(R.id.txt_titulo_pub);
            txtDescripcion_Publicacion = view.findViewById(R.id.txt_descripcion_pub);

            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listaFiltrarPuesto.get(getAdapterPosition()));
                }
            });
        }
    } /// Termina clase ViewHolder

    public PuestoAdapter(List<Puesto> puestoList, PuestoAdapterListener listener) {
        this.puestoList = puestoList;
        this.listener = listener;
        //init filter
        this.listaFiltrarPuesto = puestoList;
    }

    @Override
    public PuestoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_pub, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PuestoAdapter.MyViewHolder holder, int position) {
        // rendering view
        final Puesto puesto = listaFiltrarPuesto.get(position);
        holder.txtTitulo_Publicacion.setText(puesto.getNombre());
        holder.txtDescripcion_Publicacion.setText(puesto.getArea());
    }

    @Override
    public int getItemCount() {
        return listaFiltrarPuesto.size();
    }

    public void removeItem(int position) {
        borrarItem = listaFiltrarPuesto.remove(position);
        Iterator<Puesto> iter = puestoList.iterator();
        while (iter.hasNext()) {
            Puesto aux = iter.next();
            if (borrarItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (listaFiltrarPuesto.size() == puestoList.size()) {
            listaFiltrarPuesto.add(position, borrarItem);
        } else {
            listaFiltrarPuesto.add(position, borrarItem);
            puestoList.add(borrarItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public interface PuestoAdapterListener {
        void onContactSelected(Puesto puesto);
    }
}
