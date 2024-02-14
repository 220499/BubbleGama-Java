import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SecondFrame extends JPanel {
  private static final long serialVersionUID = 1L;
  private ArrayList<Point> points;
  private final ArrayList<Integer> diameters;
  boolean initialLoadEnd = false;
  static int round = 0;
  int timeRemaining = 10;
  Timer countdownTimer;
  private JFrame frame = new JFrame("Lets Play!");

  public SecondFrame(final int severity) {
    System.out.println("severity::::" + severity);
    diameters = new ArrayList<Integer>();
    EventQueue.invokeLater(new Runnable() {
      public void run() {

        // frame.add(new Timer());
        JLabel _lbl = new JLabel("Round: " + round);
        frame.revalidate();
        frame.repaint();
        frame.add(new SecondFrame(severity, _lbl));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
      }
    });
  }

  public SecondFrame(final int count, JLabel passedLabel) {

    points = new ArrayList<Point>();
    diameters = new ArrayList<Integer>();
    setBackground(Color.WHITE);


    final JLabel _lb2 = new JLabel("Round::" + round);
    this.add(_lb2, BorderLayout.CENTER);

   

    addMouseListener(new MouseAdapter() {

      @Override
      public void mousePressed(MouseEvent e) {
       
        if (points.size() < count && !initialLoadEnd) {
          // new RoundActionListener();

          round = 1;
          System.out.println("point.x::::" + (e.getX() + 60)
              + "point.y" + (e.getY() + 0));
          if (e.getX() + 60 <= 400 && e.getY() + 80 <= 400) {
            Point nonvalidatedPoint = new Point(e.getX(), e.getY());
            if (!overlapsOther(nonvalidatedPoint, 50)) {
              points.add(nonvalidatedPoint);
              diameters.add(100);
            }

            _lb2.setText("Round::" + round);
            
            repaint();
          } else {
            JOptionPane.showMessageDialog(null,
                "Please click inside panel to draw an Circle");
          }
        } else {
          if (points.size() == 0 && initialLoadEnd && round < 10) {
        
            createRandomPoint(count);
            removeOval(new Point(e.getX(), e.getY()));
            _lb2.setText("Round::" + round);
            repaint();
          } else if (round >= 10 && points.size() == 0) {
            JOptionPane.showMessageDialog(null,
                "Game is Over");
            round = 0;
            frame.dispose();
            new Main().main(null);
          } else {

            initialLoadEnd = true;
            removeOval(new Point(e.getX(), e.getY()));
           
            repaint();
          }
        }
      }
    });
  }

  private void createRandomPoint(int count) {
    Random rand = new Random();
    round++;
   
    System.out.println("points size:::" + points.size());
    System.out.println("count :::" + count);
    int tries = 10000;
    
    while (points.size() <= count) {
      int diameter = 100;
      int posX = rand.nextInt(400);
      int posY = rand.nextInt(400);
      System.out.println("X and Y is ::" + posX + "," + posX);
      if (posX + 60 <= 400 && posY + 80 <= 400) {
        Point nonvalidatedPoint = new Point(posX, posY);
        if (!overlapsOther(nonvalidatedPoint, 50)) {
          points.add(nonvalidatedPoint);
          diameters.add(50);
          if (points.size() == count) {
            break;
          }
        }
      }

    }

  }

  // Remove selected circle
  private void removeOval(Point selectedPoint) {
    System.out.println("selected point is" + selectedPoint.getX() + ", "
        + selectedPoint.getY());
    for (Iterator<Point> iterator = points.iterator(); iterator.hasNext();) {
      System.out.println("points inside for:::::" + selectedPoint.getX()
          + ", " + selectedPoint.getY());
      Point center = iterator.next();
      if (isInsideOval(selectedPoint, center)) {
        System.out.println("Inside is true for given point::::"
            + selectedPoint.getX() + ", " + selectedPoint.getY());
        iterator.remove();
        break;
      }
    }
  }

  // Select point Match with existing Circles
  private boolean isInsideOval(Point point, Point center) {
    int r = 50;
    int dx = point.x - center.x;
    int dy = point.y - center.y;
    return (dx * dx + dy * dy <= r * r);
  }

  // Overlap check for circles
  private boolean overlapsOther(Point position, int diameter) {
    int radius = diameter / 2;
    int centerX = position.x + radius;
    int centerY = position.y + radius;

    for (int i = 0; i < points.size(); i++) {
      Point otherPosition = points.get(i);
      int otherDiameter = diameters.get(i);
      int otherRadius = otherDiameter / 2;
      int otherCenterX = otherPosition.x + otherRadius;
      int otherCenterY = otherPosition.y + otherRadius;

      int dx = centerX - otherCenterX;
      int dy = centerY - otherCenterY;
      double distance = Math.hypot(dx, dy);
      if (distance < radius + otherRadius) {
        return true;
      }
    }
    return false;
  }

  // circle Creation in Frmae
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(Color.red);
    System.out.println(" Number of Points Drawn:::: " + points.size());
    for (Point point : points) {
      
      g2.fillOval(point.x, point.y, 50, 50);
     
    }

  }

  private boolean clickedaroundpoint(Point clicked) {
    if (!points.isEmpty()) {
      if (Point.distance(points.get(0).x + 2, points.get(0).y + 2,
          clicked.x, clicked.y) <= 5) {
        return true;
      }
    }
    return false;
  }

  class CountdownTimerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (--timeRemaining > 0) {
        System.out.println("timeRemaining::::" + timeRemaining);
        
      } else {
        
        countdownTimer.stop();
      }
    }
  }

  class RoundActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

      System.out.println("Round Number::::" + round);
      JLabel _lbl = new JLabel("Round::" + round);
      _lbl.setText("Round : " + String.valueOf(round));

    }
  }

}
