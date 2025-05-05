async function fetchProductData(page = 0, size = 8) {
    const apiEndpoint = `http://localhost:8080/products?page=${page}&size=${size}`;
    try {
        const response = await fetch(apiEndpoint, {
            method: "GET",
            headers: {
                Accept: "application/json"
            }
        });
        if (!response.ok) {
            console.error("Lỗi khi gọi API:", response.status);
            return null;
        }
        const result = await response.json();
        if (result.error || !result.data) {
            console.error("API trả về lỗi hoặc không có dữ liệu:", result);
            return null;
        }
        return result.data;
    } catch (error) {
        console.error("Lỗi:", error);
        return null;
    }
}

async function fetchAppointmentData(page = 0, size = 8) {
    const apiEndpoint = `http://localhost:8080/appointments?page=${page}&size=${size}`;
    try {
        const response = await fetch(apiEndpoint, {
            method: "GET",
            headers: {
                Accept: "application/json"
            }
        });

        if (!response.ok) {
            console.error("Lỗi khi gọi API:", response.status);
            return null;
        }

        const result = await response.json();
        return result.data;
    } catch (error) {
        console.error("Lỗi:", error);
        return null;
    }
}


async function getImageAsBlob(filename) {
    try {
        const response = await fetch(`http://localhost:8080/products/image?filename=${encodeURIComponent(filename)}`, {
            method: "GET",
        });

        if (!response.ok) {
            throw new Error("Không thể tải ảnh");
        }

        const blob = await response.blob();
        return URL.createObjectURL(blob); // Tạo URL tạm từ blob
    } catch (error) {
        console.error("Lỗi tải ảnh:", error);
        return null;
    }
}
