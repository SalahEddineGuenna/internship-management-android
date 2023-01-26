package com.example.internship_management.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internship_management.R;
import com.example.internship_management.model.EtablissementDTO;
import com.example.internship_management.model.Student;

import java.util.List;

public class EtabAdapter extends RecyclerView.Adapter<EtabHolder>{

private List<EtablissementDTO> etabList;
private OnButtonClickListener clickListener;
private OnItemDeleteListener listener;
public EtabAdapter(List<EtablissementDTO> etabList, OnButtonClickListener clickListener) {
        this.etabList = etabList;
        this.clickListener = clickListener;
        }

@NonNull
@Override
public EtabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_etab, parent, false);
        return new EtabHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull EtabHolder holder, int position) {
        EtablissementDTO etab = etabList.get(position);
        holder.name.setText(etab.getName());

        if(etab.getResponsableStageDTO() == null){
                holder.respo.setVisibility(View.GONE);
        } else {
                holder.respo.setVisibility(View.VISIBLE);
                holder.affecter.setVisibility(View.GONE);
                holder.respo.setText(etab.getResponsableStageDTO().getName() + " " + etab.getResponsableStageDTO().getLastName());
        }
        holder.affecter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        listener.onItemDelete(position);
                }
        });

        }

@Override
public int getItemCount() {
        return etabList.size();
        }

        public void setItemDeleteListener(OnItemDeleteListener listener) {
                this.listener = listener;
        }

        public interface OnButtonClickListener {
                void onButtonClick(Long id);
        }
}
