document.addEventListener("DOMContentLoaded", () => {

  const token = localStorage.getItem("accessToken");
  if (!token) return;

  const payload = JSON.parse(atob(token.split('.')[1]));
  const roles = payload.roles || [];

  if (!roles.includes("SUPER_ADMIN")) {
    window.location.replace("../pages/building.html");
  }

});