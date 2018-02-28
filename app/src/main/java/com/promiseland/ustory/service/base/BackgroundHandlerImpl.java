package com.promiseland.ustory.service.base;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import timber.log.Timber;

public final class BackgroundHandlerImpl implements BackgroundHandler {
    protected final SparseArray<CustomService> commands = new SparseArray();
    protected final SparseArray<Handler> handlerPool = new SparseArray();

    static class KSBackgroundHandler extends Handler {
        public KSBackgroundHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            Timber.d("%s: %s(%s)", "start command", msg.obj.getClass().getSimpleName(), Integer.valueOf(msg.what));
            if (msg.obj instanceof Command) {
                ((Command) msg.obj).doInBackground();
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    Handler getBackgroundHandler(Command command, CustomService service) {
        int commandId = command.commandId;
        if (this.commands.indexOfKey(commandId) < 0) {
            this.commands.put(commandId, service);
        } else {
            CustomService temp = (CustomService) this.commands.get(commandId);
            if (!(temp == null || temp.equals(service))) {
                throw new IllegalArgumentException("already used command:" + command + "(" + commandId + ") from customservice" + service.getClass());
            }
        }
        return getBackgroundHandlerFor(command.queue);
    }

    protected Handler getBackgroundHandlerFor(int queue) {
        Handler handler = (Handler) this.handlerPool.get(queue);
        if (handler != null) {
            return handler;
        }
        HandlerThread handlerThread = new HandlerThread("BackgroundHandler");
        handlerThread.setDaemon(true);
        handlerThread.start();
        handler = new KSBackgroundHandler(handlerThread.getLooper());
        this.handlerPool.put(queue, handler);
        return handler;
    }

    public void sendMessage(Command command, CustomService service) {
        Handler handler = getBackgroundHandler(command, service);
        handler.sendMessage(handler.obtainMessage(command.commandId, command));
    }
}
