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


document.addEventListener('DOMContentLoaded', function() {
    fetch('/simulate')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('ball-container');
            data.forEach(estudiante => {
                const ball = document.createElement('div');
                ball.className = 'ball';
                ball.style.left = `${Math.random() * 100}%`;
                ball.style.top = `${Math.random() * 100}%`;
                ball.textContent = `ID: ${estudiante.id}`;
                container.appendChild(ball);
            });
        })
        .catch(error => console.error('Error fetching data:', error));
});

function simulate() {
    for (let i = 0; i < numBalls; i++) {
        setTimeout(() => {
            dropBall();
            update();
        }, i * 10);
    }
}