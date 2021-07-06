package org.mockito;

import org.mockito.junit.MockitoJUnitRunner;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mark a field as a fake to be injected when using &#64;InjectMocks. Those fields will be injected during the
 * constructor
 * injection, allowing to inject types that cannot support mock or spy annotation (e.g. Strings, enums, etc).
 * <p>This annotation is supported only if a single &#64;InjectMocks annotation is present.</p>
 * <p>
 * Example:
 * <pre class="code"><code class="java">
 * public class Test{
 *    //Instance that will receive the fakes
 *    &#64;InjectMocks Target target;
 *    &#64;Mock Foo foo;
 *    &#64;Fake(1) String bar = "bar";
 *    &#64;Fake(2) String baz = "baz";
 *    private AutoCloseable closeable;
 *    &#64;Before
 *    public void init() {
 *       closeable = MockitoAnnotations.openMocks(this);
 *    }
 *    &#64;After
 *    public void release() throws Exception {
 *       closeable.close();
 *    }
 *    ...
 * }
 * </code></pre>
 * <p>
 * Same as doing:
 *
 * <pre class="code"><code class="java">
 * public class Test{
 *    //Instance that will receive the fakes
 *    Target target;
 *    &#64;Mock Foo foo;
 *    String bar = "bar";
 *    String baz = "baz";
 *    private AutoCloseable closeable;
 *    &#64;Before
 *    public void init() {
 *       closeable = MockitoAnnotations.openMocks(this);
 *       target = new Target(foo, bar, baz);
 *    }
 *    &#64;After
 *    public void release() throws Exception {
 *       closeable.close();
 *    }
 *    ...
 * }
 * </code></pre>
 * <p>
 * This annotation makes more sense when combined with annotation based configuration:
 *
 * <pre class="code"><code class="java">
 * &#64;RunWith(MockitoJUnitRunner.class)
 * public class Test{
 *    //Instance that will receive the fakes
 *    &#64;InjectMocks Target target;
 *    &#64;Mock Foo foo;
 *    &#64;Fake(1) String bar = "bar";
 *    &#64;Fake(2) String baz = "baz";
 *    ...
 * }
 * </code></pre>
 * <p>
 * Note that Mockito <strong>*does not*</strong> close the injected resources as it will with mocks or spies.
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
@Documented
public @interface Fake {

    /**
     * @return the constructor argument position of this fake
     */
    int value();

}
