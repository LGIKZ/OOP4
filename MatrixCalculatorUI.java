import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixCalculatorUI extends JFrame {
    private JTextField[][] matrixAFields;
    private JTextField[][] matrixBFields;
    private JPanel matrixAPanel;
    private JPanel matrixBPanel;
    private JTextField scalarField;
    public MatrixCalculatorUI() {
        setTitle("Matrix Calculator");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // Панель для ввода матрицы A
        matrixAPanel = new JPanel();
        matrixAPanel.setLayout(new GridLayout(3, 3));
        matrixAFields = new JTextField[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixAFields[i][j] = new JTextField(5);
                matrixAPanel.add(matrixAFields[i][j]);
            }
        }
        // Панель для ввода матрицы B
        matrixBPanel = new JPanel();
        matrixBPanel.setLayout(new GridLayout(3, 3));
        matrixBFields = new JTextField[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixBFields[i][j] = new JTextField(5);
                matrixBPanel.add(matrixBFields[i][j]);
            }
        }
        JPanel matrixContainer = new JPanel();
        matrixContainer.setLayout(new GridLayout(1, 2));
        matrixContainer.add(matrixAPanel);
        matrixContainer.add(matrixBPanel);
        JLabel matrixALabel = new JLabel("Матрица A:");
        JLabel matrixBLabel = new JLabel("Матрица B:");
        JPanel matrixLabels = new JPanel();
        matrixLabels.setLayout(new GridLayout(1, 2));
        matrixLabels.add(matrixALabel);
        matrixLabels.add(matrixBLabel);
        // Панель с кнопками для операций
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        JButton determinantButton = new JButton("Найти определитель");
        determinantButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findDeterminant();
            }
        });
        buttonPanel.add(determinantButton);
        JButton transposeButton = new JButton("Транспонировать");
        transposeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transposeMatrix();
            }
        });
        buttonPanel.add(transposeButton);
        JButton multiplyByScalarButton = new JButton("Умножить на число");
        multiplyByScalarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                multiplyByScalar();
            }
        });
        buttonPanel.add(multiplyByScalarButton);
        scalarField = new JTextField(5);
        buttonPanel.add(scalarField);
        JButton addMatricesButton = new JButton("Сложение матриц");
        addMatricesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMatrices();
            }
        });
        buttonPanel.add(addMatricesButton);
        JButton subtractMatricesButton = new JButton("Вычитание матриц");
        subtractMatricesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                subtractMatrices();
            }
        });
        buttonPanel.add(subtractMatricesButton);
        JButton multiplyMatricesButton = new JButton("Умножение матриц");
        multiplyMatricesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                multiplyMatrices();
            }
        });
        buttonPanel.add(multiplyMatricesButton);
        JButton divideMatricesButton = new JButton("Деление матриц");
        divideMatricesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                divideMatrices();
            }
        });
        buttonPanel.add(divideMatricesButton);
        // Добавление панелей в основное окно
        add(matrixLabels, BorderLayout.NORTH);
        add(matrixContainer, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
    private void findDeterminant() {
        try {
            double[][] matrix = getMatrixA();
            double determinant = calculateDeterminant(matrix);
            JOptionPane.showMessageDialog(this, "Определитель: " + determinant);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при вычислении определителя: " + e.getMessage());
        }
    }
    private void transposeMatrix() {
        try {
            double[][] matrix = getMatrixA();
            double[][] transposed = transpose(matrix);
            displayMatrix(transposed, "Транспонированная матрица");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при транспонировании матрицы: " + e.getMessage());
        }
    }
    private void multiplyByScalar() {
        try {
            double[][] matrix = getMatrixA();
            double scalar = Double.parseDouble(scalarField.getText());
            double[][] result = multiplyByScalar(matrix, scalar);
            displayMatrix(result, "Результат умножения на " + scalar);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при умножении на число: " + e.getMessage());
        }
    }
    private void addMatrices() {
        try {
            double[][] matrixA = getMatrixA();
            double[][] matrixB = getMatrixB();
            double[][] result = addMatrices(matrixA, matrixB);
            displayMatrix(result, "Результат сложения матриц");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при сложении матриц: " + e.getMessage());
        }
    }
    private void subtractMatrices() {
        try {
            double[][] matrixA = getMatrixA();
            double[][] matrixB = getMatrixB();
            double[][] result = subtractMatrices(matrixA, matrixB);
            displayMatrix(result, "Результат вычитания матриц");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при вычитании матриц: " + e.getMessage());
        }
    }
    private double[][] calculateInverse(double[][] matrix) {
        double[][] inverse = new double[3][3];
        double determinant = calculateDeterminant(matrix);
        if (determinant == 0) {
            throw new ArithmeticException("Матрица вырожденная, обратной матрицы не существует");
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverse[i][j] = cofactor(matrix, i, j) / determinant;
                System.out.println("result dimensions: " + inverse[i][j]);
            }
        }
        return inverse;
    }
    private double cofactor(double[][] matrix, int row, int col) {
        int sign = (row + col) % 2 == 0 ? 1 : -1;
        System.out.println(sign*minorcalculateDeterminant(minor(matrix, row,
                col)));
        return sign * minorcalculateDeterminant(minor(matrix, row, col));
    }
    private double[][] minor(double[][] matrix, int row, int col) {
        int size = matrix.length;
        double[][] minor = new double[size - 1][size - 1];
        int minorRow = 0;
        for (int i = 0; i < size; i++) {
            if (i == row) continue;
            int minorCol = 0;
            for (int j = 0; j < size; j++) {
                if (j == col) continue;
                minor[minorRow][minorCol] = matrix[i][j];
                minorCol++;
            }
            minorRow++;
        }
        return minor;
    }
    private void multiplyMatrices() {
        try {
            double[][] matrixA = getMatrixA();
            double[][] matrixB = getMatrixB();
            double[][] result = multiplyMatrices(matrixA, matrixB);
            displayMatrix(result, "Результат умножения матриц");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при умножении матриц: " + e.getMessage());
        }
    }
    private void divideMatrices() {
        try {
            double[][] matrixA = getMatrixA();
            double[][] matrixB = getMatrixB();
            double[][] result = divideMatrices(matrixA, matrixB);
            displayMatrix(result, "Результат деления матриц");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при делении матриц: "
                    + e.getMessage());
        }
    }
    private double[][] getMatrixA() {
        double[][] matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = Double.parseDouble(matrixAFields[i][j].getText());
            }
        }
        return matrix;
    }
    private double[][] getMatrixB() {
        double[][] matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = Double.parseDouble(matrixBFields[i][j].getText());
            }
        }
        return matrix;
    }
    private void displayMatrix(double[][] matrix, String title) {
        StringBuilder sb = new StringBuilder(title + ":\n");
        int rows = matrix.length; // Определяем количество строк
        int cols = matrix[0].length; // Определяем количество столбцов
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%.2f", matrix[i][j])).append(" ");
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }
    private double calculateDeterminant(double[][] matrix) {
        // Алгоритм для вычисления определителя матрицы 3x3
        double determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] -
                matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2]
                * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1]
                * matrix[2][0]);
        return determinant;
    }
    private double minorcalculateDeterminant(double[][] matrix) {
        double determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        return determinant;
    }
    private double[][] transpose(double[][] matrix) {
        double[][] transposed = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }
        return transposed;
    }
    private double[][] multiplyByScalar(double[][] matrix, double scalar) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        return result;
    }
    private double[][] addMatrices(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        return result;
    }
    private double[][] subtractMatrices(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        return result;
    }
    private double[][] multiplyMatrices(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return result;
    }
    private double[][] divideMatrices(double[][] matrixA, double[][] matrixB)
    {
        double[][] inverseB = calculateInverse(matrixB);
        return multiplyMatrices(matrixA, inverseB);
    }
    public static void main(String[] args) {
        new MatrixCalculatorUI();
    } }