/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.configuration;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mockito.internal.configuration.injection.MockInjection;

/**
 * Inject mock/spies dependencies for fields annotated with &#064;InjectMocks
 * <p/>
 * See {@link org.mockito.MockitoAnnotations}
 */
public class DefaultInjectionEngine {

    public void injectMocksOnFields(
        Set<Field> needingInjection, Set<Object> mocks, List<Object> fakes, Object testClassInstance) {
        MockInjection.onFields(needingInjection, testClassInstance)
                .withMocks(mocks)
                .withFakes(fakes)
                .tryConstructorInjection()
                .tryPropertyOrFieldInjection()
                .handleSpyAnnotation()
                .apply();
    }
}
