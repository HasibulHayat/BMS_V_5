document.addEventListener('DOMContentLoaded', () => {

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const form = document.getElementById('addApartmentForm');
  const buildingSelect = document.getElementById('buildingId');
  const floorSelect = document.getElementById('floorNumber');
  const sectorSelect = document.getElementById('sectorName');

  if (!form || !buildingSelect) return;

  // -----------------------------
  // LOAD BUILDINGS
  // -----------------------------
  async function loadBuildings() {
    try {
      const res = await fetch(`${API_BASE}/buildings`, {
        headers: {
          Authorization: `Bearer ${TOKEN}`
        }
      });

      if (!res.ok) throw new Error();

      const buildings = await res.json();

      buildings.forEach(b => {
        const option = document.createElement('option');
        option.value = b.id;
        option.textContent = `${b.name} (${b.buildingCode})`;
        buildingSelect.appendChild(option);
      });

    } catch (err) {
      console.error('Failed to load buildings');
    }
  }

  // -----------------------------
  // FORM SUBMIT
  // -----------------------------
  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    if (!buildingSelect.value) {
      alert("Please select a building");
      return;
    }

    const floorNumber = floorSelect.value
      ? Number(floorSelect.value)
      : null;

    const sectorName = sectorSelect.value || null;

    // Generate apartment name automatically
    let generatedName = null;

    if (floorNumber && sectorName) {
      generatedName = `${floorNumber} / ${sectorName}`;
    } else {
      alert("Floor and Sector are required to generate apartment name");
      return;
    }

    const payload = {
      buildingId: buildingSelect.value,
      name: generatedName,
      floorNumber: floorNumber,
      sectorName: sectorName,
      totalRoom: document.getElementById('totalRoom').value
        ? Number(document.getElementById('totalRoom').value)
        : null,
      totalBedroom: document.getElementById('totalBedroom').value
        ? Number(document.getElementById('totalBedroom').value)
        : null,
      totalBathroom: document.getElementById('totalBathroom').value
        ? Number(document.getElementById('totalBathroom').value)
        : null,
      totalBalconies: document.getElementById('totalBalconies').value
        ? Number(document.getElementById('totalBalconies').value)
        : null,
      areaSqFt: document.getElementById('areaSqFt').value
        ? Number(document.getElementById('areaSqFt').value)
        : null,
      parkingSpotNumber: document.getElementById('parkingSpotNumber').value
        ? Number(document.getElementById('parkingSpotNumber').value)
        : null,
      landphoneExtension: document.getElementById('landphoneExtension').value.trim() || null,
      notes: document.getElementById('notes').value.trim() || null
    };

    console.log('Creating apartment:', payload);

    try {
      const res = await fetch(`${API_BASE}/apartments`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${TOKEN}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const err = await res.json();
        throw new Error(err.message || 'Create apartment failed');
      }

      window.location.href = './apartments.html';

    } catch (err) {
      console.error(err);
      alert(err.message);
    }
  });

  loadBuildings();

});