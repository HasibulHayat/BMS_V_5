document.addEventListener('DOMContentLoaded', async () => {

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const form = document.getElementById('editApartmentForm');
  if (!form) return;

  const params = new URLSearchParams(window.location.search);
  const apartmentId = params.get('id');
  if (!apartmentId) return;

  const $ = (id) => document.getElementById(id);

  // Populate floors (1–50)
  for (let i = 1; i <= 50; i++) {
    const option = document.createElement('option');
    option.value = i;
    option.textContent = i;
    $('floorNumber').appendChild(option);
  }

  // Auto-generate name
  function generateName() {
    const floor = $('floorNumber').value;
    const sector = $('sectorName').value;

    if (floor && sector) {
      $('name').value = `${floor} / ${sector}`;
    } else {
      $('name').value = '';
    }
  }

  $('floorNumber').addEventListener('change', generateName);
  $('sectorName').addEventListener('change', generateName);

  // Load existing apartment
  try {
    const res = await fetch(`${API_BASE}/apartments/${apartmentId}`, {
      headers: { Authorization: `Bearer ${TOKEN}` }
    });

    if (!res.ok) throw new Error();

    const a = await res.json();

    $('buildingName').value = a.buildingName ?? '';
    $('floorNumber').value = a.floorNumber ?? '';
    $('sectorName').value = a.sectorName ?? '';
    $('totalRoom').value = a.totalRoom ?? '';
    $('totalBedroom').value = a.totalBedroom ?? '';
    $('totalBathroom').value = a.totalBathroom ?? '';
    $('totalBalconies').value = a.totalBalconies ?? '';
    $('areaSqFt').value = a.areaSqFt ?? '';
    $('parkingSpotNumber').value = a.parkingSpotNumber ?? '';
    $('landphoneExtension').value = a.landphoneExtension ?? '';
    $('notes').value = a.notes ?? '';

    generateName();

  } catch (err) {
    console.error('Failed to load apartment');
    window.location.href = './apartments.html';
  }

  // Submit
  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
      name: $('name').value,
      floorNumber: $('floorNumber').value
        ? Number($('floorNumber').value)
        : null,
      sectorName: $('sectorName').value || null,
      totalRoom: $('totalRoom').value
        ? Number($('totalRoom').value)
        : null,
      totalBedroom: $('totalBedroom').value
        ? Number($('totalBedroom').value)
        : null,
      totalBathroom: $('totalBathroom').value
        ? Number($('totalBathroom').value)
        : null,
      totalBalconies: $('totalBalconies').value
        ? Number($('totalBalconies').value)
        : null,
      areaSqFt: $('areaSqFt').value
        ? Number($('areaSqFt').value)
        : null,
      parkingSpotNumber: $('parkingSpotNumber').value
        ? Number($('parkingSpotNumber').value)
        : null,
      landphoneExtension: $('landphoneExtension').value.trim() || null,
      notes: $('notes').value.trim() || null
    };

    try {
      const res = await fetch(`${API_BASE}/apartments/${apartmentId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${TOKEN}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const err = await res.json();
        throw new Error(err.message || 'Update failed');
      }

      window.location.href = './apartments.html';

    } catch (err) {
      alert(err.message);
    }
  });

});