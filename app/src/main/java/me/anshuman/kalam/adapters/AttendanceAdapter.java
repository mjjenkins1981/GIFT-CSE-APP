package me.anshuman.kalam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.R;
import me.anshuman.kalam.holders.AttendanceHolder;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceHolder> {


    List<String> attendancelist;
    Context context;
    int semester;

    public AttendanceAdapter(List<String> attendancelist, Context context,int semester) {
        this.attendancelist = attendancelist;
        this.context = context;
        this.semester= semester;
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_item, parent, false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceHolder holder, final int position) {
    holder.mpercent.setText(attendancelist.get(position));
    holder.progressBar.setProgressWithAnimation(Float.parseFloat(attendancelist.get(position)), (long) 2500);
    int sem1=((semester-attendancelist.size())+(position+1));
    holder.semnum.setText("Semester "+sem1);
    }
    @Override
    public int getItemCount() {
        return attendancelist.size();
    }
    }
