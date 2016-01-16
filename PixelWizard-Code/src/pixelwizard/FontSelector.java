package pixelwizard;

// FontChooser.java
// A font chooser that allows users to pick a font by name, size, style, and
// color.  The color selection is provided by a JColorChooser pane.  This
// dialog builds an AttributeSet suitable for use with JTextPane.
//
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
//http://examples.oreilly.com/jswing2/code/ch12/FontChooser.java
//A part of this FontSelector is from the adress mentioned above
public class FontSelector extends JDialog implements ActionListener {

    JColorChooser colorChooser;
    JComboBox fontName;
    JTextField fontSize, text;
    JLabel previewLabel;
    SimpleAttributeSet attributes;
    Font newFont;
    Color newColor;

    public FontSelector(Frame parent) {
        super(parent, "Font Chooser", true); //default constructor to create the dialog box
        setSize(700, 450);

        attributes = new SimpleAttributeSet();

        // Make sure that any way the user cancels the window does the right thing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeAndCancel();
            }
        });

        // Start the long process of setting up our interface
        Container c = getContentPane();

        JPanel fontPanel = new JPanel();
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontName = new JComboBox(fonts);
        fontName.setSelectedIndex(1);
        fontName.addActionListener(this);
        fontSize = new JTextField("12", 4);
        fontSize.setHorizontalAlignment(SwingConstants.RIGHT);
        fontSize.addActionListener(this);
        text = new JTextField("Insert text here...", 20);
        text.addActionListener(this);

        fontPanel.add(fontName);
        fontPanel.add(new JLabel(" Size: "));
        fontPanel.add(fontSize);
        fontPanel.add(text);

        c.add(fontPanel, BorderLayout.NORTH);

        // Set up the color chooser panel and attach a change listener so that color
        // updates get reflected in our preview label.
        colorChooser = new JColorChooser(Color.black);
        colorChooser.getSelectionModel()
                .addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        updatePreviewColor();
                    }
                });
        c.add(colorChooser, BorderLayout.CENTER);

        JPanel previewPanel = new JPanel(new BorderLayout());
        previewLabel = new JLabel("Here's a sample of this font.");
        setForeground(colorChooser.getColor());
        previewPanel.add(previewLabel, BorderLayout.CENTER);

        // Add in the Ok and Cancel buttons for our dialog box
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                closeAndSave();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                closeAndCancel();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(okButton);
        controlPanel.add(cancelButton);
        previewPanel.add(controlPanel, BorderLayout.SOUTH);

        // Give the preview label room to grow.
        previewPanel.setMinimumSize(new Dimension(100, 100));
        previewPanel.setPreferredSize(new Dimension(100, 100));

        c.add(previewPanel, BorderLayout.SOUTH);
    }
    // Ok, something in the font changed, so figure that out and make a
    // new font for the preview label

    public void actionPerformed(ActionEvent ae) {
        // Check the name of the font
        if (!StyleConstants.getFontFamily(attributes)
                .equals(fontName.getSelectedItem())) {
            StyleConstants.setFontFamily(attributes,
                    (String) fontName.getSelectedItem());
        }
        // Check the font size (no error checking yet)
        if (StyleConstants.getFontSize(attributes)
                != Integer.parseInt(fontSize.getText())) {
            StyleConstants.setFontSize(attributes,
                    Integer.parseInt(fontSize.getText()));
        }
        // Check to see if the font should be bold

        // and update our preview label
        updatePreviewFont();
    }

    // Get the appropriate font from our attributes object and update
    // the preview label
    protected void updatePreviewFont() {
        String name = StyleConstants.getFontFamily(attributes);

        int size = StyleConstants.getFontSize(attributes);

        //Bold and italic donâ€™t work properly in beta 4.
        Font f = new Font(name, Font.PLAIN, size);
        previewLabel.setFont(f);
    }

    // Get the appropriate color from our chooser and update previewLabel
    protected void updatePreviewColor() {
        previewLabel.setForeground(colorChooser.getColor());
        // Manually force the label to repaint
        previewLabel.repaint();
    }

    public String getNewFont() {
        return newFont.getName();
    }

    public Color getNewColor() {
        return newColor;
    }

    public int getTextSize() {
        String s = fontSize.getText();
        return Integer.parseInt(s);
    }

    public String getTxt() {
        String s = text.getText();
        return s;
    }

    public void closeAndSave() {
        // Save font & color information
        newFont = previewLabel.getFont();
        newColor = previewLabel.getForeground();

        // Close the window
        setVisible(false);
    }

    public void closeAndCancel() {
        // Erase any font information and then close the window
        newFont = null;
        newColor = null;
        setVisible(false);
    }

}
