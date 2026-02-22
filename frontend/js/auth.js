// auth.js

const API_BASE_URL = "http://localhost:8080/api/v1";
const LOGIN_PAGE = "/auth/login.html";
const HOME_PAGE = "/home.html";

// =======================
// LOGIN
// =======================
document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");
  if (!form) return;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.querySelector("input[name='email']").value.trim();
    const password = document.querySelector("input[name='password']").value.trim();

    if (!email || !password) {
      alert("Email and password are required");
      return;
    }

    try {
      const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
      });

      const data = await response.json();

      if (!response.ok) {
        alert(data.message || "Login failed");
        return;
      }

      // Store token
      localStorage.setItem("accessToken", data.accessToken);

      // Store REAL expiration timestamp
      const expirationTimestamp = Date.now() + data.expiration;
      localStorage.setItem("tokenExpiration", expirationTimestamp);

      window.location.replace(HOME_PAGE);

    } catch (error) {
      console.error(error);
      alert("Server not reachable. Please try again.");
    }
  });
});

// =======================
// LOGOUT (SINGLE SOURCE)
// =======================
function logout() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("tokenExpiration");
  localStorage.removeItem("currentUser");

  window.location.replace(LOGIN_PAGE);
}

window.logout = logout;