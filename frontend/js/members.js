document.addEventListener('DOMContentLoaded', () => {

  console.log('members.js loaded');

  const API_BASE = 'http://localhost:8080/api/v1/admin';
  const TOKEN = localStorage.getItem('accessToken');

  const tableBody = document.getElementById('memberTableBody');
  const addBtn = document.getElementById('addMemberBtn');

  if (!TOKEN) {
    window.location.href = '../login.html';
    return;
  }

  if (addBtn) {
    addBtn.addEventListener('click', () => {
      window.location.href = '../pages/add-member.html';
    });
  }

  async function loadMembers() {
    try {
      const res = await fetch(`${API_BASE}/members`, {
        headers: {
          'Authorization': `Bearer ${TOKEN}`
        }
      });

      if (!res.ok) return;

      const members = await res.json();
      tableBody.innerHTML = '';

      members.forEach(renderRow);

    } catch (err) {
      console.error('Failed to load members:', err);
    }
  }

  function renderRow(member) {
    const tr = document.createElement('tr');

    tr.innerHTML = `
      <td>${member.firstName} ${member.lastName}</td>
      <td>${member.phone ?? '-'}</td>
      <td>${member.email}</td>
      <td>${member.role ?? 'MEMBER'}</td>
      <td>
        <span class="badge ${member.isActive ? 'bg-success' : 'bg-secondary'}">
          ${member.isActive ? 'Active' : 'Inactive'}
        </span>
      </td>
      <td class="text-end">
        <a href="./edit-member.html?id=${member.id}"
           class="btn btn-sm btn-outline-primary">
          Edit
        </a>
      </td>
    `;

    tableBody.appendChild(tr);
  }

  loadMembers();
});
