import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BrickBreaker extends JFrame {

    public Screen screen;
    public long start;
    public Color[] colors = new Color[] {Color.RED, Color.GREEN, Color.BLUE};
    public List<Brick> bricks = new ArrayList<>();
    public int[] redBall = new int[] {
            400, 500,
            1, 3
    };
    public int[] greenBall = new int[] {
            500, 400,
            1, 3
    };
    public int[] blueBall = new int[] {
            300, 300,
            1, 3
    };
    public int plane = 10;

    class Brick {

        public Color color;
        public Point p;

        public Brick(Point loc) {
            color = colors[(int) Math.floor(Math.random()*3)];
            p = loc;
        }

    }

    public boolean active = true, pause = false;

    class Screen extends Canvas {

        public Screen() {
            setBackground(Color.BLACK);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            if (active) {
                if (redBall[1] <= 0) {
                    redBall[3] *= -1;
                    redBall[2] *= -1;
                }
                if (greenBall[1] <= 0) {
                    greenBall[3] *= -1;
                    greenBall[2] *= -1;
                }
                if (blueBall[1] <= 0) {
                    blueBall[3] *= -1;
                    blueBall[2] *= -1;
                }

                if (redBall[0] <= 0) {
                    redBall[2] *= -1;
                }
                if (greenBall[0] <= 0) {
                    greenBall[2] *= -1;
                }
                if (blueBall[0] <= 0) {
                    blueBall[2] *= -1;
                }

                if (redBall[0] >= getWidth()) {
                    redBall[2] *= -1;
                }
                if (greenBall[0] >= getWidth()) {
                    greenBall[2] *= -1;
                }
                if (blueBall[0] >= getWidth()) {
                    blueBall[2] *= -1;
                }

                if (redBall[1] >= getHeight()) {
                    active = false;
                }
                if (greenBall[1] >= getHeight()) {
                    active = false;
                }
                if (blueBall[1] >= getHeight()) {
                    active = false;
                }

                if (redBall[0] >= plane && redBall[0] - 20 <= plane + 150) {
                    if (redBall[1] + 20 >= 700 && redBall[1] - 20 <= 720) {
                        redBall[2] *= -1;
                        redBall[3] *= -1;
                    }
                }

                if (greenBall[0] >= plane && greenBall[0] - 20 <= plane + 150) {
                    if (greenBall[1] + 20 >= 700 && greenBall[1] - 20 <= 720) {
                        greenBall[2] *= -1;
                        greenBall[3] *= -1;
                    }
                }

                if (blueBall[0] >= plane && blueBall[0] - 20 <= plane + 150) {
                    if (blueBall[1] + 20 >= 700 && blueBall[1] - 20 <= 720) {
                        blueBall[2] *= -1;
                        blueBall[3] *= -1;
                    }
                }

                //drawing the bricks
                List<Brick> forRemoval = new ArrayList<>();
                for (Brick brick : bricks) {
                    g.setColor(brick.color);
                    g.fillRect(brick.p.x, brick.p.y, 40, 40);

                    if (brick.color == Color.RED) {
                        if (brick.p.x <= redBall[0] && brick.p.x + 40 >= redBall[0]) {
                            if (brick.p.y <= redBall[1] && brick.p.y + 40 >= redBall[1]) {
                                forRemoval.add(brick);
                                redBall[2] *= -(1 * Math.random() + 1);
                                if (redBall[2] <= 3) redBall[2] = 1;
                            }
                        }
                    }

                    if (brick.color == Color.GREEN) {
                        if (brick.p.x <= greenBall[0] && brick.p.x + 40 >= greenBall[0]) {
                            if (brick.p.y <= greenBall[1] && brick.p.y + 40 >= greenBall[1]) {
                                forRemoval.add(brick);
                                greenBall[2] *= -(1 * Math.random() + 1);
                                if (greenBall[2] <= 3) greenBall[2] = 1;
                            }
                        }
                    }

                    if (brick.color == Color.BLUE) {
                        if (brick.p.x <= blueBall[0] && brick.p.x + 40 >= blueBall[0]) {
                            if (brick.p.y <= blueBall[1] && brick.p.y + 40 >= blueBall[1]) {
                                forRemoval.add(brick);
                                blueBall[2] *= -(1 * Math.random() + 1);
                                if (blueBall[2] <= 3) blueBall[2] = 1;
                            }
                        }
                    }
                }
                bricks.removeAll(forRemoval);

                //drawing the red ball
                g.setColor(Color.RED);
                g.fillArc(redBall[0], redBall[1], 20, 20, 0, 360);
                redBall[0] += redBall[2];
                redBall[1] += redBall[3];

                //drawing the green ball
                g.setColor(Color.GREEN);
                g.fillArc(greenBall[0], greenBall[1], 20, 20, 0, 360);
                greenBall[0] += greenBall[2];
                greenBall[1] += greenBall[3];

                //drawing the blue ball
                g.setColor(Color.BLUE);
                g.fillArc(blueBall[0], blueBall[1], 20, 20, 0, 360);
                blueBall[0] += blueBall[2];
                blueBall[1] += blueBall[3];

                //drawing the plane
                g.setColor(Color.YELLOW);
                g.fillRect(plane, 700, 150, 20);
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Bold", Font.BOLD, 30));
                g.drawString("Seconds: " + ((System.currentTimeMillis() - start) / 1000), 50, 50);
            }
        }
    }

    public BrickBreaker() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.createGraphics();
        g.setColor(Color.RED);
        g.fillArc(0,0, 100, 100, 0, 360);
        setIconImage(img);
        start = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 25; j++) {
                bricks.add(new Brick(new Point(j * 70, i * 70)));
            }
        }

        screen = new Screen();
        setVisible(true);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Brick Breaker");
        add(screen);
        Thread gameLoop;
        gameLoop = new Thread() {
            @Override
            public synchronized void start() {
                super.start();
                int frames = 0;
                while (active) {
                    if (!pause) {
                        while (frames <= 50) {
                            if (isFocused()) screen.repaint();
                            try {
                                wait(20);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            frames++;
                        }
                        frames = 0;
                    }
                }
            }
        };
        gameLoop.start();
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        plane = e.getXOnScreen();
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getKeyChar() == 'd') plane += 10;
        if (e.getKeyChar() == 'a') plane -= 10;
        if (e.getKeyChar() == ' ') {
            pause = !pause;
            setTitle("Brick Breaker -Paused");
        }
    }
}
