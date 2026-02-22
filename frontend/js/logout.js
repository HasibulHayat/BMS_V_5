document.addEventListener("DOMContentLoaded", () => {
  const logoutBtn = document.getElementById("logoutBtn");
  if (!logoutBtn) return;

  logoutBtn.addEventListener("click", (e) => {
    e.preventDefault();

    // Clear everything related to auth
    localStorage.clear();

    // Redirect safely (prevents back navigation issues)
    window.location.replace("/auth/login.html");
  });
});