package net.sourcewriters.spigot.rwg.legacy.api.util.java;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.JavaAccess;

public final class RandomAdapter extends RandomNumberGenerator {

    private static final Field SEED_FIELD = JavaAccess.getField(Random.class, "seed");

    private final Random random;
    private final AtomicLong seedState;

    private long seed;

    public RandomAdapter(final Random random) {
        this.random = random;
        this.seedState = (AtomicLong) JavaAccess.getValue(random, SEED_FIELD);
    }

    @Override
    public void setSeed(final long seed) {
        random.setSeed(this.seed = seed);
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public void setCompressedState(final long state) {
        seedState.set(state);
    }

    @Override
    public long getCompressedState() {
        return seedState.get();
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public short nextShort() {
        return (short) next(16);
    }

    @Override
    public short nextShort(final short bound) {
        return nextShort((short) 0, bound);
    }

    @Override
    public short nextShort(final short min, final short max) {
        if (max <= min) {
            return min;
        }
        return (short) (min + Math.abs(nextShort() % (max - min)));
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(final int bound) {
        return random.nextInt(bound);
    }

    @Override
    public int nextInt(final int min, final int max) {
        return min + random.nextInt(max - min);
    }

    @Override
    public long nextLong() {
        return random.nextLong();
    }

    @Override
    public long nextLong(final long bound) {
        return nextLong(0L, bound);
    }

    @Override
    public long nextLong(final long min, final long max) {
        if (max <= min) {
            return min;
        }
        return min + Math.abs(nextLong() % (max - min));
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public float nextFloat(final float bound) {
        return nextFloat(0, bound);
    }

    @Override
    public float nextFloat(final float min, final float max) {
        if (max <= min) {
            return min;
        }
        return min + nextFloat() * (max - min);
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public double nextDouble(final double bound) {
        return nextDouble(0, bound);
    }

    @Override
    public double nextDouble(final double min, final double max) {
        if (max <= min) {
            return min;
        }
        return min + nextDouble() * (max - min);
    }

}
