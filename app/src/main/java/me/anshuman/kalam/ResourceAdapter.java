package me.anshuman.kalam;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceHolder> {


    List<ResourceList> resourceLists;
    Context context;

    public ResourceAdapter(List<ResourceList> resourceLists, Context context) {
        this.resourceLists = resourceLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ResourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resource_item, parent, false);
        return new ResourceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResourceHolder holder, final int position) {
        holder.mName.setText(resourceLists.get(position).getName());
        holder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url;
                if(resourceLists.get(position).getName().contains("pdf"))
                    url="https://docs.google.com/gview?embedded=true&url="+resourceLists.get(position).getLink();
                else
                    url=resourceLists.get(position).getLink();
                Intent intent=new Intent(context,CMSWV.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
        holder.mName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                downloadFile(context, resourceLists.get(position).getName(),DIRECTORY_DOWNLOADS,resourceLists.get(position).getLink());
                Toast toast =  Toast.makeText(context, "Downloading "+resourceLists.get(position).getName(), Toast.LENGTH_SHORT);
                View view2 = toast.getView();
                view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                TextView text = view2.findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE);
                toast.show();
                return true;
            }
        });
    }
    public void downloadFile(Context context, String fileName, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName);

        assert downloadmanager != null;
        downloadmanager.enqueue(request);
    }
    @Override
    public int getItemCount() {
        return resourceLists.size();
    }
}
