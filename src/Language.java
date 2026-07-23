import java.util.Arrays;
import java.util.Scanner;

public class Language {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] prices = new int[n];

        for (int i = 0; i < n; i++) {
            prices[i] = sc.nextInt();
        }

        // Ordenamos los precios para poder aplicar búsqueda binaria
        Arrays.sort(prices);

        int q = sc.nextInt();

        for (int i = 0; i < q; i++) {
            int coins = sc.nextInt();

            // Cantidad de precios menores o iguales que coins
            int answer = upperBound(prices, coins);

            System.out.println(answer);
        }

        sc.close();
    }

    /*
     * Retorna la posición del primer precio
     * estrictamente mayor que coins.
     */
    static int upperBound(int[] prices, int coins) {
        int left = 0;
        int right = prices.length;

        while (left < right) {
            int middle = left + (right - left) / 2;

            if (prices[middle] <= coins) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        return left;
    }
}