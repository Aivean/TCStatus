package org.aivean.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author izaytsev
 *         3/11/12 11:02 PM
 */
public class Resource {

    public static final BufferedImage ALARM = loadImageIfAvailable("alarm.png");
    public static final BufferedImage PERSONAL_BUILD_ICON_BLUE = loadImageIfAvailable("personal_icon_blue.png");
    public static final BufferedImage PERSONAL_BUILD_ICON_RED = loadImageIfAvailable("personal_icon_red.png");
    public static URL ALERT_SOUND_URL = Resource.class.getClassLoader().getResource("SysAlert.wav");
    public static URL BEEP_SOUND_URL = Resource.class.getClassLoader().getResource("beep.wav");

    private static BufferedImage loadImageIfAvailable(String resourceName) {
        try {
            return ImageIO.read(Resource.class.getClassLoader().getResource(resourceName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
