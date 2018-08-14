package com.yt.runtimepermissiontest;

import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<ContactModel> mList;

    public ContactAdapter(List<ContactModel> list) {
        this.mList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView phoneText;
        ViewHolder(View view) {
            super(view);
            nameText = view.findViewById(R.id.name);
            phoneText = view.findViewById(R.id.tel);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);
        ViewHolder viewHolder = new  ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel model = mList.get(position);

        holder.nameText.setText(model.getName());
        holder.phoneText.setText(model.getPhone());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
