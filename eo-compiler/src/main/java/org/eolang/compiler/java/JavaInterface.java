/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 eolang.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.compiler.java;

import java.util.Collection;
import org.cactoos.Input;
import org.cactoos.io.BytesAsInput;
import org.cactoos.list.MappedIterable;
import org.cactoos.text.FormattedText;
import org.cactoos.text.JoinedText;
import org.cactoos.text.UncheckedText;
import org.eolang.compiler.syntax.Method;

/**
 * File with java interface.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class JavaInterface implements JavaFile {
    /**
     * Interface name.
     */
    private final String name;

    /**
     * Methods.
     */
    private final Collection<Method> methods;

    /**
     * Ctor.
     *
     * @param name Interface name
     * @param methods Interface methods
     */
    public JavaInterface(final String name, final Collection<Method> methods) {
        this.name = name;
        this.methods = methods;
    }

    @Override
    public String path() {
        return new UncheckedText(
            new FormattedText("%s.java", this.name)
        ).asString();
    }

    @Override
    public Input code() {
        return new BytesAsInput(
            new FormattedText(
                "package eo;\n\npublic interface %s {\n    %s\n}",
                this.name,
                new UncheckedText(
                    new JoinedText(
                        "\n    ",
                        new MappedIterable<>(
                            this.methods,
                            mtd -> new UncheckedText(
                                new FormattedText("%s;", mtd.java())
                            ).asString()
                        )
                    )
                ).asString()
            )
        );
    }
}
