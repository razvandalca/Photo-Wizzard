/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelwizard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DrawArea extends JPanel implements MouseMotionListener { //draw area is a panel that is added to the application frame and defines some mouse movements

    BufferedImage imageoriginal, image, image_gray;
    int pressedX, pressedY, releasedX, releasedY, clickedX, clickedY;
    int size = 5;//default size
    int clipArt_size = 5;//default size
    Color colorSelected = Color.black;//default color
    String pathtofileopen_string;
    String pathtofilesave = null;

    public DrawArea() {
        super(); //calles the default constructor of JPanel that creates a panel
        this.setPreferredSize(new Dimension(2500, 2500)); //sets the prefferd dimension of the panel that is put over a scrollable planel

        this.addMouseMotionListener(this); //adds mouse motin listener on the created panel
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //depending on the counter c, a certain action is performed
                //no values are saved unless the counter is different from 0 and this is why at the end after the action is performed, c is set back to 0
                if (AplicationFrame.c == 1) { 

                    clickedX = e.getX();
                    clickedY = e.getY();
                    addTextToImage();
                    AplicationFrame.c = 0;

                }
                if (AplicationFrame.c == 2) {

                    clickedX = e.getX();
                    clickedY = e.getY();
                    clipArt();
                    AplicationFrame.c = 0;

                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressedX = e.getX();
                pressedY = e.getY();

            }

            @Override
            public void mouseReleased(MouseEvent e) { //works depending on "counter" from application frame
                //this counter shows which button is pressed and some actions are performed, accordingly
                int counterD = AplicationFrame.counter;
                releasedX = e.getX();
                releasedY = e.getY();
                switch (counterD) {

                    case 2: {

                        int width = Math.abs(releasedX - pressedX);
                        int heigth = Math.abs(releasedY - pressedY);

                        Graphics2D g = image.createGraphics();
                        g.setColor(colorSelected);
                        g.setStroke(new BasicStroke(size)); //size of outline

                        g.drawRect(Math.min(pressedX, releasedX), Math.min(pressedY, releasedY), width, heigth);
                                //x coordinate of rectangle to be draw     coordinate of rectangle to be draw

                        g.dispose(); //clear the memory
                        repaint(); //refresh the panel so that the drawings appear
                        break;
                    }
                    case 3: {

                        int width = Math.abs(releasedX - pressedX);
                        int heigth = Math.abs(releasedY - pressedY);
                        Graphics2D g = image.createGraphics();
                        g.setColor(colorSelected);
                        g.setStroke(new BasicStroke(size));
                        g.drawOval(Math.min(pressedX, releasedX), Math.min(pressedY, releasedY), width, heigth);

                        g.dispose();
                        repaint();
                        break;
                    }
                    case 5: {

                        Graphics2D g = image.createGraphics();
                        g.setColor(colorSelected);
                        g.setStroke(new BasicStroke(size));
                        g.drawLine(pressedX, pressedY, releasedX, releasedY);

                        g.dispose();
                        repaint();
                        break;
                    }

                }

            }
        });

    }

    void loadImage() throws IOException {
        //check if the user has selected something
        pathtofileopen_string = pathttoFile_open(); //loads the buffered image with the image selector
        if (pathtofileopen_string != null) {
            image = ImageIO.read(new File(pathtofileopen_string));

        }
        
    }

    public void doDrawing(Graphics g) { //this method uses the graphic object of JPanel to draw a image, i.e. to create it in the virtual memory
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null); //The fourth argument is just the image observer, which is the object to be notified as more of the image is converted

    }

    @Override
    public void paintComponent(Graphics g) { 
//This allows us to pass an image into the newly created JImagePanel, which will then handle all painting for the life cycle of the application. 
//JImagePanel overrides paintComponent and makes it paint the Image at the supplied coordinates. 
        super.paintComponent(g);
        doDrawing(g);
        repaint();
    }
    

    public String pathttoFile_open() {
        JFileChooser fcopen = new JFileChooser();
        fcopen.showOpenDialog(this);
        if (fcopen.getSelectedFile() == null) {
            return null;
        }
        String pathtofile = fcopen.getSelectedFile().getAbsolutePath();
    

        return pathtofile;
    }

    public void saveImage() {
        JFileChooser fcsave = new JFileChooser();
        int answ = fcsave.showSaveDialog(this);
        File file_saved = null;
        if (fcsave.APPROVE_OPTION == answ) {
            file_saved = fcsave.getSelectedFile();

            try {
                ImageIO.write(image, "jpg", file_saved);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        }
    }

    //Effects
    public void clear() {
        if (image == null) { //if there is no image, the method creates a white rectangle with the dimensions of the panel
            image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) image.createGraphics();
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

            g2d.dispose();
            repaint();
        } else { //if there is a image loaded, a white rectangle with the same dimensions is drawn over it
            
            Graphics2D g2d = (Graphics2D) image.createGraphics();

            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            
            repaint();
            g2d.dispose();
            
        }
    }

    public void grayImage() { //i and j are coordonates of the pixels of the image. 
        //for each pixel, we get its RGB and to make it grey we do the arithmetic mean of them
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed());
                int green = (int) (c.getGreen());
                int blue = (int) (c.getBlue());
                int gry = (red + green + blue) / 3;
                Color newColor = new Color(gry, gry, gry);

                image.setRGB(j, i, newColor.getRGB());
            }
        }
        Graphics ggray = image.getGraphics();
        ggray.drawImage(image, 0, 0, null);
        ggray.dispose();
        repaint();
    }

    public void negativeImage() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                Color c = new Color(image.getRGB(j, i));
                int red = (int) (255 - c.getRed());
                int green = (int) (255 - c.getGreen());
                int blue = (int) (255 - c.getBlue());
                Color newColor = new Color(red, green, blue);

                image.setRGB(j, i, newColor.getRGB());
            }
        }
        Graphics gneg = image.getGraphics();
        gneg.drawImage(image, 0, 0, null);
        gneg.dispose();
        repaint();
    }

    public void addTextToImage() {

        Graphics g = image.createGraphics();
        Font f = new Font(AplicationFrame.fs1.getNewFont(), Font.PLAIN, AplicationFrame.fs1.getTextSize());//creates a new font; it gets it from the font selector that is created in the app frame when the button add text is clicked
                                                  //font type, style, size
        g.setFont(f); //sets the font on the graphic object
        g.setColor(AplicationFrame.fs1.getNewColor()); //sets the color also from font selector
        g.drawString(AplicationFrame.fs1.getTxt(), clickedX, clickedY); //writes a string with the text from from selector that is created in application frame
        g.dispose();
        repaint();

    }

    public void brightup() {

        RescaleOp op = new RescaleOp((float) 1.1, 0, null); //This class performs a pixel-by-pixel rescaling of the data in the source image by multiplying the sample values for each pixel by a scale factor and then adding an offset.
       //1.1 means increasing the brightness by 10%
        op.filter(image, image); //performs a single operation resulting in an output image that is actually the initial image, modified
        Graphics gb = image.createGraphics();
        gb.drawImage(image, 0, 0, null);
        gb.dispose();
        repaint();
    }

    public void brightdown() {

        RescaleOp op = new RescaleOp((float) 0.9, 0, null);
        image = op.filter(image, image);
        Graphics gb = image.createGraphics();
        gb.drawImage(image, 0, 0, null);
        gb.dispose();
        repaint();
    }

    public void sepia() {//image manipulation on pixels, standard formulas used for each RGB

        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed());
                int green = (int) (c.getGreen());
                int blue = (int) (c.getBlue());
                int gry = (red + green + blue) / 3;

                red = green = blue = gry;
                red = red + (20 * 2);
                green = green + 20;
                if (red > 255) {
                    red = 255;
                }
                if (green > 255) {
                    green = 255;
                }
                if (blue > 255) {
                    blue = 255;
                }

                // Darken blue color to increase sepia effect
                blue -= 25;

                // normalize if out of bounds
                if (blue < 0) {
                    blue = 0;
                }
                if (blue > 255) {
                    blue = 255;
                }
                Color newColor = new Color(red, green, blue);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        Graphics gsep = image.getGraphics();
        gsep.drawImage(image, 0, 0, this);
        gsep.dispose();
        repaint();
    }

    public void blur() {
//        to blur an image, we use a convolution operation. It is a mathematical operation which is also used in edge detection or noise elimination. 
//        The blur filter operation replaces each pixel in image with an average of the pixel and its neighbours. Convolutions are per-pixel operations. 
//           The same arithmetic is repeated for every pixel in the image. A kernel can be thought of as a two-dimensional grid of numbers that passes 
//           over each pixel of an image in sequence, performing calculations along the way. Since images can also be thought of as two-dimensional 
//           grids of numbers, applying a kernel to an image can be visualized as a small grid (the kernel) moving across a substantially 
//           larger grid (the image). (http://zetcode.com/gfx/java2d/java2dimages/)
               
        Kernel kernel = new Kernel(3, 3,
                new float[]{
                    1f / 9f, 1f / 9f, 1f / 9f,
                    1f / 9f, 1f / 9f, 1f / 9f,
                    1f / 9f, 1f / 9f, 1f / 9f});

        BufferedImageOp op = new ConvolveOp(kernel);

        image = op.filter(image, null);
        Graphics gblur = image.createGraphics();
        gblur.drawImage(image, 0, 0, this);
        gblur.dispose();
        repaint();

    }

    //creates a mirrored image, for horizontal the mirror in on the right side of the image and for vertical at the buttom
    public BufferedImage createhorizontalflipImage() {

        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, image.getColorModel().getTransparency());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(image, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }

    public void horizontalflip() {
        Graphics g = image.getGraphics();
        g.drawImage(createhorizontalflipImage(), 0, 0, this);
        repaint();
    }

    public BufferedImage createverticalflipImage() {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, image.getColorModel().getTransparency());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(image, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return dimg;

    }

    public void verticalflip() {
        Graphics g = image.getGraphics();
        g.drawImage(createverticalflipImage(), 0, 0, this);
        repaint();
    }

    public BufferedImage createRotatedImage() {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, image.getType());
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(75), w / 2, h / 2);
        g.drawImage(image,  0, 0, null);
        g.dispose();
        return dimg;

    }

    public void rotateImage() {
        Graphics g = image.getGraphics();
        g.drawImage(createRotatedImage(), 0, 0, null);
        repaint();
    }
    BufferedImage buff2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

    public void createclipArt() {

        if (image != null) {

            try {
                buff2 = ImageIO.read(new File(pathttoFile_open()));
            } catch (IOException ex) {
                Logger.getLogger(DrawArea.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ssize = JOptionPane.showInputDialog(null, "Please enter the size of the clipArt...", "Please enter the size", JOptionPane.INFORMATION_MESSAGE);
            clipArt_size = Integer.parseInt(ssize);
            
        } else {
            JOptionPane.showMessageDialog(this, "There is no image to add a clipart to...");
        }

    }

    public void clipArt() {

        Graphics g = image.getGraphics();
        g.drawImage(buff2, clickedX, clickedY, clipArt_size, clipArt_size, null);
        g.dispose();
        repaint();
    }

    //Mouse Listener
    @Override
    public void mouseDragged(MouseEvent e) {
        int counterD = AplicationFrame.counter;
        switch (counterD) { //use counter to check which button was pressed

            case 1: { 

                int x = e.getX();
                int y = e.getY();
                Graphics2D g = image.createGraphics();
                g.setColor(colorSelected);
                g.fillOval(x, y, size, size);
                g.dispose();
                repaint();
                break;

            }

            case 4: {
                int x = e.getX();
                int y = e.getY();
                Graphics g = image.createGraphics();
                g.setColor(Color.WHITE);
                g.fillOval(x, y, size, size);
                g.dispose();
                repaint();
                break;
            }

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Color choseColor() {

        colorSelected = JColorChooser.showDialog(null, "Change the Color of what you draw", Color.black); 
//opens a window where you can choose the drawing color

        return colorSelected;
    }

    public int choseSize() {
        String ssize = JOptionPane.showInputDialog(null, "Please enter the size", "Please enter the size", JOptionPane.INFORMATION_MESSAGE);
        //opens a window where you can choose the drawing size
        size = Integer.parseInt(ssize);
        return size;
    }

    public void cancelEdit() throws IOException {
        if(pathtofileopen_string !=null){
            image = ImageIO.read(new File(pathtofileopen_string)); //if there is a image, redraws it
            Graphics g=image.getGraphics();
            g.drawImage(image, 0, 0, null);
            repaint();
            g.dispose();
        }
        else{
            clear(); //if there is no image, calls clear()
        }
    }
   
}
