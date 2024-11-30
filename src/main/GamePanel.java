package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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

    ArrayList <ArrayList<Integer>> bombsList = new ArrayList<>(10);

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

                ArrayList<Integer> click = getGrid(clickX, clickY);
                System.out.println("Mouse click in: "+click.getFirst()+", "+click.get(1));

                boolean isLeft = SwingUtilities.isLeftMouseButton(e);
                boolean isRight = SwingUtilities.isRightMouseButton(e);

                if (isRight) {
                    if (checkBomb(getGrid(clickX, clickY))) {
                        gameOver();
                    }
                } else if (isLeft) {
                    addFlag(clickX, clickY);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        gameStart();
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g) {
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            for (int i = 0; i < 10; i++) {
                g.setColor(Color.red);
                g.fillRect(bombsList.get(i).getFirst() * UNIT_SIZE, bombsList.get(i).get(1) * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void gameStart () {
        initField();
        score = 0;
        bombs = 10;
        flags = 10;
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public boolean checkCoordinate (Integer x, Integer y) {
        ArrayList <Integer> bomb = new ArrayList<>();
        bomb.add(x);
        bomb.add(y);

        return bombsList.contains(bomb);
    }

    public void initField () {
        random = new Random();
        ArrayList <Integer> Coordinates;
        for (int i = 0; i < 10; i++) {
            Coordinates = new ArrayList<>();

            Integer x = random.nextInt(SCREEN_WIDTH/UNIT_SIZE);
            Integer y = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE);

            if (!checkCoordinate(x, y)) {
                Coordinates.add(x);
                Coordinates.add(y);
                bombsList.add(Coordinates);
            }
        }
        System.out.println(bombsList);

        bombsList.sort((bomb1, bomb2) -> {
            if (!bomb1.getFirst().equals(bomb2.getFirst())) {
                return bomb1.getFirst() - bomb2.getFirst();
            } else {
                return bomb1.get(1) - bomb2.get(1);
            }
        });
    }

    public boolean checkBomb (ArrayList <Integer> checkBomb) {
        return bombsList.contains(checkBomb);
    }

    private ArrayList<Integer> getGrid(Integer x, Integer y) {
        Integer xCoordinate = x/UNIT_SIZE;
        Integer yCoordinate = y/UNIT_SIZE;
        
        ArrayList <Integer> gridCoordinate = new ArrayList<>();
        gridCoordinate.add(xCoordinate);
        gridCoordinate.add(yCoordinate);
        
        return gridCoordinate;
    }

    public void addFlag (Integer x, Integer y) {

    }

    public void gameOver () {
        System.out.println("Perdeu otário HAHAHAHA");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {

        }
    }
}
