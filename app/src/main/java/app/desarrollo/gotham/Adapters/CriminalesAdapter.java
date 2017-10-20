package app.desarrollo.gotham.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import app.desarrollo.gotham.CriminalActivity;
import app.desarrollo.gotham.Entidades.Criminales;
import app.desarrollo.gotham.ItemClickListener;
import app.desarrollo.gotham.R;


public class CriminalesAdapter extends RecyclerView.Adapter<CriminalesAdapter.MyViewHolder> {

    private List<Criminales> listaCriminalesList;
    private Context context;
    @Override
    public CriminalesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lista_criminales, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CriminalesAdapter.MyViewHolder holder, int position) {
        Criminales criminal = listaCriminalesList.get(position);
        holder.txtNombre.setText("Nombre: "+criminal.getNombre());
        holder.txtDireccion.setText("Direccion: "+criminal.getDireccion());
        holder.criminal = criminal;
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick, Criminales criminal) {
                Intent intent = new Intent(context, CriminalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Criminal", (Serializable) criminal);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
       return listaCriminalesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtNombre, txtDireccion;
        private ItemClickListener itemClickListener;
        public Criminales criminal;

        public MyViewHolder(View view) {
            super(view);
            txtNombre = (TextView) view.findViewById(R.id.txtNombre);
            txtDireccion = (TextView) view.findViewById(R.id.txtDireccion);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false, criminal);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
    }

    public CriminalesAdapter(List<Criminales> lista, Context context){
        this.listaCriminalesList = lista;
        this.context = context;
    }
}
