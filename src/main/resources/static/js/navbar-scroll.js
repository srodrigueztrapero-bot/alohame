(function () {
  function applyScrollState() {
    var navbars = document.querySelectorAll('.navbar.bg-dark');
    if (!navbars.length) {
      return;
    }

    var isScrolled = window.scrollY > 12;
    navbars.forEach(function (navbar) {
      navbar.classList.toggle('navbar-scrolled', isScrolled);
    });
  }

  applyScrollState();
  window.addEventListener('scroll', applyScrollState, { passive: true });
})();

