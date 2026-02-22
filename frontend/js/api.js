// api.js

const API_BASE_URL = "http://localhost:8080/api/v1";

/**
 * Internal request handler
 */
async function request(method, url, body = null) {
  const token = localStorage.getItem("accessToken");

  const headers = {
    "Content-Type": "application/json",
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const options = {
    method,
    headers,
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  let response;
  try {
    response = await fetch(`${API_BASE_URL}${url}`, options);
  } catch (err) {
    throw {
      status: 0,
      message: "Server not reachable",
    };
  }

  let data = null;
  try {
    data = await response.json();
  } catch {
    // no body
  }

  // 🔒 Unauthorized → force logout
  if (response.status === 401) {
    localStorage.clear();
    window.location.replace('/auth/login.html');
    return;
  }

  // ❌ Other errors
  if (!response.ok) {
    throw {
      status: response.status,
      message: data?.message || "Request failed",
      errors: data?.errors || null,
    };
  }

  return data;
}

/**
 * Public API helpers
 */
const api = {
  get: (url) => request("GET", url),
  post: (url, body) => request("POST", url, body),
  put: (url, body) => request("PUT", url, body),
  delete: (url) => request("DELETE", url),
};

export default api;
