document.addEventListener("DOMContentLoaded", async () => {

  const TOKEN = localStorage.getItem("accessToken");
  if (!TOKEN) return;

  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");
  if (!id) return;

  try {
    const res = await fetch(
      `http://localhost:8080/api/v1/admin/buildings/${id}`,
      { headers: { Authorization: `Bearer ${TOKEN}` } }
    );

    if (!res.ok) throw new Error();

    const b = await res.json();

    renderBasic(b);
    renderLocation(b);
    renderStructure(b);
    renderFacilities(b);
    renderAdditional(b);

  } catch (err) {
    console.error("Failed to load building", err);
  }

});


function value(v) {
  return v !== null && v !== undefined && v !== "" ? v : "-";
}

function badgeBoolean(val) {
  if (val === true) return `<span class="badge bg-success">Yes</span>`;
  if (val === false) return `<span class="badge bg-danger">No</span>`;
  return "-";
}


/* =========================
   BASIC (NO DESCRIPTION)
========================= */
function renderBasic(b) {

document.getElementById("basicInfo").innerHTML = `
  <div class="hero-building">

    <h3 class="fw-bold mb-1">${value(b.name)}</h3>
    <small>${value(b.buildingType)}</small>

    <hr class="border-light">

    <div class="row mt-3">
      <div class="col-md-6">
        <strong>Building Code:</strong> ${value(b.buildingCode)}
      </div>
      <div class="col-md-6">
        <strong>Registration #:</strong> ${value(b.registrationNumber)}
      </div>
    </div>

  </div>
`;
}


/* =========================
   LOCATION (WITH DESCRIPTION)
========================= */
function renderLocation(b) {

document.getElementById("locationInfo").innerHTML = `
  <div class="hero-building">

    <h4 class="fw-bold mb-3">
      <i class="bi bi-geo-alt me-2"></i>
      Location
    </h4>

    <div class="row">

      <div class="col-md-6 mb-3">
        <strong>Address</strong><br>
        ${value(b.street)}, ${value(b.area)}<br>
        ${value(b.city)}, ${value(b.district)}<br>
        ${value(b.country)} - ${value(b.postalCode)}
      </div>

      <div class="col-md-6 mb-3">
        <strong>Description</strong><br>
        ${value(b.description)}
      </div>

    </div>

  </div>
`;
}


/* =========================
   STRUCTURE (UNCHANGED)
========================= */
function renderStructure(b) {

document.getElementById("structureInfo").innerHTML = `

  <!-- PRIMARY METRICS -->
  <div class="row text-center mb-4">

    <div class="col-md-3 mb-3">
      <div class="structure-metric metric-blue">
        <div class="metric-icon"><i class="bi bi-building"></i></div>
        <h3>${value(b.totalFloor)}</h3>
        <small>Total Floors</small>
      </div>
    </div>

    <div class="col-md-3 mb-3">
      <div class="structure-metric metric-green">
        <div class="metric-icon"><i class="bi bi-door-open"></i></div>
        <h3>${value(b.totalUnit)}</h3>
        <small>Total Units</small>
      </div>
    </div>

    <div class="col-md-3 mb-3">
      <div class="structure-metric metric-purple">
        <div class="metric-icon"><i class="bi bi-layers"></i></div>
        <h3>${value(b.basementFloor)}</h3>
        <small>Basement Floors</small>
      </div>
    </div>

    <div class="col-md-3 mb-3">
      <div class="structure-metric metric-orange">
        <div class="metric-icon"><i class="bi bi-arrow-up-square"></i></div>
        <h3>${value(b.elevatorCount)}</h3>
        <small>Elevators</small>
      </div>
    </div>

  </div>


  <!-- CONSTRUCTION TIMELINE -->
  <div class="construction-box mb-4 text-center">

    <div class="row">
      <div class="col-md-4">
        <div class="timeline-item">
          <i class="bi bi-calendar-event"></i>
          <div>
            <small>Construction Start</small>
            <div class="fw-bold">${value(b.constructionStart)}</div>
          </div>
        </div>
      </div>

      <div class="col-md-4">
        <div class="timeline-item">
          <i class="bi bi-calendar-check"></i>
          <div>
            <small>Construction End</small>
            <div class="fw-bold">${value(b.constructionEnd)}</div>
          </div>
        </div>
      </div>

      <div class="col-md-4">
        <div class="timeline-item">
          <i class="bi bi-car-front-fill"></i>
          <div>
            <small>Total Parking</small>
            <div class="fw-bold">${value(b.totalParking)}</div>
          </div>
        </div>
      </div>
    </div>

  </div>


  <!-- AREA DETAILS -->
<div class="area-box">
  <div class="row text-center">

    <div class="col-md-4 mb-3">
      <div class="area-item area-land">
        <i class="bi bi-aspect-ratio"></i>
        <div>
          <small>Land Area (SqFt)</small>
          <div class="fw-bold">${value(b.landAreaSqFt)}</div>
        </div>
      </div>
    </div>

    <div class="col-md-4 mb-3">
      <div class="area-item area-floor">
        <i class="bi bi-grid"></i>
        <div>
          <small>Floor Area (SqFt)</small>
          <div class="fw-bold">${value(b.floorAreaSqFt)}</div>
        </div>
      </div>
    </div>

    <div class="col-md-4 mb-3">
      <div class="area-item area-unit">
        <i class="bi bi-bounding-box"></i>
        <div>
          <small>Unit Area (SqFt)</small>
          <div class="fw-bold">${value(b.unitAreaSqFt)}</div>
        </div>
      </div>
    </div>

  </div>
</div>

`;
}


/* =========================
   FACILITIES (ONLY FACILITIES)
========================= */
function renderFacilities(b) {

document.getElementById("facilityInfo").innerHTML = `
  <div class="row text-center">

    <div class="col-md-3 mb-4">
      <div class="facility-icon text-warning"><i class="bi bi-lightning-fill"></i></div>
      <div>Generator</div>
      ${badgeBoolean(b.hasGenerator)}
    </div>

    <div class="col-md-3 mb-4">
      <div class="facility-icon text-success"><i class="bi bi-shield-check"></i></div>
      <div>Guard</div>
      ${badgeBoolean(b.hasGuard)}
    </div>

    <div class="col-md-3 mb-4">
      <div class="facility-icon text-danger"><i class="bi bi-camera-video-fill"></i></div>
      <div>CCTV</div>
      ${badgeBoolean(b.hasCCTV)}
    </div>

    <div class="col-md-3 mb-4">
      <div class="facility-icon text-primary"><i class="bi bi-droplet-fill"></i></div>
      <div>Water</div>
      ${value(b.waterSource)}
    </div>

  </div>
`;
}


/* =========================
   ADDITIONAL INFO
========================= */
function renderAdditional(b) {

  document.getElementById("additionalInfo").innerHTML = `

    <div class="row mb-3">

      <div class="col-md-6">
        <strong>Developer</strong><br>
        ${value(b.developerName)}
      </div>

      <div class="col-md-6">
        <strong>Owner Association</strong><br>
        ${value(b.ownerAssociationName)}
      </div>

    </div>
  `;
}