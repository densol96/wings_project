package lv.wings.util.mapping;

@FunctionalInterface
public interface ImageFactory<T, I> {
    I create(T entity, String url, Integer position);
}
