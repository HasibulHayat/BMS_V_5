document.addEventListener("DOMContentLoaded", () => {
  const btn = document.getElementById("createLeaseBtn");

  if (btn) {
    btn.addEventListener("click", () => {
      window.location.href = "./create-lease.html";
    });
  }
});
