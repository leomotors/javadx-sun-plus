// @ts-check

import fs from "node:fs/promises";

const target = process.argv[2];

const content = await fs.readFile(target, "utf8");
const lines = content.split("\n");

const xToLane = {
  64: 0,
  192: 1,
  320: 2,
  448: 3,
};

const laneToLane = ["1 3", "4 6", "7 9", "10 12"];

let hitObjectSection = false;
let chart = "";

for (const line of lines) {
  if (line.includes("[HitObjects]")) {
    hitObjectSection = true;
    continue;
  }

  if (!hitObjectSection) continue;

  if (!line.trim()) continue;

  const tokens = line.split(",");

  const lane = xToLane[+tokens[0]];
  const type = +tokens[3];

  chart += `TAP ${tokens[2]} ${laneToLane[lane]} 0\n`;

  if (type % 2 == 0) {
    // HOLD
    const endTime = +tokens[5].split(":")[0];
    chart += `HOLD ${tokens[2]} ${laneToLane[lane]} ${endTime}\n`;
  }
}

await fs.writeFile("output.txt", chart);
