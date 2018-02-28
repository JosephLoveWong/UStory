package com.promiseland.ustory.service.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Command {
    public final int commandId;
    public final int queue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Queue {
    }

    public abstract void doInBackground();

    public Command(int commandId) {
        this(commandId, 1);
    }

    public Command(int commandId, int queue) {
        this.commandId = commandId;
        this.queue = queue;
    }
}
