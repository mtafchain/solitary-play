
public class test {

    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            System.out.print(i + " : ");
            for (int j = 1; j <= i; j++) {
                int puissance = 0;
                for (int k = 1; k < j; k++) {
                    puissance = puissance + (i * j);
                }
                System.out.print(puissance + "  ");
            }
            System.out.println();
        }
    }
}
