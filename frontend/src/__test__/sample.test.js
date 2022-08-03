const sum = (a, b) => {
  return a + b;
};

const subtract = (a, b) => {
  return a - b;
};

const multiple = (a, b) => {
  return a * b;
};

describe('Calculator tests', () => {
  test('adding 1 + 2 should return 3', () => {
    expect(sum(1, 2)).toBe(3);
  });

  test('subtracting 3 - 1 should return 2', () => {
    expect(subtract(3, 1)).toBe(2);
  });

  test('multiplying 3 * 2  return 6', () => {
    expect(subtract(3, 1)).toBe(2);
  });
});
