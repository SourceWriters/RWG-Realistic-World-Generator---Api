package net.sourcewriters.spigot.rwg.legacy.api.util.java.wait;

import java.util.concurrent.Future;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.Status;

@FunctionalInterface
@SuppressWarnings("rawtypes")
public interface WaitFunction<E> {

	public static final WaitFunction<com.syntaxphoenix.syntaxapi.utils.general.Status> SYNTAX_STATUS = com.syntaxphoenix.syntaxapi.utils.general.Status::isDone;
	public static final WaitFunction<Status> STATUS = Status::isDone;
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
