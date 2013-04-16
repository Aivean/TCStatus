package org.aivean.util;

import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

/**
 * @author izaytsev
 *         3/11/12 10:24 PM
 */
@Ignore
public class AudioPlayerTest {
    @Test
    public void testPlay() throws Exception {
        AudioPlayer.play(Resource.ALERT_SOUND_URL);
    }
}
