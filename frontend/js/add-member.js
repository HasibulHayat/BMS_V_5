document.addEventListener('DOMContentLoaded', () => {

  console.log('add-member.js loaded');

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const form = document.getElementById('addMemberForm');
  const buildingSelect = document.getElementById('buildingId');

  if (!TOKEN) {
    window.location.href = '../login.html';
    return;
  }

  if (!form || !buildingSelect) return;

  async function loadBuildings() {
    try {
      const res = await fetch(`${API_BASE}/buildings`, {
        headers: {
          'Authorization': `Bearer ${TOKEN}`
        }
      });

      if (!res.ok) return;

      const buildings = await res.json();

      buildings.forEach(b => {
        const opt = document.createElement('option');
        opt.value = b.id;
        opt.textContent = `${b.name} (${b.buildingCode})`;
        buildingSelect.appendChild(opt);
      });

    } catch (err) {
      console.error('Failed to load buildings:', err);
    }
  }

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
      buildingId: buildingSelect.value,
      firstName: firstName.value.trim(),
      lastName: lastName.value.trim(),
      username: username.value.trim(),
      email: email.value.trim(),
      phone: phone.value.trim(),
      gender: gender.value,
      password: password.value
    };

    try {
      const res = await fetch(`${API_BASE}/members`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${TOKEN}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) throw new Error();

      window.location.href = './members.html';

    } catch (err) {
      console.error('Failed to create member:', err);
    }
  });

  loadBuildings();
});
