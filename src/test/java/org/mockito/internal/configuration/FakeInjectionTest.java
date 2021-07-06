/*
 * Copyright (c) 2019 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.configuration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Fake;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

public class FakeInjectionTest {

    @InjectMocks Target target;
    @Mock Foo foo;
    @Fake(0) Foo first = new Foo();
    @Mock Bar bar;
    @Fake(2) String baz = "baz";
    private AutoCloseable closable;

    @Before
    public void init() {
        closable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void release() throws Exception {
        closable.close();
    }

    @Test
    public void injectMocks() {
        Assert.assertSame(first, target.getFoo());
        Assert.assertSame(bar, target.getBar());
        Assert.assertEquals(baz, target.getBaz());
    }

    public static class Target {
        private final Foo foo;
        private final Bar bar;
        private final String baz;

        public Target(Foo foo, Bar bar, String baz) {
            this.foo = foo;
            this.bar = bar;
            this.baz = baz;
        }

        public Foo getFoo() {
            return foo;
        }

        public Bar getBar() {
            return bar;
        }

        public String getBaz() {
            return baz;
        }
    }

    public static class Foo {
    }

    public static class Bar {
    }
}
