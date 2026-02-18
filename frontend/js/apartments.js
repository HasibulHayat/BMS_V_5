document.addEventListener('DOMContentLoaded', () => {

  console.log('apartments.js loaded');

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const tableBody = document.getElementById('apartmentTableBody');
  const addBtn = document.getElementById('addApartmentBtn');

  console.log('Add button:', addBtn);

  if (addBtn) {
    addBtn.addEventListener('click', () => {
      window.location.href = '../pages/add-apartment.html';
    });
  }

  async function loadApartments() {
    try {
      const buildingRes = await fetch(`${API_BASE}/buildings`, {
        headers: {
          'Authorization': `Bearer ${TOKEN}`
        }
      });

      const buildings = await buildingRes.json();

      tableBody.innerHTML = '';

      for (const building of buildings) {
        await loadApartmentsByBuilding(building.id, building.name);
      }

    } catch (err) {
      console.error(err);
      // alert('Failed to load apartments');
    }
  }

  async function loadApartmentsByBuilding(buildingId, buildingName) {
    const res = await fetch(
      `${API_BASE}/apartments/building/${buildingId}`,
      {
        headers: {
          'Authorization': `Bearer ${TOKEN}`
        }
      }
    );

    if (!res.ok) return;

    const apartments = await res.json();

    apartments.forEach(apartment => {
      renderApartmentRow(apartment, buildingName);
    });
  }

  function renderApartmentRow(apartment, buildingName) {
    const tr = document.createElement('tr');

    tr.innerHTML = `
      <td>${apartment.name}</td>
      <td>${buildingName}</td>
      <td>${apartment.floorNumber ?? '-'}</td>
      <td>${apartment.totalRoom ?? '-'}</td>
      <td>
        <span class="badge bg-secondary">Unknown</span>
      </td>
      <td class="text-end">
        <a href="./edit-apartment.html?id=${apartment.id}"
           class="btn btn-sm btn-outline-primary">
          Edit
        </a>
      </td>
    `;

    tableBody.appendChild(tr);
  }

  loadApartments();
});
