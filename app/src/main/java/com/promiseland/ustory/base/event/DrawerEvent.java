package com.promiseland.ustory.base.event;

public class DrawerEvent {
    public final int gravity;
    public final boolean isOpened;

    public DrawerEvent(boolean isOpened, int gravity) {
        this.isOpened = isOpened;
        this.gravity = gravity;
    }
}
