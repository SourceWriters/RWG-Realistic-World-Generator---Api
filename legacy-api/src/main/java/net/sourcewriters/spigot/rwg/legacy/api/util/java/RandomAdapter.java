package net.sourcewriters.spigot.rwg.legacy.api.util.java;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookup;

public final class RandomAdapter extends RandomNumberGenerator {

    private static final ClassLookup LOOKUP = ClassLookup.of(Random.class).searchField("seed", "seed", AtomicLong.class);
    
    private final Random random;
    private final AtomicLong seedState;
    
    private long seed;

    public RandomAdapter(Random random) {
        this.random = random;
        this.seedState = (AtomicLong) LOOKUP.getFieldValue(random, "seed");
    }

    @Override
    public void setSeed(long seed) {
        random.setSeed(this.seed = seed);
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public void setCompressedState(long state) {
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
    public short nextShort(short bound) {
        return nextShort((short) 0, bound);
    }

    @Override
    public short nextShort(short min, short max) {
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
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Override
    public int nextInt(int min, int max) {
        return min + random.nextInt(max - min);
    }

    @Override
    public long nextLong() {
        return random.nextLong();
    }

    @Override
    public long nextLong(long bound) {
        return nextLong(0L, bound);
    }

    @Override
    public long nextLong(long min, long max) {
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
    public float nextFloat(float bound) {
        return nextFloat(0, bound);
    }

    @Override
    public float nextFloat(float min, float max) {
        if (max <= min) {
            return min;
        }
        return min + (nextFloat() * (max - min));
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public double nextDouble(double bound) {
        return nextDouble(0, bound);
    }

    @Override
    public double nextDouble(double min, double max) {
        if (max <= min) {
            return min;
        }
        return min + (nextDouble() * (max - min));
    }

}
