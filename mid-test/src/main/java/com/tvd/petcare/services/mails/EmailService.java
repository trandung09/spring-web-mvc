package com.tvd.petcare.services.mails;

import com.tvd.petcare.dtos.requests.PurchaseProductRequest;
import com.tvd.petcare.utils.MailTemplate;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class EmailService {

    final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String adminEmailAddress;

    public void sendConfirmPurchaseProductEmail(String customerEmail, String customerName, String customerPhoneNumber, PurchaseProductRequest request) throws Exception {
        String senderName = "Drool - Pet Care";
        String subject = "Xác nhận đơn hàng của bạn";

        String content = MailTemplate.readConfirmPurchaseProductEmailTemplate();

        double totalPrice = request.getPrice() * request.getQuantity();

        content = content.replace("[[PHONE]]", customerPhoneNumber);
        content = content.replace("[[USER_NAME]]", customerName); // ✅ Dùng tên thay vì email
        content = content.replace("[[PRODUCT_NAME]]", request.getProductName());
        content = content.replace("[[UNIT_PRICE]]", String.format("%,.0f", request.getPrice())); // thêm định dạng ngăn cách ngàn
        content = content.replace("[[QUANTITY]]", String.valueOf(request.getQuantity()));
        content = content.replace("[[TOTAL_PRICE]]", String.format("%,.0f", totalPrice)); // thêm định dạng ngăn cách ngàn

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(adminEmailAddress, senderName);
        helper.setTo(customerEmail);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    public void sendConfirmAppointmentRequestEmail(String customerEmail, String customerName, String customerRequestContent, boolean confirm) throws Exception {
        String senderName = "Drool - Pet Care";
        String subject = confirm
                ? "Xác nhận yêu cầu đặt lịch hẹn"
                : "Từ chối yêu cầu đặt lịch hẹn";

        String content = MailTemplate.readConfirmAppointmentRequestEmailTemplate();

        content = content.replace("[[USER_NAME]]", customerName);
        content = content.replace("[[REQUEST_CONTENT]]", customerRequestContent);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(adminEmailAddress, senderName);
        helper.setTo(customerEmail);
        helper.setSubject(subject);
        helper.setText(content, true); // true để gửi HTML

        mailSender.send(message);
    }

}
