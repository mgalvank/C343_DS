/**
 * Created by mvc on 11/1/2015.
 * 1,
 * The Graphing class is used to visualize a simple function then print it.
 * 2,
 * The function is expected to be represented in an iterable data structure(for now it's a List),
 * where the indexes are the x-values and elements are y-values.
 * 3,
 * This class provides two interfaces, WriteImageFile and VisualAndWrite.
 * The former one asks for a file name to write the generated graph;
 * the latter one visualize the graph, then call the former one.
 */

import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GraphingData extends JPanel {
    static int [][] datas;
    static int PAD = 20;
    static JFrame frame = new JFrame();

    public GraphingData(final List<List<Integer>> _datas) {
            final int l = _datas.size();
        datas = new int[l][];
        PAD = _datas.get(0).size();
        for(int index=0; index<l; index+=1) {
            List<Integer> _data = _datas.get(index);
            datas[index] = new int[PAD];
            for (int i = 0; i < PAD; i += 1) {
                //System.out.println(_data.get(i));
                datas[index][i] = _data.get(i);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "data";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "x axis";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double) (w - 2 * PAD) / (datas[0].length - 1);
        double scale = (double) (h - 2 * PAD) / getMax();
        g2.setPaint(Color.green.darker());
        for(int index=0; index<datas.length; index+=1) {
            int[] data = datas[index];
            for (int i = 0; i < data.length - 1; i++) {
                double x1 = PAD + i * xInc;
                double y1 = h - PAD - scale * data[i];
                double x2 = PAD + (i + 1) * xInc;
                double y2 = h - PAD - scale * data[i + 1];
                g2.draw(new Line2D.Double(x1, y1, x2, y2));
            }
            // Mark data points.
            g2.setPaint(Color.red);
            for (int i = 0; i < data.length; i++) {
                double x = PAD + i * xInc;
                double y = h - PAD - scale * data[i];
                g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
            }
        }
    }

    private int getMax() {
        int max = -Integer.MAX_VALUE;
            int[] data = datas[0];
            for (int index = 0; index < data.length; index += 1) {
                if (data[index] > max)
                    max = data[index];
            }
        return max;
    }

    public void writeImageFile() {
        //final String fileName = JOptionPane.showInputDialog(frame, "Please enter the file name to save");
        BufferedImage bufferedImage = new BufferedImage(frame.getSize().width, frame.getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();
        frame.paint(g);
        g.dispose();
        try {
            ImageIO.write(bufferedImage, "png", new File( "result.png"));
        }catch (Exception e) {}
    }

    public void visualAndWrite() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(400,400);
        frame.setLocation(200,200);
        frame.setVisible(true);
        writeImageFile();
    }

    /*
    public static void main(String[] args) {
        final List<Integer> l = new LinkedList<>();
        final Random random = new Random();
        for(int index=0; index<100; index+=1) {
            l.add(random.nextInt(200));
        }

        new FloodIt.GraphingData(l).visualAndWrite();
    }
    */
}