package com.promiseland.ustory.ui.drawer;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.promiseland.ustory.R;

import java.util.List;

public class DrawerAdapter extends Adapter<ViewHolder> {
    DrawerAdapterCallbacks mCallbacks;
    private LayoutInflater mInflater;
    private final List<DrawerItem> mItems;
    private final int mSelectedColor;
    private final int mUnselectedColor;

    public interface DrawerAdapterCallbacks {
        void onPositionSelected(int position);
    }

    public class NavDrawerHeaderHolder extends ViewHolder {
        public NavDrawerHeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public class NavDrawerItemHolder extends ViewHolder {
        private final int colorRegular;
        private final int colorSelected;
        private final ImageView icon;
        private final View root;
        private final TextView title;

        public NavDrawerItemHolder(View itemView, int colorSelected, int colorRegular) {
            super(itemView);
            this.colorSelected = colorSelected;
            this.colorRegular = colorRegular;
            this.root = itemView;
            this.icon = itemView.findViewById(R.id.drawer_item_icon);
            this.title = itemView.findViewById(R.id.drawer_item_title);
            itemView.setOnClickListener(v -> DrawerAdapter.this.mCallbacks.onPositionSelected(NavDrawerItemHolder.this.getAdapterPosition()));
        }

        public void bind(DrawerItem item) {
            if (this.icon != null) {
                int iconActiveResource;
                if (item.isSelected()) {
                    iconActiveResource = item.getIcons().getIconActiveResource();
                } else {
                    iconActiveResource = item.getIcons().getIconInactiveResource();
                }
                this.icon.setImageResource(iconActiveResource);
            }
            if (this.title != null) {
                this.title.setText(item.getTitle());
                this.title.setTextColor(item.isSelected() ? this.colorSelected : this.colorRegular);
            }
            this.root.setSelected(item.isSelected());
        }
    }

    public DrawerAdapter(Context context, List<DrawerItem> items, DrawerAdapterCallbacks callbacks) {
        this.mItems = items;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mCallbacks = callbacks;
        this.mUnselectedColor = ContextCompat.getColor(context, R.color.text_grey);
        this.mSelectedColor = ContextCompat.getColor(context, R.color.honey_melon);
    }

    public boolean isEnabled(int position) {
        if (position < 0 && position >= this.mItems.size()) {
            return false;
        }
        try {
            boolean enabled;
            if (this.mItems.get(position).getType() == DrawerItem.Type.SEPARATOR || this.mItems.get(position).getType() == DrawerItem.Type.TOP) {
                enabled = false;
            } else {
                enabled = true;
            }
            return enabled;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DrawerItem.Type.TOP.ordinal()) {
            return new NavDrawerHeaderHolder(this.mInflater.inflate(R.layout.list_item_drawer_top, parent, false));
        }
        if (viewType == DrawerItem.Type.SEPARATOR.ordinal()) {
            return new NavDrawerHeaderHolder(this.mInflater.inflate(R.layout.list_item_drawer_separator, parent, false));
        }
        return new NavDrawerItemHolder(this.mInflater.inflate(R.layout.list_item_drawer, parent, false), this.mSelectedColor, this.mUnselectedColor);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof NavDrawerItemHolder) {
            ((NavDrawerItemHolder) holder).bind(this.mItems.get(position));
        }
    }

    public int getItemCount() {
        return this.mItems.size();
    }

    public int getItemViewType(int position) {
        return this.mItems.get(position).getType().ordinal();
    }

    public void setSelected(int position) {
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mItems.get(i).setSelected(false);
            if (i == position) {
                this.mItems.get(i).setSelected(true);
            }
        }
        notifyDataSetChanged();
    }

    public void tearDown() {
        this.mCallbacks = null;
        this.mInflater = null;
    }
}
