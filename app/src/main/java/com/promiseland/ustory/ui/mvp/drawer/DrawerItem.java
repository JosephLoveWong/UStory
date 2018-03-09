package com.promiseland.ustory.ui.mvp.drawer;

public class DrawerItem {
    private TwoStateIcon icons;
    private boolean isSelected;
    private int title;
    private Type type;

    public static class TwoStateIcon {
        private final int iconActiveResource;
        private final int iconInactiveResource;

        public TwoStateIcon(int iconInactiveResource, int iconActiveResource) {
            this.iconInactiveResource = iconInactiveResource;
            this.iconActiveResource = iconActiveResource;
        }

        public int getIconInactiveResource() {
            return this.iconInactiveResource;
        }

        public int getIconActiveResource() {
            return this.iconActiveResource;
        }
    }

    public enum Type {
        TOP,
        SEPARATOR,
        ITEM
    }

    public DrawerItem(Type type, TwoStateIcon icons, int title) {
        this.type = type;
        this.icons = icons;
        this.title = title;
    }

    public Type getType() {
        return this.type;
    }

    public TwoStateIcon getIcons() {
        return this.icons;
    }

    public int getTitle() {
        return this.title;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
