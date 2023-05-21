const S = (x) => (x == 2 ? 1 : x);
const E = (x) => (x == 11 ? 12 : x);
for (let i = 1; i < 30; i++) {
  for (let j = 0; j < 10; j++) {
    console.log(`FLICK ${81 + 1000 * i + 100 * j} ${S(j + 2)} ${E(j + 2)} 0`);
  }
}
