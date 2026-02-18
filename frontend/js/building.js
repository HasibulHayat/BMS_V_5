document.addEventListener("DOMContentLoaded", async () => {
  const tableBody = document.getElementById("buildingTableBody");
  const addBuildingBtn = document.getElementById("addBuildingBtn");
  const TOKEN = localStorage.getItem("accessToken");
  const API_URL = "http://localhost:8080/api/v1/admin/buildings";

  addBuildingBtn.addEventListener("click", () => {
    window.location.href = "../pages/add-building.html";
  });

  async function loadBuildings() {
    try {
      const res = await fetch(API_URL, {
        headers: {
          Authorization: `Bearer ${TOKEN}`
        }
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
      const tr = document.createElement("tr");

      tr.innerHTML = `
        <td>${b.name}</td>
        <td>-</td>
        <td>0</td>
        <td>0</td>
        <td class="text-end">
          <button class="btn btn-sm btn-outline-primary me-2"
                  onclick="editBuilding('${b.id}')">
            <i class="bi bi-pencil"></i>
          </button>
        </td>
      `;

      tableBody.appendChild(tr);
    });
  }

  function renderEmptyState() {
    tableBody.innerHTML = `
      <tr>
        <td colspan="5" class="text-center py-4 text-muted">
          No buildings found. Please create a building to continue.
        </td>
      </tr>
    `;
  }

  loadBuildings();
});

// global
function editBuilding(id) {
  window.location.href = `../pages/edit-building.html?id=${id}`;
}
