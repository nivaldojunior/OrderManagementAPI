package br.com.OrderManagementAPI.service;

import br.com.OrderManagementAPI.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {

    private final JavaMailSender javaMailSender;

    public void sendEmail(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(order.getUser().getEmail());
            message.setSubject("Order completed");
            message.setText("Order ID: " + order.getId() + " is Completed");

            log.info("Sending email notification for order completion - Order ID: {}", order.getId());
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage());
        }
    }
}
