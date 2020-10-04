package com.example.imageupload;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DImageAdapter extends RecyclerView.Adapter<DImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Prescription> mPrescription;
    private OnItemClickListener mListener;

    public DImageAdapter(Context mContext, List<Prescription> mPrescription) {
        this.mContext = mContext;
        this.mPrescription = mPrescription;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dimage_items,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Prescription currentPres = mPrescription.get(position);
        holder.textViewLocation.setText(currentPres.getLocation());
        holder.textViewNumber.setText(currentPres.getNumber().toString());
        Picasso.get().load(currentPres.getUrl())
                .fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPrescription.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {
        public TextView textViewLocation;
        public TextView textViewNumber;
        public ImageView imageView;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocation = itemView.findViewById(R.id.text_view_Location);
            textViewNumber = itemView.findViewById(R.id.text_view_Number);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //contextMenu.setHeaderTitle("Select Action");
            //MenuItem doWhatever = contextMenu.add(Menu.NONE,1,1,"Whatever");
            MenuItem delete = contextMenu.add(Menu.NONE,1,1,"Delete item");
            //doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                     mListener.onDeleteClick(position);
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        //void onWhateverClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
