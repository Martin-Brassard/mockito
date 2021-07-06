package org.mockito.internal.configuration.injection.scanner;

import org.mockito.Fake;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.util.reflection.FieldReader;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Scan fakes to allow injection.
 */
public class FakeScanner {
    private final Object instance;
    private final Class<?> clazz;

    /**
     * Creates a FakeScanner.
     *
     * @param instance The test instance
     * @param clazz The class in the type hierarchy of this instance.
     */
    public FakeScanner(Object instance, Class<?> clazz) {
        this.instance = instance;
        this.clazz = clazz;
    }

    /**
     * Scan and add fakes for the given <code>testClassInstance</code> and <code>clazz</code> in the type hierarchy.
     *
     * @param fakes list of fakes
     */
    public void addFakes(List<Object> fakes) {
        for (Field field : clazz.getDeclaredFields()) {
            // stubs only
            FieldReader fieldReader = new FieldReader(instance, field);
            if (field.isAnnotationPresent(Fake.class)) {
                addToPos(fakes, field.getAnnotation(Fake.class).value(), fieldReader.read());
            }
        }
    }

    private void addToPos(List<Object> fakes, int pos, Object o) {
        for (int i = fakes.size(); i <= pos; i++) {
            fakes.add(null);
        }
        if (fakes.get(pos) != null) {
            throw new MockitoException("Invalid fake declaration. Multiple fields declared for position " + pos);
        }
        fakes.set(pos, o);
    }
}
