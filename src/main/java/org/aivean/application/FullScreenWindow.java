package org.aivean.application;


import org.aivean.teamcity.model.Builds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;


/**
 * @author izaytsev
 *         2/29/12 11:06 PM
 *
 *  The main frame that provides canvas for drawing
 */

public class FullScreenWindow extends Window {

    public FullScreenWindow(boolean fullscreen) {
        super(new Frame() {{
            this.setResizable(false);
            this.setIgnoreRepaint(true);
            this.setUndecorated(true);
        }});

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()>=2) {
                    System.exit(0);
                }
            }
        });

        setLayout(new BorderLayout());

        //get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //sets the location of the window to top left of screen
        if (fullscreen) {
            setBounds(0, 0, screenSize.width, screenSize.height);
        } else {
            setBounds(screenSize.width-16*37, 0, screenSize.width, 9*37);
        }
    }




    public Graphics2D getGraphics() {
            BufferStrategy strategy = getBufferStrategy();
        if (strategy==null) {
            createBufferStrategy(2);
            strategy = getBufferStrategy();
            if (strategy == null) {
                throw new IllegalStateException("Cannot create buffer strategy!");
            }
        }
            return (Graphics2D)strategy.getDrawGraphics();
    }

    /**
     Updates the display.
     */
    public void update() {
            BufferStrategy strategy = getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
        }
        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }

}


