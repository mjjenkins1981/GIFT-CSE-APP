package me.anshuman.kalam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.R;
import me.anshuman.kalam.model.ClassDetail;

@Keep
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {


    List<ClassDetail> classDetails;
    Context context;

    public RecyclerAdapter(List<ClassDetail> classDetails, Context context) {
        this.classDetails = classDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyHolder holder, int position) {

        ClassDetail classlist = classDetails.get(position);
        holder.subject.setText(classlist.getSubject());
        holder.start.setText(classlist.getStart());
        holder.end.setText(classlist.getEnd());
        holder.faculty.setText(classlist.getFaculty());
        holder.room.setText(classlist.getRoom());
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try {
            if (classDetails.size() == 0) {
                arr = 0;
            } else {
                arr = classDetails.size();
            }
        } catch (Exception e) {

        }
        return arr;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView start, subject, end, faculty, room;

        public MyHolder(View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.start);
            subject = itemView.findViewById(R.id.subject);
            end = itemView.findViewById(R.id.end);
            faculty = itemView.findViewById(R.id.faculty);
            room = itemView.findViewById(R.id.room);
        }
    }
}
