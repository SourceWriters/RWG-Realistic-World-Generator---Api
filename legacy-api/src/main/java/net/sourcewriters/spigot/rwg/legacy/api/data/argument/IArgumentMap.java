package net.sourcewriters.spigot.rwg.legacy.api.data.argument;

import net.sourcewriters.spigot.rwg.legacy.api.data.argument.exception.ArgumentStack;

public interface IArgumentMap {
    
    boolean has(String key);
    
    boolean has(String key, Class<?> type);
    
    Option<Object> get(String key);
    
    <E> Option<E> get(String key, Class<E> type);
    
    default <E> E getOrStack(String key, Class<E> type, ArgumentStack stack) {
        return get(key, type).orElseRun(() -> stack.push(key, type));
    }
    
    IArgumentMap set(String key, Object value);
    
    IArgumentMap remove(String key);
    
    IArgumentMap clear();
    
    IArgumentMap clone();

}
