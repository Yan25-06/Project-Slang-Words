// package main;

// import javax.swing.*;
// import java.awt.*;
// import java.util.Scanner;

// public class Main{
//     private static final Scanner scanner = new Scanner(System.in);
//     public static void main(String[] args) throws Exception{
//         SlangDictionary dictionary;
//         try {
//             dictionary = new SlangDictionary();
//         } 
//         catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//         int choice;
//         do {
//             System.out.println("\n===== SLANG WORD =====");
//             System.out.println("1. Search slang");
//             System.out.println("2. Search by definition");
//             System.out.println("3. Show history");
//             System.out.println("4. Add slang");
//             System.out.println("5. Edit slang");
//             System.out.println("6. Delete slang");
//             System.out.println("7. Reset dictionary");
//             System.out.println("8. Random slang word");
//             System.out.println("9. Quiz slang word");
//             System.out.println("10. Quiz definition");
//             System.out.println("0. Exit");
//             System.out.print("Choose: ");
//             choice = Integer.parseInt(scanner.nextLine());

//             switch (choice) {
//                 case 1 -> {
//                     System.out.println("Enter a slang word to search:");
//                     String slang = scanner.nextLine();
//                     System.out.print("Definitions for '" + slang + "': " + dictionary.searchSlang(slang));
//                 }
//                 case 2 -> {
//                     System.out.println("Enter a definition to search:");
//                     String definition = scanner.nextLine();
//                     System.out.print("Slang words for definition '" + definition + "': " + dictionary.searchDefinition(definition));
//                 }
//                 case 3 -> dictionary.showHistory();
//                 case 4 -> {
//                     System.out.println("Enter a slang word to add:");
//                     String slang = scanner.nextLine();
//                     System.out.println("Enter a definition of the slang:");
//                     String defination = scanner.nextLine();
//                     dictionary.addSlang(slang, defination);
//                 }
//                 case 5 -> {
//                     System.out.println("Enter a slang word to edit:");
//                     String slang = scanner.nextLine();
//                     dictionary.editSlang(slang);
//                 }
//                 case 6 -> {
//                     System.out.println("Enter a slang word to delete:");
//                     String slang = scanner.nextLine();
//                     dictionary.deleteSlang(slang);
//                 }
//                 case 7 -> {
//                     dictionary.resetDictionary();
//                     System.out.println("Dictionary has been reset.");
//                 }
//                 case 8 -> {
//                     String randomSlang = dictionary.getRandomSlang();
//                     System.out.println("Random Slang Word: " + randomSlang);
//                     System.out.println("Definitions: " + dictionary.searchSlang(randomSlang));
//                 }
//                 case 9 -> dictionary.quizSlang();
//                 case 10 -> dictionary.quizDefinition();
//                 case 0 -> System.out.print("Exiting...");
//                 default -> System.out.println("Invalid choice!");
//             }
//         } while (choice != 0);
//     }   
// }

package main;
import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    private SlangDictionary dictionary;

    public MainMenuGUI() throws Exception {
        dictionary = new SlangDictionary();

        setTitle("Slang Dictionary");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 12, 5, 5));

        // Tạo các nút
        add(createButton("Search slang", e -> {
            String slang = JOptionPane.showInputDialog(this, "Enter slang:");
            if (slang != null) {
                try {
                    JOptionPane.showMessageDialog(this, dictionary.searchSlang(slang));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error searching slang: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));

        add(createButton("Search by definition", e -> {
            String def = JOptionPane.showInputDialog(this, "Enter definition:");
            if (def != null) {
                try {
                    JOptionPane.showMessageDialog(this, dictionary.searchDefinition(def));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error searching definition: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));

        add(createButton("Show history", e -> {
            try {
                dictionary.showHistory();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading history: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }));

        add(createButton("Add slang", e -> {
            String slang = JOptionPane.showInputDialog(this, "Enter slang:");
            String def = JOptionPane.showInputDialog(this, "Enter definition:");
            if (slang != null && def != null) dictionary.addSlang(slang, def);
        }));

        add(createButton("Edit slang", e -> {
            String slang = JOptionPane.showInputDialog(this, "Enter slang to edit:");
            if (slang != null) dictionary.editSlang(slang);
        }));

        add(createButton("Delete slang", e -> {
            String slang = JOptionPane.showInputDialog(this, "Enter slang to delete:");
            if (slang != null) dictionary.deleteSlang(slang);
        }));
        add(createButton("Reset dictionary", e -> dictionary.resetDictionary()));
        add(createButton("Random slang word", e -> dictionary.getRandomSlang()));
        add(createButton("Quiz slang word", e -> dictionary.quizSlang()));
        add(createButton("Quiz definition", e -> dictionary.quizDefinition()));

        setVisible(true);
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.addActionListener(action);
        return btn;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainMenuGUI();
            } catch (Exception e) {
                e.printStackTrace();    
            }   
        });
    }
}
