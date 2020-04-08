package me.anshuman.kalam.holders;

import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.R;

public class AttendanceHolder extends RecyclerView.ViewHolder {

    public TextView mpercent,semnum;
    public CircularProgressBar progressBar;
    public AttendanceHolder(@NonNull View itemView) {
        super(itemView);
        semnum=itemView.findViewById(R.id.semnum);
        mpercent=itemView.findViewById(R.id.attpercentage);
        progressBar=itemView.findViewById(R.id.attendanceprogressbar);



    }
}
