package net.sourcewriters.spigot.rwg.legacy.api.util.java.wait;

import java.util.concurrent.Future;

import com.syntaxphoenix.syntaxapi.utils.general.Status;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.info.IStatus;

@FunctionalInterface
@SuppressWarnings("rawtypes")
public interface WaitFunction<E> {

	public static final WaitFunction<Status> SYNTAX_STATUS = Status::isDone;
	public static final WaitFunction<IStatus> STATUS = IStatus::isDone;
	public static final WaitFunction<Future> FUTURE = Future::isDone;

	/*
	 * 
	 */

	public static final long WAIT_INTERVAL = 10L;
	public static final int WAIT_INFINITE = -1;

	default void await(E waited) {
		await(waited, WAIT_INTERVAL);
	}

	default void await(E waited, long interval) {
		await(waited, interval, WAIT_INFINITE);
	}

	default void await(E waited, long interval, int length) {
		while (!isDone(waited)) {
			try {
				Thread.sleep(interval);
			} catch (InterruptedException ignore) {
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
