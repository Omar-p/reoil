package edu.tanta.fci.reoil.catalog;

import edu.tanta.fci.reoil.exceptions.NotFoundException;
import edu.tanta.fci.reoil.user.UserService;
import edu.tanta.fci.reoil.user.entities.User;
import edu.tanta.fci.reoil.user.registration.events.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CartUserService {

  private final UserService userService;
  private final CartRepository cartRepository;
  private final CatalogRepository catalogRepository;
  private final OrderRepository orderRepository;

  public void addItemToCart(Long itemId, String username) {

    final User user = userService.findByUsername(username);

    final Cart cart = cartRepository.findCartByUser(user);


    if (cart.getOrderLines().stream().anyMatch(ol -> ol.getItem().getId().equals(itemId))) {
      cart.getOrderLines().stream().filter(ol -> ol.getItem().getId().equals(itemId)).findFirst().ifPresent(
          ol -> ol.setQuantity(ol.getQuantity() + 1)
      );
      cartRepository.save(cart);
      return;
    }

    OrderLine ol = toOrderLine(catalogRepository.findById(itemId).orElseThrow(
        () -> new NotFoundException("No item with that id")
    ));

    cart.addOrderLine(ol);
  }

  OrderLine toOrderLine(Item item) {
    final OrderLine orderLine = new OrderLine();
    orderLine.setItem(item);
    orderLine.setQuantity(1L);
    return orderLine;
  }

  public void removeOrderLine(Long id, String username) {
    final User user = userService.findByUsername(username);

    final Cart cart = cartRepository.findCartByUser(user);

    cart.removeOrderLine(id);
    cartRepository.save(cart);
  }

  public List<OrderLine> getCartItems(String username) {
    final User user = userService.findByUsername(username);

    final Cart cart = cartRepository.findCartByUser(user);
    return cart.getOrderLines();
  }

  public void increaseOrderLineQuantity(Long olId, String username) {
    final User user = userService.findByUsername(username);

    final Cart cart = cartRepository.findCartByUser(user);

    final OrderLine orderLine = cart.getOrderLines().stream().filter(ol -> ol.getId().equals(olId)).findFirst().orElseThrow(
        () -> new NotFoundException("No order line with that id")
    );

    orderLine.setQuantity(orderLine.getQuantity() + 1);
    cartRepository.save(cart);
  }

  public void decreaseOrderLineQuantity(Long id, String username) {
    final User user = userService.findByUsername(username);

    final Cart cart = cartRepository.findCartByUser(user);

    final OrderLine orderLine = cart.getOrderLines().stream().filter(ol -> ol.getId().equals(id)).findFirst().orElseThrow(
            () -> new NotFoundException("No order line with that id")
    );

    if (orderLine.getQuantity() == 1) {
      cart.removeOrderLine(id);
      cartRepository.save(cart);
      return;
    }

    orderLine.setQuantity(orderLine.getQuantity() - 1);
    cartRepository.save(cart);

  }

  public OrderDto submit(String username) {

    final User user = userService.findByUsername(username);

    final Cart cart = cartRepository.findCartByUser(user);


    Order order = new Order(cart);
    cart.getOrderLines().clear();
    cartRepository.save(cart);
    order = orderRepository.saveAndFlush(order);

    return new OrderDto(order.getOrderLines(), order.getOrderStatus(), order.getCreatedAt(), order.getTrackingNumber());
  }

  @EventListener(UserRegistrationEvent.class)
  public void onUserRegistration(UserRegistrationEvent userRegistrationEvent) {
    final User user = userRegistrationEvent.getUser();
    final Cart cart = new Cart();
    cart.setUser(user);
    cartRepository.save(cart);
  }
}
