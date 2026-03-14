document.addEventListener('DOMContentLoaded', async () => {

  const API_BASE = 'http://localhost:8080/api/v1/superadmin/admins';
  const TOKEN = localStorage.getItem('accessToken');

  const params = new URLSearchParams(window.location.search);
  const adminId = params.get('id');

  if (!adminId) return;

  try {
    const res = await fetch(`${API_BASE}/${adminId}`, {
      headers: {
        Authorization: `Bearer ${TOKEN}`
      }
    });

    if (!res.ok) throw new Error();

    const admin = await res.json();

    renderHero(admin);
    renderContact(admin);
    renderAccount(admin);

  } catch (err) {
    console.error('Failed to load admin');
    window.location.href = './admins.html';
  }

});


function value(v) {
  return v !== null && v !== undefined && v !== '' ? v : '-';
}


function renderHero(admin) {

  document.getElementById('adminHero').innerHTML = `
    <div class="hero-building">

      <h3 class="fw-bold mb-1">
        ${value(admin.firstName)} ${value(admin.lastName)}
      </h3>

      <small>${value(admin.email)}</small>

      <hr class="border-light">

      <div class="row mt-3">
        <div class="col-md-6">
          <strong>Status:</strong>
          <span class="badge ${admin.isActive ? 'bg-success' : 'bg-secondary'} ms-2">
            ${admin.isActive ? 'Active' : 'Inactive'}
          </span>
        </div>

        <div class="col-md-6">
          <strong>User ID:</strong>
          ${value(admin.id)}
        </div>
      </div>

    </div>
  `;
}


function renderContact(admin) {

  document.getElementById('contactInfo').innerHTML = `
    <div class="row">

      <div class="col-md-6 mb-3">
        <strong>Phone</strong><br>
        ${value(admin.phone)}
      </div>

      <div class="col-md-6 mb-3">
        <strong>Email</strong><br>
        ${value(admin.email)}
      </div>

    </div>
  `;
}


function renderAccount(admin) {

  document.getElementById('accountInfo').innerHTML = `
    <div class="row">

      <div class="col-md-6 mb-3">
        <strong>Account Active</strong><br>
        ${admin.isActive ? 'Yes' : 'No'}
      </div>

      <div class="col-md-6 mb-3">
        <strong>Role</strong><br>
        ADMIN
      </div>

    </div>
  `;
}