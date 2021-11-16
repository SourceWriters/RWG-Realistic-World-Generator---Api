package net.sourcewriters.spigot.rwg.legacy.api.util.java.wait;

import java.util.concurrent.Future;

import com.syntaxphoenix.syntaxapi.utils.general.Status;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.info.IStatus;

@FunctionalInterface
@SuppressWarnings("rawtypes")
public interface WaitFunction<E> {

    WaitFunction<Status> SYNTAX_STATUS = Status::isDone;
    WaitFunction<IStatus> STATUS = IStatus::isDone;
    WaitFunction<Future> FUTURE = Future::isDone;

    /*
     * 
     */

    long WAIT_INTERVAL = 10L;
    int WAIT_INFINITE = -1;

    default void await(final E waited) {
        await(waited, WAIT_INTERVAL);
    }

    default void await(final E waited, final long interval) {
        await(waited, interval, WAIT_INFINITE);
    }

    default void await(final E waited, final long interval, int length) {
        while (!isDone(waited)) {
            try {
                Thread.sleep(interval);
            } catch (final InterruptedException ignore) {
                break;
            }
            if (length == -1) {
                continue;
            }
            if (length-- == 0) {
                break;
            }
        }
    }

    boolean isDone(E waited);

}
