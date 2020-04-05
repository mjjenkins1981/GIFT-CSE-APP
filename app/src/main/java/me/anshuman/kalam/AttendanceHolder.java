package me.anshuman.kalam;

import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceHolder extends RecyclerView.ViewHolder {

    TextView mpercent,semnum;
    CircularProgressBar progressBar;
    public AttendanceHolder(@NonNull View itemView) {
        super(itemView);
        semnum=itemView.findViewById(R.id.semnum);
        mpercent=itemView.findViewById(R.id.attpercentage);
        progressBar=itemView.findViewById(R.id.attendanceprogressbar);



    }
}
