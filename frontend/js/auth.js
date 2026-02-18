// auth.js

const API_BASE_URL = "http://localhost:8080/api/v1";

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
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
      });

      const data = await response.json();

      if (!response.ok) {
        alert(data.message || "Login failed");
        return;
      }

      // ✅ Store JWT
      localStorage.setItem("accessToken", data.accessToken);

      // Optional: store expiration if you want later
      localStorage.setItem("tokenExpiration", data.expiration);

      // ✅ Redirect to dashboard
      window.location.href = "../home.html";

    } catch (error) {
      console.error(error);
      alert("Server not reachable. Please try again.");
    }
  });
});

// auth.js (add at bottom)

function logout() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("tokenExpiration");
  window.location.href = "/login.html";
}

// expose globally
window.logout = logout;
