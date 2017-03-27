package sg.edu.nus.iss.medipal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.EditCategoryActivity;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.pojo.Category;

/**
 * Created by zhiguo on 15/3/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<Category> categoryList;


    //custom view holder to show the appointment details as card
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView code;
        public TextView remainder;
        public TextView description;
        public ImageView edit;

        public CardView cardView;


        public CategoryViewHolder(View view) {
            super(view);
            //get reference to the card view elements
            cardView = (CardView)view.findViewById(R.id.card_view);
            name = (TextView) view.findViewById(R.id.categoryname);
            description = (TextView) view.findViewById(R.id.categorydescription);
            edit = (ImageView) view.findViewById(R.id.edit);
            remainder = (TextView) view.findViewById(R.id.categoryremainder);
            code = (TextView) view.findViewById(R.id.categorycode);



                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent EditCategory = new Intent(mContext,EditCategoryActivity.class);
                        Category category = categoryList.get(getAdapterPosition());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("category",category);

                        //EditCategory.setClass(context,EditCategoryActivity.class);
                        EditCategory.putExtras(bundle);
                        EditCategory.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        mContext.startActivity(EditCategory);
                    }
                });
        }
    }

    //constructor for adapter

    public CategoryAdapter(Context mContext, List<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;

    }

    //called once in beginning to load the view
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_category_row_layout, parent, false);

        return new CategoryViewHolder(itemView);
    }

    //used to populate the view elements with adapter data
    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {

        //get appointment data from list using current position as index
        Category category = categoryList.get(position);

        //populate the view elements
        holder.name.setText(category.getCategory_name());
        holder.code.setText("Code: "+category.getCategory_code());
        holder.description.setText(category.getCategory_des());
        if((category.getId() > 1) && (category.getId() <=4)){
            holder.edit.setVisibility(View.GONE);
        }
        String reminderStatus;
        if(category.getRemind())
        {reminderStatus = "Reminder is ON";}
        else {reminderStatus = "Reminder is OFF";}

        holder.remainder.setText(reminderStatus);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void refreshCategorys() {

        categoryList.clear();

        categoryList.addAll(App.hm.getCategorys(mContext));

        notifyDataSetChanged();
    }

}
