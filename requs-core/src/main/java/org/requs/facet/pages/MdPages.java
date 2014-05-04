/**
 * Copyright (c) 2009-2014, requs.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the requs.org nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.requs.facet.pages;

import com.jcabi.aspects.Immutable;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.petebevin.markdown.MarkdownProcessor;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.requs.Doc;
import org.requs.Docs;
import org.requs.Facet;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Markdown pages.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.7
 */
@Immutable
@ToString(of = { })
@EqualsAndHashCode
public final class MdPages implements Facet {

    @Override
    public void touch(final Docs docs) throws IOException {
        final Directives dirs = new Directives()
            .xpath("/")
            .pi(
                "xml-stylesheet",
                "href='pages.xsl' type='text/xsl'"
            )
            // @checkstyle MultipleStringLiteralsCheck (1 line)
            .add("pages");
        final XML main = new XMLDocument(docs.get("main.xml").read());
        for (final XML page : main.nodes("/spec/pages/page")) {
            final String title = page.xpath("title/text()").get(0);
            docs.get(String.format("pages/%s.html", title)).write(
                MdPages.html(
                    title,
                    page.xpath("info/informal/text()").get(0)
                )
            );
            dirs.add("page").attr("title", title).up();
        }
        final Doc index = docs.get("pages.xml");
        try {
            index.write(new Xembler(dirs).xml());
        } catch (final ImpossibleModificationException ex) {
            throw new IllegalStateException(ex);
        }
        index.name("pages", "Markdown Pages");
        // @checkstyle MultipleStringLiteralsCheck (1 line)
        docs.get("pages.xsl").write(
            IOUtils.toString(
                this.getClass().getResourceAsStream("pages.xsl"),
                CharEncoding.UTF_8
            )
        );
    }

    /**
     * Convert markdown to HTML.
     * @param title Title of it
     * @param markdown Markdown
     * @return HTML
     */
    private static String html(final String title, final String markdown) {
        return StringUtils.join(
            "<!DOCTYPE html><html xmlns='http://www.w3.org/1999/xhtml'>",
            "<head>",
            String.format(
                "<title>%s</title>",
                StringEscapeUtils.escapeHtml4(title)
            ),
            "<link rel='stylesheet' type='text/css' href='../requs.css'/>",
            "</head><body>",
            String.format(
                "<h1>%s</h1>",
                StringEscapeUtils.escapeHtml4(title)
            ),
            new MarkdownProcessor().markdown(markdown),
            "</body></html>"
        );
    }

}
