package com.example.q.cs496_week2_new.tabs.Gallery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.q.cs496_week2_new.R;

import java.util.ArrayList;

public class SharedAdapter extends RecyclerView.Adapter<SharedAdapter.ViewHolder> {

    private Context mContext;
    private GalleryFragment mFragment;
    private ArrayList<SharedItem> mImages;

    public SharedAdapter(Context mContext, GalleryFragment mFragment, ArrayList<SharedItem> mImages) {
        this.mContext = mContext;
        this.mFragment = mFragment;
        this.mImages = mImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SharedItem mImage = mImages.get(position);
        Glide.with(mContext)
                .load(mImage.getFile())
                .thumbnail(0.5f)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mImages.isEmpty()) {
            return 0;
        } else {
            return mImages.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private FrameLayout allLayout;
        private ImageView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);

            allLayout = (FrameLayout) view.findViewById(R.id.itemGallery);
            imageView = (ImageView) view.findViewById(R.id.galleryView);
            allLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, ViewActivity.class);
            intent.putExtra("index", getLayoutPosition());
            intent.putExtra("images", mImages);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Glide.get(mContext).clearMemory();
    }
}
