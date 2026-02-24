document.addEventListener("DOMContentLoaded", async () => {

  const tableBody = document.getElementById("buildingTableBody");
  const addBuildingBtn = document.getElementById("addBuildingBtn");
  const TOKEN = localStorage.getItem("accessToken");

  const API_URL = "http://localhost:8080/api/v1/admin/buildings";

  if (!TOKEN) return;

  let currentRoles = [];

  // 🔹 Load current user roles
  async function loadCurrentUser() {
    try {
      const res = await fetch("http://localhost:8080/api/v1/auth/me", {
        headers: { Authorization: `Bearer ${TOKEN}` }
      });

      if (!res.ok) return;

      const me = await res.json();
      currentRoles = me.roles || [];

      applyRolePermissions();

    } catch (err) {
      console.error("Failed to load current user", err);
    }
  }

  // 🔹 Role-based UI
  function applyRolePermissions() {
    if (currentRoles.includes("SUPER_ADMIN")) {
      addBuildingBtn?.classList.remove("d-none");
    }
  }

  addBuildingBtn?.addEventListener("click", () => {
    window.location.href = "../pages/add-building.html";
  });

  // 🔹 Load buildings
  async function loadBuildings() {
    try {
      const res = await fetch(API_URL, {
        headers: { Authorization: `Bearer ${TOKEN}` }
      });

      if (!res.ok) throw new Error("Failed to load buildings");

      const buildings = await res.json();
      renderBuildings(buildings);

    } catch (err) {
      console.error(err);
      renderEmptyState();
    }
  }

  function renderBuildings(data) {
    tableBody.innerHTML = "";

    if (!data || data.length === 0) {
      renderEmptyState();
      return;
    }

    data.forEach(b => {

      const canEdit =
        currentRoles.includes("SUPER_ADMIN") ||
        currentRoles.includes("ADMIN");

      const canView =
        currentRoles.includes("SUPER_ADMIN") ||
        currentRoles.includes("ADMIN") ||
        currentRoles.includes("OWNER");

      const buildingTypeBadge = b.buildingType
        ? `<span class="badge bg-success-subtle text-dark">${b.buildingType}</span>`
        : "";

      const tr = document.createElement("tr");

      tr.innerHTML = `
        <!-- BUILDING COLUMN -->
        <td>
          <div class="fw-semibold">${b.name}</div>

          <div class="mt-2">
            ${buildingTypeBadge}
          </div>
        </td>

        <!-- ADDRESS COLUMN -->
        <td>
          <div class="">
            <div>${b.street || ""}</div>
            <div>${b.area || ""}</div>
            <div>
              ${b.city || ""}${b.city && b.district ? ", " : ""}${b.district || ""}
            </div>
            <div>
              ${b.country || ""}${b.postalCode ? " - " + b.postalCode : ""}
            </div>
          </div>
        </td>

        <!-- FEATURES COLUMN -->
        <td>
          <div class="">
            <div>${b.totalFloor ?? "-"} Floors</div>
            <div>${b.totalUnit ?? "-"} Units</div>
            <div class="text-muted">${b.ownerAssociationName || "-"}</div>
          </div>
        </td>

        <!-- ACTIONS COLUMN -->
        <td class="text-end">
          ${
            canView
              ? `<button class="btn btn-sm btn-outline-success me-2"
                    onclick="viewBuilding('${b.id}')">
                    <i class="bi bi-eye"></i>
                </button>`
              : ""
          }

          ${
            canEdit
              ? `<button class="btn btn-sm btn-outline-primary"
                    onclick="editBuilding('${b.id}')">
                    <i class="bi bi-pencil"></i>
                </button>`
              : ""
          }
        </td>
      `;

      tableBody.appendChild(tr);
    });
  }

  function renderEmptyState() {
    tableBody.innerHTML = `
      <tr>
        <td colspan="4" class="text-center py-5">
          <div class="text-muted">
            <i class="bi bi-building fs-2 d-block mb-3"></i>
            <div class="fw-semibold">No buildings found</div>
            <div class="small">Start by adding your first building.</div>
          </div>
        </td>
      </tr>
    `;
  }

  await loadCurrentUser();
  await loadBuildings();

});

// 🔹 Global functions
function editBuilding(id) {
  window.location.href = `../pages/edit-building.html?id=${id}`;
}

function viewBuilding(id) {
  window.location.href = `../pages/view-building.html?id=${id}`;
}