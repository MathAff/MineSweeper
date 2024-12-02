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
    static final int UNIT_SIZE = 120;
//    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int delay = 100;
//    final int[] x = new int[GAME_UNITS];
//    final int[] y = new int[GAME_UNITS];
    Timer timer;
    Random random;

    boolean running;
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
                int clickX = e.getX();
                int clickY = e.getY();


                boolean isLeft = SwingUtilities.isLeftMouseButton(e);
                boolean isRight = SwingUtilities.isRightMouseButton(e);

                if (isRight) {
                    if (checkBomb(clickX, clickY)) {
                        gameOver();
                    } else {
                        System.out.println("There is "+ checkBombsAround(clickX / UNIT_SIZE, clickY / UNIT_SIZE)+" bombs in ("+clickX / UNIT_SIZE+", "+clickY / UNIT_SIZE+")");
                    }
                } else if (isLeft) {
                    int a = addFlag(clickX, clickY);
                    System.out.println(a);
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

    private Integer checkBombsAround(Integer x, Integer y) {

        int bombsAround;
        bombsAround = 0;

        if (!checkBomb(x, y)) {
            for (int k = 1; k <= 8; k++) {
                switch (k) {
                    case 1:
                        if(checkBomb(x-1, y-1)){
                            bombsAround++;
                        }
                        break;
                    case 2:
                        if (checkBomb(x-1, y)){
                            bombsAround++;
                        }
                        break;
                    case 3:
                        if (checkBomb(x-1, y+1)){
                            bombsAround++;
                        }
                        break;
                    case 4:
                        if (checkBomb(x, y-1)){
                            bombsAround++;
                        }
                        break;
                    case 5:
                        if (checkBomb(x, y+1)){
                            bombsAround++;
                        }
                        break;
                    case 6:
                        if (checkBomb(x+1, y-1)){
                            bombsAround++;
                        }
                        break;
                    case 7:
                        if (checkBomb(x+1, y)){
                            bombsAround++;
                        }
                        break;
                    case 8:
                        if (checkBomb(x+1, y+1)){
                            bombsAround++;
                        }
                        break;
                }
            }
            return bombsAround;
        } else {
            return -1;
        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g) {
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            for (ArrayList<Integer> bomb : bombsList) {
                g.setColor(Color.red);
                g.fillRect(bomb.getFirst() * UNIT_SIZE, bomb.get(1) * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }

            Integer bombsAround;
//            Color color;

            for (int i = 0; i <= SCREEN_HEIGHT / UNIT_SIZE; i++) {

                for (int j = 0; j <= SCREEN_WIDTH / UNIT_SIZE; j++) {

                    bombsAround = checkBombsAround(i, j);

                    if (bombsAround > 0) {
                        g.setColor(Color.green);
                        g.setFont(new Font("Arial", Font.BOLD, 50));
//                        System.out.println("("+i+", "+j+") has "+bombsAround+" bombs");
                        g.drawString(String.valueOf(bombsAround), (i * UNIT_SIZE), (j * UNIT_SIZE));
                    }
                }
            }
        }
    }

    public void gameStart () {
        this.bombs = 10;
        this.flags = 10;
        running = true;
        initField();
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
        for (int i = 0; i < bombs; i++) {
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

    public boolean checkBomb (Integer x, Integer y) {
        ArrayList <Integer> checkBomb = new ArrayList<>();
        checkBomb.add(x);
        checkBomb.add(y);

        return bombsList.contains(checkBomb);
    }

//    private ArrayList<Integer> getGrid(Integer x, Integer y) {
//        Integer xCoordinate = x/UNIT_SIZE;
//        Integer yCoordinate = y/UNIT_SIZE;
//
//        ArrayList <Integer> gridCoordinate = new ArrayList<>();
//        gridCoordinate.add(xCoordinate);
//        gridCoordinate.add(yCoordinate);
//
//        return gridCoordinate;
//    }

    public Integer addFlag (Integer x, Integer y) {
        return x + y;
    }

    public void gameOver () {
        System.out.println("You Lose");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {

        }
    }
}
