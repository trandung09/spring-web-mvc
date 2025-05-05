document.addEventListener("DOMContentLoaded", async () => {
    const tableBody = document.getElementById("table-body");
    const paginationContainer = document.getElementById("pagination");

    let currentPage = 0;
    const pageSize = 8;

    async function renderTable(appointments) {
        tableBody.innerHTML = "";
        if (appointments.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7" class="text-center">Không có lịch hẹn nào</td></tr>`;
            return;
        }

        for (const appointment of appointments) {
            const row = `
                <tr>
                    <td>${appointment.id}</td>
                    <td>${appointment.bookerName}</td>
                    <td>${appointment.bookerPhoneNumber}</td>
                    <td>${appointment.bookerEmail}</td>
                    <td>${appointment.status}</td>
                    <td>${appointment.createdAt}</td>
                    <td> 
                        <button data-id="${appointment.id}" class="view-appointment-btn btn btn-primary btn-sm">
                            <i class="fas fa-eye"></i> Xem chi tiết
                        </button>
                        <button data-id="${appointment.id}" class="change-status-btn btn btn-warning btn-sm" ${appointment.status === 'APPROVED' || appointment.status === 'REJECTED' ? 'disabled' : ''}>
                            <i class="fas fa-sync-alt"></i> Thay đổi trạng thái
                        </button>
                    </td>
                </tr>
                `;
            tableBody.insertAdjacentHTML('beforeend', row);
        }
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
        const data = await fetchAppointmentData(page);
        if (data) {
            await renderTable(data.content);
            await renderPagination(data);
            currentPage = data.number;
        }
    }

    async function openAppointmentDetailsModal(appointmentId) {
        try {
            const response = await fetch(`http://localhost:8080/appointments/${appointmentId}`);
            if (!response.ok) {
                throw new Error('Không thể tải thông tin lịch hẹn');
            }
            const appointment = await response.json();



            document.getElementById("bookerName").value = appointment.data.bookerName;
            document.getElementById("bookerPhoneNumber").value = appointment.data.bookerPhoneNumber;
            document.getElementById("bookerEmail").value = appointment.data.bookerEmail;
            document.getElementById("content").value = appointment.data.content;
            document.getElementById("status").value = appointment.data.status;
            document.getElementById("createdAt").value = appointment.data.createdAt;
            document.getElementById("updatedAt").value = appointment.data.updatedAt;

            const appointmentDetailsModal = new bootstrap.Modal(document.getElementById('appointmentDetailsModal'));
            appointmentDetailsModal.show();
        } catch (error) {
            console.error("Lỗi khi mở modal chi tiết lịch hẹn:", error);
            alert("Đã xảy ra lỗi khi tải chi tiết lịch hẹn.");
        }
    }

    // document.getElementById("table-body").addEventListener("click", function (e) {
    //     if (e.target && e.target.classList.contains("view-appointment-btn")) {
    //         const appointmentId = e.target.getAttribute("data-id");
    //         openAppointmentDetailsModal(appointmentId);
    //     }
    // });

    let selectedAppointmentId = null;

    function handleChangeStatusClick(appointmentId) {
        selectedAppointmentId = appointmentId;
        const changeStatusModal = new bootstrap.Modal(document.getElementById('changeStatusModal'));
        changeStatusModal.show();
    }

    async function confirmStatusChange() {
        const selectedValue = document.querySelector('input[name="newStatus"]:checked').value;

        console.log("#################");
        console.log(selectedValue);
        console.log("#################");

        const status = selectedValue === "REJECTED" ? "REJECTED" : "APPROVED";

        try {
            const response = await fetch(`http://localhost:8080/appointments/${selectedAppointmentId}?status=${status}`, {
                method: "PATCH",
            });

            if (!response.ok) {
                throw new Error("Không thể cập nhật trạng thái.");
            }

            bootstrap.Modal.getInstance(document.getElementById('changeStatusModal')).hide();
            await loadPage(currentPage);
            alert("Cập nhật trạng thái thành công!");
        } catch (error) {
            console.error("Lỗi cập nhật trạng thái:", error);
            alert("Đã xảy ra lỗi khi cập nhật trạng thái.");
        }
    }



    document.getElementById("table-body").addEventListener("click", function (e) {
        const viewBtn = e.target.closest(".view-appointment-btn");
        const changeBtn = e.target.closest(".change-status-btn");

        if (viewBtn) {
            const appointmentId = viewBtn.getAttribute("data-id");
            openAppointmentDetailsModal(appointmentId);
        }

        if (changeBtn) {
            const appointmentId = changeBtn.getAttribute("data-id");
            handleChangeStatusClick(appointmentId);
        }
    });

    document.getElementById("confirmChangeStatusBtn").addEventListener("click", confirmStatusChange);

    await loadPage(currentPage);
});
