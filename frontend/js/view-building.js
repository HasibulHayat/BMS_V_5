document.addEventListener("DOMContentLoaded", async () => {

  const TOKEN = localStorage.getItem("accessToken");
  if (!TOKEN) return;

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id) return;

  const API_URL = `http://localhost:8080/api/v1/admin/buildings/${id}`;

  try {
    const res = await fetch(API_URL, {
      headers: { Authorization: `Bearer ${TOKEN}` }
    });

    if (!res.ok) throw new Error();

    const b = await res.json();

    renderBasic(b);
    renderLocation(b);
    renderStructure(b);
    renderFacilities(b);

  } catch (err) {
    console.error("Failed to load building", err);
  }

});


function value(v) {
  return v !== null && v !== undefined && v !== "" ? v : "-";
}


function renderBasic(b) {

  document.getElementById("basicInfo").innerHTML = `
    <div class="row mb-3">
      <div class="col-md-4"><strong>Name:</strong><br>${value(b.name)}</div>
      <div class="col-md-4"><strong>Building Code:</strong><br>${value(b.buildingCode)}</div>
      <div class="col-md-4"><strong>Registration Number:</strong><br>${value(b.registrationNumber)}</div>
    </div>

    <div class="row mb-3">
      <div class="col-md-4"><strong>Type:</strong><br>${value(b.buildingType)}</div>
      <div class="col-md-8"><strong>Description:</strong><br>${value(b.description)}</div>
    </div>
  `;
}


function renderLocation(b) {

  document.getElementById("locationInfo").innerHTML = `
    <div class="row mb-3">
      <div class="col-md-6">
        <strong>Address:</strong><br>
        ${value(b.street)}, ${value(b.area)}<br>
        ${value(b.city)}, ${value(b.district)}<br>
        ${value(b.country)} - ${value(b.postalCode)}
      </div>

      <div class="col-md-3">
        <strong>Latitude:</strong><br>${value(b.latitude)}
      </div>

      <div class="col-md-3">
        <strong>Longitude:</strong><br>${value(b.longitude)}
      </div>
    </div>
  `;
}


function renderStructure(b) {

  document.getElementById("structureInfo").innerHTML = `
    <div class="row mb-3">
      <div class="col-md-3"><strong>Total Floors:</strong><br>${value(b.totalFloor)}</div>
      <div class="col-md-3"><strong>Basement Floors:</strong><br>${value(b.basementFloor)}</div>
      <div class="col-md-3"><strong>Total Units:</strong><br>${value(b.totalUnit)}</div>
      <div class="col-md-3"><strong>Elevators:</strong><br>${value(b.elevatorCount)}</div>
    </div>

    <div class="row mb-3">
      <div class="col-md-4"><strong>Construction Start:</strong><br>${value(b.constructionStart)}</div>
      <div class="col-md-4"><strong>Construction End:</strong><br>${value(b.constructionEnd)}</div>
      <div class="col-md-4"><strong>Total Parking:</strong><br>${value(b.totalParking)}</div>
    </div>
  `;
}


function renderFacilities(b) {

  document.getElementById("facilityInfo").innerHTML = `
    <div class="row mb-3">
      <div class="col-md-3"><strong>Generator:</strong><br>${value(b.hasGenerator)}</div>
      <div class="col-md-3"><strong>Guard:</strong><br>${value(b.hasGuard)}</div>
      <div class="col-md-3"><strong>CCTV:</strong><br>${value(b.hasCCTV)}</div>
      <div class="col-md-3"><strong>Water Source:</strong><br>${value(b.waterSource)}</div>
    </div>

    <div class="row mb-3">
      <div class="col-md-4"><strong>Land Area (SqFt):</strong><br>${value(b.landAreaSqFt)}</div>
      <div class="col-md-4"><strong>Floor Area (SqFt):</strong><br>${value(b.floorAreaSqFt)}</div>
      <div class="col-md-4"><strong>Unit Area (SqFt):</strong><br>${value(b.unitAreaSqFt)}</div>
    </div>

    <div class="row mb-3">
      <div class="col-md-6"><strong>Developer:</strong><br>${value(b.developerName)}</div>
      <div class="col-md-6"><strong>Owner Association:</strong><br>${value(b.ownerAssociationName)}</div>
    </div>

    <div class="row">
      <div class="col-12">
        <strong>Notes:</strong><br>${value(b.notes)}
      </div>
    </div>
  `;
}