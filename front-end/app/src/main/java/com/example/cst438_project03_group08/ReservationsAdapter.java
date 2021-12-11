package com.example.cst438_project03_group08;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>{
    private Context context;
    private List<Reservations> reservations;
    private int userId;

    public ReservationsAdapter(List<Reservations> allReservations, Context context, int userId) {
        reservations = allReservations;
        this.context = context;
        this.userId = userId;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        ReservationViewHolder rvh = new ReservationViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservations reservation = reservations.get(position);
        holder.tvBookId.setText("BookId: " + reservation.getBookId());
        holder.tvUserId.setText("UserId: " + reservation.getUserId());
        holder.tvDate.setText("Date: " + reservation.getDate());
        holder.reservation = reservation;
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserId;
        public TextView tvBookId;
        public TextView tvDate;
        public Button btnRemove;
        Reservations reservation;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvBookId = itemView.findViewById(R.id.tvBookId);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("REMOVE", "Successfully remove reservation " + reservation.getBookId());
                    Intent intent = new Intent(context, MyReservations.class);
                    intent.putExtra("BookId", String.valueOf(reservation.getBookId()));
                    intent.putExtra("UserId", String.valueOf(reservation.getUserId()));
                    intent.putExtra("ReservationId", String.valueOf(reservation.getReservationId()));
                    ((Activity)context).finish();
                    context.startActivity(intent);
                }
            });
        }
    }

}
