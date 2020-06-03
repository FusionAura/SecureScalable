/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.sfsb;

import dto.CartItem;
import java.util.ArrayList;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class ShopCartBean implements ShopCartBeanRemote {

    private ArrayList<CartItem> cart;

    public ShopCartBean() {
        cart = new ArrayList<CartItem>();
    }

    private boolean add(CartItem cartItem) {
        boolean result = false;
        try {
            result = cart.add(cartItem);
        } catch (Exception ex) {
        }
        return result;
    }

    //Add Items to the shop cart
    @Override
    public boolean addCartItem(CartItem cartItem) {
        boolean exists = false;
        if (cartItem != null) {
            try {

                for (CartItem item : cart) {
                    if (item.getItemId().equals(cartItem.getItemId())) {
                        exists = true;
                        item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                        break;
                    }
                }
                if (exists == false) {
                    add(cartItem);
                }

                return true;
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<CartItem> getCart() {
        return cart;
    }

    @Remove
    public void remove() {
        cart = null;
    }

    @Override
    public boolean deleteCartItem(String itemId) {
        if (itemId != null) {
            try {

                if (!cart.isEmpty()) {
                    for (CartItem item : cart) {
                        if (item.getItemId().equals(itemId)) {
                            cart.remove(item);
                            return true;
                        }
                    }
                }
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public boolean updateCartItem(CartItem cartItem) {
        int i;
        if (cartItem != null) {
            try {

                for (i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getItemId().equals(cartItem.getItemId())) {
                        cart.set(i, cartItem);
                        return true;
                    }
                }
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

}
