package com.example.mytodoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.sql.StatementEvent;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    ArrayList<model> dataholder;



    public myadapter(ArrayList<model> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.dtitle.setText(dataholder.get(position).getTitle());
        holder.ddetail.setText(dataholder.get(position).getDetail());


        holder.llrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.add_update_lay);

                EditText edtTitle = dialog.findViewById(R.id.edtTitle);
                EditText edtDetail = dialog.findViewById(R.id.edtDetail);
                Button btnAction = dialog.findViewById(R.id.btnAction);
                TextView txtTitle = dialog.findViewById(R.id.txtTitle);

                txtTitle.setText("Update Task");

                btnAction.setText("Update");

                edtTitle.setText((dataholder.get(holder.getAdapterPosition())).title);
                edtDetail.setText((dataholder.get(holder.getAdapterPosition())).detail);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title="",detail="";

                        if (!edtTitle.getText().toString().equals("") && (!edtDetail.getText().toString().equals(""))) {
                            title = edtTitle.getText().toString();
                            detail = edtDetail.getText().toString();




                        } else {
                            Toast.makeText(view.getContext(), "You forget to enter something", Toast.LENGTH_SHORT).show();
                        }
                        dataholder.set(holder.getAdapterPosition(),new model(title,detail));
                        notifyItemChanged(holder.getAdapterPosition());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.llrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Task")
                        .setMessage("Are you sure to delete the task")
                        .setIcon(R.drawable.delete_img)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                dataholder.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();





                return true;

            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String check ="0";
                Intent intent = new Intent();
                if (compoundButton.isChecked()) {
                    intent.putExtra(check, 1);
                    Toast.makeText(compoundButton.getContext(),
                            "Hurray, it's done!", Toast.LENGTH_LONG).show();


                } else
                {
                    intent.putExtra(check, 0);
                    Toast.makeText(compoundButton.getContext(),
                            "Todo task is added!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView dtitle,ddetail;
        RelativeLayout llrow;
        CheckBox checkBox;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            dtitle=(TextView)itemView.findViewById(R.id.disptitle);
            ddetail=(TextView)itemView.findViewById(R.id.dispdetail);
            llrow = (RelativeLayout) itemView.findViewById(R.id.llrow);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);

        }

    }
}
