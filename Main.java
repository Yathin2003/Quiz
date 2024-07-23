
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Main {

    public  static Map<String, String> users = new HashMap<>();
    private static final List<Quiz> quizzes = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        showLoginScreen();
    }



    private static void showLoginScreen() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 10, 80, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        frame.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        frame.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        frame.add(loginButton);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (login(username, password)) {
                frame.dispose();
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Login failed!");
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(180, 80, 80, 25);
        frame.add(registerButton);

        registerButton.addActionListener((ActionEvent e) -> {
            showRegisterScreen();
        });

        frame.setVisible(true);
    }

    private static void showRegisterScreen() {
        JFrame frame = new JFrame("Register");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 10, 80, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        frame.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        frame.add(passwordText);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 80, 100, 25);
        frame.add(registerButton);

        registerButton.addActionListener((ActionEvent e) -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (register(username, password)) {
                JOptionPane.showMessageDialog(frame, "Registration successful!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Registration failed!");
            }
        });

        frame.setVisible(true);
    }

    private static boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        return true;
    }

    private static boolean login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = new User(username);
            return true;
        }
        return false;
    }

    private static void showDashboard() {
        JFrame frame = new JFrame("Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton createQuizButton = new JButton("Create Quiz");
        createQuizButton.setBounds(10, 10, 150, 25);
        frame.add(createQuizButton);

        createQuizButton.addActionListener((ActionEvent e) -> {
            showCreateQuizScreen();
        });

        JButton takeQuizButton = new JButton("Take Quiz");
        takeQuizButton.setBounds(10, 50, 150, 25);
        frame.add(takeQuizButton);

        takeQuizButton.addActionListener((ActionEvent e) -> {
            showTakeQuizScreen();
        });

        frame.setVisible(true);
    }

    private static void showCreateQuizScreen() {
        JFrame frame = new JFrame("Create Quiz");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Quiz Title:");
        titleLabel.setBounds(10, 10, 80, 25);
        frame.add(titleLabel);

        JTextField titleText = new JTextField(20);
        titleText.setBounds(100, 10, 160, 25);
        frame.add(titleText);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(10, 40, 80, 25);
        frame.add(categoryLabel);

        JTextField categoryText = new JTextField(20);
        categoryText.setBounds(100, 40, 160, 25);
        frame.add(categoryText);

        JTextArea questionsTextArea = new JTextArea();
        questionsTextArea.setBounds(10, 70, 360, 200);
        frame.add(questionsTextArea);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(10, 280, 80, 25);
        frame.add(saveButton);

        saveButton.addActionListener((ActionEvent e) -> {
            String title = titleText.getText();
            String category = categoryText.getText();
            String[] questions = questionsTextArea.getText().split("\n");
            createQuiz(title, category, questions);
            JOptionPane.showMessageDialog(frame, "Quiz created successfully!");
            frame.dispose();
        });

        frame.setVisible(true);
    }

    private static void createQuiz(String title, String category, String[] questions) {
        Quiz quiz = new Quiz(title, category, Arrays.asList(questions));
        quizzes.add(quiz);
    }

    private static void showTakeQuizScreen() {
        JFrame frame = new JFrame("Take Quiz");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel quizIdLabel = new JLabel("Quiz ID:");
        quizIdLabel.setBounds(10, 10, 80, 25);
        frame.add(quizIdLabel);

        JTextField quizIdText = new JTextField(20);
        quizIdText.setBounds(100, 10, 160, 25);
        frame.add(quizIdText);

        JTextArea quizTextArea = new JTextArea();
        quizTextArea.setBounds(10, 40, 360, 250);
        frame.add(quizTextArea);

        JButton loadQuizButton = new JButton("Load Quiz");
        loadQuizButton.setBounds(10, 300, 100, 25);
        frame.add(loadQuizButton);

        loadQuizButton.addActionListener((ActionEvent e) -> {
            int quizId = Integer.parseInt(quizIdText.getText());
            Quiz quiz = loadQuiz(quizId);
            if (quiz != null) {
                quizTextArea.setText(String.join("\n", quiz.getQuestions()));
            } else {
                JOptionPane.showMessageDialog(frame, "Quiz not found!");
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(280, 300, 80, 25);
        frame.add(submitButton);

        submitButton.addActionListener((ActionEvent e) -> {
            // Submit quiz logic
            JOptionPane.showMessageDialog(frame, "Quiz submitted!");
        });

        frame.setVisible(true);
    }

    private static Quiz loadQuiz(int quizId) {
        if (quizId >= 0 && quizId < quizzes.size()) {
            return quizzes.get(quizId);
        }
        return null;
    }

    static class User {
        private final String username;

        public User(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }

    static class Quiz {
        public  String title;
        public  String category;
        public  List<String> questions;

        public Quiz(String title, String category, List<String> questions) {
            this.title = title;
            this.category = category;
            this.questions = questions;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        public List<String> getQuestions() {
            return questions;
        }
    }
}
