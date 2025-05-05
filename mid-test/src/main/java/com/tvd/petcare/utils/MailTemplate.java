package com.tvd.petcare.utils;

public class MailTemplate {

    public static String readConfirmPurchaseProductEmailTemplate() {
        return """
        <!DOCTYPE html>
        <html lang="vi">
          <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Xác nhận đơn hàng - Drool Pet Care</title>
            <style>
              body {
                font-family: Arial, sans-serif;
                color: #333;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
              }
              .container {
                max-width: 600px;
                margin: 20px auto;
                background-color: #fff;
                padding: 20px;
                border: 1px solid #ddd;
              }
              .logo {
                font-size: 20px;
                font-weight: bold;
                color: #6a5acd;
              }
              .header {
                border-bottom: 1px solid #ddd;
                padding-bottom: 10px;
                margin-bottom: 20px;
              }
              .content {
                text-align: center;
              }
              .content p {
                text-align: left;
                font-size: 16px;
                margin: 5px 0;
              }
              .product-info {
                margin: 20px 0;
                border: 1px solid #ccc;
                padding: 15px;
                background-color: #f9f9f9;
                text-align: left;
              }
              .product-info p {
                margin: 8px 0;
              }
              .footer {
                font-size: 12px;
                color: #666;
                margin-top: 20px;
                border-top: 1px solid #ddd;
                padding-top: 10px;
              }
              .footer a {
                color: #007bff;
                text-decoration: none;
              }
            </style>
          </head>
          <body>
            <div class="container">
              <div class="header">
                <span class="logo">Drool - Pet Care</span>
              </div>
              <div class="content">
                <p>Chào [[USER_NAME]],</p>
                <p>Cảm ơn bạn đã đặt hàng tại Drool! Dưới đây là thông tin đơn hàng của bạn:</p>

                <p><strong>Số điện thoại:</strong> [[PHONE]]</p>

                <div class="product-info">
                  <p><strong>Tên sản phẩm:</strong> [[PRODUCT_NAME]]</p>
                  <p><strong>Đơn giá:</strong> [[UNIT_PRICE]] VND</p>
                  <p><strong>Số lượng:</strong> [[QUANTITY]]</p>
                  <p><strong>Tổng cộng:</strong> [[TOTAL_PRICE]] VND</p>
                </div>

                <p>Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất.</p>
                <p>Nếu bạn có bất kỳ câu hỏi nào, hãy liên hệ với chúng tôi qua email bên dưới.</p>
              </div>
              <div class="footer">
                <p>Người gửi: Admin, UTC, Cầu Giấy, Hà Nội, Việt Nam.</p>
                <p>Liên hệ: <a href="mailto:trandung09082004@gmail.com">trandung09082004@gmail.com</a></p>
              </div>
            </div>
          </body>
        </html>
        """;
    }

    public static String readConfirmAppointmentRequestEmailTemplate() {
        return """
    <!DOCTYPE html>
    <html lang="vi">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Xác nhận yêu cầu đặt lịch hẹn - Drool Pet Care</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
          }
          .container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ddd;
          }
          .logo {
            font-size: 20px;
            font-weight: bold;
            color: #6a5acd;
          }
          .header {
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
            margin-bottom: 20px;
          }
          .content {
            text-align: center;
          }
          .content p {
            text-align: left;
            font-size: 16px;
            margin: 5px 0;
          }
          .request-info {
            margin: 20px 0;
            border: 1px solid #ccc;
            padding: 15px;
            background-color: #f9f9f9;
            text-align: left;
          }
          .request-info p {
            margin: 8px 0;
          }
          .footer {
            font-size: 12px;
            color: #666;
            margin-top: 20px;
            border-top: 1px solid #ddd;
            padding-top: 10px;
          }
          .footer a {
            color: #007bff;
            text-decoration: none;
          }
        </style>
      </head>
      <body>
        <div class="container">
          <div class="header">
            <span class="logo">Drool - Pet Care</span>
          </div>
          <div class="content">
            <p>Chào [[USER_NAME]],</p>
            <p>Chúng tôi đã nhận được yêu cầu đặt lịch hẹn của bạn. Dưới đây là nội dung bạn đã gửi:</p>

            <div class="request-info">
              <p><strong>Nội dung yêu cầu:</strong></p>
              <p>[[REQUEST_CONTENT]]</p>
            </div>

            <p>Chúng tôi sẽ liên hệ lại với bạn trong thời gian sớm nhất để xác nhận lịch hẹn.</p>
            <p>Nếu bạn có bất kỳ câu hỏi nào, hãy liên hệ với chúng tôi qua email bên dưới.</p>
          </div>
          <div class="footer">
            <p>Người gửi: Admin, UTC, Cầu Giấy, Hà Nội, Việt Nam.</p>
            <p>Liên hệ: <a href="mailto:trandung09082004@gmail.com">trandung09082004@gmail.com</a></p>
          </div>
        </div>
      </body>
    </html>
    """;
    }

}
