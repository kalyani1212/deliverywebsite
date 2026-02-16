// src/main/resources/static/js/app.js
document.addEventListener("DOMContentLoaded", () => {
  // Demo OTP quick-fill (for dev)
  const demoBtn = document.getElementById("demo-otp");
  if (demoBtn) {
    demoBtn.addEventListener("click", () => {
      alert("Demo OTP is printed to server console; open server terminal to read it and paste in Verify OTP screen.");
    });
  }

  console.log("QuickDeliver frontend loaded.");
});
