package me.anshuman.kalam;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResourceHolder extends RecyclerView.ViewHolder {

    TextView mName;
    public ResourceHolder(@NonNull View itemView) {
        super(itemView);

        mName=itemView.findViewById(R.id.resource_single_tv);



    }
}