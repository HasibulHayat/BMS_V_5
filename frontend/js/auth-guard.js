document.addEventListener('DOMContentLoaded', () => {
  const token = localStorage.getItem('accessToken');
  const expiration = localStorage.getItem('tokenExpiration');

  // No token → redirect
  if (!token) {
    window.location.replace('/auth/login.html');
    return;
  }

  // Expired token → clear and redirect
  if (expiration && Date.now() > parseInt(expiration)) {
    localStorage.clear();
    window.location.replace('/auth/login.html');
  }
});