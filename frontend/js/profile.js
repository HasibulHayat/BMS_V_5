import { loadCurrentUser } from "./current-user.js";

document.addEventListener("DOMContentLoaded", async () => {
  const user = await loadCurrentUser();
  if (!user) return;

  const safe = (v) => (v === null || v === undefined || v === "" ? "-" : v);

  // -------------------------
  // Header / Summary
  // -------------------------
  document.getElementById("profileFullName").textContent =
    `${safe(user.firstName)} ${safe(user.lastName)}`;

  document.getElementById("profileRole").textContent =
    safe(user.roles?.join(", "));

  document.getElementById("profileUsername").textContent =
    safe(user.username);

  document.getElementById("profilePhone").textContent =
    safe(user.phone);

  document.getElementById("profileEmail").textContent =
    safe(user.email);

  // -------------------------
  // Personal Info
  // -------------------------
  document.getElementById("profileFirstName").textContent =
    safe(user.firstName);

  document.getElementById("profileLastName").textContent =
    safe(user.lastName);

  document.getElementById("profileGender").textContent =
    formatGender(user.gender);

  document.getElementById("profileOccupation").textContent =
    safe(user.occupation);

  document.getElementById("profileBloodGroup").textContent =
    formatBloodGroup(user.bloodGroup);

  document.getElementById("profileBirthDate").textContent =
    safe(user.birthDate);

  document.getElementById("profileStatus").textContent =
    user.isActive ? "Active" : "Inactive";
});

// -------------------------
// Helpers
// -------------------------
function formatGender(gender) {
  if (!gender) return "-";
  return gender.charAt(0) + gender.slice(1).toLowerCase();
}

function formatBloodGroup(bg) {
  if (!bg) return "-";
  return bg.replace("_POS", "+").replace("_NEG", "-");
}
