package com.example.ladashop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.Viewholder> implements Filterable {
    private ArrayList<ListingItem> vListingItemsData = new ArrayList<>();
    private ArrayList<ListingItem> vListingItemsDataAll = new ArrayList<>();
    private Context vContext;
    private int lastPosition = -1;


    ListingsAdapter(Context context, ArrayList<ListingItem> itemsData){
            this.vListingItemsData = itemsData;
            this.vListingItemsDataAll = itemsData;
            this.vContext = context;
    }

    @Override
    public ListingsAdapter.Viewholder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(vContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ListingsAdapter.Viewholder holder, int position) {
        ListingItem currentItem = vListingItemsData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(vContext, R.anim.slide_in);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return vListingItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return listingFilter;
    }

    private Filter listingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           ArrayList<ListingItem> filteredList = new ArrayList<>();
           FilterResults results = new FilterResults();

           if(charSequence == null || charSequence.length() == 0){
               results.count = vListingItemsDataAll.size();
               results.values = vListingItemsDataAll;
           }else{
               String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ListingItem item : vListingItemsDataAll){
                    if(item.getNev().toLowerCase(Locale.ROOT).contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
               results.count = filteredList.size();
               results.values = filteredList;
           }

           return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            vListingItemsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class Viewholder extends RecyclerView.ViewHolder{
        private TextView vTitle;
        private TextView vInfo;
        private TextView vPrice;
        private ImageView vItemImg;

        public Viewholder(View itemView) {
            super(itemView);

            vTitle = itemView.findViewById(R.id.itemTitle);
            vInfo = itemView.findViewById(R.id.subTitle);
            vPrice = itemView.findViewById(R.id.price);
            vItemImg = itemView.findViewById(R.id.itemImage);

        }

        public void bindTo(ListingItem currentItem) {
            vTitle.setText(currentItem.getNev());
            vInfo.setText(currentItem.getLeiras());
            vPrice.setText(currentItem.getAr());

            Glide.with(vContext).load(currentItem.getImgResource()).into(vItemImg);
            itemView.findViewById(R.id.remove).setOnClickListener(view -> ((ListingsActivity)vContext).deletePart(currentItem));
            }
        }
    }

