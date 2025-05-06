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

    public void sendConfirmPurchaseProductEmail(String bookerEmail, String bookerName, String bookerPhoneNumber, PurchaseProductRequest request) throws Exception {
        String senderName = "Drool - Pet Care";
        String subject = "Xác nhận đơn hàng của bạn";

        String content = MailTemplate.readConfirmPurchaseProductEmailTemplate();

        double totalPrice = request.getPrice() * request.getQuantity();

        content = content.replace("[[PHONE]]", bookerPhoneNumber);
        content = content.replace("[[USER_NAME]]", bookerName);
        content = content.replace("[[PRODUCT_NAME]]", request.getProductName());
        content = content.replace("[[UNIT_PRICE]]", String.format("%,.0f", request.getPrice()));
        content = content.replace("[[QUANTITY]]", String.valueOf(request.getQuantity()));
        content = content.replace("[[TOTAL_PRICE]]", String.format("%,.0f", totalPrice));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(adminEmailAddress, senderName);
        helper.setTo(bookerEmail);
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

        String confirmOrRejectRequestContent = confirm
                ? "sẽ liên hệ lại với bạn trong thời gian sớm nhất để xác nhận lịch hẹn."
                : "xin thông báo rằng lịch hẹn của không thể thực hiện được lịch hẹn của bạn vì một vài lý do.";
        content = content.replace("[[CONFIRM_OR_REJECT]]", confirmOrRejectRequestContent );

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(adminEmailAddress, senderName);
        helper.setTo(customerEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

}
