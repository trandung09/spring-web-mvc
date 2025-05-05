    document.addEventListener("DOMContentLoaded", async () => {
        const tableBody = document.getElementById("table-body");
        const paginationContainer = document.getElementById("pagination");

        let currentPage = 0;
        const pageSize = 8;

        const productDetailsModal = new bootstrap.Modal(document.getElementById('productDetailsModal'));
        const productNameElem = document.getElementById("productNameDetails");
        const productPriceElem = document.getElementById("productPriceDetails");
        const productDescriptionElem = document.getElementById("productDescriptionDetails");
        const productImageElem = document.getElementById("productImage");

        const updateProductModal = new bootstrap.Modal(document.getElementById('updateProductModal'));
        const productNameInput = document.getElementById("productName");
        const productPriceInput = document.getElementById("productPrice");
        const productDescriptionInput = document.getElementById("productDescription");
        const saveProductBtn = document.getElementById("saveProductBtn");

        const createProductModal = new bootstrap.Modal(document.getElementById('createProductModal'));
        const createProductBtn = document.querySelector(".create-product-btn");

        const newProductName = document.getElementById("newProductName");
        const newProductPrice = document.getElementById("newProductPrice");
        const newProductDescription = document.getElementById("newProductDescription");

        createProductBtn.addEventListener("click", () => {

            newProductName.value = "";
            newProductPrice.value = 0;
            newProductDescription.value = "";

            createProductModal.show();
        });

        document.getElementById("saveNewProductBtn").addEventListener("click", async () => {
            const productName = document.getElementById("newProductName").value;
            const productPrice = document.getElementById("newProductPrice").value;
            const productDescription = document.getElementById("newProductDescription").value;
            const productImage = document.getElementById("newProductImage").files[0];

            if (!productName || !productPrice || !productDescription || !productImage) {
                alert("Vui lòng điền đầy đủ thông tin!");
                return;
            }

            const formData = new FormData();
            formData.append("name", productName);
            formData.append("price", productPrice);
            formData.append("description", productDescription);
            formData.append("image", productImage);

            try {
                const response = await fetch("http://localhost:8080/products", {
                    method: "POST",
                    body: formData
                });

                if (response.ok) {
                    alert("Tạo sản phẩm thành công!");
                    createProductModal.hide();
                    await loadPage(currentPage);
                } else {
                    alert("Tạo sản phẩm thất bại.");
                }
            } catch (error) {
                console.error("Lỗi khi gửi yêu cầu tạo sản phẩm:", error);
                alert("Đã có lỗi xảy ra. Vui lòng thử lại.");
            }
        });

        async function renderTable(products) {
            tableBody.innerHTML = "";
            if (products.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="6" class="text-center">Không có sản phẩm nào</td></tr>`;
                return;
            }

            for (const product of products) {
                // let imageTag = '<span class="text-muted">Không có ảnh</span>';
                //
                // console.log(product.imagePath)
                //
                // if (product.imagePath) {
                //     const imageUrl = await getImageAsBlob(product.imagePath);
                //     if (imageUrl) {
                //         imageTag = `<img src="${imageUrl}" alt="Ảnh" width="60">`;
                //     }
                // }

                const row = `
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}₫</td>
                    <td class="description">${product.description}</td>
                    <td> 
                        <button data-id="${product.id}" class="details-product-btn btn btn-primary btn-sm">
                            <i class="fas fa-edit"></i> Chi tiết
                       </button>
                       <button data-id="${product.id}" class="update-product-btn btn btn-warning btn-sm">
                            <i class="fas fa-edit"></i> Cập nhật
                       </button>
                      
                    </td>
                </tr>
            `;
                tableBody.insertAdjacentHTML('beforeend', row);
            }
        }

        async function openDetailsModal(productId) {

            const response = await fetch(`http://localhost:8080/products/${productId}`);
            const product = await response.json();

            document.getElementById("productNameDetails").value = product.data.name;
            document.getElementById("productPriceDetails").value = product.data.price;
            document.getElementById("productDescriptionDetails").value = product.data.description;

            const imageUrl = await getImageAsBlob(product.data.imagePath);
            document.getElementById("productImage").src = imageUrl;

            const productDetailsModal = new bootstrap.Modal(document.getElementById('productDetailsModal'));
            productDetailsModal.show();
        }

        async function renderPagination(pageInfo) {
            paginationContainer.innerHTML = "";
            const { number, totalPages, first, last } = pageInfo;

            const prevBtn = document.createElement("button");
            prevBtn.className = "btn btn-sm btn-outline-primary";
            prevBtn.textContent = "Previous";
            prevBtn.disabled = first;
            prevBtn.addEventListener("click", async () => {
                if (!first) {
                    await loadPage(number - 1);
                }
            });
            paginationContainer.appendChild(prevBtn);

            for (let i = 0; i < totalPages; i++) {
                const pageBtn = document.createElement("button");
                pageBtn.className = `btn btn-sm ${i === number ? "btn-primary" : "btn-outline-primary"}`;
                pageBtn.textContent = i + 1;
                pageBtn.addEventListener("click", () => loadPage(i));
                paginationContainer.appendChild(pageBtn);
            }

            const nextBtn = document.createElement("button");
            nextBtn.className = "btn btn-sm btn-outline-primary";
            nextBtn.textContent = "Next";
            nextBtn.disabled = last;
            nextBtn.addEventListener("click", () => {
                if (!last) {
                    loadPage(number + 1);
                }
            });
            paginationContainer.appendChild(nextBtn);
        }

        async function loadPage(page) {
            const data = await fetchProductData(page);
            if (data) {
                renderTable(data.content);
                await renderPagination(data);
                currentPage = data.number;
            }
        }

        async function openUpdateModal(productId) {
            const response = await fetch(`http://localhost:8080/products/${productId}`);
            const product = await response.json()

            console.log(product.data.name)

            productNameInput.value = product.data.name;
            productPriceInput.value = product.data.price;
            productDescriptionInput.value = product.data.description;

            saveProductBtn.onclick = () => {

                const isConfirmed = window.confirm("Bạn có chắc chắn muốn thay đổi sản phẩm này?");
                if (isConfirmed) {
                    updateProduct(productId);
                } else {
                    // alert("Thao tác thay đổi đã bị hủy.");
                }
            };

            updateProductModal.show();
        }

        async function updateProduct(productId) {
            const productName = productNameInput.value;
            const productPrice = productPriceInput.value;
            const productDescription = productDescriptionInput.value;
            const productImageFile = document.getElementById("updateProductImage").files[0]; // Bạn cần thêm thẻ input type="file" trong modal cập nhật

            console.log("#######################")
            console.log(productImageFile);

            const formData = new FormData();
            formData.append("name", productName);
            formData.append("price", productPrice);
            formData.append("description", productDescription);

            if (productImageFile) {
                formData.append("image", productImageFile);
            }

            try {
                const response = await fetch(`http://localhost:8080/products/${productId}`, {
                    method: "PATCH",
                    body: formData
                });

                if (response.ok) {
                    updateProductModal.hide();
                    await loadPage(currentPage);
                    alert("Cập nhật sản phẩm thành công.");
                } else {
                    alert("Cập nhật sản phẩm thất bại.");
                }
            } catch (error) {
                console.error("Lỗi khi cập nhật sản phẩm:", error);
                alert("Đã có lỗi xảy ra khi cập nhật.");
            }
        }


        tableBody.addEventListener("click", function(e) {
            if (e.target && e.target.classList.contains("details-product-btn")) {
                const productId = e.target.getAttribute("data-id");
                openDetailsModal(productId);
            }
        });

        tableBody.addEventListener("click", function(e) {
            if (e.target && e.target.classList.contains("update-product-btn")) {
                const productId = e.target.getAttribute("data-id");
                openUpdateModal(productId);
            }
        });

        await loadPage(currentPage);
    });
