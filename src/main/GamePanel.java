package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int delay = 100;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    Timer timer;
    Random random;

    boolean running;
    Integer score;
    Integer bombs;
    Integer flags;

    GamePanel () {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Integer clickX = e.getX();
                Integer clickY = e.getY();

                boolean isLeft = SwingUtilities.isLeftMouseButton(e);
                boolean isRight = SwingUtilities.isRightMouseButton(e);

                if (isRight) {
                    checkBomb(clickX, clickY);
                } else if (isLeft) {
                    addFlag(clickX, clickY);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        gameStart();
    }

    public void gameStart () {
        initField();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void initField () {

    }

    public void checkBomb (Integer x, Integer y) {

    }

    public void addFlag (Integer x, Integer y) {

    }

    public void gameOver () {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {

        }
    }
}
