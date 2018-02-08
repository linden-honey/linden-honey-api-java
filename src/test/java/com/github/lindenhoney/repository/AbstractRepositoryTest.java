package com.github.lindenhoney.repository;

import com.github.lindenhoney.util.TestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
abstract public class AbstractRepositoryTest<T extends Persistable<ID>, ID extends Serializable, REPO extends PagingAndSortingRepository<T, ID>> {

    protected T entity;
    protected List<? extends T> entities;
    protected Sort sort;
    protected Pageable pageable;
    protected List<String> columnNames;

    @Autowired
    protected REPO repository;

    protected abstract T generateEntity();

    protected List<? extends T> generateEntities(int maxEntitiesCount) {
        return IntStream
                .rangeClosed(TestUtils.MIN_ENTITIES_COUNT, TestUtils.getRandomInteger(maxEntitiesCount))
                .parallel()
                .mapToObj(it -> generateEntity())
                .collect(Collectors.toList());
    }

    protected Sort generateSort() {
        return Sort.by(getRandomFieldName());
    }

    protected Pageable generatePageable() {
        return PageRequest.of(1, TestUtils.getRandomInteger(TestUtils.MAX_ENTITIES_COUNT));
    }

    protected List<String> getFieldsNames() {
        return Stream.of(entity.getClass().getDeclaredFields())
                .filter(field -> !"serialVersionUID".equals(field.getName()))
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    protected String getRandomFieldName() {
        return getFieldsNames().get(RandomUtils.nextInt(0, getFieldsNames().size()));
    }

    @Before
    @Rollback
    public void initialization() {
        this.entity = generateEntity();
        this.entities = generateEntities(TestUtils.MAX_ENTITIES_COUNT);
        this.sort = generateSort();
        this.pageable = generatePageable();
        this.columnNames = getFieldsNames();
    }

    @Test
    public void countEntitiesTest() {
        final long before = repository.count();
        repository.save(entity);
        final long actual = repository.count();

        assertThat(actual, is(before + 1));
    }

    @Test
    public void deleteEntityByIdTest() {
        final ID savedId = repository.save(entity).getId();
        repository.deleteById(savedId);

        assertThat(repository.existsById(savedId), is(false));
    }

    @Test
    public void deleteSequenceOfEntitiesTest() {
        final Iterable<? extends T> saved = repository.saveAll(entities);
        final List<ID> ids = StreamSupport.stream(saved.spliterator(), true)
                .map(Persistable::getId)
                .collect(Collectors.toList());
        repository.deleteAll(saved);

        assertThat(repository.findAllById(ids), emptyIterable());
    }

    @Test
    public void deleteEntityTest() {
        final T saved = repository.save(entity);
        repository.delete(saved);

        assertThat(repository.existsById(saved.getId()), is(false));
    }

    @Test
    public void deleteAllEntitiesTest() {
        final T entities = repository.save(entity);
        repository.deleteAll();

        assertThat(repository.findAll(), not(hasItem(entities)));
    }

    @Test
    public void entityExistenceByIdTest() {
        final T saved = repository.save(entity);

        assertThat(repository.existsById(saved.getId()), is(true));
    }

    @Test
    public void findAllEntitiesTest() {
        final Iterable<? extends T> saved = repository.saveAll(entities);

        assertThat(repository.findAll(), is(saved));
    }

    @Test
    public void findAllEntitiesByIds() {
        final Iterable<? extends T> saved = repository.saveAll(entities);
        final List<ID> ids = StreamSupport.stream(saved.spliterator(), true)
                .map(Persistable::getId)
                .collect(Collectors.toList());
        final Iterable<T> found = repository.findAllById(ids);

        assertThat(found, is(saved));
    }

    @Test
    public void findAllEntitiesByPageableTest() {
        repository.saveAll(entities);
        final Page<T> found = repository.findAll(pageable);

        assertThat(found.getTotalPages() <= entities.size(), is(true));
    }

    @Test
    public void findAllEntitiesBySortTest() {
        repository.saveAll(entities);
        final Iterable<T> found = repository.findAll(sort);

        assertThat(found, not(emptyIterable()));
    }

    @Test
    public void findByIdEntityByIdTest() {
        final T saved = repository.save(entity);
        final T found = repository.findById(entity.getId()).orElse(null);

        assertThat(saved, is(found));
    }

    @Test
    public void saveSequenceOfEntitiesTest() {
        final Iterable<? extends T> saved = repository.saveAll(entities);

        assertThat(repository.findAll(), is(saved));
    }

    @Test
    public void saveOneEntityTest() {
        final T saved = repository.save(entity);

        assertThat(repository.findById(saved.getId()), is(saved));
    }

    @Test
    public void saveOneEntityAndFlushTest() {
        final T saved = repository.save(entity);

        assertThat(repository.findById(saved.getId()), is(saved));
    }
}
