document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("addBuildingForm");
  const TOKEN = localStorage.getItem("accessToken");
  const API_URL = "http://localhost:8080/api/v1/admin/buildings";

  const nameInput = document.getElementById("name");
  const buildingCodeInput = document.getElementById("buildingCode");
  const registrationNumberInput = document.getElementById("registrationNumber");
  const buildingTypeInput = document.getElementById("buildingType");
  const cityInput = document.getElementById("city");
  const countryInput = document.getElementById("country");
  const descriptionInput = document.getElementById("description");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
      name: nameInput.value.trim(),
      buildingCode: buildingCodeInput.value.trim(),
      registrationNumber: registrationNumberInput.value.trim(),
      buildingType: buildingTypeInput.value.trim(),
      city: cityInput.value.trim(),
      country: countryInput.value.trim(),
      description: descriptionInput.value.trim()
    };

    console.log("Submitting building:", payload);

    try {
      const res = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${TOKEN}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const err = await res.json();
        throw new Error(err.message || "Building creation failed");
      }

      window.location.href = "../pages/building.html";

    } catch (err) {
      console.error(err);
      alert(err.message);
    }
  });
});
