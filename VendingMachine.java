import java.util.Random;

/**
 * Code for VendingMachine class.
 * @version 1.0
 * @author aprasad72
 */
public class VendingMachine {
    private static double totalSales;
    private VendingItem[][][] shelf;
    private int luckyChance;
    private Random rand;
    /**
    * Constructor that defines a VendingMachine object
    * Constructor also restocks VendingMachine when object is created
    */
    public VendingMachine() {
        totalSales = 0;
        shelf = new VendingItem[6][3][5];
        luckyChance = 0;
        rand = new Random();
        restock();
    }
    /**
    * @param code user's input for VendingItem position
    * @return VendingItem object
    * Checks user input validity before returning VendingItem object
    * Prints appropriate error statements
    * Updates totalSales class variable if needed
    */
    public VendingItem vend(String code) {
        if (code.length() != 2) {
            System.out.println("Error! Input is invalid, try again.");
            return null;
        }
        if (Character.isAlphabetic(code.charAt(1))) {
            System.out.println("Error! Input is invalid, try again.");
            return null;
        }
        char temp0 = Character.toUpperCase(code.charAt(0));
        int strOne = (int) (temp0 - 'A');
        int strTwo = (Integer.parseInt(code.substring(1))) - 1;
        if ((strOne > 6 || strOne < -1) || (strTwo > 2 || strTwo < 0)) {
            System.out.println("Error! Input is invalid, try again.");
            return null;
        }
        if (shelf[strOne][strTwo][0] == null) {
            System.out.println("Item is not available.");
            return null;
        } else if (free()) {
            System.out.println("Congratulations! Item was free.");
            return shelf[strOne][strTwo][0];
        } else {
            totalSales += shelf[strOne][strTwo][0].getPrice();
            VendingItem temp = shelf[strOne][strTwo][0];
            for (int i = 0; i < shelf[0][0].length - 1; i++) {
                shelf[strOne][strTwo][i] = shelf[strOne][strTwo][i + 1];
                if (i == shelf[strOne][strTwo].length - 2) {
                    shelf[strOne][strTwo][i + 1] = null;
                }
            }
            return temp;
        }
    }
    /**
    * @return boolean variable (i.e. true or false)
    * Determines if the user should recieve their item for free
    * Based on percent chance equivelent to luckyChance
    * luckyChance adjusted accordingly
    */
    private boolean free() {
        if (rand.nextInt(100) < luckyChance) {
            luckyChance = 0;
            return true;
        } else {
            luckyChance++;
            return false;
        }
    }
    /**
    * Restocks VendingMachine object with random VendingItem objects
    */
    public void restock() {
        VendingItem[] a;
        a = VendingItem.values();
        for (int i = 0; i < shelf.length; i++) {
            for (int j = 0; j < shelf[i].length; j++) {
                for (int k = 0; k < shelf[i][j].length; k++) {
                    shelf[i][j][k] = a[rand.nextInt(a.length)];
                }
            }
        }
    }
    /**
    * @return totalSales class variable
    */
    public static double getTotalSales() {
        return totalSales;
    }
    /**
    * @return integer count of how many VendingItem objects in VendingMachine
    */
    public int getNumberOfItems() {
        int count = 0;
        for (int i = 0; i < shelf.length; i++) {
            for (int j = 0; j < shelf[i].length; j++) {
                for (int k = 0; k < shelf[i][j].length; k++) {
                    if (shelf[i][j][k] != null) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    /**
    * @return combined total price of all the VendingItem objects in the
    * VendingMachine
    */
    public double getTotalValue() {
        double sum = 0.0;
        for (int i = 0; i < shelf.length; i++) {
            for (int j = 0; j < shelf[i].length; j++) {
                for (int k = 0; k < shelf[i][j].length; k++) {
                    if (shelf[i][j][k] != null) {
                        sum += shelf[i][j][k].getPrice();
                    }
                }
            }
        }
        return sum;
    }
    /**
    * @return luckyChance class variable
    */
    public int getLuckyChance() {
        return luckyChance;
    }
    /**
    * @return string representation of VendingMachine
    */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("----------------------------------------------------------" +
            "------------\n");
        s.append("                            VendaTron 9000                " +
            "            \n");
        for (int i = 0; i < shelf.length; i++) {
            s.append("------------------------------------------------------" +
                "----------------\n");
            for (int j = 0; j < shelf[0].length; j++) {
                VendingItem item = shelf[i][j][0];
                String str = String.format("| %-20s ",
                    (item == null ? "(empty)" : item.name()));
                s.append(str);
            }
            s.append("|\n");
        }
        s.append("----------------------------------------------------------" +
            "------------\n");
        s.append(String.format("There are %d items with a total " +
            "value of $%.2f.%n", getNumberOfItems(), getTotalValue()));
        s.append(String.format("Total sales across vending machines " +
            "is now: $%.2f.%n", getTotalSales()));
        return s.toString();
    }
}
