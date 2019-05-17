package com.jyoti.NoFoodWastage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {



    private static final String TAG = "PostAdapter";
    private List<Post> postsItemList;
    Context context;

    public PostAdapter(List<Post> postItemList, Context context) {
        this.postsItemList = postItemList;
        this.context = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View postProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_card, parent, false);
        PostViewHolder pvh = new PostViewHolder(postProductView);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, final int position) {
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
                //Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
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

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePostImage;
        TextView txtPostTitle;
       TextView txtProductPrice;
        TextView txtProductWeight;
        TextView txtProductQty;
        public ImageView likeImageView;
        public ImageView shareImageView;
        public PostViewHolder(View view) {
            super(view);
            imagePostImage=view.findViewById(R.id.coverImageView);
            txtPostTitle=view.findViewById(R.id.titleTextView);
         /*   txtProductPrice = view.findViewById(R.id.idProductPrice);
            txtProductWeight = view.findViewById(R.id.idProductWeight);
            txtProductQty = view.findViewById(R.id.idProductQty);*/
            likeImageView = (ImageView) view.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) view.findViewById(R.id.shareImageView);
            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //int id = (int)likeImageView.getTag();
                    //if( id == R.drawable.ic_like){

                        likeImageView.setTag(R.drawable.ic_liked);
                        likeImageView.setImageResource(R.drawable.ic_liked);

                       // Toast.makeText(getActivity(),titleTextView.getText()+" added to favourites",Toast.LENGTH_SHORT).show();

                    /*}else{

                        likeImageView.setTag(R.drawable.ic_like);
                        likeImageView.setImageResource(R.drawable.ic_like);
                      //  Toast.makeText(getActivity(),titleTextView.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();


                    }*/

                }
            });




            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getResources().getResourcePackageName(coverImageView.getId())
                            + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));


                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));*/

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBodyText = "Check it out. There is a new food donation post available on the NoFoodWaste app";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"New food donation post");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                    context.startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

                }
            });




        }
    }

}
