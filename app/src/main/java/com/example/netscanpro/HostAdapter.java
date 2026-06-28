package com.example.netscanpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * HostAdapter — adaptador para mostrar hosts en RecyclerView
 * Estilo terminal: IP verde si activo, rojo si DOWN
 */
public class HostAdapter extends RecyclerView.Adapter<HostAdapter.HostViewHolder> {

    private final List<HostModel> hostList;

    public HostAdapter(List<HostModel> hostList) {
        this.hostList = hostList;
    }

    public static class HostViewHolder extends RecyclerView.ViewHolder {
        TextView tvIp, tvBadge, tvRtt, tvHostname;

        public HostViewHolder(View itemView) {
            super(itemView);
            tvIp       = itemView.findViewById(R.id.tvIp);
            tvBadge    = itemView.findViewById(R.id.tvBadge);
            tvRtt      = itemView.findViewById(R.id.tvRtt);
            tvHostname = itemView.findViewById(R.id.tvHostname);
        }
    }

    @NonNull
    @Override
    public HostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_host, parent, false);
        return new HostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HostViewHolder holder, int position) {
        HostModel host = hostList.get(position);

        // IP: verde si activo, gris si no responde
        holder.tvIp.setText(host.getIp());
        holder.tvIp.setTextColor(host.isAlive()
                ? ContextCompat.getColor(holder.itemView.getContext(), R.color.green_accent)
                : ContextCompat.getColor(holder.itemView.getContext(), R.color.text_muted));

        // Badge ALIVE / DOWN
        if (host.isAlive()) {
            holder.tvBadge.setText("ALIVE");
            holder.tvBadge.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green_accent));
        } else {
            holder.tvBadge.setText("DOWN");
            holder.tvBadge.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red_accent));
        }

        // RTT en azul (solo si responde)
        if (host.isAlive()) {
            holder.tvRtt.setText(host.getRttMs() + " ms");
            holder.tvRtt.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue_accent));
            holder.tvRtt.setVisibility(View.VISIBLE);
        } else {
            holder.tvRtt.setVisibility(View.GONE);
        }

        // Hostname debajo de la IP (si se resolvió)
        if (host.getHostname() != null
                && !host.getHostname().isEmpty()
                && !host.getHostname().equals(host.getIp())) {
            holder.tvHostname.setText(host.getHostname());
            holder.tvHostname.setVisibility(View.VISIBLE);
        } else {
            holder.tvHostname.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return hostList.size();
    }

    // Agrega un host y notifica al RecyclerView
    public void addHost(HostModel host) {
        hostList.add(host);
        notifyItemInserted(hostList.size() - 1);
    }

    // Limpia la lista para un nuevo escaneo
    public void clearHosts() {
        int size = hostList.size();
        hostList.clear();
        notifyItemRangeRemoved(0, size);
    }
}
