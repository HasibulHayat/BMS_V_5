document.addEventListener('DOMContentLoaded', () => {

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const tableBody = document.getElementById('ownerTableBody');
  const addBtn = document.getElementById('addOwnerBtn');

  // -----------------------------
  // Add Owner button
  // -----------------------------
  if (addBtn) {
    addBtn.addEventListener('click', () => {
      window.location.href = './add-owner.html';
    });
  }

  // -----------------------------
  // Load owners
  // -----------------------------
  async function loadOwners() {
    try {
      const res = await fetch(`${API_BASE}/owners`, {
        headers: {
          'Authorization': `Bearer ${TOKEN}`
        }
      });

      if (!res.ok) {
        throw new Error('Failed to load owners');
      }

      const owners = await res.json();
      tableBody.innerHTML = '';

      owners.forEach(renderOwnerRow);

    } catch (err) {
      console.error('Failed to load owners:', err);
      // silent for development
    }
  }

  // -----------------------------
  // Render row
  // -----------------------------
  function renderOwnerRow(owner) {
    const tr = document.createElement('tr');

    const fullName = `${owner.firstName} ${owner.lastName}`;

    tr.innerHTML = `
      <td>${fullName}</td>
      <td>${owner.phonePrimary}</td>
      <td>${owner.email}</td>
      <td>
        ${
          owner.isActive
            ? '<span class="badge bg-success">Active</span>'
            : '<span class="badge bg-secondary">Inactive</span>'
        }
      </td>
      <td class="text-end">
        <a
          href="./edit-owner.html?id=${owner.id}"
          class="btn btn-sm btn-outline-primary"
        >
          Edit
        </a>
      </td>
    `;

    tableBody.appendChild(tr);
  }

  // -----------------------------
  // Init
  // -----------------------------
  loadOwners();

});
