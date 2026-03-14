document.addEventListener("DOMContentLoaded", async () => {

  const TOKEN = localStorage.getItem("accessToken");
  if (!TOKEN) return;

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id) return;

  const API_URL = `http://localhost:8080/api/v1/admin/apartments/${id}`;

  try {
    const res = await fetch(API_URL, {
      headers: { Authorization: `Bearer ${TOKEN}` }
    });

    if (!res.ok) throw new Error();

    const a = await res.json();

    renderHero(a);
    renderStructure(a);
    renderAdditional(a);

  } catch (err) {
    console.error("Failed to load apartment", err);
  }

});


function value(v) {
  return v !== null && v !== undefined && v !== "" ? v : "-";
}


function renderHero(a) {

  document.getElementById("apartmentHero").innerHTML = `
    <div class="hero-building">

      <h3 class="fw-bold mb-1">${value(a.name)}</h3>
      <small>Floor ${value(a.floorNumber)} · Sector ${value(a.sectorName)}</small>

      <hr class="border-light">

      <div class="row mt-3">
        <div class="col-md-6">
          <strong>Building:</strong> ${value(a.buildingName)}
        </div>
        <div class="col-md-6">
          <strong>Area:</strong> ${value(a.areaSqFt)} SqFt
        </div>
      </div>

    </div>
  `;
}


function renderStructure(a) {

  document.getElementById("structureInfo").innerHTML = `
    <div class="row text-center">

      <div class="col-md-3 mb-3">
        <h3 class="text-primary">${value(a.totalRoom)}</h3>
        <div class="text-muted small">Total Rooms</div>
      </div>

      <div class="col-md-3 mb-3">
        <h3 class="text-success">${value(a.totalBedroom)}</h3>
        <div class="text-muted small">Bedrooms</div>
      </div>

      <div class="col-md-3 mb-3">
        <h3 class="text-warning">${value(a.totalBathroom)}</h3>
        <div class="text-muted small">Bathrooms</div>
      </div>

      <div class="col-md-3 mb-3">
        <h3 class="text-info">${value(a.totalBalconies)}</h3>
        <div class="text-muted small">Balconies</div>
      </div>

    </div>
  `;
}


function renderAdditional(a) {

  document.getElementById("additionalInfo").innerHTML = `
    <div class="row mb-3">

      <div class="col-md-6">
        <strong>Parking Spot:</strong><br>
        ${value(a.parkingSpotNumber)}
      </div>

      <div class="col-md-6">
        <strong>Landphone Extension:</strong><br>
        ${value(a.landphoneExtension)}
      </div>

    </div>

    <div class="row">
      <div class="col-12">
        <strong>Notes:</strong><br>
        ${value(a.notes)}
      </div>
    </div>
  `;
}