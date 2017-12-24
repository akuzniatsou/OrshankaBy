package com.studio.mpak.orshankanews.parsers;

import org.jsoup.nodes.Document;

public interface DocumentParser<T> {

    T parse(Document document);
}
