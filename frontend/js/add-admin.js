document.addEventListener('DOMContentLoaded', () => {

  const API_BASE = 'http://localhost:8080/api/v1/superadmin/admins';
  const TOKEN = localStorage.getItem('accessToken');

  const form = document.getElementById('addAdminForm');

  if (!TOKEN) {
    window.location.href = '../login.html';
    return;
  }

  if (!form) return;

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
      firstName: firstName.value.trim(),
      lastName: lastName.value.trim(),
      username: username.value.trim(),
      email: email.value.trim(),
      phone: phone.value.trim(),
      gender: gender.value,
      password: password.value
    };

    try {
      const res = await fetch(API_BASE, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${TOKEN}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) throw new Error();

      window.location.href = './admins.html';

    } catch (err) {
      alert('Failed to create admin');
      console.error(err);
    }
  });
});
