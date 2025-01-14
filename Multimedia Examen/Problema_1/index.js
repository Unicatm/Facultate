const canvas = document.getElementById("cnv");
const svg = document.getElementById("svg");
const audio = document.getElementById("audio");

const ctx = canvas.getContext("2d");

const rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
rect.setAttributeNS(null, "x", 100);
rect.setAttributeNS(null, "y", 100);
rect.setAttributeNS(null, "width", 300);
rect.setAttributeNS(null, "height", 200);
rect.style.fill = "blue";
svg.appendChild(rect);

rect.onclick = () => {
  audio.play();
};

ctx.rotate((Math.PI * 45) / 180);
ctx.fillText("Text", 50, 50);
ctx.stoke();
