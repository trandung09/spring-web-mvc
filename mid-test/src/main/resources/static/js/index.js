document.addEventListener("DOMContentLoaded", async () => {
    let currentPage = 0;
    let totalPages = 1;
    const size = 3;

    async function loadProducts(page) {
        const response = await fetchProductData(page, size);
        if (response) {
            totalPages = response.totalPages;
            await renderProducts(response.content);
            updatePaginationButtons();
        }
    }

    async function renderProducts(products) {
        const productContainer = document.getElementById("food_container");
        productContainer.innerHTML = '';

        if (products.length === 0) {
            productContainer.innerHTML = "<p>No products available.</p>";
            return;
        }

        for (const product of products) {
            const productImageURL = await getImageAsBlob(product.imagePath);
            const productBox = document.createElement("div");
            productBox.classList.add("box");

            productBox.innerHTML = `
                <div class="img-box">
                    <img src="${productImageURL}" alt="${product.name}" />
                </div>
                <div class="detail-box">
                    <h6 id="product-name">${product.name}</h6>
                    <h3>
                        <span>đ</span>
                        <span id="product-price">${product.price}</span>
                    </h3>
                    <a href="#" class="buy-now-btn" data-name="${product.name}" data-id="${product.id}">
                        Buy Now
                    </a>
                </div>
            `;

            productContainer.appendChild(productBox);
        }

        document.querySelectorAll(".buy-now-btn").forEach((btn) => {
            btn.addEventListener("click", function (e) {
                e.preventDefault();

                const productName = this.getAttribute("data-name");
                const productId = this.getAttribute("data-id");

                productNameField.textContent = productName;

                modal.style.display = "flex";
                modal.setAttribute("data-product-id", productId);
            });
        });
    }

    function updatePaginationButtons() {
        const prevBtn = document.getElementById("prevBtn");
        const nextBtn = document.getElementById("nextBtn");

        prevBtn.disabled = currentPage === 0;
        nextBtn.disabled = currentPage >= totalPages - 1;
    }

    window.onload = () => {
        loadProducts(currentPage);
    };

    document.getElementById("nextBtn").addEventListener("click", () => {
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadProducts(currentPage);
        }
    });

    document.getElementById("prevBtn").addEventListener("click", () => {
        if (currentPage > 0) {
            currentPage--;
            loadProducts(currentPage);
        }
    });

    const sendBtn = document.getElementById("sendBtn");
    const confirmModal = document.getElementById("appointmentConfirmRequestModal");
    const confirmYesBtn = document.getElementById("appointmentConfirmYesBtn");
    const confirmNoBtn = document.getElementById("appointmentConfirmNoBtn");

    const fullNameInput = document.getElementById("booker-fullName");
    const phoneNumberInput = document.getElementById("booker-phoneNumber");
    const emailInput = document.getElementById("booker-email");
    const messageInput = document.getElementById("booker-message");

    function isValidPhoneNumber(phoneNumber) {
        const phonePattern = /^[0-9]{10}$/;
        return phonePattern.test(phoneNumber);
    }

    function isValidEmail(email) {
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return emailPattern.test(email);
    }

    sendBtn.addEventListener("click", () => {
        const fullName = fullNameInput.value.trim();
        const phoneNumber = phoneNumberInput.value.trim();
        const email = emailInput.value.trim();
        const message = messageInput.value.trim();

        if (!fullName || !phoneNumber || !email || !message) {
            alert("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            alert("Số điện thoại phải có 10 chữ số!");
            return;
        }

        if (!isValidEmail(email)) {
            alert("Email không hợp lệ!");
            return;
        }

        confirmModal.style.display = "flex";
    });

    confirmYesBtn.addEventListener("click", async () => {
        const bookerName = fullNameInput.value.trim();
        const bookerPhoneNumber = phoneNumberInput.value.trim();
        const bookerEmail = emailInput.value.trim();
        const content = messageInput.value.trim();

        console.log(bookerName)
        console.log(bookerEmail)

        try {
            const response = await fetch("http://localhost:8080/appointments", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    bookerName,
                    bookerPhoneNumber,
                    bookerEmail,
                    content
                })
            });

            if (response.ok) {
                alert("Yêu cầu đã được gửi thành công!");
                fullNameInput.value = '';
                phoneNumberInput.value = '';
                emailInput.value = '';
                messageInput.value = '';
            } else {
                alert("Đã xảy ra lỗi khi gửi yêu cầu. Vui lòng thử lại.");
            }
        } catch (error) {
            console.error("Lỗi khi gửi yêu cầu:", error);
            alert("Có lỗi xảy ra khi gửi yêu cầu.");
        }

        confirmModal.style.display = "none";
    });

    confirmNoBtn.addEventListener("click", () => {
        confirmModal.style.display = "none";
    });
});
