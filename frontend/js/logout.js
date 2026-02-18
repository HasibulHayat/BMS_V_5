document.addEventListener("DOMContentLoaded", () => {
  const logoutBtn = document.getElementById("logoutBtn");
  if (!logoutBtn) return;

  logoutBtn.addEventListener("click", (e) => {
    e.preventDefault();

    // 1️⃣ Remove auth data
    localStorage.removeItem("accessToken");

    // (optional but good)
    localStorage.removeItem("currentUser");

    // 2️⃣ Redirect to login
    window.location.href = "../auth/login.html";
  });
});
