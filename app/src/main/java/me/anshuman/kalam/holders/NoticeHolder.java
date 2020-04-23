package me.anshuman.kalam.holders;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.R;

public class NoticeHolder extends RecyclerView.ViewHolder {
    public TextView date,title;
    public NoticeHolder(View view){
        super(view);
        date=view.findViewById(R.id.datetv);
        title=view.findViewById(R.id.titletv);
    }
}
