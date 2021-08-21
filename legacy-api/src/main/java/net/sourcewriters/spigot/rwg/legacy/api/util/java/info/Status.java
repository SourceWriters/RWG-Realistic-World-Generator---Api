package net.sourcewriters.spigot.rwg.legacy.api.util.java.info;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class Status implements IStatus {

    public static final Status EMPTY = new Status(true);

    public static final Status create() {
        return new Status(false);
    };

    private final AtomicLong total = new AtomicLong(0);
    private final AtomicLong marked = new AtomicLong(0);
    
    private final AtomicLong failed = new AtomicLong(0);
    private final AtomicLong success = new AtomicLong(0);
    private final AtomicLong skipped = new AtomicLong(0);
    private final AtomicLong cancelled = new AtomicLong(0);

    private final AtomicBoolean done = new AtomicBoolean();

    /**
     * Constructs a Status with an specific done state
     * 
     * @param loaded - defines if it was loaded or not
     */
    private Status(boolean done) {
        this.done.set(done);
    }

    /**
     * Constructs a Status
     * 
     * @param total - starting total amount of objects to load
     */
    public Status(long total) {
        this.total.set(total);
    }

    /**
     * Set the loading to done
     */
    public void done() {
        done.getAndSet(true);
    }

    /**
     * Checks if loading is completly done
     * 
     * @return if loading is done
     */
    public boolean isDone() {
        return done.get();
    }

    /**
     * Marks one object as successful
     * 
     * @return if it was marked or not
     */
    public boolean success() {
        if (isDone() || !mark()) {
            return false;
        }
        success.incrementAndGet();
        return true;
    }

    /**
     * Marks one object as successful
     * 
     * @return if it was marked or not
     */
    public boolean failed() {
        if (isDone() || !mark()) {
            return false;
        }
        failed.incrementAndGet();
        return true;
    }

    /**
     * Marks one object as skipped
     * 
     * @return if it was marked or not
     */
    public boolean skip() {
        if (isDone() || !mark()) {
            return false;
        }
        skipped.incrementAndGet();
        return true;
    }

    /**
     * Marks one object as cancelled
     * 
     * @return if it was marked or not
     */
    public boolean cancel() {
        if (isDone() || !mark()) {
            return false;
        }
        cancelled.incrementAndGet();
        return true;
    }

    /**
     * Marks an object
     * 
     * @return if it was marked or not
     */
    private boolean mark() {
        if (marked.longValue() == total.longValue()) {
            return false;
        }
        marked.incrementAndGet();
        return true;
    }

    /**
     * Add one object to total objects to load
     */
    public void add() {
        add(1);
    }

    /**
     * Add amount to total objects to load
     *
     * @param amount - amount to add
     */
    public void add(long amount) {
        if (isDone()) {
            return;
        }
        total.addAndGet(amount);
    }

    /**
     * Add LoadingStatus to this status
     * 
     * @param status - LoadingStatus to add
     */
    public void add(IStatus status) {
        if (isDone()) {
            return;
        }
        total.addAndGet(status.getTotal());
        marked.addAndGet(status.getMarked());
        failed.addAndGet(status.getFailed());
        success.addAndGet(status.getSuccess());
        skipped.addAndGet(status.getSkipped());
        cancelled.addAndGet(status.getCancelled());
    }

    /**
     * Get the total amount of objects to load
     * 
     * @return the total amount
     */
    public long getTotal() {
        return total.longValue();
    }

    /**
     * Get the total amount of objects that were marked
     * 
     * @return the marked amount
     */
    public long getMarked() {
        return marked.longValue();
    }

    /**
     * Get the total amount of objects that failed to load
     * 
     * @return the failed amount
     */
    public long getFailed() {
        return failed.longValue();
    }

    /**
     * Get the amount of objects that we successfully loaded
     * 
     * @return the successful amount
     */
    public long getSuccess() {
        return success.longValue();
    }

    /**
     * Get the amount of objects that were not loaded
     * 
     * @return the not loaded amount
     */
    public long getSkipped() {
        return skipped.longValue();
    }

    /**
     * Get the amount of objects that were cancelled on load
     * 
     * @return the cancelled amount
     */
    public long getCancelled() {
        return cancelled.longValue();
    }

}
