document.addEventListener('DOMContentLoaded', async () => {

  const token = localStorage.getItem('accessToken');
  if (!token) return;

  try {
    const res = await fetch('http://localhost:8080/api/v1/auth/me', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    if (!res.ok) return;

    const me = await res.json();
    const userRoles = me.roles || [];

    // Find all role-restricted elements
    document.querySelectorAll('[data-roles]').forEach(el => {
      const allowedRoles = el.dataset.roles.split(',');

      const hasAccess = allowedRoles.some(role =>
        userRoles.includes(role)
      );

      if (!hasAccess) {
        el.style.display = 'none';
      }
    });

  } catch (err) {
    console.error('Role sidebar error:', err);
  }
});
