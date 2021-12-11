package com.example.cst438_project03_group08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllReservationsAdapter extends RecyclerView.Adapter<AllReservationsAdapter.ReservationViewHolder>{
    private Context context;
    private List<Reservations> reservations;

    public AllReservationsAdapter(List<Reservations> allReservations, Context context, int userId) {
        reservations = allReservations;
        this.context = context;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_reservations, parent, false);
        ReservationViewHolder rvh = new ReservationViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservations reservation = reservations.get(position);
        holder.tvBookId.setText("BookId: " + reservation.getBookId());
        holder.tvUserId.setText("UserId: " + reservation.getUserId());
        holder.tvDate.setText("Date: (12/10/21)");
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
        Reservations reservation;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvBookId = itemView.findViewById(R.id.tvBookId);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

}
