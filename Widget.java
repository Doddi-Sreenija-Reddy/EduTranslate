import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Widget {
    private JFrame frame;
    private JPanel a;

    public Widget() {
        frame = new JFrame("Translator");
        a = new JPanel(new BorderLayout());
        JButton b = new JButton("Translator");
        a.add(b, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new FlowLayout());
        String[] languages = {"Telugu", "Urdu", "Hindi", "Tamil", "Bengali", "Japanese", "Chinese", "Korean", "Russian", "German", "French"};

        for (String language : languages) {
            JButton languageButton = new JButton(language);
            centerPanel.add(languageButton);

            languageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When a language button is clicked, open the pdf.java window
                    new pdf(); // Create an instance of the pdf class to open the PDF uploader window
                }
            });
        }

        frame.add(a, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Set the frame to maximize to fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Widget widget = new Widget();
        });
    }
}
