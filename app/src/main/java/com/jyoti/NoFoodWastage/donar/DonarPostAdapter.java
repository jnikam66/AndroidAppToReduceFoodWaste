package com.jyoti.NoFoodWastage.donar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jyoti.NoFoodWastage.Donar_DetailsPage2;
import com.jyoti.NoFoodWastage.Post;
import com.jyoti.NoFoodWastage.PostAdapter;
import com.jyoti.NoFoodWastage.R;
import com.jyoti.NoFoodWastage.SingleItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DonarPostAdapter extends RecyclerView.Adapter<DonarPostAdapter.DonarPostViewHolder> {


    private static final String TAG = "DonarPostAdapter";
    private List<Post> postsItemList;
    Context context;

    public DonarPostAdapter(List<Post> postItemList, Context context) {
        this.postsItemList = postItemList;
        this.context = context;
    }

    @Override
    public DonarPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View postProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donar_post_layout, parent, false);
        DonarPostAdapter.DonarPostViewHolder pvh = new DonarPostAdapter.DonarPostViewHolder(postProductView);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DonarPostAdapter.DonarPostViewHolder holder, final int position) {
        //  holder.imagePostImage.setImageResource(postsItemList.get(position).getImageUrl());
        // StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        Log.e(TAG, "URI "+postsItemList.get(position).getImageUrl());
        Picasso.get().load(postsItemList.get(position).getImageUrl()).into(holder.imagePostImage);

        holder.txtPostTitle.setText(postsItemList.get(position).getTitle());
      /*  holder.txtProductPrice.setText(grocderyItemList.get(position).getProductPrice());
        holder.txtProductWeight.setText(grocderyItemList.get(position).getProductWeight());
        holder.txtProductQty.setText(grocderyItemList.get(position).getProductQty());*/

        holder.imagePostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = postsItemList.get(position).getTitle();
               // Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
                //   context.startActivity(new Intent(context, SingleItem.class));
                Post selectedPost = postsItemList.get(position);
                Intent intent = new Intent(context, SingleItem.class);
                //  intent.putExtra("selectedPost", selectedPost);
                context.startActivity(intent);


                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedPost", selectedPost);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsItemList.size();
    }


    public class DonarPostViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePostImage;
        TextView txtPostTitle;
        TextView txtProductPrice;
        TextView txtProductWeight;
        TextView txtProductQty;
        public DonarPostViewHolder(View view) {
            super(view);
            imagePostImage=view.findViewById(R.id.coverImageView);
            txtPostTitle=view.findViewById(R.id.titleTextView);
         /*   txtProductPrice = view.findViewById(R.id.idProductPrice);
            txtProductWeight = view.findViewById(R.id.idProductWeight);
            txtProductQty = view.findViewById(R.id.idProductQty);*/
        }
    }

}
