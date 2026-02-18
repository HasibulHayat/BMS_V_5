document.addEventListener('DOMContentLoaded', () => {

  const API_BASE = 'http://localhost:8080/api/v1/superadmin/admins';
  const TOKEN = localStorage.getItem('accessToken');

  const tableBody = document.getElementById('adminTableBody');
  const addBtn = document.getElementById('addAdminBtn');

  if (!TOKEN) {
    window.location.href = '../login.html';
    return;
  }

  addBtn?.addEventListener('click', () => {
    window.location.href = '../pages/add-admin.html';
  });

  async function loadAdmins() {
    try {
      const res = await fetch(API_BASE, {
        headers: {
          'Authorization': `Bearer ${TOKEN}`
        }
      });

      if (!res.ok) throw new Error();

      const admins = await res.json();
      tableBody.innerHTML = '';

      admins.forEach(admin => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td>${admin.firstName} ${admin.lastName}</td>
          <td>${admin.phone ?? '-'}</td>
          <td>${admin.email}</td>
          <td>
            <span class="badge ${admin.isActive ? 'bg-success' : 'bg-secondary'}">
              ${admin.isActive ? 'Active' : 'Inactive'}
            </span>
          </td>
          <td class="text-end">
            <button class="btn btn-sm btn-outline-secondary" disabled>
              Edit
            </button>
          </td>
        `;
        tableBody.appendChild(tr);
      });

    } catch (err) {
      console.error('Failed to load admins', err);
    }
  }

  loadAdmins();
});
