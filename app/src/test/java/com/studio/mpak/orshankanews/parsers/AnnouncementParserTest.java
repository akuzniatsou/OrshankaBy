package com.studio.mpak.orshankanews.parsers;

import static org.junit.Assert.*;

import com.studio.mpak.orshankanews.domain.Announcement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * //TODO: [before commit] class description.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/17/2018
 *
 * @author Andrei Kuzniatsou
 */
public class AnnouncementParserTest {
    private AnnouncementParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new AnnouncementParser();
    }

    @Test
    public void testParser() throws Exception {
        File file = new File("C:\\workspace\\OrshankaBy\\app\\src\\test\\resources", "announsement_source.html");
        InputStream inputStream = new FileInputStream(file);
        System.out.println(file);

        Document document = Jsoup.parse(file, "UTF-8");
        ArrayList<Announcement<String>> announcements = parser.parse(document);
        System.out.println(announcements);


    }
}