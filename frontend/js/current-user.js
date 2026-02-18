const API_BASE = "http://localhost:8080/api/v1";

export async function loadCurrentUser() {
  const token = localStorage.getItem("accessToken");
  if (!token) {
    window.location.href = "/login.html";
    return;
  }

  const res = await fetch(`${API_BASE}/users/me`, {
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });

  if (res.status === 401) {
    localStorage.clear();
    window.location.href = "/login.html";
    return;
  }

  return await res.json();
}
