import javax.swing.JOptionPane;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.ArrayList;

public class Asteriods extends Applet implements KeyListener, ActionListener {

    heart heart1, heart2, heart3, heart4, heart5, heart6, heart7, heart8, heart9, heart10;
    int stars[][] = new int[500][2];
    SpaceCraft Ship;
    Timer Mr_Tock;
    Image offscreen;
    Graphics offg;
    Boolean upKey = false, leftKey = false, downKey = false, rightKey = false, spacekey = false, qkey = false;
    ArrayList<SpaceRock> rockList;
    ArrayList<bullet> bulletlist;
    ArrayList<debris> ExplosionList;
    ArrayList<cash> cashlist;
    int level, leveldelay, numberkey = 10, buy = 10, money = 9999999, asteroidKills = 0;
    boolean shop = false;
    boolean buyReady = true;
    boolean paused = false;

    public void init() {
        String rules = "Arrow keys to move. \nBack arrow key to apply breaks. \n"
                + "P to pause. \nHold space to shoot.\nShop appears at the end of each level. \n"
                + "Asteroids drop green squares which give money. \n"
                + "Hold q for quick shots. (Use only if planning on cheating)";
        JOptionPane.showMessageDialog(this, rules);
        if (paused == false) {
            Ship = new SpaceCraft();
            bulletlist = new ArrayList();
            rockList = new ArrayList();
            heart1 = new heart();
            heart1.xposition = 10;
            heart1.yposition = 10;
            heart1.updatePosition();
            heart2 = new heart();
            heart2.xposition = 60;
            heart2.yposition = 10;
            heart2.updatePosition();
            heart3 = new heart();
            heart3.xposition = 110;
            heart3.yposition = 10;
            heart3.updatePosition();
            heart4 = new heart();
            heart4.xposition = 160;
            heart4.yposition = 10;
            heart4.updatePosition();
            heart5 = new heart();
            heart5.xposition = 210;
            heart5.yposition = 10;
            heart5.updatePosition();
            heart6 = new heart();
            heart6.xposition = 260;
            heart6.yposition = 10;
            heart6.updatePosition();
            heart7 = new heart();
            heart7.xposition = 310;
            heart7.yposition = 10;
            heart7.updatePosition();
            heart8 = new heart();
            heart8.xposition = 360;
            heart8.yposition = 10;
            heart8.updatePosition();
            heart9 = new heart();
            heart9.xposition = 410;
            heart9.yposition = 10;
            heart9.updatePosition();
            heart10 = new heart();
            heart10.xposition = 460;
            heart10.yposition = 10;
            heart10.updatePosition();
            ExplosionList = new ArrayList();
            cashlist = new ArrayList();
            for (int i = 0; i < 9; i++) {
                Ship.upgrades[i][0] = 1;
            }
            Ship.upgrades[8][0] = 3;

            for (int i = 0; i < 2; i++) {
                rockList.add(new SpaceRock());
            }
            this.setSize(900, 600);
            this.addKeyListener(this);
            offscreen = createImage(this.getWidth(), this.getHeight());
            offg = offscreen.getGraphics();

            for (int i = 0; i < stars.length; i++) {

                stars[i][0] = (int) (Math.random() * 900);
                stars[i][1] = (int) (Math.random() * 600);
            }
            Mr_Tock = new Timer(20, this);
        }
    }

    public void start() {
        if (paused == false) {
            Mr_Tock.start();
        }
    }

    public void stop() {
        if (paused == true) {
            Mr_Tock.stop();
        }
    }

    public void actionPerformed(ActionEvent e) {

        keyCheck();
        if (paused == false) {
            Ship.updatePosition();
            for (int i = 0; i < rockList.size(); i++) {
                rockList.get(i).updatePosition();
            }
            for (int i = 0; i < bulletlist.size(); i++) {
                bulletlist.get(i).updatePosition();
                if (bulletlist.get(i).bulletcounter > 100 * (double) ((double) Ship.upgrades[6][0] / (3 + Ship.upgrades[4][0])) || bulletlist.get(i).active == false) {
                    bulletlist.remove(i);
                }
            }
            for (int i = 0; i < ExplosionList.size(); i++) {
                ExplosionList.get(i).updatePosition();
                if (ExplosionList.get(i).debriscounter > 50) {
                    ExplosionList.remove(i);
                }
            }
            for (int i = 0; i < cashlist.size(); i++) {
                cashlist.get(i).updatePosition();
                if (cashlist.get(i).cashcounter > 300 || cashlist.get(i).active == false) {
                    cashlist.remove(i);
                }
            }
            checkCollisions();
            checkrockdestruction();
            respawnShip();
            resetlevel();
        }
    }

    public void checkrockdestruction() {

        for (int i = 0; i < rockList.size(); i++) {

            if (rockList.get(i).active == false) {
                if (rockList.get(i).size > 1) {
                    rockList.add(new SpaceRock((int) rockList.get(i).xposition, (int) rockList.get(i).yposition, rockList.get(i).size - 1));
                    rockList.add(new SpaceRock((int) rockList.get(i).xposition, (int) rockList.get(i).yposition, rockList.get(i).size - 1));
                }
                rockList.remove(i);
            }
        }

    }

    public void paint(Graphics g) {

        if (paused == true) {
            g.setColor(Color.YELLOW);
            g.drawString("Game is Paused", 400, 300);
        }
        offg.setColor(Color.black);
        offg.fillRect(0, 0, 900, 600);
        offg.setColor(Color.yellow);
        for (int i = 0; i < stars.length; i++) {
            offg.drawOval(stars[i][0], stars[i][1], 1, 1);
        }

        offg.setColor(Color.ORANGE);
        offg.drawString("Cash " + money, 800, 20);
        for (int i = 0; i < rockList.size(); i++) {
            rockList.get(i).paint(offg);
        }
        offg.drawString("Level " + (level + 1), 800, 40);
        offg.drawString("Asteroids Shot " + asteroidKills, 750, 60);
        offg.setColor(Color.YELLOW);
        for (int i = 0; i < bulletlist.size(); i++) {
            bulletlist.get(i).paint(offg);
        }
        if (Ship.active) {
            offg.setColor(Color.BLUE);
            Ship.paint(offg);
        }
        offg.setColor(Color.red);
        for (int i = 0; i < ExplosionList.size(); i++) {
            ExplosionList.get(i).paint(offg);
        }
        offg.setColor(Color.green);
        for (int i = 0; i < cashlist.size(); i++) {
            cashlist.get(i).paint(offg);
        }
        offg.setColor(Color.YELLOW);
        offg.setColor(Color.red);
        if (Ship.heart == 10) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
            heart4.paint(offg);
            heart5.paint(offg);
            heart6.paint(offg);
            heart7.paint(offg);
            heart8.paint(offg);
            heart9.paint(offg);
            heart10.paint(offg);
        }
        if (Ship.heart == 9) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
            heart4.paint(offg);
            heart5.paint(offg);
            heart6.paint(offg);
            heart7.paint(offg);
            heart8.paint(offg);
            heart9.paint(offg);
        }
        if (Ship.heart == 8) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
            heart4.paint(offg);
            heart5.paint(offg);
            heart6.paint(offg);
            heart7.paint(offg);
            heart8.paint(offg);
        }
        if (Ship.heart == 7) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
            heart4.paint(offg);
            heart5.paint(offg);
            heart6.paint(offg);
            heart7.paint(offg);
        }
        if (Ship.heart == 6) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
            heart4.paint(offg);
            heart5.paint(offg);
            heart6.paint(offg);
        }
        if (Ship.heart == 5) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
            heart4.paint(offg);
            heart5.paint(offg);
        }
        if (Ship.heart == 4) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
            heart4.paint(offg);
        }
        if (Ship.heart == 3) {
            heart1.paint(offg);
            heart2.paint(offg);
            heart3.paint(offg);
        }
        if (Ship.heart == 2) {
            heart1.paint(offg);
            heart2.paint(offg);
        }
        if (Ship.heart == 1) {
            heart1.paint(offg);
        }

        if (rockList.isEmpty()) {
            offg.setColor(Color.YELLOW);
            leveldelay++;
            offg.drawString("You Beat Level " + (level + 1), 400, 300);
            if (leveldelay > 450) {
                shop = true;
                shop(offg);
            }
            for (int number = 0; number < 10; number++) {
                ExplosionList.add(new debris((int) Ship.xposition, (int) Ship.yposition));
            }
            offg.setColor(Color.RED);
        } else if (Ship.heart <= 0) {
            offg.drawString("Game Over", 400, 300);
            offg.drawString("Score:", 400, 320);
            offg.drawString("          Level: " + (level + 1), 400, 340);
            offg.drawString("           Cash:  " + money, 400, 360);
            offg.drawString("  Asteroid Hits:  " + asteroidKills, 400, 380);
            offg.drawString("Total Score: " + (asteroidKills * (level + 1)), 400, 410);
            offg.setColor(Color.BLUE);
        }
        g.drawImage(offscreen, 0, 0, this);
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacekey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            qkey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_1) {
            numberkey = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            numberkey = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_3) {
            numberkey = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_4) {
            numberkey = 4;
        }
        if (e.getKeyCode() == KeyEvent.VK_5) {
            numberkey = 5;
        }
        if (e.getKeyCode() == KeyEvent.VK_6) {
            numberkey = 6;
        }
        if (e.getKeyCode() == KeyEvent.VK_7) {
            numberkey = 7;
        }
        if (e.getKeyCode() == KeyEvent.VK_8) {
            numberkey = 8;
        }
        if (e.getKeyCode() == KeyEvent.VK_9) {
            numberkey = 9;
        }
        /*if (e.getKeyCode() == KeyEvent.VK_N && shop == true && money >= 100000) {
        numberkey = 10;
        }*/
        if (e.getKeyCode() == KeyEvent.VK_0) {
            numberkey = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_P && shop != true) {
            if (paused == true) {
                paused = false;
            } else if (paused == false) {
                paused = true;
            }
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacekey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            qkey = false;
        }
        numberkey = 10;
        buyReady = true;
    }

    public void keyCheck() {
        if (paused == false) {
            if (upKey) {
                Ship.accelerate();
            }
            if (leftKey) {
                Ship.rotateLeft();
            }

            if (rightKey) {
                Ship.rotateRight();
            }
            if (spacekey) {
                if (Ship.firedelay > 20 && Ship.active) {
                    Ship.firedelay = 0;

                    for (int i = 1; i <= Ship.upgrades[7][0]; i++) {
                        bulletlist.add(new bullet((int) Ship.xposition, (int) Ship.yposition, (Ship.angle + Math.PI / 2) - ((Ship.upgrades[7][0] - 1) * (double) (10 * (Math.PI / 180))) + ((i - 1) * (double) (20 * (double) (Math.PI / 180))), (int) Ship.xspeed, (int) Ship.yspeed, Ship.upgrades[5][0], Ship.upgrades[4][0]));
                    }
                }
            }
            if (qkey) {
                if (Ship.active) {
                    Ship.firedelay = 0;
                    bulletlist.add(new bullet((int) Ship.xposition, (int) Ship.yposition, Ship.angle + Math.PI / 2, (int) Ship.xspeed, (int) Ship.yspeed, Ship.upgrades[5][0], Ship.upgrades[4][0]));
                    for (int i = 1; i <= Ship.upgrades[7][0]; i++) {
                        bulletlist.add(new bullet((int) Ship.xposition, (int) Ship.yposition, (Ship.angle + Math.PI / 2) - ((Ship.upgrades[7][0] - 1) * (double) (10 * (Math.PI / 180))) + ((i - 1) * (double) (20 * (double) (Math.PI / 180))), (int) Ship.xspeed, (int) Ship.yspeed, Ship.upgrades[5][0], Ship.upgrades[4][0]));
                    }
                }
            }
            if (downKey) {
                Ship.deccelerate();
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public boolean collision(VectorSprite thing1, VectorSprite thing2) {

        int x, y;

        for (int i = 0; i < thing1.drawShape.npoints; i++) {
            x = thing1.drawShape.xpoints[i];
            y = thing1.drawShape.ypoints[i];

            if (thing2.drawShape.contains(x, y)) {
                return true;
            }
        }

        for (int i = 0; i < thing2.drawShape.npoints; i++) {
            x = thing2.drawShape.xpoints[i];
            y = thing2.drawShape.ypoints[i];


            if (thing1.drawShape.contains(x, y)) {
                return true;
            }
        }
        return false;

    }

    public void checkCollisions() {

        for (int i = 0; i < rockList.size(); i++) {
            if (collision(Ship, rockList.get(i)) == true && Ship.active) {
                for (int number = 0; number < 10; number++) {
                    ExplosionList.add(new debris((int) Ship.xposition, (int) Ship.yposition));
                    rockList.get(i).active = false;
                    money += 10 * level;
                    for (int number2 = 0; number2 < 10; number2++) {
                        ExplosionList.add(new debris((int) rockList.get(i).xposition, (int) rockList.get(i).yposition));
                    }
                    if (Math.random() > 0.8) {
                        cashlist.add(new cash((int) rockList.get(i).xposition, (int) rockList.get(i).yposition));
                    }
                }
                asteroidKills += 1;
                Ship.hit();
            }
            for (int j = 0; j < bulletlist.size(); j++) {
                if (collision(bulletlist.get(j), rockList.get(i)) == true) {
                    rockList.get(i).active = false;
                    bulletlist.get(j).active = false;
                    money += 10;
                    asteroidKills += 1;
                    for (int number = 0; number < 10; number++) {
                        ExplosionList.add(new debris((int) rockList.get(i).xposition, (int) rockList.get(i).yposition));
                    }
                    if (Math.random() > 0.8) {
                        cashlist.add(new cash((int) rockList.get(i).xposition, (int) rockList.get(i).yposition));
                    }
                }
            }
        }
        for (int i = 0; i < cashlist.size(); i++) {
            if (collision(Ship, cashlist.get(i)) == true && Ship.active) {
                cashlist.get(i).active = false;
                money += 10 * level;
            }
        }
    }

    public void respawnShip() {
        if (Ship.active == false && Ship.counter > 50 && isrespawnsafe() == true && Ship.heart > 0) {
            Ship.reset();
        }
    }

    public boolean isrespawnsafe() {
        for (int i = 0; i < rockList.size(); i++) {
            int x = (int) rockList.get(i).xposition - 450;
            int y = (int) rockList.get(i).yposition - 300;
            int c = (int) Math.sqrt(x * x + y * y);
            if (c < 100) {
                return false;
            }
        }
        return true;


    }

    public void resetlevel() {
        if (leveldelay > 500 && shop == false) {
            leveldelay = 0;
            level++;
            Ship.reset();
            for (int j = 0; j < bulletlist.size(); j++) {
                bulletlist.get(j).active = false;
            }
            for (int i = 0; i < 4 + level; i++) {
                rockList.add(new SpaceRock());
            }
        }
        repaint();
    }

    public void shop(Graphics g) {
        if (paused == false) {
            Ship.upgrades[0][1] = 500;
            Ship.upgrades[1][1] = 500;
            Ship.upgrades[2][1] = 500;
            Ship.upgrades[3][1] = 2000;
            Ship.upgrades[4][1] = 2000;
            Ship.upgrades[5][1] = 4000;
            Ship.upgrades[6][1] = 2000;
            Ship.upgrades[7][1] = 5000;
            Ship.upgrades[8][1] = 200;
            //Ship.upgrades[9][1] = 100000;

            g.drawString("Press the number indicated before the upgrade to purchase", 300, 350);
            g.drawString("Press 1 : Upgrade lvl " + Ship.upgrades[0][0] + " Thrust $" + Ship.upgrades[0][0] * Ship.upgrades[0][1], 300, 370);
            g.drawString("Press 2 : Upgrade lvl " + Ship.upgrades[1][0] + " Turn Speed $" + Ship.upgrades[1][0] * Ship.upgrades[1][1], 300, 390);
            g.drawString("Press 3 : Upgrade lvl " + Ship.upgrades[2][0] + " Brakes $" + Ship.upgrades[2][0] * Ship.upgrades[2][1], 300, 410);
            g.drawString("Press 4 : Upgrade lvl " + Ship.upgrades[3][0] + " Fire Rate $" + Ship.upgrades[3][0] * Ship.upgrades[3][1], 300, 430);
            g.drawString("Press 5 : Upgrade lvl " + Ship.upgrades[4][0] + " Bullet Speed $" + Ship.upgrades[4][0] * Ship.upgrades[4][1], 300, 450);
            g.drawString("Press 6 : Upgrade lvl " + Ship.upgrades[5][0] + " Bullet Size $" + Ship.upgrades[5][0] * Ship.upgrades[5][1], 300, 470);
            g.drawString("Press 7 : Upgrade lvl " + Ship.upgrades[6][0] + " Bullet Life $" + Ship.upgrades[6][0] * Ship.upgrades[6][1], 300, 490);
            g.drawString("Press 8 : Upgrade lvl " + Ship.upgrades[7][0] + " Bullet Spread $" + Ship.upgrades[7][0] * Ship.upgrades[7][1], 300, 510);
            g.drawString("Press 9 : Upgrade lvl " + Ship.upgrades[8][0] + " Add One Life $" + Ship.upgrades[8][0] * Ship.upgrades[8][1], 300, 530);
            //g.drawString("     Automatic : Secret power $100,000", 300, 550);
            g.drawString("Press 0 : To continue to level " + (level + 2), 300, 550);


            if (numberkey >= 1 && numberkey <= 9 && buyReady) {

                if (money >= Ship.upgrades[numberkey - 1][0] * Ship.upgrades[numberkey - 1][1]) {
                    money -= Ship.upgrades[numberkey - 1][0] * Ship.upgrades[numberkey - 1][1];
                    Ship.upgrades[numberkey - 1][0] += 1;

                    if (numberkey == 9) {
                        if (Ship.heart < 10) {
                            Ship.heart += 1;
                            if (Ship.heart == 10) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                                heart4.paint(offg);
                                heart5.paint(offg);
                                heart6.paint(offg);
                                heart7.paint(offg);
                                heart8.paint(offg);
                                heart9.paint(offg);
                                heart10.paint(offg);
                            }
                            if (Ship.heart == 9) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                                heart4.paint(offg);
                                heart5.paint(offg);
                                heart6.paint(offg);
                                heart7.paint(offg);
                                heart8.paint(offg);
                                heart9.paint(offg);
                            }
                            if (Ship.heart == 8) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                                heart4.paint(offg);
                                heart5.paint(offg);
                                heart6.paint(offg);
                                heart7.paint(offg);
                                heart8.paint(offg);
                            }
                            if (Ship.heart == 7) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                                heart4.paint(offg);
                                heart5.paint(offg);
                                heart6.paint(offg);
                                heart7.paint(offg);
                            }
                            if (Ship.heart == 6) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                                heart4.paint(offg);
                                heart5.paint(offg);
                                heart6.paint(offg);
                            }
                            if (Ship.heart == 5) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                                heart4.paint(offg);
                                heart5.paint(offg);
                            }
                            if (Ship.heart == 4) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                                heart4.paint(offg);
                            }
                            if (Ship.heart == 3) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                                heart3.paint(offg);
                            }
                            if (Ship.heart == 2) {
                                heart1.paint(offg);
                                heart2.paint(offg);
                            }
                            if (Ship.heart == 1) {
                                heart1.paint(offg);
                            }
                        } else {
                            Ship.upgrades[numberkey - 1][0] -= 1;
                            money += Ship.upgrades[numberkey - 1][0] * Ship.upgrades[numberkey - 1][1];
                        }
                    }
                    /*if (numberkey == 10) {
                    Ship.upgrades[3][0] += 15;
                    Ship.upgrades[4][0] += 15;
                    Ship.upgrades[5][0] += 15;
                    Ship.upgrades[6][0] += 15;
                    Ship.upgrades[7][0] += 15;
                    Ship.upgrades[8][0] += 15;
                    Ship.upgrades[9][0] = 10;
                    
                    Ship.upgrades[3][1] = 0;
                    Ship.upgrades[4][1] = 0;
                    Ship.upgrades[5][1] = 0;
                    Ship.upgrades[6][1] = 0;
                    Ship.upgrades[7][1] = 0;
                    Ship.upgrades[8][1] = 0;
                    Ship.upgrades[9][1] = 0;
                    } else {
                    Ship.upgrades[numberkey - 1][0] -= 1;
                    money += Ship.upgrades[numberkey - 1][0] * Ship.upgrades[numberkey - 1][1];
                    }*/
                    numberkey = 11;
                    buy = 1;
                    buyReady = false;
                } else {
                    buy = 2;
                }
            } else if (numberkey == 0) {
                shop = false;
                buy = 0;
                buyReady = true;
            }

            if (buy == 1) {
                g.drawString("upgrade purchased", 300, 590);
            } else if (buy == 2) {
                g.drawString("not enough cash", 300, 590);
            }
        }
    }
}