package com.example.sns_project.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sns_project.PostInfo;
import com.example.sns_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ArrayList<PostInfo> mDataset;
    private Activity activity;
    private FirebaseFirestore firebaseFirestore;

    static class MainViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        MainViewHolder(Activity activity, CardView v, PostInfo postInfo) {
            super(v);
            cardView = v;

            LinearLayout contetnsLayout = cardView.findViewById(R.id.contentsLayout);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ArrayList<String> contentsList = postInfo.getContents();

            if(contetnsLayout.getChildCount() == 0){
                for (int i = 0; i < contentsList.size(); i++ ){
                    String contents = contentsList.get(i);
                    if(Patterns.WEB_URL.matcher(contents).matches()){
                        ImageView imageView = new ImageView(activity);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        contetnsLayout.addView(imageView);
                    }else {
                        TextView textView = new TextView(activity);
                        textView.setLayoutParams(layoutParams);
                        contetnsLayout.addView(textView);
                    }
                }
            }
        }
    }

    public MainAdapter(Activity activity, ArrayList<PostInfo> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(activity, cardView, mDataset.get(viewType));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, mainViewHolder.getAdapterPosition());
            }
        });

        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
        titleTextView.setText(mDataset.get(position).getTitle());

        TextView createdAtTextView = cardView.findViewById(R.id.createAtTextView);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));

        LinearLayout contetnsLayout = cardView.findViewById(R.id.contentsLayout);
        ArrayList<String> contentsList = mDataset.get(position).getContents();

        for (int i = 0; i < contentsList.size(); i++ ){
            String contents = contentsList.get(i);
            if(Patterns.WEB_URL.matcher(contents).matches()){
                Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into((ImageView)contetnsLayout.getChildAt(i));
            }else {
                ((TextView)contetnsLayout.getChildAt(i)).setText(contents);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.modify:

                        return true;
                    case R.id.delete:
                        firebaseFirestore.collection("posts").document(mDataset.get(position).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startToast("게시글을 삭제하였습니다.");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        startToast("게시글을 삭제하지 못하였습니다.");
                                    }
                                });
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }

    private void startToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}