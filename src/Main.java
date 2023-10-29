import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество уравнений: ");
        int equations = scanner.nextInt();

        System.out.println("Введите количество неизвестных: ");
        int unknowns = scanner.nextInt();

        double[][] matrix = new double[equations][unknowns + 1];

        System.out.println("Введите коэффициенты при неизвестных и результативные при уравнениях:");
        for (int i = 0; i < equations; i++) {
            for (int j = 0; j <= unknowns; j++) {
                System.out.println("Введите элемент: [" + i + "][" + j + "]");
                while (true) {
                    try {
                        matrix[i][j] = scanner.nextDouble();
                        break;
                    } catch (Exception e) {
                        System.out.println("Нужно ввести число, можно с плавающей точкой");
                    }
                }
            }
        }

        scanner.close();

        if (solveGauss(matrix)) {
            System.out.println("Решение СЛАУ:");
            printSolution(matrix);
        } else {
            System.out.println("Система уравнений не имеет однозначного решения.");
        }
    }

    private static boolean solveGauss(double[][] matrix) {
        int equations = matrix.length;
        int unknowns = matrix[0].length - 1;

        for (int i = 0; i < equations; i++) {
            int maxRowIndex = i;
            for (int j = i + 1; j < equations; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[maxRowIndex][i])) {
                    maxRowIndex = j;
                }
            }

            if (matrix[maxRowIndex][i] == 0) {
                return false;
            }

            double[] temp = matrix[i];
            matrix[i] = matrix[maxRowIndex];
            matrix[maxRowIndex] = temp;

            for (int j = i + 1; j < equations; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k <= unknowns; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }

        for (int i = equations - 1; i >= 0; i--) {
            double factor = matrix[i][i];
            for (int j = i + 1; j <= unknowns; j++) {
                matrix[i][j] /= factor;
            }
            matrix[i][i] = 1;

            for (int j = 0; j < i; j++) {
                double temp = matrix[j][i];
                for (int k = i; k <= unknowns; k++) {
                    matrix[j][k] -= temp * matrix[i][k];
                }
            }
        }

        return true;
    }

    private static void printSolution(double[][] matrix) {
        int unknowns = matrix[0].length - 1;

        for (int i = 0; i < matrix.length; i++) {
            System.out.print("Уравнение " + (i + 1) + ": ");
            for (int j = 0; j < unknowns; j++) {
                System.out.print(matrix[i][j] + "*x" + (j + 1) + " + ");
            }
            System.out.println("= " + matrix[i][unknowns]);
        }

        System.out.println("Решение:");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println("x" + (i + 1) + " = " + matrix[i][unknowns]);
        }
    }
}