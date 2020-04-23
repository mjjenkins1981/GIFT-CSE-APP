package me.anshuman.kalam.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.R;
import me.anshuman.kalam.holders.NoticeHolder;
import me.anshuman.kalam.model.NoticeData;
import me.anshuman.kalam.views.CMSWV;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeHolder> {
    List<NoticeData> noticeList;
    Context context;

    public NoticeAdapter(List<NoticeData> noticeList, Context context ){
        this.noticeList=noticeList;
        this.context=context;
    }
    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false);
        return new NoticeHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final NoticeHolder holder, final int position) {
        if(position>0 &&
                noticeList.get(position).getDate().equals(noticeList.get(position-1).getDate())
        ){
            holder.date.setVisibility(View.GONE);
        }
        holder.date.setText(noticeList.get(position).getDate());
        holder.title.setText(noticeList.get(position).getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CMSWV.class);
                intent.putExtra("url", noticeList.get(position).getLink());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return noticeList.size();
    }
}
