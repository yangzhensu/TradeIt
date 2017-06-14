package steve.yang.tradeit.interfaces;

import steve.yang.tradeit.data.Sale;
import steve.yang.tradeit.data.User;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public interface IDbHelper {

    void addSale(Sale sale);

    void removeSale(Sale sale);

    void addUser(User user);

    void removeUser(User user);

}
