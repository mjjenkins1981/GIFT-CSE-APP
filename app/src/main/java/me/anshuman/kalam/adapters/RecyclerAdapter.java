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
        View view = LayoutInflater.from(context).inflate(R.layout.class_card, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyHolder holder, int position) {
        if(position==0){
            holder.topline.setVisibility(View.INVISIBLE);
        }
        if(position==getItemCount()-1){
            holder.bottomline.setVisibility(View.INVISIBLE);
        }
        ClassDetail classlist = classDetails.get(position);
        holder.subject.setText(classlist.getSubject());
        String starttime=classlist.getStart();
        String[] timing=starttime.split(" ");
        holder.start1.setText(timing[0]);
        holder.start2.setText(timing[1]);
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
        TextView start1,start2, subject, faculty, room;
        View topline,bottomline;

        public MyHolder(View itemView) {
            super(itemView);
            start1 = itemView.findViewById(R.id.start1);
            start2=itemView.findViewById(R.id.start2);
            subject = itemView.findViewById(R.id.subject);
            faculty = itemView.findViewById(R.id.faculty);
            room = itemView.findViewById(R.id.room);
            topline=itemView.findViewById(R.id.linetop);
            bottomline=itemView.findViewById(R.id.linebottom);
        }
    }
}
