package edu.tanta.fci.reoil.user.events;

import edu.tanta.fci.reoil.catalog.Order;
import edu.tanta.fci.reoil.catalog.OrderRepository;
import edu.tanta.fci.reoil.catalog.OrderStatus;
import edu.tanta.fci.reoil.repositories.UserRepository;
import edu.tanta.fci.reoil.user.entities.User;
import edu.tanta.fci.reoil.worker.AssignedOrderAcceptedEvent;
import edu.tanta.fci.reoil.worker.AssignedOrderRejectedEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler {

  private final JavaMailSender mailSender;
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;

  @EventListener(AssignedOrderAcceptedEvent.class)
  public void handleAssignedOrderAcceptedEvent(AssignedOrderAcceptedEvent event) {
    System.out.println("AssignedOrderAcceptedEvent: " + event.getSource());
    var orderAndUser = (Map<String, Object>) event.getSource();
    var order = (Order)orderAndUser.get("order");
    var user = (User) orderAndUser.get("user");

    var email = user.getEmail();
    var subject = "REOIL: Order Accepted";

    var points = order.getOrderLines().stream()
        .map(ol -> ol.getQuantity() * ol.getItem().getPoints())
        .reduce(0L, Long::sum);

    user.setPoints(user.getPoints() + points);
    userRepository.save(user);
    final Order referenceById = orderRepository.getReferenceById(order.getId());
    referenceById.setOrderStatus(OrderStatus.ACCEPTED);
    orderRepository.save(referenceById);
    sendEmail(email, subject, "<br><br>Your order <b>[tracking_id=%s]</b> has been accepted".formatted(order.getTrackingNumber()));
  }

  @EventListener(AssignedOrderRejectedEvent.class)
  public void handleAssignedOrderRejectedEvent(AssignedOrderRejectedEvent event) {
    System.out.println("AssignedOrderRejectedEvent: " + event.getSource());
    var orderAndUser = (Map<String, Object>) event.getSource();
    var order = (Order)orderAndUser.get("order");
    var user = (User) orderAndUser.get("user");

    var email = user.getEmail();
    var subject = "REOIL: Order Rejected";

    final Order referenceById = orderRepository.getReferenceById(order.getId());
    referenceById.setOrderStatus(OrderStatus.REJECTED);
    orderRepository.save(referenceById);

    sendEmail(email, subject, "<br><br>Your order <b>[tracking_id=%s]</b> has been rejected".formatted(order.getTrackingNumber()));

  }


  private void sendEmail(String email, String subject, String text) {
    var message = mailSender.createMimeMessage();
    try {
      var helper = new MimeMessageHelper(message, true);
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText(text, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

}
