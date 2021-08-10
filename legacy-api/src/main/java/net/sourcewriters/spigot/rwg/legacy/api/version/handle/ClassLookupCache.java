package net.sourcewriters.spigot.rwg.legacy.api.version.handle;

public class ClassLookupCache extends AbstractClassLookupCache<ClassLookup> {

    @Override
    protected ClassLookup create(Class<?> clazz) {
        return ClassLookup.of(clazz);
    }

    @Override
    protected ClassLookup create(String path) {
        return ClassLookup.of(path);
    }

}
