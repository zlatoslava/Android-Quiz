package savinykh.zlatoslava.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import savinykh.zlatoslava.Listeners.ListItemClickListener;
import savinykh.zlatoslava.Model.CategoryModel;
import savinykh.zlatoslava.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;
    private Activity mActivity;

    private ArrayList<CategoryModel> mCategoryList;
    private ListItemClickListener mItemClickListener;

    public CategoryAdapter(Context context, Activity activity, ArrayList<CategoryModel> categoryList) {
        mContext = context;
        mActivity = activity;
        mCategoryList = categoryList;
    }

    public void setItemClickListener(ListItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_recycler, parent, false);
        return new ViewHolder(view, viewType, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryModel model = mCategoryList.get(position);

        String categoryName = model.getCategoryName();
        holder.tvCategoryTitle.setText(Html.fromHtml(categoryName));
        holder.tvCategoryId.setText(String.valueOf(position + 1));

        holder.lytContainer.setBackgroundColor(Color.GRAY);
//        switch (categoryName) {
//            case "Android Basics": {
//                holder.lytContainer.setBackgroundColor(Color.GRAY);
//                break;
//            }
//            case "Advanced":{
//                holder.lytContainer.setBackgroundColor(Color.GREEN);
//                break;
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return (null != mCategoryList ? mCategoryList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout lytContainer;
        private TextView tvCategoryTitle, tvCategoryId;
        private ListItemClickListener itemClickListener;

        public ViewHolder(View itemView, int viewType, ListItemClickListener itemClickListener) {
            super(itemView);

            this.itemClickListener = itemClickListener;
            lytContainer = itemView.findViewById(R.id.lytContainer);
            tvCategoryId = itemView.findViewById(R.id.categoryId);
            tvCategoryTitle = itemView.findViewById(R.id.titleText);

            lytContainer.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getLayoutPosition(), view);
            }

        }
    }
}
