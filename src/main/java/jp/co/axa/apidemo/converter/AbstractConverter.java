package jp.co.axa.apidemo.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AbstractConverter
 * AbstractConverter class is an abstract class that provides method in order to convert entity to
 * dto and vice versa.
 *
 * @param <T> Entity Class.
 * @param <V> Dto Class.
 */
public abstract class AbstractConverter<T, V> {

    /**
     * Convert entity <T> to dto <V>.
     *
     * @param entity entity <T>.
     * @return dto <V>.
     */
    public abstract V entityToDto(T entity);

    /**
     * Convert dto <V> to entity <T>.
     *
     * @param dto dto <V>.
     * @return entity <T>.
     */
    public abstract T dtoToEntity(V dto);

    /**
     * Convert List of entities <T> to List of dtos <V>.
     *
     * @param entityList List of entities.
     * @return List<V> List of dtos.
     */
    public List<V> entityToDtoList(List<T> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
    }

    /**
     * Convert List of dtos <V> to List of entities <T>.
     *
     * @param dtoList List of dtos.
     * @return List<T> List of entities.
     */
    public List<T> dtoToEntityList(List<V> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream().map(entity -> dtoToEntity(entity)).collect(Collectors.toList());
    }
}