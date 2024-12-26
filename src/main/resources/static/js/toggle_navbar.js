const navbar = document.getElementById("navbar");

function toggleNavbar() {
	if (navbar.classList.contains("h-14")) {
		navbar.classList.remove("h-14");
		navbar.classList.add("h-40");
	} else {
		navbar.classList.add("h-14");
		navbar.classList.remove("h-40");
	}
}
