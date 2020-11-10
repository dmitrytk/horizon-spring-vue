/**
 * round number to decimal places
 * @param {number} value
 * @param decimalPlaces
 * @return {number}
 */
const round = (value, decimalPlaces = 0) => {
  let result = value * (10 ** decimalPlaces);
  result = Math.round(result);
  return result / (10 ** decimalPlaces);
};

export default round;
