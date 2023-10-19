package com.rit.robusta.inmemory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public abstract class InMemoryRepository<Key, Entity> {

    private final Map<Key, Entity> map = new ConcurrentHashMap<>();
    private final Class<Key> keyClass;
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Class<Entity> entityClass;

    protected InMemoryRepository(Class<Key> keyClass, Class<Entity> entityClass) {
        this.keyClass = keyClass;
        this.entityClass = entityClass;
    }

    public Optional<Entity> findById(Key id) {
        return Optional.ofNullable(map.get(id));
    }

    public Optional<Entity> findBy(Predicate<Entity> predicate) {
        return map.values().stream().filter(predicate).findAny();
    }

    public Collection<Entity> findAllBy(Predicate<Entity> predicate) {
        return map.values().stream().filter(predicate).toList();
    }

    public void delete(Entity entity) {
        map.remove(provideKey(entity), entity);
    }

    public void deleteById(Key key) {
        map.remove(key);
    }

    public Entity save(Entity entity) {
        var key = provideNotNullKey(entity);
        overrideKey(entity, key);
        map.put(key, entity);
        return entity;
    }

    private Key provideNotNullKey(Entity entity) {
        return Optional.ofNullable(provideKey(entity)).orElseGet(this::randomKey);
    }

    protected Key randomKey() {
        if (keyClass.equals(String.class)) {
            return keyClass.cast(UUID.randomUUID().toString());
        }

        throw new UnsupportedOperationException("Cannot generate random key with class " + keyClass.getSimpleName());
    }

    protected abstract Key provideKey(Entity entity);

    protected abstract void overrideKey(Entity entity, Key key);
}
