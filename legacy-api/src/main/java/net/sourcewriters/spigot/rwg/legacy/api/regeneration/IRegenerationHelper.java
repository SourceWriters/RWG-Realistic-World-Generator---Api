package net.sourcewriters.spigot.rwg.legacy.api.regeneration;

import org.bukkit.Chunk;
import org.bukkit.Location;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.info.IStatus;

public interface IRegenerationHelper {

    /**
     * Regenerates a chunk
     * 
     * @param chunk to be regenerated
     * 
     * @return the current status object to keep track of the process
     */
    IStatus regenerate(Chunk chunk);

    /**
     * Regenerates a range of chunks
     * 
     * @param chunks to be regenerated
     * 
     * @return the current status objects to keep track of the process
     * 
     *         The status of this method will contain two different status objects.
     *         The first one is a normal Status object which keeps track of the
     *         chunk progress while the other object is just a reference which
     *         passes the values of the current chunk through, basically the second
     *         one is the same as the one of {@code regenerate(Chunk)} while the
     *         other one is to keep track how many chunks are done and if the
     *         process is complete.
     */
    IStatus[] regenerate(Chunk[] chunks);

    /**
     * Gets the chunks between two positions
     * 
     * @param first  position
     * @param second position
     * 
     * @return the chunks between the first and second position
     */
    Chunk[] getChunks(Location first, Location second);

    /**
     * Gets the chunks between two positions and merges them with an existing array
     * of chunks
     * 
     * @param first  position
     * @param second position
     * @param array  the existing array of chunks
     * 
     * @return the chunks between the first and second position
     */
    default Chunk[] getChunksAndMerge(Location first, Location second, Chunk... array) {
        Chunk[] current = getChunks(first, second);
        Chunk[] output = new Chunk[current.length + array.length];
        System.arraycopy(current, 0, output, 0, current.length);
        System.arraycopy(array, 0, output, current.length, array.length);
        return output;
    }

}
