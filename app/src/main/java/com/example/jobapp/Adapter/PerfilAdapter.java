package com.example.jobapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobapp.LogicaNegocio.Perfil;
import com.example.jobapp.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.MyViewHolder> {

    private List<Perfil> perfilList;
    private List<Perfil> listaFiltrarPerfil;
    private PerfilAdapterListener listener;
    private Perfil borrarItem;

    /// Clase de ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombre, txtCedula, txtEdad, txtLugar, txtCorreo, txtTelefono, txtDescripcion;
        //two layers
        public RelativeLayout viewForeground; //viewBackgroundDelete, viewBackgroundEdit;


        public MyViewHolder(View view) {
            super(view);
            txtNombre = view.findViewById(R.id.txt_nombre_per);
            txtCedula = view.findViewById(R.id.txt_cedula);
            txtEdad = view.findViewById(R.id.txt_edad);
            txtLugar = view.findViewById(R.id.txt_lugar);
            txtCorreo = view.findViewById(R.id.txt_correo_per);
            txtTelefono = view.findViewById(R.id.txt_telefono_per);
            txtDescripcion = view.findViewById(R.id.txt_descripcion_per);

           // viewBackgroundDelete = view.findViewById(R.id.view_background_delete);
            //viewBackgroundEdit = view.findViewById(R.id.view_background_edit);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listaFiltrarPerfil.get(getAdapterPosition()));
                }
            });
        }
    } /// Termina clase ViewHolder


    public PerfilAdapter(List<Perfil> perfilList, PerfilAdapterListener listener) {
        this.perfilList = perfilList;
        this.listener = listener;
        //init filter
        this.listaFiltrarPerfil = perfilList;
    }

    @Override
    public PerfilAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_perfil, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PerfilAdapter.MyViewHolder holder, int position) {
        // rendering view
        final Perfil perfil = listaFiltrarPerfil.get(position);
        holder.txtNombre.setText("Nombre: " + perfil.getNombre());
        holder.txtCedula.setText("ID: " + perfil.getId());
        holder.txtEdad.setText("Edad: " + perfil.getEdad());
        holder.txtLugar.setText("Vivienda: " + perfil.getLugar_habitacion());
        holder.txtCorreo.setText("Correo: " + perfil.getCorreo());
        holder.txtTelefono.setText("Teléfono: " + perfil.getTelefono());
        holder.txtDescripcion.setText("Descripción Personal \n" + perfil.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return perfilList.size();
    }

    public void removeItem(int position) {
        borrarItem = listaFiltrarPerfil.remove(position);
        Iterator<Perfil> iter = perfilList.iterator();
        while (iter.hasNext()) {
            Perfil aux = iter.next();
            if (borrarItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (listaFiltrarPerfil.size() == perfilList.size()) {
            listaFiltrarPerfil.add(position, borrarItem);
        } else {
            listaFiltrarPerfil.add(position, borrarItem);
            perfilList.add(borrarItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public interface PerfilAdapterListener {
        void onContactSelected(Perfil perfil);
    }
}
