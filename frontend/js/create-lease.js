document.addEventListener("DOMContentLoaded", () => {
  const residentType = document.getElementById("residentType");

  const existingUserSection = document.getElementById("existingUserSection");
  const newUserSection = document.getElementById("newUserSection");
  const ownerSection = document.getElementById("ownerSection");

  if (!residentType) return;

  residentType.addEventListener("change", () => {
    // hide all
    existingUserSection.classList.add("d-none");
    newUserSection.classList.add("d-none");
    ownerSection.classList.add("d-none");

    // show selected
    switch (residentType.value) {
      case "EXISTING_USER":
        existingUserSection.classList.remove("d-none");
        break;

      case "NEW_USER":
        newUserSection.classList.remove("d-none");
        break;

      case "APARTMENT_OWNER":
        ownerSection.classList.remove("d-none");
        break;
    }
  });
});
