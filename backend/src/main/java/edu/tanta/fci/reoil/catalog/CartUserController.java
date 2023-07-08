package edu.tanta.fci.reoil.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('SCOPE_USER')")
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartUserController {

  private final CartUserService cartUserService;

  @GetMapping("/items")
  public List<OrderLine> getCart(Authentication authentication) {
    return cartUserService.getCartItems(authentication.getName());
  }


  @PostMapping("/items/{itemId}")
  public ResponseEntity<?> addItemToCart(Authentication authentication, @PathVariable Long itemId) {
    cartUserService.addItemToCart(itemId, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/orderlines/{id}")
  public ResponseEntity<?> removeOrderLine(Authentication authentication, @PathVariable("id") Long id) {
    cartUserService.removeOrderLine(id, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/orderlines/{id}/increase")
  public ResponseEntity<?> increaseOrderLineQuantity(Authentication authentication, @PathVariable("id") Long id) {
    cartUserService.increaseOrderLineQuantity(id, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/orderlines/{id}/decrease")
  public ResponseEntity<?> decreaseOrderLineQuantity(Authentication authentication, @PathVariable("id") Long id) {
    cartUserService.decreaseOrderLineQuantity(id, authentication.getName());
    return ResponseEntity.ok().build();
  }

  @PostMapping("submit")
    public ResponseEntity<OrderDto> submit(Authentication authentication) {
    final OrderDto orderDto = cartUserService.submit(authentication.getName());
    return ResponseEntity.created(URI.create("api/v1/orders/" + orderDto.trackingNumber().toString()))
        .body(orderDto);
    }






}

