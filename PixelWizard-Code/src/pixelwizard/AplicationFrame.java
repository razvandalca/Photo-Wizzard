
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelwizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;

/**
 *
 * @author dalca-razvan
 */
public class AplicationFrame extends JFrame implements ActionListener {

    JMenu menu1, menu2, help;
    JMenuBar menubar;
    JButton cancelediting,brush, line, square, circle, eraser, clipart, flipH, flipV, addText, brightup, brightdown, colorselected, size;
    JMenuItem newb, open, save, close, gray_effect, translucent, negative, ebrightness, sepia, blur,
            rotate, userguide, appinfo;
    JToolBar tool_box;
    DrawArea canvas = new DrawArea();
    JScrollPane j;

    public AplicationFrame() {
        //we call the constructor from the extended class
        super("PixelWizard");
        
        //set the app icon
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/Icons/PixelWizard.png"));
        this.setIconImage(icon.getImage());
        
        //set the size of the window , it can be resized 
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //create a pane that is scrollabele
        j = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        j.setViewportView(canvas);
        
        //call the methods that create the menu and toolbox
        making_menu();
        making_tool_box();
        
        //add all to the frame
        this.add(menubar, BorderLayout.NORTH);
        this.add(tool_box, BorderLayout.SOUTH);
        this.add(j, BorderLayout.CENTER);
        //set the frame visible
        this.setVisible(true);

    }
    
    //create the menu by defining each button and adding an action listener to it
    public void making_menu() {
        menubar = new JMenuBar();
        menu1 = new JMenu("File");
        menu2 = new JMenu("Image Filters");
        help = new JMenu("Help");
        newb = new JMenuItem("New", new ImageIcon(this.getClass().getResource("/Icons/new.png")));
        save = new JMenuItem("Save", new ImageIcon(this.getClass().getResource("/Icons/save.png")));
        close = new JMenuItem("Close", new ImageIcon(this.getClass().getResource("/Icons/close.png")));
        open = new JMenuItem("Open", new ImageIcon(this.getClass().getResource("/Icons/open.png")));
        gray_effect = new JMenuItem("Gray Filter");
        negative = new JMenuItem("Negative Filter");
        sepia = new JMenuItem("Sepia Filter");
        blur = new JMenuItem("Blur Filter");
        rotate = new JMenuItem("Rotate", new ImageIcon(this.getClass().getResource("/Icons/hazard.png")));
        userguide = new JMenuItem("User Guide");
        appinfo = new JMenuItem("Information");

        menu1.add(newb);
        newb.addActionListener(this);
        newb.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menu1.addSeparator();
        menu1.add(open);
        open.addActionListener(this);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menu1.addSeparator();
        menu1.add(save);
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menu1.addSeparator();
        menu1.add(close);
        close.addActionListener(this);
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK));

        menu2.add(gray_effect);
        gray_effect.addActionListener(this);
        menu2.addSeparator();
        menu2.add(negative);
        negative.addActionListener(this);
        menu2.addSeparator();
        menu2.add(sepia);
        sepia.addActionListener(this);
        menu2.addSeparator();
        menu2.add(blur);
        blur.addActionListener(this);
        menu2.addSeparator();
        menu2.add(rotate);
        rotate.addActionListener(this);

        help.add(userguide);
        userguide.addActionListener(this);
        help.addSeparator();
        help.add(appinfo);
        appinfo.addActionListener(this);

        menubar.add(menu1);
        menubar.add(menu2);
        menubar.add(help);
    }

    //create the toolbox. 
    public void making_tool_box() {
        tool_box = new JToolBar("ToolBar");
        tool_box.setLayout(new FlowLayout(FlowLayout.CENTER));
        tool_box.setBorder(BorderFactory.createTitledBorder("Tools"));
        brush = new JButton(new ImageIcon(this.getClass().getResource("/Icons/brushicon.png")));
        square = new JButton(new ImageIcon(this.getClass().getResource("/Icons/rectangle.png")));
        circle = new JButton(new ImageIcon(this.getClass().getResource("/Icons/circle.png")));
        eraser = new JButton(new ImageIcon(this.getClass().getResource("/Icons/erase.png")));
        clipart = new JButton(new ImageIcon(this.getClass().getResource("/Icons/clipArt.png")));
        flipH = new JButton(new ImageIcon(this.getClass().getResource("/Icons/horizontalflip.png")));
        flipV = new JButton(new ImageIcon(this.getClass().getResource("/Icons/verticalflip.png")));
        addText = new JButton(new ImageIcon(this.getClass().getResource("/Icons/addText.png")));
        brightup = new JButton(new ImageIcon(this.getClass().getResource("/Icons/brightness+.png")));
        brightdown = new JButton(new ImageIcon(this.getClass().getResource("/Icons/brightness-.png")));
        line = new JButton(new ImageIcon(this.getClass().getResource("/Icons/line.png")));
        colorselected = new JButton(new ImageIcon(this.getClass().getResource("/Icons/colorselected.png")));
        size = new JButton(new ImageIcon(this.getClass().getResource("/Icons/size.png")));
        cancelediting= new JButton("Cancel All Editing", new ImageIcon(this.getClass().getResource("/Icons/cancel.png")));

        
        brush.addActionListener(this);
        square.addActionListener(this);
        circle.addActionListener(this);
        eraser.addActionListener(this);
        clipart.addActionListener(this);
        flipH.addActionListener(this);
        flipV.addActionListener(this);
        addText.addActionListener(this);
        brightup.addActionListener(this);
        brightdown.addActionListener(this);
        line.addActionListener(this);
        colorselected.addActionListener(this);
        size.addActionListener(this);
        cancelediting.addActionListener(this);

        
        //set the information when hovering over the button
        ToolTipManager.sharedInstance().setInitialDelay(1);
        brush.setToolTipText("Brush");
        square.setToolTipText("Add Square");
        circle.setToolTipText("Add Circle");
        eraser.setToolTipText("Eraser");
        clipart.setToolTipText("Add Clipart");
        flipH.setToolTipText("Flip Horizontally");
        flipV.setToolTipText("Flip Vertically");
        addText.setToolTipText("Add Text");
        brightup.setToolTipText("Bright Up");
        brightdown.setToolTipText("Bright Down");
        line.setToolTipText("Draw Line");
        colorselected.setToolTipText("Select Color");
        size.setToolTipText("Set Brush/Line Size");
        cancelediting.setToolTipText("Cancel All Editing");

        tool_box.add(brush);
        tool_box.add(line);
        tool_box.add(square);
        tool_box.add(circle);
        tool_box.add(eraser);
        tool_box.add(colorselected);
        tool_box.add(size);
        tool_box.add(clipart);
        tool_box.add(flipH);
        tool_box.add(flipV);
        tool_box.add(addText);
        tool_box.add(brightup);
        tool_box.add(brightdown);
        tool_box.add(cancelediting);


    }
    
    
    static int counter = 0;//this counter is user to verify which button is pressed and to retrive the actions acordingly 
    static int c = 0;//it is used for clipart/add text and as long as it is equal to 0, mouse clicked does not get any values for x and y
    static FontSelector fs1;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(brush)) {
            //verify if the counter has any value but 1 andif it does, its value is set to 1, that meaning the button enables the use of brush
            if (counter == 0 || counter == 2 || counter == 3 || counter == 4) {
                

                counter = 1;//we set the couter to 1 that is specific to the brush button 

                //we change tha color  so that the user knows that the button is clicked and the actions for that button are available
                brush.setBackground(Color.lightGray);
                
                //set the rest of the buttons to the default color
                square.setBackground(new JButton().getBackground());
                circle.setBackground(new JButton().getBackground());
                eraser.setBackground(new JButton().getBackground());
                line.setBackground(new JButton().getBackground());
            } else {
                //if the counter is already 1 we change the counter to 0 and change the color of the button to the default so the user sees that the button si not active
                counter = 0;
                brush.setBackground(new JButton().getBackground());
            }

        }
        
        //the following buttons are implemented on the same idea as the brush button, having the following value:
        //2-draw square 3-draw circle 4-eraser 5-draw line
        if (e.getSource().equals(square)) {
            if (counter == 0 || counter == 1 || counter == 3 || counter == 4 || counter == 5) {
                counter = 2;
                square.setBackground(Color.lightGray);
                brush.setBackground(new JButton().getBackground());
                circle.setBackground(new JButton().getBackground());
                eraser.setBackground(new JButton().getBackground());
                line.setBackground(new JButton().getBackground());
            } else {
                counter = 0;
                square.setBackground(new JButton().getBackground());
            }
        }
        if (e.getSource().equals(circle)) {
            if (counter == 0 || counter == 1 || counter == 2 || counter == 4 || counter == 5) {
                counter = 3;
                circle.setBackground(Color.lightGray);
                square.setBackground(new JButton().getBackground());
                brush.setBackground(new JButton().getBackground());
                eraser.setBackground(new JButton().getBackground());
                line.setBackground(new JButton().getBackground());
            } else {
                counter = 0;
                circle.setBackground(new JButton().getBackground());
            }
        }
        if (e.getSource().equals(eraser)) {
            if (counter == 0 || counter == 1 || counter == 2 || counter == 3 || counter == 5) {
                counter = 4;
                eraser.setBackground(Color.lightGray);
                square.setBackground(new JButton().getBackground());
                circle.setBackground(new JButton().getBackground());
                brush.setBackground(new JButton().getBackground());
                line.setBackground(new JButton().getBackground());
            } else {
                counter = 0;
                eraser.setBackground(new JButton().getBackground());
            }

        }
        if (e.getSource().equals(line)) {
            if (counter == 0 || counter == 1 || counter == 2 || counter == 3 || counter == 4) {
                counter = 5;
                line.setBackground(Color.lightGray);
                square.setBackground(new JButton().getBackground());
                circle.setBackground(new JButton().getBackground());
                eraser.setBackground(new JButton().getBackground());
                brush.setBackground(new JButton().getBackground());
            } else {
                counter = 0;
                line.setBackground(new JButton().getBackground());
            }
        }
        if (e.getSource().equals(colorselected)) {
            canvas.choseColor();

        }
        if (e.getSource().equals(size)) {
            canvas.choseSize();

        }
        if (e.getSource().equals(flipH)) {

            canvas.horizontalflip();
        }
        if (e.getSource().equals(flipV)) {

            canvas.verticalflip();
        }
        if (e.getSource().equals(addText)) {
            if(counter!=0) //set the counter 0 so we do not do any drawing when clicking on the image on the spot where we want to add text
                counter=0;
            fs1 = new FontSelector(null);
            fs1.setVisible(true);
            c = 1; //set the counter from initial value 0, to 1, so we can get the coordinates from the mouse clicked, that we use in draw area for adding text
        }
        if (e.getSource().equals(clipart)) {
            if(counter!=0)
                counter=0;
            c = 2; //set the counter from initial value 0, to 2, so we can get the coordinates from the mouse clicked, that we use in draw area for adding text
            canvas.createclipArt();
        }
        
        //for the next buttons just check which is pressed and to the corresponding action
        
        if (e.getSource().equals(open)) {
            try {
                canvas.loadImage();
            } catch (IOException ex) {
                Logger.getLogger(AplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(save)) {

            canvas.saveImage();
        }
        if (e.getSource().equals(newb)) {
            canvas.clear();

        }
        if (e.getSource().equals(gray_effect)) {
            canvas.grayImage();

        }

        if (e.getSource().equals(negative)) {
            canvas.negativeImage();

        }

        if (e.getSource().equals(sepia)) {
            canvas.sepia();

        }
        if (e.getSource().equals(blur)) {
            canvas.blur();

        }

        if (e.getSource().equals(rotate)) {
            canvas.rotateImage();

        }
        if (e.getSource().equals(brightup)) {
            canvas.brightup();

        }

        if (e.getSource().equals(brightdown)) {
            canvas.brightdown();
        }
        if (e.getSource().equals(close)) {
            int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program ?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (reply == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        if (e.getSource().equals(appinfo)) {
            Information info = new Information();
        }
        
        //http://stackoverflow.com/questions/2546968/open-pdf-file-on-fly-from-java-application
        if (e.getSource().equals(userguide)) {
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("\"src/userguide.pdf\"");
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + myFile);
                } catch (IOException ex) {

                }
            }

        }
        if(e.getSource().equals(cancelediting)){
            try {
                canvas.cancelEdit();
            } catch (IOException ex) {
                Logger.getLogger(AplicationFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    

    }
}
