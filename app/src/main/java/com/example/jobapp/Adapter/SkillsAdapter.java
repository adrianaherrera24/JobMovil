package com.example.jobapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobapp.LogicaNegocio.Skill;
import com.example.jobapp.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyViewHolder> {

    private List<Skill> skillList;
    private List<Skill> listaFiltrarSkill;
    private SkillsAdapterListener listener;
    private Skill borrarItem;

    /// Clase de ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombre, txtDescripcion;
        //two layers
        public RelativeLayout viewForeground, viewBackgroundDelete, viewBackgroundEdit;

        public MyViewHolder(View view) {
            super(view);
            txtNombre = view.findViewById(R.id.txt_nombre);
            txtDescripcion = view.findViewById(R.id.txt_descripcion);

            viewBackgroundDelete = view.findViewById(R.id.view_background_delete);
            viewBackgroundEdit = view.findViewById(R.id.view_background_edit);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listaFiltrarSkill.get(getAdapterPosition()));
                }
            });
        }
    } /// Termina clase ViewHolder

    public SkillsAdapter(List<Skill> skillList, SkillsAdapterListener listener) {
        this.skillList = skillList;
        this.listener = listener;
        //init filter
        this.listaFiltrarSkill = skillList;
    }

    @Override
    public SkillsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_skill, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SkillsAdapter.MyViewHolder holder, int position) {
        // rendering view
        final Skill skill = listaFiltrarSkill.get(position);
        holder.txtNombre.setText(skill.getNombre());
        holder.txtDescripcion.setText(skill.getDescripcion());
    }


    public void removeItem(int position) {
        borrarItem = listaFiltrarSkill.remove(position);
        Iterator<Skill> iter = skillList.iterator();
        while (iter.hasNext()) {
            Skill aux = iter.next();
            if (borrarItem.equals(aux))
                iter.remove();
        }
        // notify item removed
        notifyItemRemoved(position);
    }

    public void restoreItem(int position) {

        if (listaFiltrarSkill.size() == skillList.size()) {
            listaFiltrarSkill.add(position, borrarItem);
        } else {
            listaFiltrarSkill.add(position, borrarItem);
            skillList.add(borrarItem);
        }
        notifyDataSetChanged();
        // notify item added by position
        notifyItemInserted(position);
    }

    public Skill getSwipedItem(int index) {
        if (this.skillList.size() == this.listaFiltrarSkill.size()) { //not filtered yet
            return skillList.get(index);
        } else {
            return listaFiltrarSkill.get(index);
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (skillList.size() == listaFiltrarSkill.size()) { // without filter
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(skillList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(skillList, i, i - 1);
                }
            }
        } else {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(listaFiltrarSkill, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(listaFiltrarSkill, i, i - 1);
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public int getItemCount() {
        return listaFiltrarSkill.size();
    }

    public interface SkillsAdapterListener {
        void onContactSelected(Skill skills);
    }
}
