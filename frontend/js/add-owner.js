document.addEventListener('DOMContentLoaded', () => {

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const form = document.getElementById('addOwnerForm');
  const buildingSelect = document.getElementById('buildingId');
  const apartmentSelect = document.getElementById('apartmentName');

  if (!form) return;

  // ----------------------------------
  // Load buildings
  // ----------------------------------
  async function loadBuildings() {
    const res = await fetch(`${API_BASE}/buildings`, {
      headers: { Authorization: `Bearer ${TOKEN}` }
    });

    const buildings = await res.json();

    buildings.forEach(b => {
      const opt = document.createElement('option');
      opt.value = b.id;
      opt.textContent = b.name;
      buildingSelect.appendChild(opt);
    });
  }

  // ----------------------------------
  // Load apartments when building changes
  // ----------------------------------
  buildingSelect.addEventListener('change', async () => {
    apartmentSelect.innerHTML = '<option value="">Select apartment</option>';

    const buildingId = buildingSelect.value;
    if (!buildingId) return;

    const res = await fetch(
      `${API_BASE}/apartments/building/${buildingId}`,
      { headers: { Authorization: `Bearer ${TOKEN}` } }
    );

    const apartments = await res.json();

    apartments.forEach(a => {
      const opt = document.createElement('option');
      opt.value = a.id;
      opt.textContent = `${a.name} (Floor ${a.floorNumber ?? '-'})`;
      apartmentSelect.appendChild(opt);
    });
  });

  // ----------------------------------
  // Submit form
  // ----------------------------------
  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    // STEP 1: Create owner user
    const ownerPayload = {
      firstName: document.getElementById('firstName').value.trim(),
      lastName: document.getElementById('lastName').value.trim(),
      username: document.getElementById('username').value.trim(),
      email: document.getElementById('email').value.trim(),
      phone: document.getElementById('phone').value.trim(),
      gender: document.getElementById('gender').value,
      password: document.getElementById('password').value
    };

    let ownerProfileId;

    try {
      const ownerRes = await fetch(`${API_BASE}/owners`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${TOKEN}`
        },
        body: JSON.stringify(ownerPayload)
      });

      if (!ownerRes.ok) {
        const err = await ownerRes.json();
        throw new Error(err.message || 'Owner creation failed');
      }

      const owner = await ownerRes.json();
      ownerProfileId = owner.profileId ?? owner.id; // depends on response design

    } catch (err) {
      alert(err.message);
      return;
    }

    // STEP 2: Assign ownership
    const ownershipPayload = {
      apartmentId: apartmentSelect.value,
      ownerProfileId,
      ownershipPercentage: Number(
        document.getElementById('ownershipPercantage').value
      ),
      ownershipStartDate: new Date().toISOString().split('T')[0],
      notes: document.getElementById('notes').value.trim() || null
    };

    try {
      const res = await fetch(`${API_BASE}/apartment-ownerships`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${TOKEN}`
        },
        body: JSON.stringify(ownershipPayload)
      });

      if (!res.ok) {
        const err = await res.json();
        throw new Error(err.message || 'Ownership assignment failed');
      }

      window.location.href = './owners.html';

    } catch (err) {
      alert(err.message);
    }
  });

  loadBuildings();
});
