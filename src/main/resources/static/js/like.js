function like(postId) {
	const data = new FormData();
	data.append("postId", postId);

	const csrf_token = document.head.querySelector("meta[name='_csrf']").content;
	const csrf_header = document.head.querySelector("meta[name='_csrf_header']").content;

	fetch("/like?postId=" + postId, {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
			[[csrf_header]]: csrf_token,
		},
		credentials: "same-origin",
		
	})
	.then((res) => res.text())
	.then((resText) => {
		const likeSvg = document.getElementById("likeSvg_" + postId);
		const likeCounter = document.getElementById("likeCounter_" + postId);
		console.log(resText, resText == "Liked", resText === "Liked");
		if (resText === "Liked") {
			likeSvg.classList.add("fill-amber-500/100", "stroke-amber-300");
			likeSvg.classList.remove("stroke-white", "hover:stroke-purple-600");
			likeCounter.textContent = Number(likeCounter.textContent) + 1;
		} else {
			likeSvg.classList.remove("fill-amber-500/100", "stroke-amber-300");
			likeSvg.classList.add("stroke-white", "hover:stroke-purple-600");
			likeCounter.textContent = Number(likeCounter.textContent) - 1;
		}
	});
}
