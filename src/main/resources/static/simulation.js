const svg = d3.select("svg");
const width = +svg.attr("width");
const height = +svg.attr("height");

const numBalls = 1000;
const numBins = 20;
const binWidth = width / numBins;
const balls = [];

const bins = Array.from({ length: numBins }, () => 0);

function dropBall() {
    let position = numBins / 2;
    for (let i = 0; i < numBins; i++) {
        if (Math.random() < 0.5) {
            position -= 0.5;
        } else {
            position += 0.5;
        }
    }
    const binIndex = Math.floor(position);
    bins[binIndex]++;
    balls.push({ x: binIndex * binWidth + binWidth / 2, y: height - bins[binIndex] * 5 });
}

function update() {
    const circles = svg.selectAll("circle").data(balls);

    circles.enter()
        .append("circle")
        .attr("class", "ball")
        .attr("cx", d => d.x)
        .attr("cy", 0)
        .attr("r", 5)
        .transition()
        .duration(1000)
        .attr("cy", d => d.y);

    circles.exit().remove();
}

document.addEventListener("DOMContentLoaded", function() {
    const container = document.createElement("div");
    container.id = "chartContainer";
    container.style.width = "80%";
    container.style.height = "600px";
    container.style.margin = "0 auto";
    container.style.textAlign = "center";

    const button = document.createElement("button");
    button.innerText = "Reiniciar Simulación";
    button.style.display = "block";
    button.style.margin = "20px auto";
    button.addEventListener("click", function() {
        balls.length = 0;
        bins.fill(0);
        svg.selectAll("circle").remove();
        simulate();
    });

    document.body.appendChild(button);
    document.body.appendChild(container);
    container.appendChild(svg.node());

    simulate();
});

function simulate() {
    for (let i = 0; i < numBalls; i++) {
        setTimeout(() => {
            dropBall();
            update();
        }, i * 10);
    }
}