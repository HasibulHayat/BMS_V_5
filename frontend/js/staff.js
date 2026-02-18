document.addEventListener('DOMContentLoaded', () => {

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const tableBody = document.getElementById('staffTableBody');
  const addBtn = document.getElementById('addStaffBtn');

  if (addBtn) {
    addBtn.addEventListener('click', () => {
      window.location.href = './add-staff.html';
    });
  }

  async function loadStaff() {
    try {
      const res = await fetch(`${API_BASE}/staff`, {
        headers: {
          'Authorization': `Bearer ${TOKEN}`
        }
      });

      if (!res.ok) return;

      const staffList = await res.json();
      tableBody.innerHTML = '';

      staffList.forEach(renderRow);

    } catch (err) {
      console.error('Failed to load staff:', err);
    }
  }

  function renderRow(staff) {
    const tr = document.createElement('tr');

    tr.innerHTML = `
      <td>${staff.firstName} ${staff.lastName}</td>
      <td>${staff.phonePrimary}</td>
      <td>${staff.email}</td>
      <td>
        ${
          staff.isActive
            ? '<span class="badge bg-success">Active</span>'
            : '<span class="badge bg-secondary">Inactive</span>'
        }
      </td>
      <td class="text-end">
        <a
          href="./edit-staff.html?id=${staff.id}"
          class="btn btn-sm btn-outline-primary"
        >
          Edit
        </a>
      </td>
    `;

    tableBody.appendChild(tr);
  }

  loadStaff();
});
