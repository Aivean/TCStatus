package org.aivean.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author izaytsev
 *         3/1/12 11:48 AM
 */
public class StringUtilTest {
    @Test
    public void testFormatTimeSec() throws Exception {
        Assert.assertEquals("1:02", StringUtil.formatTimeSec(1 * 60 + 2));
        Assert.assertEquals("1:01:02", StringUtil.formatTimeSec(1 * 3600 + 1 * 60 + 2));
    }
}
