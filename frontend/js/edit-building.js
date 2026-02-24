document.addEventListener("DOMContentLoaded", async () => {

  const form = document.getElementById("editBuildingForm");
  if (!form) return;

  const token = localStorage.getItem("accessToken");
  if (!token) return;

  const params = new URLSearchParams(window.location.search);
  const buildingId = params.get("id");
  if (!buildingId) return;

  const $ = (id) => document.getElementById(id);

  const toNumber = (val) => val ? Number(val) : null;
  const toBoolean = (val) => val === "" ? null : val === "true";

  try {

    const res = await fetch(
      `http://localhost:8080/api/v1/admin/buildings/${buildingId}`,
      {
        headers: { "Authorization": `Bearer ${token}` }
      }
    );

    if (!res.ok) throw new Error("Failed to load building");

    const b = await res.json();

    Object.keys(b).forEach(key => {
      if ($(key)) {
        if (typeof b[key] === "boolean") {
          $(key).value = b[key].toString();
        } else if (b[key] !== null) {
          $(key).value = b[key];
        }
      }
    });

  } catch (err) {
    console.error(err);
    alert("Failed to load building");
    window.location.href = "../pages/building.html";
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
      name: $("name").value || null,
      buildingType: $("buildingType").value || null,
      buildingCode: $("buildingCode").value || null,
      registrationNumber: $("registrationNumber").value || null,
      description: $("description").value || null,
      street: $("street").value || null,
      area: $("area").value || null,
      city: $("city").value || null,
      district: $("district").value || null,
      country: $("country").value || null,
      postalCode: $("postalCode").value || null,
      latitude: toNumber($("latitude").value),
      longitude: toNumber($("longitude").value),
      totalFloor: toNumber($("totalFloor").value),
      basementFloor: toNumber($("basementFloor").value),
      totalUnit: toNumber($("totalUnit").value),
      elevatorCount: toNumber($("elevatorCount").value),
      totalParking: toNumber($("totalParking").value),
      waterSource: $("waterSource").value || null,
      developerName: $("developerName").value || null,
      hasGenerator: toBoolean($("hasGenerator").value),
      hasGuard: toBoolean($("hasGuard").value),
      hasCCTV: toBoolean($("hasCCTV").value),
      landAreaSqFt: toNumber($("landAreaSqFt").value),
      floorAreaSqFt: toNumber($("floorAreaSqFt").value),
      unitAreaSqFt: toNumber($("unitAreaSqFt").value),
      constructionStart: $("constructionStart").value || null,
      constructionEnd: $("constructionEnd").value || null,
      ownerAssociationName: $("ownerAssociationName").value || null,
      notes: $("notes").value || null
    };

    try {

      const res = await fetch(
        `http://localhost:8080/api/v1/admin/buildings/${buildingId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },
          body: JSON.stringify(payload)
        }
      );

      if (!res.ok) {
        const errData = await res.json();
        throw new Error(errData.message || "Update failed");
      }

      window.location.href = "../pages/building.html";

    } catch (err) {
      console.error(err);
      alert(err.message);
    }

  });

});