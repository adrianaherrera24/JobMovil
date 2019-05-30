package com.example.jobapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobapp.LogicaNegocio.Referencia;
import com.example.jobapp.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ReferenciaAdapter extends RecyclerView.Adapter<ReferenciaAdapter.MyViewHolder>{

    private List<Referencia> referenciaList;
    private List<Referencia> listaFiltrarReferencia;
    private ReferenciaAdapter.ReferenciaAdapterListener listener;
    private Referencia borrarItem;

    /// Clase de ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombre, txtDescripcion, txtTelefono, txtEmail;
        //two layers
        public RelativeLayout viewForeground, viewBackgroundDelete, viewBackgroundEdit;


        public MyViewHolder(View view) {
            super(view);
            txtNombre = view.findViewById(R.id.txt_nombre_ref);
            txtDescripcion = view.findViewById(R.id.txt_descripcion_ref);
            txtTelefono = view.findViewById(R.id.txt_telefono_ref);
            txtEmail = view.findViewById(R.id.txt_email);

            viewBackgroundDelete = view.findViewById(R.id.view_background_delete);
            viewBackgroundEdit = view.findViewById(R.id.view_background_edit);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listaFiltrarReferencia.get(getAdapterPosition()));
                }
            });
        }
    } /// Termina clase ViewHolder


    public ReferenciaAdapter(List<Referencia> referenciaList, ReferenciaAdapter.ReferenciaAdapterListener listener) {
        this.referenciaList = referenciaList;
        this.listener = listener;
        //init filter
        this.listaFiltrarReferencia = referenciaList;
    }

    @Override
    public ReferenciaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_ref, parent, false);

        return new ReferenciaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReferenciaAdapter.MyViewHolder holder, int position) {
        // rendering view
        final Referencia ref = listaFiltrarReferencia.get(position);
        holder.txtNombre.setText(ref.getNombre());
        holder.txtDescripcion.setText(ref.getDescripcion());
        holder.txtEmail.setText("Correo electrónico: " + ref.getEmail());
        holder.txtTelefono.setText("Teléfono: " + ref.getTelefono());
    }

    @Override
    public int getItemCount() {
        return listaFiltrarReferencia.size();
    }

    public void removeItem(int position) {
        borrarItem = listaFiltrarReferencia.remove(position);
        Iterator<Referencia> iter = referenciaList.iterator();
        while (iter.hasNext()) {
            Referencia aux = iter.next();
            if (borrarItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (listaFiltrarReferencia.size() == referenciaList.size()) {
            listaFiltrarReferencia.add(position, borrarItem);
        } else {
            listaFiltrarReferencia.add(position, borrarItem);
            referenciaList.add(borrarItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public Referencia getSwipedItem(int index) {
        if (this.referenciaList.size() == this.listaFiltrarReferencia.size()) { //not filtered yet
            return referenciaList.get(index);
        } else {
            return listaFiltrarReferencia.get(index);
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (referenciaList.size() == listaFiltrarReferencia.size()) { // without filter
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(referenciaList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(referenciaList, i, i - 1);
                }
            }
        } else {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(listaFiltrarReferencia, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(listaFiltrarReferencia, i, i - 1);
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface ReferenciaAdapterListener {
        void onContactSelected(Referencia referencia);
    }
}
