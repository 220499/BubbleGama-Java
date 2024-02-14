import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main {
  private JFrame frame = new JFrame("Bubble Burst");
  private int sliderValue = 1;

  public Main() {
    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    frame.add(new MainPane());
    frame.pack();
    frame.setSize(700, 600);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public class MainPane extends JPanel {

    private static final long serialVersionUID = 1L;

    public MainPane() {
      frame.setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridwidth = GridBagConstraints.REMAINDER;

      final JSlider slider = slider();
      JButton bStart = startButton();
      JButton bRestart = restartButton();

      frame.add(slider, gbc);
      frame.add(bStart, gbc);
      frame.add(bRestart, gbc);

      bStart.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println("sleepSense:::" + sliderValue);
          new SecondFrame(sliderValue + 3).setVisible(true);
          frame.dispose();
        }
      });

      bRestart.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println("sleepSense:::" + sliderValue);
          new SecondFrame(sliderValue + 3).setVisible(true);
          frame.dispose();
        }
      });

      slider.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          sliderValue = slider.getValue();

        }
      });

    }

    public JButton restartButton() {
      JButton bRestart = new JButton("RESTART");
      bRestart.setBackground(Color.GRAY);
      bRestart.setVisible(false);
      return bRestart;
    }

    public JSlider slider() {
      JSlider slider = new JSlider(1, 3, 1);

      Dictionary<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
      labels.put(1, new JLabel("Easy"));
      labels.put(2, new JLabel("Medium"));
      labels.put(3, new JLabel("Hard"));

      slider.setLabelTable(labels);
      slider.setMajorTickSpacing(1);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      return slider;
    }

    public JButton startButton() {
      JButton bStart = new JButton("START");
      bStart.setBackground(Color.GREEN);
      return bStart;
    }

  }

  public static void main(String[] args) {
    new Main();

  }

}
