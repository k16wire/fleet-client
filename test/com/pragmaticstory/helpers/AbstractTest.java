package com.pragmaticstory.helpers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.After;
import org.junit.Before;
import play.test.FakeApplication;
import play.test.Helpers;

/**
 * User: 1001923
 * Date: 15. 3. 27.
 * Time: 오후 4:07
 */
public class AbstractTest {
    protected static FakeApplication app;

    @Before
    public void before() throws Exception{
        app = Helpers.fakeApplication();
        Helpers.start(app);
    }

    @After
    public void after() throws Exception{
        Helpers.stop(app);
    }

    public String datetime(){
        return new DateTime(DateTime.now()
                .toDate())
                .toString(DateTimeFormat
                        .forPattern("yyyyMMddHHmmss"));
    }
}
