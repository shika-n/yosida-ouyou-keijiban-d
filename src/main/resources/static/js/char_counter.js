const charCounterElement = document.getElementById("charCounter");
const textAreaElement = document.getElementById("contentTextArea");

charCounterElement.textContent = textAreaElement.textLength + "/256";
textAreaElement.addEventListener("input", (_) => {
	charCounterElement.textContent = textAreaElement.textLength + "/256";
});
