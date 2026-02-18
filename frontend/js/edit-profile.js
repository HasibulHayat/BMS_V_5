import { loadCurrentUser } from "./current-user.js";

document.addEventListener("DOMContentLoaded", async () => {

  const form = document.getElementById("editProfileForm");
  if (!form) return;

  const user = await loadCurrentUser();
  if (!user) return;

  const $ = (id) => document.getElementById(id);

  // -------------------------
  // PREFILL FORM
  // -------------------------
  $("firstName").value = user.firstName ?? "";
  $("middleName").value = user.middleName ?? "";
  $("lastName").value = user.lastName ?? "";

  $("phonePrimary").value = user.phone ?? "";
  $("phoneSecondary").value = user.phoneSecondary ?? "";

  $("gender").value = user.gender ?? "";
  $("bloodGroup").value = user.bloodGroup ?? "";

  $("birthDate").value = user.birthDate ?? "";
  $("isMarried").value =
    user.isMarried === null || user.isMarried === undefined
      ? ""
      : user.isMarried.toString();

  $("permanentAddress").value = user.permanentAddress ?? "";
  $("occupation").value = user.occupation ?? "";

  $("nationalId").value = user.nationalId ?? "";
  $("passportId").value = user.passportId ?? "";
  $("drivingLicenseId").value = user.drivingLicenseId ?? "";

  $("emergencyContactName").value = user.emergencyContactName ?? "";
  $("emergencyContactPhone").value = user.emergencyContactPhone ?? "";

  $("notes").value = user.notes ?? "";

  // -------------------------
  // SUBMIT UPDATED PROFILE
  // -------------------------
  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
      firstName: $("firstName").value || null,
      middleName: $("middleName").value || null,
      lastName: $("lastName").value || null,

      phoneSecondary: $("phoneSecondary").value || null,

      gender: $("gender").value || null,
      bloodGroup: $("bloodGroup").value || null,

      birthDate: $("birthDate").value || null,
      isMarried:
        $("isMarried").value === ""
          ? null
          : $("isMarried").value === "true",

      permanentAddress: $("permanentAddress").value || null,
      occupation: $("occupation").value || null,

      nationalId: $("nationalId").value || null,
      passportId: $("passportId").value || null,
      drivingLicenseId: $("drivingLicenseId").value || null,

      emergencyContactName: $("emergencyContactName").value || null,
      emergencyContactPhone: $("emergencyContactPhone").value || null,

      notes: $("notes").value || null
    };

    try {
      const token = localStorage.getItem("accessToken");

      const res = await fetch("http://localhost:8080/api/v1/users/me", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) throw new Error("Update failed");

      window.location.href = "../pages/profile.html";

    } catch (err) {
      console.error(err);
      alert("Failed to update profile");
    }
  });
});
