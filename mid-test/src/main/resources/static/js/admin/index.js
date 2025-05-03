document.addEventListener("DOMContentLoaded", function () {
    const btn = document.getElementById('btn-toggle');
    const sidebar = document.getElementById('sidebar');

    btn.addEventListener('click', () => {
        sidebar.classList.toggle('collapsed');
    });
});