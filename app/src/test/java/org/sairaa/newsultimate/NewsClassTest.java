package org.sairaa.newsultimate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NewsClassTest {
    News news;
    @Before
    public void setUp()throws Exception{

        news = new News("aa",
                "aa","aaa","ddd","dd",
                "ddd","ddd","ddd",null,"ddd","dd");
    }
    @Test
    public void testGetTitle() throws Exception{
        assertEquals("ddd",news.getWebTitle());
    }
}
