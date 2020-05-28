/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edsfsb;

import dto.CartItem;
import ed.sfsb.ShopCartBeanRemote;
import java.util.ArrayList;
import java.util.Scanner;
import javax.ejb.EJB;

public class ShopCartAppClient {

    @EJB
    private static ShopCartBeanRemote shopCart;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Boolean running = true;

        ShopCartAppClient appClient = new ShopCartAppClient();// show that the shopCart is empty
        appClient.displayCart();// assuming they are selected by the user
        CartItem item1 = new CartItem("000001", "Intel Core i7 CPU", 349.99, 2);
        CartItem item3 = new CartItem("000001", "Intel Core i7 CPU", 349.99, 2);
        CartItem item2 = new CartItem("000002", "Intel SSD 512GB", 299.99, 3);
        CartItem item4 = new CartItem("000003", "Intel USB 2GB", 299.99, 3);
        CartItem item5 = new CartItem("000004", "Intel HDD 3GB", 299.99, 3);
        CartItem item6 = new CartItem("000001", "Intel Core i7 CPU", 299.99, 1);

        appClient.addCart(item1);
        appClient.addCart(item2);
        appClient.displayCart();

        while (running) {
            String typeCode = "";

            System.out.println("\nTest new CRUD operations"
                    + "\n1: Add Test Items"
                    + "\n2: Remove Test Items"
                    + "\n3: Update"
                    + "\n4: Display Cart"
                    + "\n5: Exit");
            System.out.println("\n\nPlease Select an option (1-5):");
            Scanner scanner = new Scanner(System.in);
            String select = scanner.nextLine();
            switch (select) {
                case "1": {
                    appClient.addCart(item4);
                    appClient.addCart(item5);
                    appClient.addCart(item1);
                    appClient.addCart(item2);
                    appClient.addCart(item3);
                    appClient.addCart(null);
                    appClient.displayCart();
                    break;
                }
                case "2": {
                    appClient.Remove(item4);
                    appClient.displayCart();
                    appClient.Remove(item5);
                    appClient.displayCart();
                    break;

                }
                case "3": {
                    appClient.UpdateCart(item6);
                    appClient.displayCart();
                }

                break;

                case "4": {
                    appClient.displayCart();
                    break;
                }
                case "5": {
                    running = false;
                    break;
                }
                default: {

                    break;
                }
            }
        }
    }
    public void UpdateCart(CartItem item) {
        if (item != null) {
            System.out.println("Updating the cart by setting " + item.getDescription() + " " + item.getQuantity() + "");
            if (shopCart.updateCartItem(item)) {
                System.out.println("The following " + item.getQuantity() + " " + item.getDescription() + " has been updated.");
            } else {
                System.out.println("Sorry, The following " + item.getQuantity() + " " + item.getDescription() + " cannot be updated since they do not exist.");
            }
        } else {
            System.out.println("Does not exist. Null Value attempted to be added.");
        }
    }

    public void addCart(CartItem item) {
        if (item != null) {
            System.out.println("Adding item " + item.getDescription() + " to cart");
            if (shopCart.addCartItem(item)) {
                System.out.println("Your order of " + item.getQuantity() + " " + item.getDescription() + " has been added.");
            } else {
                System.out.println("Sorry, your order of " + item.getQuantity() + " " + item.getDescription() + " cannotbe added due to low stock.");
            }
        }
    }

    public void Remove(CartItem item) {
        if (item != null) {
            System.out.println("Removing item " + item.getDescription() + " from cart");
            if (shopCart.deleteCartItem(item.getItemId())) {
                System.out.println("The following " + item.getQuantity() + " " + item.getDescription() + " has been removed.");
            } else {
                System.out.println("Sorry, The following " + item.getQuantity() + " " + item.getDescription() + " cannot be removed since they do not exist.");
            }
        } else {
            System.out.println("Sorry, your order of " + item.getQuantity() + " " + item.getDescription() + " cannotbe added due to low stock.");
        }
    }

    public void displayCart() {
        ArrayList<CartItem> ciList = shopCart.getCart();
        if (ciList.isEmpty()) {
            System.out.println("The shopping cart is empty!");
            return;
        }
        System.out.println("Your shopping cart has the following items:");
        double total = 0.0;
        for (CartItem ci : ciList) {
            double unitPrice = ci.getUnitPrice();
            double quantity = ci.getQuantity();
            double subTotal = unitPrice * quantity;
            System.out.println("Item: " + ci.getDescription() + "\tUnit Price: " + ci.getUnitPrice() + "\tQuantity: " + ci.getQuantity() + "\tSub-Total: " + subTotal);
            total = total + subTotal;
        }
        System.out.println("---");
        System.out.println("Total price: " + total);
        System.out.println("----End of Shopping Cart---");
    }
}
