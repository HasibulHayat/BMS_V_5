import { loadCurrentUser } from "./current-user.js";

document.addEventListener("DOMContentLoaded", async () => {
  try {
    const user = await loadCurrentUser();
    if (!user) return;

    const fullName = `${user.firstName} ${user.lastName}`;
    const role = user.roles[0];

    // Navbar text
    const navName = document.getElementById("navUserName");
    const dropdownName = document.getElementById("dropdownUserName");
    const dropdownRole = document.getElementById("dropdownUserRole");

    if (navName) navName.textContent = fullName;
    if (dropdownName) dropdownName.textContent = fullName;
    if (dropdownRole) dropdownRole.textContent = role;

  } catch (err) {
    console.error("Failed to load user", err);
  }
});
