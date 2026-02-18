document.addEventListener('DOMContentLoaded', () => {

  const API_URL = 'http://localhost:8080/api/v1/admin/staff';
  const TOKEN = localStorage.getItem('accessToken');
  const form = document.getElementById('addStaffForm');

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
      const res = await fetch(API_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${TOKEN}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) throw new Error();

      window.location.href = './staff.html';

    } catch (err) {
      console.error('Failed to create staff:', err);
    }
  });

});
