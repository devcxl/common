package cn.devcxl.common.cache;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryCacheService<K, V> {
    private ConcurrentHashMap<K, CacheEntry<V>> cache;

    public MemoryCacheService() {
        this.cache = new ConcurrentHashMap<>();
    }

    public void put(K key, V value) {
        CacheEntry<V> entry = new CacheEntry<>(value, -1);
        cache.put(key, entry);
    }

    public void put(K key, V value, long expirationTime) {
        long currentTime = System.currentTimeMillis();
        CacheEntry<V> entry = new CacheEntry<>(value, currentTime + expirationTime);
        cache.put(key, entry);
    }

    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry != null) {
            long currentTime = System.currentTimeMillis();
            if (entry.getExpirationTime() == -1 || currentTime < entry.getExpirationTime()) {
                return entry.getValue();
            } else {
                // 缓存项已过期，移除
                cache.remove(key);
                return null;
            }
        }
        return null; // 缓存中不存在该键
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.forEach((k, v) -> {
            if (v != null) {
                long currentTime = System.currentTimeMillis();
                if (currentTime > v.getExpirationTime()) {
                    cache.remove(k);
                }
            }
        });
    }

    private static class CacheEntry<V> {
        private final V value;
        private final long expirationTime;

        public CacheEntry(V value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }

        public V getValue() {
            return value;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
}
