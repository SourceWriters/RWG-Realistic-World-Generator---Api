package net.sourcewriters.spigot.rwg.legacy.api.util.java.info;

public interface IStatus {

    void done();

    boolean isDone();

    boolean success();

    boolean failed();

    boolean skip();

    boolean cancel();

    void add();

    void add(long amount);

    void add(IStatus status);

    long getTotal();

    long getMarked();

    long getFailed();

    long getSuccess();

    long getSkipped();

    long getCancelled();

}
