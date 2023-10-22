import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class pdf {
    private JFrame frame;

    public pdf() {
        frame = new JFrame("PDF Uploader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        createUI();
    }

    private void createUI() {
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                GradientPaint gradient = new GradientPaint(0, 0, Color.PINK, getWidth(), getHeight(), Color.BLUE);
                ((Graphics2D) g).setPaint(gradient);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        contentPanel.setLayout(new BorderLayout());

        JButton translateButton = new JButton("Translate");
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the path of the selected PDF file
                String pdfFilePath = getSelectedPDFFilePath(); // Opens a file dialog for PDF selection

                if (pdfFilePath != null) {
                    executePythonTranslation(pdfFilePath);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a PDF file first.");
                }
            }
        });

        contentPanel.add(translateButton, BorderLayout.SOUTH);
        frame.add(contentPanel);
        frame.setVisible(true);
    }

    private String getSelectedPDFFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(frame); // Opens a file dialog

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile(); // Gets the selected file
            return selectedFile.getAbsolutePath(); // Returns the file path
        } else {
            return null;
        }
    }

    private void executePythonTranslation(String pdfFilePath) {
        // Execute the Python script here, passing the PDF file path as an argument
        try {
            String pythonScript = "french.py";
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScript, pdfFilePath);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                JOptionPane.showMessageDialog(frame, "Translation completed successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Translation failed.");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new pdf();
        });
    }
}
